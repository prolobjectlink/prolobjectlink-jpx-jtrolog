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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.logicware.jpi.AbstractQuery;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;

import jTrolog.engine.Prolog;
import jTrolog.engine.Solution;
import jTrolog.parser.Parser;
import jTrolog.terms.Struct;
import jTrolog.terms.Term;
import jTrolog.terms.Var;

public class JTrologQuery extends AbstractQuery implements PrologQuery {

    private Solution solution;
    private final Prolog jtrolog;
    private final List<String> variables = new ArrayList<String>();

    private void enumerateVariables(List<String> vector, Term term) {
	if (!(term instanceof Var)) {
	    if (term instanceof Struct) {
		Struct struct = (Struct) term;
		Var[] vars = struct.getVarList();
		for (int i = 0; i < vars.length; i++) {
		    enumerateVariables(variables, vars[i]);
		}
	    }
	} else if (!vector.contains(term.toString())) {
	    vector.add(term.toString());
	}
    }

    JTrologQuery(PrologEngine engine, String query) {
	super(engine);
	jtrolog = engine.unwrap(JTrologEngine.class).engine;
	enumerateVariables(variables, new Parser(query).nextTerm(false));
	try {
	    this.solution = jtrolog.solve("" + query + ".");
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }

    JTrologQuery(PrologEngine engine, PrologTerm[] terms) {
	super(engine);
	jtrolog = engine.unwrap(JTrologEngine.class).engine;
	Term term = fromTerm(terms[terms.length - 1], Term.class);
	for (int i = terms.length; i > 1; i--) {
	    term = new Struct(",", new Term[] { fromTerm(terms[i - 2], Term.class), term });
	}
	enumerateVariables(variables, term);
	try {
	    this.solution = jtrolog.solve((Struct) term);
	} catch (Throwable e) {
	    e.printStackTrace();
	}
    }

    public boolean hasSolution() {
	// return solution.success();
	return solution != null;
    }

    public boolean hasMoreSolutions() {
	try {
	    return jtrolog.hasOpenAlternatives();
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	return false;
    }

    public PrologTerm[] oneSolution() {
	int index = 0;
	Map<String, PrologTerm> solution = oneVariablesSolution();
	PrologTerm[] array = new PrologTerm[solution.size()];
	if (array.length > 0) {
	    for (Iterator<String> i = variables.iterator(); i.hasNext();) {
		array[index++] = solution.get(i.next());
	    }
	}
	return array;
    }

    public Map<String, PrologTerm> oneVariablesSolution() {
	Map<String, PrologTerm> map = new HashMap<String, PrologTerm>();
	for (Iterator<String> i = variables.iterator(); i.hasNext();) {
	    String vName = i.next();
	    Term vtTerm = solution.getBinding(vName);
	    if (vtTerm != null) {
		PrologTerm pTerm = toTerm(vtTerm, PrologTerm.class);
		map.put(vName, pTerm);
	    }
	}
	return map;
    }

    public PrologTerm[] nextSolution() {
	PrologTerm[] array = oneSolution();
	try {
	    if (hasMoreSolutions()) {
		solution = jtrolog.solveNext();
		return array;
	    }
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	return array;
    }

    public Map<String, PrologTerm> nextVariablesSolution() {
	Map<String, PrologTerm> map = oneVariablesSolution();
	try {

	    if (hasMoreSolutions()) {
		solution = jtrolog.solveNext();
	    }
	    return map;
	} catch (Throwable e) {
	    e.printStackTrace();
	}
	return new HashMap<String, PrologTerm>(0);
    }

    public PrologTerm[][] nSolutions(int n) {
	if (n > 0) {
	    // m:solutionSize
	    int m = 0, index = 0;
	    List<PrologTerm[]> all = new ArrayList<PrologTerm[]>();

	    PrologTerm[] array = oneSolution();
	    m = array.length > m ? array.length : m;
	    index++;
	    all.add(array);

	    while (hasMoreSolutions() && index < n) {
		try {
		    solution = jtrolog.solveNext();
		    array = oneSolution();
		    m = array.length > m ? array.length : m;
		    index++;
		    all.add(array);
		} catch (Throwable e) {
		    e.printStackTrace();
		}

	    }

	    PrologTerm[][] allSolutions = new PrologTerm[n][m];
	    for (int i = 0; i < n; i++) {
		array = all.get(i);
		for (int j = 0; j < m; j++) {
		    allSolutions[i][j] = array[j];
		}
	    }
	    return allSolutions;
	}
	return new PrologTerm[0][0];
    }

    public Map<String, PrologTerm>[] nVariablesSolutions(int n) {
	if (n > 0) {
	    int index = 0;
	    Map<String, PrologTerm>[] solutionMaps = new HashMap[n];

	    Map<String, PrologTerm> solutionMap = oneVariablesSolution();
	    solutionMaps[index++] = solutionMap;

	    while (hasMoreSolutions() && index < n) {
		try {
		    solution = jtrolog.solveNext();
		    solutionMap = oneVariablesSolution();
		    solutionMaps[index++] = solutionMap;
		} catch (Throwable e) {
		    e.printStackTrace();
		}
	    }
	    return solutionMaps;
	}
	return new HashMap[0];
    }

    public PrologTerm[][] allSolutions() {
	// n:solutionCount, m:solutionSize
	int n = 0, m = 0;
	List<PrologTerm[]> all = new ArrayList<PrologTerm[]>();

	PrologTerm[] array = oneSolution();
	if (array.length > 0) {
	    m = array.length > m ? array.length : m;
	    n++;
	    all.add(array);
	}

	while (hasMoreSolutions()) {
	    try {
		solution = jtrolog.solveNext();
		array = oneSolution();
		if (array.length > 0) {
		    m = array.length > m ? array.length : m;
		    n++;
		    all.add(array);
		}
	    } catch (Throwable e) {
		e.printStackTrace();
	    }

	}

	PrologTerm[][] allSolutions = new PrologTerm[n][m];
	for (int i = 0; i < n; i++) {
	    array = all.get(i);
	    for (int j = 0; j < m; j++) {
		allSolutions[i][j] = array[j];
	    }
	}
	return allSolutions;
    }

    public Map<String, PrologTerm>[] allVariablesSolutions() {
	List<Map<String, PrologTerm>> allVariables = new ArrayList<Map<String, PrologTerm>>();

	Map<String, PrologTerm> variables = oneVariablesSolution();
	if (!variables.isEmpty()) {
	    allVariables.add(variables);
	}

	while (hasMoreSolutions()) {
	    try {
		solution = jtrolog.solveNext();
		variables = oneVariablesSolution();
		if (!variables.isEmpty()) {
		    allVariables.add(variables);
		}
	    } catch (Throwable e) {
		e.printStackTrace();
	    }
	}

	int lenght = allVariables.size();
	Map<String, PrologTerm>[] allVariablesSolution = new HashMap[lenght];
	for (int i = 0; i < lenght; i++) {
	    allVariablesSolution[i] = allVariables.get(i);
	}
	return allVariablesSolution;
    }

    @Override
    public String toString() {
	return "" + solution + "";
    }

    public void dispose() {
	solution = null;
    }

}
