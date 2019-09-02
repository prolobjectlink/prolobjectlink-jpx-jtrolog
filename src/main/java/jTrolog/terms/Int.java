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
 * Int class represents the integer prolog data type
 */
@SuppressWarnings({ "serial" })
public class Int extends jTrolog.terms.Number {

	private int value;

	public Int() {

	}

	public Int(String v) throws NumberFormatException {
		value = java.lang.Integer.parseInt(v);
	}

	public Int(int v) {
		value = v;
	}

	/**
	 * Returns the value of the Integer as int
	 */
	public int intValue() {
		return value;
	}

	/**
	 * Returns the value of the Integer as float
	 */
	public float floatValue() {
		return (float) value;
	}

	/**
	 * Returns the value of the Integer as double
	 */
	public double doubleValue() {
		return (double) value;
	}

	/**
	 * Returns the value of the Integer as long
	 */
	public long longValue() {
		return value;
	}

	public String toString() {
		return java.lang.Integer.toString(value);
	}

	public static Number create(String s) throws NumberFormatException {
		try {
			return new Int(s);
		} catch (NumberFormatException e) {
			return new Long(s);
		}
	}

	public boolean equals(Object n) {
		if (n instanceof Int)
			return longValue() == ((Int) n).longValue();
		return false;
	}
}
