/*
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */
package io.github.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import io.github.prolobjectlink.db.prolog.PrologCalendar;

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
