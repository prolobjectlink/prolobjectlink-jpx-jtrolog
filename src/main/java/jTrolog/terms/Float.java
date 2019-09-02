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
 * Float class represents the float prolog data type
 */
@SuppressWarnings("serial")
public class Float extends Number {

	private float value;

	public Float() {
	}

	public Float(float v) {
		value = v;
	}

	public Float(String v) {
		value = java.lang.Float.parseFloat(v);
	}

	/**
	 * Returns the value of the Float as int
	 */
	public int intValue() {
		return (int) value;
	}

	/**
	 * Returns the value of the Float as float
	 */
	public float floatValue() {
		return value;
	}

	/**
	 * Returns the value of the Float as double
	 */
	public double doubleValue() {
		return value;
	}

	/**
	 * Returns the value of the Float as long
	 */
	public long longValue() {
		return (long) value;
	}

	public String toString() {
		return java.lang.Float.toString(value);
	}

	public boolean equals(Object n) {
		if (n instanceof Float)
			return Number.compareDoubleValues(this, (Number) n) == 0;
		return false;
	}
}
