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

import org.logicware.jpi.ArityError;
import org.logicware.jpi.FunctorError;
import org.logicware.jpi.IndicatorError;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;

import jTrolog.terms.Term;
import jTrolog.terms.Var;

public class JTrologVariable extends JTrologTerm implements PrologVariable {

	private String name;

	private JTrologVariable(PrologProvider provider, Term var) {
		super(ATOM_TYPE, provider, var);
	}

	JTrologVariable(PrologProvider provider, int n) {
		this(provider, "_", n);
		this.name = "_";
	}

	JTrologVariable(PrologProvider provider, String name, int n) {
		super(VARIABLE_TYPE, provider, name, n);
		this.name = name;
	}

	public boolean isAnonymous() {
		return ((Var) value).isAnonymous();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public PrologTerm[] getArguments() {
		return new JTrologVariable[0];
	}

	@Override
	public int getArity() {
		throw new ArityError(this);
	}

	@Override
	public String getFunctor() {
		throw new FunctorError(this);
	}

	@Override
	public String getIndicator() {
		throw new IndicatorError(this);
	}

	@Override
	public boolean hasIndicator(String functor, int arity) {
		throw new IndicatorError(this);
	}

	public int getPosition() {
		return vIndex;
	}

	@Override
	public PrologTerm clone() {
		return new JTrologVariable(provider, value);
	}

}
