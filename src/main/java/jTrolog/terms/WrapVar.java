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

/**
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "serial" })
public class WrapVar extends Var implements Wrapper {

	Var basis;
	int context;
	String[] nameNumbers;

	public WrapVar(Var var, int renameVarID) {
		if (var instanceof WrapVar)
			throw new RuntimeException("building a WrapVar from another WrapVar");
		basis = var;
		context = renameVarID;
	}

	public boolean equals(Object t) {
		return t instanceof WrapVar && context == ((WrapVar) t).context && basis.equals(((WrapVar) t).basis);
	}

	public int hashCode() {
		return basis.hashCode() + context * 100;
	}

	public boolean isAnonymous() {
		return basis.isAnonymous();
	}

	public String toString() {
		return basis.toString();
	}

	public String toStringSmall() {
		return basis.toStringSmall();
	}

	public int getContext() {
		return context;
	}

	public Term getBasis() {
		return basis;
	}
}
