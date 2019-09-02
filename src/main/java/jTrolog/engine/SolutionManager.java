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

import jTrolog.terms.*;

import java.io.*;
import java.util.*;

/**
 * Sorts the result into a Solution object. Arranges the bindings into a
 * LinkedHashMap.
 * 
 * @author janerist@stud.ntnu.no
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "rawtypes", "unchecked","serial" })
class SolutionManager implements Serializable {

	private static HashMap setupResultTable(BindingsTable result, Struct query) {
		// 1. stringToStructList a new goal object based on the results and
		// query
		HashMap bindings = new HashMap();
		if (query.getVarList() == null)
			return bindings;
		// 2. stringToStructList a simple HashMap based on only the query vars.
		for (int i = 0; i < query.getVarList().length; i++) {
			Var var = query.getVarList()[i];
			Term link = result.flatCopy(var, 0);
			if (link.equals(var))
				link = null;
			bindings.put(var, link);
		}
		bindings = replaceUnknownManInTheMiddle(bindings);
		bindings = reverseAnyNullLinks(bindings);
		return bindings;
	}

	private static HashMap reverseAnyNullLinks(HashMap bindings) {
		LinkedHashMap almostACopy = new LinkedHashMap();
		for (Iterator it = bindings.keySet().iterator(); it.hasNext();) {
			Term key1 = (Term) it.next();
			Object link1 = bindings.get(key1);
			if (link1 == null && bindings.containsValue(key1)) {
				// key1 is not linked, find another key2 that links to key1
				for (Iterator iterator = bindings.keySet().iterator(); iterator.hasNext();) {
					Term key2 = (Term) iterator.next();
					Term link2 = (Term) bindings.get(key2);
					// key2 links to key1, return key2
					if (key1.equals(link2)) {
						almostACopy.put(key1.toString(), key2);
						break;
					}
				}
			} else
				almostACopy.put(key1.toString(), link1);
		}
		return almostACopy;
	}

	private static HashMap replaceUnknownManInTheMiddle(HashMap bindings) {
		Collection linked = bindings.values();
		for (Iterator it = linked.iterator(); it.hasNext();) {
			Term isLinked = (Term) it.next();
			if (!(isLinked instanceof Var))
				continue;
			if (bindings.keySet().contains(isLinked))
				continue;
			int howOftenIsIsLinkedLinked = Collections.frequency(linked, isLinked);
			if (howOftenIsIsLinkedLinked <= 1)
				continue;
			// isLinked is manInTheMiddle
			LinkedList keys = new LinkedList();
			for (Iterator it2 = bindings.keySet().iterator(); it2.hasNext();) {
				Term varName = (Term) it2.next();
				if (isLinked.equals(bindings.get(varName)))
					keys.add(varName);
			}
			for (int i = 0; i < keys.size(); i++) {
				Var key = (Var) keys.get(i);
				if (i == keys.size() - 1)
					bindings.put(key, null);
				else
					bindings.put(key, (Var) keys.getLast());
			}
		}
		return bindings;
	}

	static Solution prepareSolution(Struct query, BindingsTable result) {
		if (result == null)
			return new Solution(null);

		HashMap bindings = SolutionManager.setupResultTable(result, query);
		Term goal = result.flatCopy(query, 0);
		return new Solution(bindings, goal);
	}
}
