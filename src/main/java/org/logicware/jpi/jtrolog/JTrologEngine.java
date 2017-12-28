/*
 * #%L
 * prolobjectlink-jtrolog
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.logicware.jpi.jtrolog;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.logicware.jpi.JavaEngine;
import org.logicware.jpi.Licenses;
import org.logicware.jpi.OperatorEntry;
import org.logicware.jpi.PredicateIndicator;
import org.logicware.jpi.PrologClause;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologIndicator;
import org.logicware.jpi.PrologOperator;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;

import jTrolog.engine.Prolog;
import jTrolog.errors.PrologException;
import jTrolog.lib.BuiltIn;
import jTrolog.lib.IOLibrary;
import jTrolog.lib.Library;
import jTrolog.parser.Parser;
import jTrolog.terms.Clause;
import jTrolog.terms.Int;
import jTrolog.terms.Struct;
import jTrolog.terms.StructAtom;
import jTrolog.terms.Term;

public final class JTrologEngine extends JavaEngine implements PrologEngine {

	final Prolog engine;

	protected JTrologEngine(PrologProvider provider) {
		this(provider, new Prolog());
	}

	protected JTrologEngine(PrologProvider provider, Prolog engine) {
		super(provider);
		this.engine = engine;
	}

	public void consult(String path) {
		engine.clearTheory();
		include(path);
	}

	public void persist(String path) {
		try {
			FileWriter writer = new FileWriter(path);
			writer.write(engine.getTheory());
			writer.close();
		} catch (IOException e) {
			// created but not exception is reported
		}
	}

	public void include(String path) {
		try {
			InputStream is = new FileInputStream(path);
			engine.addTheory(IOLibrary.readStream(is));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (PrologException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void abolish(String functor, int arity) {
		String pi = functor + "/" + arity;
		try {
			engine.abolish(pi);
		} catch (PrologException e) {
			e.printStackTrace();
		}
	}

	private boolean exist(Clause clause) {
		String key = clause.head.name + "/" + clause.head.arity;
		Iterator<?> i = engine.dynamicPredicateIndicators();
		while (i.hasNext()) {
			String predIndicator = (String) i.next();
			if (predIndicator.equals(key)) {
				try {
					List<?> list = engine.find(predIndicator);
					for (Object object : list) {
						if (object instanceof Clause) {
							Clause c = (Clause) object;
							if (c.head.equals(clause.head)) {

								Struct[] ctail = c.tail;
								Struct[] clausetail = clause.tail;

								if (ctail.length != clausetail.length) {
									return false;
								}

								for (int j = 0; j < clausetail.length; j++) {
									if (!ctail[j].equals(clausetail[j])) {
										return false;
									}

								}

								return true;
							}
						}
					}
				} catch (PrologException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void asserta(String stringClause) {
		try {
			Term term = new Parser(stringClause).nextTerm(false);
			asserta(BuiltIn.convertTermToClause(term));
		} catch (PrologException e) {
			e.printStackTrace();
		}
	}

	public void asserta(PrologTerm head, PrologTerm... body) {
		Struct h = fromTerm(head, Struct.class);
		Struct b[] = new Struct[body.length];
		for (int i = 0; i < body.length; i++) {
			b[i] = fromTerm(body[i], Struct.class);
		}
		Struct o = fromTerm(head, body, Struct.class);
		asserta(new Clause(b, h, o));
	}

	private void asserta(Clause clause) {
		if (!exist(clause)) {
			try {
				engine.assertA(clause);
			} catch (PrologException e) {
				e.printStackTrace();
			}
		}
	}

	public void assertz(String stringClause) {
		try {
			Term term = new Parser(stringClause).nextTerm(false);
			assertz(BuiltIn.convertTermToClause(term));
		} catch (PrologException e) {
			e.printStackTrace();
		}
	}

	public void assertz(PrologTerm head, PrologTerm... body) {
		Struct h = fromTerm(head, Struct.class);
		Struct b[] = new Struct[body.length];
		for (int i = 0; i < body.length; i++) {
			b[i] = fromTerm(body[i], Struct.class);
		}
		Struct o = fromTerm(head, body, Struct.class);
		assertz(new Clause(b, h, o));
	}

	private void assertz(Clause clause) {
		if (!exist(clause)) {
			try {
				engine.assertZ(clause);
			} catch (PrologException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean clause(String stringClause) {
		try {
			Term term = new Parser(stringClause).nextTerm(false);
			return clause(BuiltIn.convertTermToClause(term));
		} catch (PrologException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean clause(PrologTerm head, PrologTerm... body) {
		Struct h = fromTerm(head, Struct.class);
		Struct b[] = new Struct[body.length];
		for (int i = 0; i < body.length; i++) {
			b[i] = fromTerm(body[i], Struct.class);
		}
		Struct o = fromTerm(head, body, Struct.class);
		return clause(new Clause(b, h, o));
	}

	private boolean clause(Clause clause) {
		String key = clause.head.name + "/" + clause.head.arity;
		Iterator<?> i = engine.dynamicPredicateIndicators();
		while (i.hasNext()) {
			String predIndicator = (String) i.next();
			if (predIndicator.equals(key)) {
				try {
					List<?> list = engine.find(predIndicator);
					for (Object object : list) {
						if (object instanceof Clause) {
							Clause c = (Clause) object;
							if (Prolog.match(c.original, clause.original)) {
								return true;
							}
						}
					}
				} catch (PrologException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	public void retract(String stringClause) {
		try {
			Term term = new Parser(stringClause).nextTerm(false);
			retract(BuiltIn.convertTermToClause(term));
		} catch (PrologException e) {
			e.printStackTrace();
		}
	}

	public void retract(PrologTerm head, PrologTerm... body) {
		Struct h = fromTerm(head, Struct.class);
		Struct b[] = new Struct[body.length];
		for (int i = 0; i < body.length; i++) {
			b[i] = fromTerm(body[i], Struct.class);
		}
		Struct o = fromTerm(head, body, Struct.class);
		retract(new Clause(b, h, o));
	}

	private void retract(Clause clause) {
		try {
			engine.retract(clause.original);
		} catch (PrologException e) {
			e.printStackTrace();
		}
	}

	public PrologQuery query(String stringQuery) {
		return new JTrologQuery(this, stringQuery);
	}

	public PrologQuery query(PrologTerm... terms) {
		return new JTrologQuery(this, terms);
	}

	public void operator(int priority, String specifier, String operator) {
		engine.opNew(operator, specifier, priority);
	}

	public boolean currentPredicate(String functor, int arity) {
		String key = functor.matches(JTrologTerm.SIMPLE_ATOM_REGEX) ?

				functor + "/" + arity

				: "'" + functor + "'/" + arity;

		// supported built-ins
		boolean isBuiltin = engine.hasPrimitive(key) || engine.hasPrimitiveExp(key);

		// user defined predicates
		if (!isBuiltin) {
			try {
				if (!engine.find(key).isEmpty()) {
					return true;
				}
			} catch (PrologException e) {
				e.printStackTrace();
			}
		}

		// not defined
		return isBuiltin;
	}

	public boolean currentOperator(int priority, String specifier, String operator) {
		return currentOperators().contains(new OperatorEntry(priority, specifier, operator));
	}

	public Set<PrologIndicator> currentPredicates() {

		// built-ins on libraries
		Iterator<?> libraries = engine.getCurrentLibraries();
		Set<PrologIndicator> builtins = new HashSet<PrologIndicator>();
		while (libraries.hasNext()) {
			Object object = libraries.next();
			if (object instanceof Library) {
				Library library = (Library) object;
				String theory = library.getTheory();
				// System.out.println(theory);
				// Parser parser = new Parser(theory);
				// Term term = parser.nextTerm(true);
				// while (term != null) {
				// if (term instanceof Struct) {
				// Struct struct = (Struct) term;
				// System.out.println(struct.name + "/" + struct.arity);
				// }
				// term = parser.nextTerm(false);
				// }
			}
		}

		// user defined predicates
		Iterator<?> i = engine.dynamicPredicateIndicators();
		while (i.hasNext()) {
			String predIndicator = (String) i.next();
			try {
				List<?> list = engine.find(predIndicator);
				for (int j = 0; j < list.size(); j++) {
					Object object = list.get(j);
					if (object instanceof Clause) {
						Clause clause = (Clause) object;
						String functor = clause.head.name;
						int arity = clause.head.arity;
						PredicateIndicator p = new PredicateIndicator(functor, arity);
						builtins.add(p);
					}
				}
			} catch (PrologException e) {
				e.printStackTrace();
			}
		}
		return builtins;
	}

	public Set<PrologOperator> currentOperators() {
		Set<PrologOperator> operators = new HashSet<PrologOperator>();
		Iterator<?> i = engine.getCurrentOperators();
		while (i.hasNext()) {
			Object object = i.next();
			if (object instanceof Struct) {
				Struct o = (Struct) object;
				String name = ((StructAtom) o.getArg(2)).name;
				int priority = ((Int) o.getArg(0)).intValue();
				String specifier = ((StructAtom) o.getArg(1)).name;
				OperatorEntry op = new OperatorEntry(priority, specifier, name);
				operators.add(op);
			}
		}
		return operators;
	}

	public Iterator<PrologClause> getProgramIterator() {
		Collection<PrologClause> cls = new LinkedList<PrologClause>();
		Parser parser = new Parser(engine.getTheory());
		for (Iterator<?> iterator = parser.iterator(); iterator.hasNext();) {
			Term term = (Term) iterator.next();
			if (term instanceof Struct) {
				Struct struct = (Struct) term;
				if (struct.name.equals(":-") && struct.arity == 2) {
					PrologTerm head = toTerm(struct.getArg(0), PrologTerm.class);
					PrologTerm body = toTerm(struct.getArg(1), PrologTerm.class);
					cls.add(new JTrologClause(head, body, false, false, false));
				} else {
					PrologTerm head = toTerm(struct, PrologTerm.class);
					cls.add(new JTrologClause(head, false, false, false));
				}
			}
		}
		return new PrologProgramIterator(cls);
	}

	public int getProgramSize() {
		int counter = 0;
		Iterator<?> i = engine.dynamicPredicateIndicators();
		while (i.hasNext()) {
			String predIndicator = (String) i.next();
			try {
				List<?> list = engine.find(predIndicator);
				counter += list.size();
			} catch (PrologException e) {
				e.printStackTrace();
			}
		}
		return counter;
	}

	public String getLicense() {
		return Licenses.NO_SPECIFIED;
	}

	public String getVersion() {
		return Prolog.VERSION;
	}

	public String getName() {
		return "jTrolog";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((engine == null) ? 0 : engine.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JTrologEngine other = (JTrologEngine) obj;
		if (engine == null) {
			if (other.engine != null)
				return false;
		} else if (!engine.equals(other.engine))
			return false;
		return true;
	}

	public void dispose() {
		if (engine != null) {
			engine.clearTheory();
		}
	}

}
