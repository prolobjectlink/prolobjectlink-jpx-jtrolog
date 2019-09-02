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
package jTrolog.terms;

import java.util.Iterator;

import jTrolog.engine.Prolog;
import jTrolog.parser.Parser;
@SuppressWarnings({ "serial" })
public class StructAtom extends Struct {

	public StructAtom(String name) {
		super(name, new Term[0]);
		type = Term.ATOM;
	}

	public boolean equals(Object t) {
		return t instanceof StructAtom && name == ((StructAtom) t).name;
	}

	public String toString() {
		if (name.isEmpty()) {
			return "''";
		} else if (!Parser.isAtom(name) && !isOperator(name) && !name.equals("[]")) {
			return "'" + name + "'";
		}
		return name;
	}

	public String toStringSmall() {
		return toString();
	}

	private boolean isOperator(String name) {
		Prolog engine = Prolog.defaultMachine;
		Iterator<?> i = engine.getCurrentOperators();
		while (i.hasNext()) {
			Object object = i.next();
			if (object instanceof Struct) {
				Struct o = (Struct) object;
				String n = ((StructAtom) o.getArg(2)).name;
				if (name.equals(n)) {
					return true;
				}
			}
		}
		return false;
	}

}
