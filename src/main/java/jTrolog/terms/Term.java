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
import java.util.LinkedList;

/**
 * Term class - root prolog data type
 * 
 * @see jTrolog.terms.Struct
 * @see Var
 * @see jTrolog.terms.Number
 * @author ivar.orstavik@hist.no
 */
@SuppressWarnings({ "rawtypes", "serial" })
public abstract class Term implements java.io.Serializable {

	public int type;

	public final static int VAR = 1;
	public final static int NUMBER = 2;
	public final static int STRUCT = 3;
	public final static int ATOM = 4;

	// important atoms to be used only once
	public static final Term TRUE = new StructAtom("true".intern());
	public static final Term FALSE = new StructAtom("false".intern());
	public static final StructAtom emptyList = new StructAtom("[]");
	public static final Iterator iterator = new LinkedList().iterator();

	// Tree data Jens Teubner prepost style:
	// http://www-db.in.tum.de/~grust/teaching/ws0506/XML-DB/db-supp-xml-pdf
	// page 17
	public int pos = 0;

	public Term[] tree;
	public int[] prePost;

	protected Term() {
		tree = new Term[] { this };
		prePost = new int[] { 0 };
	}

	public abstract String toStringSmall();
}
