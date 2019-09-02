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

import java.io.Serializable;

/**
 * This class represents a token read by the prolog term tokenizer
 */
@SuppressWarnings({ "serial" })
class Token implements Serializable {
	// token textual representation
	String seq;
	// token type and attribute
	int type;

	static final int ATOM = 'A';
	static final int SQ_SEQUENCE = 'S';
	static final int DQ_SEQUENCE = 'D';
	static final int OPERATOR = 'O';
	static final int FUNCTOR = 'F';

	static final int ATOM_OPERATOR = 'B';
	static final int ATOM_FUNCTOR = 'a';
	static final int OPERATOR_FUNCTOR = 'p';
	static final int SQ_FUNCTOR = 's';

	static final int VARIABLE = 'v';
	static final int EOF = 'e';
	static final int INTEGER = 'i';
	static final int FLOAT = 'f';

	public Token(String s, int t) {
		seq = s.intern();
		type = t;
	}

	public String getValue() {
		return seq;
	}

	public boolean isOperator(boolean commaIsEndMarker) {
		if (commaIsEndMarker && ",".equals(seq))
			return false;
		return type == OPERATOR || type == ATOM_OPERATOR || type == OPERATOR_FUNCTOR;
	}

	public boolean isFunctor() {
		return type == FUNCTOR || type == ATOM_FUNCTOR || type == SQ_FUNCTOR || type == OPERATOR_FUNCTOR;
	}

	public boolean isNumber() {
		return type == INTEGER || type == FLOAT;
	}

	boolean isEOF() {
		return type == EOF;
	}

	boolean isType(int type) {
		return this.type == type;
	}

	boolean isAtom() {
		return type == ATOM || type == ATOM_OPERATOR || type == ATOM_FUNCTOR || type == SQ_FUNCTOR || type == SQ_SEQUENCE || type == DQ_SEQUENCE;
	}
}
