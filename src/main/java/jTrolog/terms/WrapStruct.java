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

import jTrolog.terms.Struct;
import jTrolog.terms.Term;
import jTrolog.terms.Wrapper;
import jTrolog.engine.BindingsTable;


/**
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "serial" })
public class WrapStruct extends Struct implements Wrapper {

	public final int context;
	private Struct basis;

	public WrapStruct(Struct struct, int renameVarID) {
		super(struct.name, struct.arity, struct.predicateIndicator);
		basis = struct;
		context = renameVarID;
	}

	public int getOperatorType() {
		return basis.getOperatorType();
	}

	public Term getArg(int i) {
		return BindingsTable.wrapWithID(basis.getArg(i), context);
	}

	public Var[] getVarList() {
		return basis.getVarList();
	}

	public int getContext() {
		return context;
	}

	public Term getBasis() {
		return basis;
	}

	// public boolean equals(Object t) {
	// return (t instanceof WrapStruct && ((WrapStruct) t).getContext() ==
	// context && ((WrapStruct) t).getBasis().equals(basis));
	// }

	public String toString() {
		return basis.toString();
	}
}
