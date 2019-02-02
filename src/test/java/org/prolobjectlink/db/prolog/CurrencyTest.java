/*
 * #%L
 * prolobjectlink-jpp-jtrolog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;

import java.util.Currency;
import java.util.Locale;

import org.junit.Test;
import org.prolobjectlink.db.prolog.PrologCurrency;

public class CurrencyTest {

	private Locale locale = Locale.getDefault();
	private Currency currency = Currency.getInstance(locale);
	private PrologCurrency prologCurrency = new PrologCurrency(currency.getCurrencyCode());

	@Test
	public final void testHashCode() {
		assertEquals(new PrologCurrency(currency.getCurrencyCode()).hashCode(), prologCurrency.hashCode());
	}

	@Test
	public final void testGetCurrencyCode() {
		assertEquals(currency.getCurrencyCode(), prologCurrency.getCurrencyCode());
	}

	@Test
	public final void testGetJavaUtilCurrency() {
		assertEquals(currency, prologCurrency.getJavaUtilCurrency());
	}

	@Test
	public final void testToString() {
		assertEquals(currency.toString(), prologCurrency.toString());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new PrologCurrency(currency.getCurrencyCode()), prologCurrency);
	}

}
