/*
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package jTrolog.engine;

import jTrolog.terms.Term;

import java.util.HashMap;
import java.util.Iterator;

/**
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "rawtypes" })
public class Solution {

	private static final HashMap emptyMap = new HashMap();

	HashMap bindings;
	Term solution;

	public Solution(Term solution) {
		this.bindings = emptyMap;
		this.solution = solution;
	}

	public Solution(HashMap bindings, Term solution) {
		this.bindings = bindings;
		this.solution = solution;
	}

	/**
	 * @return true if the query was a success, false otherwise
	 */
	public boolean success() {
		return solution != null;
	}

	/**
	 * @return the solution to the query as a Term
	 */
	public Term getSolution() {
		return solution;
	}

	/**
	 * @return the link of the Variable corresponding to varName, if no Var was
	 *         named varName in the query, null is returned if the Var was
	 *         linked to an any Var, that any Var is returned
	 */
	public Term getBinding(String varName) {
		return (Term) bindings.get(varName);
	}

	public String toString() {
		return solution == null ? "no" : solution.toString();
	}

	public String bindingsToString() {
		StringBuffer buffy = new StringBuffer();
		for (Iterator it = bindings.keySet().iterator(); it.hasNext();) {
			String variable = (String) it.next();
			Term binding = (Term) bindings.get(variable);
			buffy.append(variable).append(": ").append(binding).append("\n");
		}
		return buffy.toString();
	}
}
