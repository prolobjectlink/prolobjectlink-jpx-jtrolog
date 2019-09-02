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

import jTrolog.parser.Parser;

import java.util.Iterator;

/**
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "rawtypes" })
class ListIterator implements Iterator {

	Term hereIAmBaby;

	public ListIterator(Struct origin) {
		hereIAmBaby = origin;
	}

	public boolean hasNext() {
		return hereIAmBaby != null;
	}

	public Object next() {
		if (hereIAmBaby == null)
			throw new IndexOutOfBoundsException("iterating out of list");
		if (hereIAmBaby.equals(Term.emptyList)) {
			hereIAmBaby = null;
			return Term.emptyList;
		}
		if (hereIAmBaby instanceof Struct && ((Struct) hereIAmBaby).predicateIndicator == Parser.listSignature) {
			Term timeToDeliver = ((Struct) hereIAmBaby).getArg(0);
			hereIAmBaby = ((Struct) hereIAmBaby).getArg(1);
			return timeToDeliver;
		}
		Term ImYours = hereIAmBaby;
		hereIAmBaby = null;
		return ImYours;
	}

	public void remove() {
		throw new UnsupportedOperationException("don't delete on List iteration");
	}
}
