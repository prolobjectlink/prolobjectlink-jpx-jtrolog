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
 * Number abstract class represents numbers prolog data type
 */
@SuppressWarnings({ "serial" })
public abstract class Number extends EvaluableTerm {

	protected Number() {
		type = Term.NUMBER;
	}

	// two real numbers that are similiar within this margin are considered
	// equal
	public static final double MARGIN = 0.0000000000000005;

	/**
	 * Returns the value of the number as int
	 */
	public abstract int intValue();

	/**
	 * Returns the value of the number as float
	 */
	public abstract float floatValue();

	/**
	 * Returns the value of the number as long
	 */
	public abstract long longValue();

	/**
	 * Returns the value of the number as double
	 */
	public abstract double doubleValue();

	/**
	 * @return 0 if one is the same as two (within the MARGIN of error) 1 if one
	 *         is greater than two -1 if one is smaller than two
	 */
	public static int compareDoubleValues(Number one, Number two) {
		double value = one.doubleValue() - two.doubleValue();
		if (value > 0 ? value < MARGIN : -value < MARGIN)
			return 0;
		return value > 0 ? 1 : -1;
	}

	public static Number create(String s) throws NumberFormatException {
		try {
			return Int.create(s);
		} catch (NumberFormatException ee) {
			return new Double(s);
		}
	}

	public static Number getIntegerNumber(long num) {
		if (num > Integer.MIN_VALUE && num < Integer.MAX_VALUE)
			return new Int((int) num);
		return new Long(num);
	}

	public String toStringSmall() {
		return toString();
	}
}
