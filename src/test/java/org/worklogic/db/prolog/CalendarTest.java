/*
 * #%L
 * prolobjectlink-db-jtrolog
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
package org.worklogic.db.prolog;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

public class CalendarTest {

	private Calendar calendar = Calendar.getInstance();
	private PrologCalendar prologCalendar = new PrologCalendar();

	@Test
	public final void testGetJavaUtilCalendar() {
		assertEquals(calendar, prologCalendar.getJavaUtilCalendar());
	}

	@Test
	public final void testGetTimeInMillis() {
		assertEquals(calendar.getTimeInMillis(), prologCalendar.getTimeInMillis());
	}

	@Test
	public final void testIsLenient() {
		assertEquals(calendar.isLenient(), prologCalendar.isLenient());
	}

	@Test
	public final void testGetFirstDayOfWeek() {
		assertEquals(calendar.getFirstDayOfWeek(), prologCalendar.getFirstDayOfWeek());
	}

	@Test
	public final void testGetMinimalDaysInFirstWeek() {
		assertEquals(calendar.getMinimalDaysInFirstWeek(), prologCalendar.getMinimalDaysInFirstWeek());
	}

}
