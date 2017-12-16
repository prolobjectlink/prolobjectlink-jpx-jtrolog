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

import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.logicware.jpi.OperatorEntry;

import jTrolog.engine.Prolog;
import jTrolog.terms.Int;
import jTrolog.terms.Struct;
import jTrolog.terms.StructAtom;

final class JTrologOperatorSet extends AbstractSet<OperatorEntry> {

    protected final Set<OperatorEntry> operators;

    public JTrologOperatorSet() {
	Prolog engine = new Prolog();
	operators = new HashSet<OperatorEntry>();
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
    }

    protected boolean currentOp(String opreator) {
	for (OperatorEntry operatorEntry : operators) {
	    if (operatorEntry.getOperator().equals(opreator)) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public Iterator<OperatorEntry> iterator() {
	return operators.iterator();
    }

    @Override
    public int size() {
	return operators.size();
    }

}
