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
 * Wrapper object to store both the original Struct and its converted clause in
 * the same object
 * 
 * @author ivar.orstavik@hist.no
 */
public class Clause {

	public final Struct[] tail;
	public final Struct head;
	public final Struct original;

	public Clause(Struct[] tail, Struct head, Struct original) {
		this.tail = tail;
		this.head = head;
		this.original = original;
	}
}
