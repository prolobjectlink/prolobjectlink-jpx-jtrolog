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
package jTrolog.parser;

import jTrolog.terms.Term;

import java.util.NoSuchElementException;

/**
 * This class represents an iterator of terms from a string.
 * 
 * @see jTrolog.terms.Term
 */
@SuppressWarnings({ "rawtypes", "serial" })
class TermIterator implements java.util.Iterator, java.io.Serializable {

	private Parser parser;
	private boolean hasNext;
	private Term next;

	TermIterator(Parser p) {
		parser = p;
		next = parser.nextTerm(true);
		hasNext = (next != null);
	}

	public Object next() {
		if (hasNext) {
			if (next == null)
				next = parser.nextTerm(true);
			hasNext = false;
			Term temp = next;
			next = null;
			return temp;
		} else if (hasNext()) {
			hasNext = false;
			Term temp = next;
			next = null;
			return temp;
		}
		throw new NoSuchElementException();
	}

	public boolean hasNext() {
		if (hasNext)
			return hasNext;
		next = parser.nextTerm(true);
		if (next != null)
			hasNext = true;
		return hasNext;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}
