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
import java.util.List;

import org.logicware.jpi.AbstractProvider;
import org.logicware.jpi.PrologAtom;
import org.logicware.jpi.PrologConverter;
import org.logicware.jpi.PrologDouble;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologExpression;
import org.logicware.jpi.PrologFloat;
import org.logicware.jpi.PrologInteger;
import org.logicware.jpi.PrologList;
import org.logicware.jpi.PrologLong;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologStructure;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;

import jTrolog.parser.Parser;
import jTrolog.terms.Struct;
import jTrolog.terms.Term;

public class JTrologProvider extends AbstractProvider implements PrologProvider {

    public JTrologProvider() {
	super(new JTrologConverter());
    }

    public JTrologProvider(PrologConverter<Term> converter) {
	super(converter);
    }

    public boolean isCompliant() {
	return false;
    }

    public boolean preserveQuotes() {
	return true;
    }

    public PrologTerm prologNil() {
	return new JTrologNil(this);
    }

    public PrologTerm prologCut() {
	return new JTrologCut(this);
    }

    public PrologTerm prologFail() {
	return new JTrologFail(this);
    }

    public PrologTerm prologTrue() {
	return new JTrologTrue(this);
    }

    public PrologTerm prologFalse() {
	return new JTrologFalse(this);
    }

    public PrologTerm prologEmpty() {
	return new JTrologEmpty(this);
    }

    // engine

    public PrologEngine newEngine() {
	return new JTrologEngine(this);
    }

    // parser helpers

    public PrologTerm parsePrologTerm(String term) {
	return toTerm(new Parser(term).nextTerm(false), PrologTerm.class);
    }

    public PrologTerm[] parsePrologTerms(String stringTerms) {
	List<PrologTerm> list = new ArrayList<PrologTerm>();
	Parser parser = new Parser(stringTerms);
	Term term = parser.nextTerm(false);
	while (term != null && term instanceof Struct) {
	    Struct struct = (Struct) term;
	    if (struct.name.equals(",") && struct.arity == 2) {
		list.add(toTerm(struct.getArg(0), PrologTerm.class));
		term = struct.getArg(1);
	    } else {
		list.add(toTerm(term, PrologTerm.class));
		term = parser.nextTerm(false);
	    }
	}
	return list.toArray(new PrologTerm[0]);
    }

    // terms

    public PrologAtom newAtom(String functor) {
	return new JTrologAtom(this, functor);
    }

    public PrologFloat newFloat(Number value) {
	return new JTrologFloat(this, value);
    }

    public PrologDouble newDouble(Number value) {
	return new JTrologDouble(this, value);
    }

    public PrologInteger newInteger(Number value) {
	return new JTrologInteger(this, value);
    }

    public PrologLong newLong(Number value) {
	return new JTrologLong(this, value);
    }

    public PrologVariable newVariable() {
	// return new JTrologVariable(this);
	String message = "Use newVariable(int position)";
	throw new UnsupportedOperationException(message);
    }

    public PrologVariable newVariable(String name) {
	// return new JTrologVariable(this, name);
	String message = "Use newVariable(String name, int position)";
	throw new UnsupportedOperationException(message);
    }

    public PrologVariable newVariable(int position) {
	if (position < 0) {
	    throw new IllegalArgumentException("Not allowed negative position");
	}
	return new JTrologVariable(this, position + 1);
    }

    public PrologVariable newVariable(String name, int position) {
	if (position < 0) {
	    throw new IllegalArgumentException("Not allowed negative position");
	}
	return new JTrologVariable(this, name, position + 1);
    }

    public PrologList newList() {
	return new JTrologEmpty(this);
    }

    public PrologList newList(PrologTerm[] arguments) {
	if (arguments != null && arguments.length > 0) {
	    return new JTrologList(this, arguments);
	}
	return new JTrologEmpty(this);
    }

    public PrologList newList(PrologTerm head, PrologTerm tail) {
	return new JTrologList(this, head, tail);
    }

    public PrologList newList(PrologTerm[] arguments, PrologTerm tail) {
	return new JTrologList(this, arguments, tail);
    }

    public PrologStructure newStructure(String functor, PrologTerm... arguments) {
	return new JTrologStructure(this, functor, arguments);
    }

    public PrologExpression newExpression(PrologTerm left, String operator, PrologTerm right) {
	return new JTrologExpression(this, left, operator, right);
    }

    @Override
    public String toString() {
	return "TuPrologProvider [converter=" + converter + "]";
    }

}
