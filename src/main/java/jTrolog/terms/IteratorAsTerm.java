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

/**
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "rawtypes", "serial" })
public class IteratorAsTerm extends Term {

	Iterator impl;

	public IteratorAsTerm(Iterator impl) {
		this.impl = impl;
	}

	public boolean hasNext() {
		return impl.hasNext();
	}

	public Object next() {
		return impl.next();
	}

	public String toStringSmall() {
		return "iteratorTerm_" + hashCode();
	}
}
