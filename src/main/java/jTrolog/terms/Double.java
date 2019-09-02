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
 * Double class represents the double prolog data type
 */
@SuppressWarnings("serial")
public class Double extends jTrolog.terms.Float {

	private double value;

	public Double(double v) {
		value = v;
	}

	public Double(String v) throws NumberFormatException {
		value = java.lang.Double.parseDouble(v);
	}

	/**
	 * Returns the value of the Double as int
	 */
	final public int intValue() {
		return (int) value;
	}

	/**
	 * Returns the value of the Double as float
	 */
	final public float floatValue() {
		return (float) value;
	}

	/**
	 * Returns the value of the Double as double
	 */
	final public double doubleValue() {
		return value;
	}

	/**
	 * Returns the value of the Double as long
	 */
	final public long longValue() {
		return (long) value;
	}

	public String toString() {
		return java.lang.Double.toString(value);
	}
}
