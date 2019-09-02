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
 * Long class represents the long prolog data type
 */
@SuppressWarnings({ "serial" })
public class Long extends Int {

	private long value;

	public Long(long v) {
		value = v;
	}

	public Long(String val) {
		value = java.lang.Long.parseLong(val);
	}

	/**
	 * Returns the value of the Integer as int
	 */
	final public int intValue() {
		if (value > Integer.MAX_VALUE)
			throw new RuntimeException("value not intable");
		return (int) value;
	}

	/**
	 * Returns the value of the Integer as float
	 */
	final public float floatValue() {
		return (float) value;
	}

	/**
	 * Returns the value of the Integer as double
	 */
	final public double doubleValue() {
		return (double) value;
	}

	/**
	 * Returns the value of the Integer as long
	 */
	final public long longValue() {
		return value;
	}

	public String toString() {
		return java.lang.Long.toString(value);
	}
}
