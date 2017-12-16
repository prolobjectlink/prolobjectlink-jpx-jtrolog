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

import java.util.ArrayList;
import java.util.Iterator;

import org.logicware.jpi.AbstractConverter;
import org.logicware.jpi.PrologAtom;
import org.logicware.jpi.PrologConverter;
import org.logicware.jpi.PrologDouble;
import org.logicware.jpi.PrologExpression;
import org.logicware.jpi.PrologFloat;
import org.logicware.jpi.PrologInteger;
import org.logicware.jpi.PrologLong;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologStructure;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;
import org.logicware.jpi.UnknownTermError;

import jTrolog.terms.Double;
import jTrolog.terms.Float;
import jTrolog.terms.Int;
import jTrolog.terms.Long;
import jTrolog.terms.Struct;
import jTrolog.terms.StructAtom;
import jTrolog.terms.Term;
import jTrolog.terms.Var;

public class JTrologConverter extends AbstractConverter<Term> implements PrologConverter<Term> {

    protected static final JTrologOperatorSet OPERATORS = new JTrologOperatorSet();

    @Override
    public PrologTerm toTerm(Term prologTerm) {
	if (prologTerm.equals(Term.TRUE)) {
	    return new JTrologTrue(provider);
	} else if (prologTerm.equals(Term.FALSE)) {
	    return new JTrologFalse(provider);
	}

	// long extend integer and double extend float
	// be careful with instance check order

	else if (prologTerm instanceof Double) { // double always before float
	    return new JTrologDouble(provider, ((Double) prologTerm).doubleValue());
	} else if (prologTerm instanceof Float) { // float always after double
	    return new JTrologFloat(provider, ((Float) prologTerm).floatValue());
	} else if (prologTerm instanceof Long) { // long always before integer
	    return new JTrologLong(provider, ((Long) prologTerm).longValue());
	} else if (prologTerm instanceof Int) { // integer always after long
	    return new JTrologInteger(provider, ((Int) prologTerm).intValue());
	} else if (prologTerm instanceof Var) {
	    Var var = (Var) prologTerm;
	    String name = var.toString();
	    PrologVariable v = sharedVariables.get(name);
	    if (v == null) {
		v = new JTrologVariable(provider, name, var.nrInStruct);
		sharedVariables.put(v.toString(), v);
	    }
	    return v;
	} else if (prologTerm instanceof Struct) {

	    Struct struct = (Struct) prologTerm;
	    int arity = struct.arity;
	    String functor = struct.name;
	    Term[] arguments = new Term[arity];

	    if (struct == Term.emptyList) {
		return new JTrologEmpty(provider);
	    }

	    // atom and constants
	    else if (prologTerm instanceof StructAtom) {
		if (functor.equals("nil")) {
		    return new JTrologNil(provider);
		} else if (functor.equals("!")) {
		    return new JTrologCut(provider);
		} else if (functor.equals("fail")) {
		    return new JTrologFail(provider);
		} else {
		    return new JTrologAtom(provider, functor);
		}
	    }

	    // list
	    else if (struct.name.equals(".") && struct.arity == 2) {
		ArrayList<Term> args = new ArrayList<Term>();
		Iterator<?> i = Struct.iterator(struct);
		while (i.hasNext()) {
		    Term term = (Term) i.next();
		    args.add(term);
		}
		return new JTrologList(provider, args.toArray(arguments));
	    }

	    // expression
	    else if (arity == 2 && OPERATORS.currentOp(functor)) {
		Term left = struct.getArg(0);
		Term right = struct.getArg(1);
		return new JTrologExpression(provider, left, functor, right);
	    }

	    // structure
	    else {
		for (int i = 0; i < arity; i++) {
		    arguments[i] = struct.getArg(i);
		}
		return new JTrologStructure(provider, functor, arguments);
	    }

	} else {
	    throw new UnknownTermError(prologTerm);
	}
    }

    @Override
    public Term fromTerm(PrologTerm term) {
	switch (term.getType()) {
	case PrologTerm.NIL_TYPE:
	    return new StructAtom("nil");
	case PrologTerm.CUT_TYPE:
	    return new StructAtom("!");
	case PrologTerm.FAIL_TYPE:
	    return new StructAtom("fail");
	case PrologTerm.TRUE_TYPE:
	    return Term.TRUE;
	case PrologTerm.FALSE_TYPE:
	    return Term.FALSE;
	case PrologTerm.EMPTY_TYPE:
	    return Term.emptyList;
	case PrologTerm.ATOM_TYPE:
	    return new StructAtom(removeQuoted(((PrologAtom) term).getStringValue()));
	case PrologTerm.FLOAT_TYPE:
	    return new Float(((PrologFloat) term).getFloatValue());
	case PrologTerm.INTEGER_TYPE:
	    return new Int(((PrologInteger) term).getIntValue());
	case PrologTerm.DOUBLE_TYPE:
	    return new Double(((PrologDouble) term).getDoubleValue());
	case PrologTerm.LONG_TYPE:
	    return new Long(((PrologLong) term).getLongValue());
	case PrologTerm.VARIABLE_TYPE:
	    PrologVariable v = (PrologVariable) term;
	    String name = v.getName();
	    Term variable = sharedPrologVariables.get(name);
	    if (variable == null) {
		variable = new Var(name, v.getPosition());
		sharedPrologVariables.put(name, variable);
	    }
	    return variable;
	case PrologTerm.LIST_TYPE:
	    PrologTerm[] elements = term.getArguments();
	    if (elements != null && elements.length > 0) {
		Term list = Term.emptyList;
		int offset = elements[elements.length - 1].isEmptyList() ? 2 : 1;
		for (int i = elements.length - offset; i >= 0; --i) {
		    list = new Struct(".", new Term[] { fromTerm(elements[i], Term.class), list });
		}
		return list;
	    }
	    return Term.emptyList;
	case PrologTerm.STRUCTURE_TYPE:
	    String functor = term.getFunctor();
	    Term[] arguments = fromTermArray(((PrologStructure) term).getArguments());
	    return new Struct(functor, arguments);
	case PrologTerm.EXPRESSION_TYPE:
	    PrologExpression expression = (PrologExpression) term;
	    Term left = fromTerm(expression.getLeft());
	    String operator = expression.getOperator();
	    Term right = fromTerm(expression.getRight());
	    return new Struct(operator, new Term[] { left, right });
	default:
	    throw new UnknownTermError(term);
	}
    }

    @Override
    public Term[] fromTermArray(PrologTerm[] terms) {
	Term[] prologTerms = new Term[terms.length];
	for (int i = 0; i < terms.length; i++) {
	    prologTerms[i] = fromTerm(terms[i]);
	}
	return prologTerms;
    }

    @Override
    public Term fromTerm(PrologTerm head, PrologTerm[] body) {
	Term h = fromTerm(head);
	if (body != null && body.length > 0) {
	    Term b = fromTerm(body[body.length - 1]);
	    for (int i = body.length - 2; i >= 0; --i) {
		b = new Struct(",", new Term[] { fromTerm(body[i]), b });
	    }
	    return new Struct(":-", new Term[] { h, b });
	}
	return new Struct(":-", new Term[] { h, Term.TRUE });
    }

    @Override
    public PrologProvider createProvider() {
	return new JTrologProvider(this);
    }

    @Override
    public String toString() {
	return "TuPrologConverter";
    }

}
