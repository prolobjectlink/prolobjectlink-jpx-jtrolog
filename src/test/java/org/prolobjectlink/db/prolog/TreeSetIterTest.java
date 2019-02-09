/*
 * #%L
 * prolobjectlink-jpp-jtrolog
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
package org.prolobjectlink.db.prolog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;
import org.prolobjectlink.db.prolog.PrologTreeSet;

public class TreeSetIterTest extends CollectionTest {

	private Iterator<Integer> iterator;
	private PrologTreeSet<Integer> expected = new PrologTreeSet<Integer>();
	private PrologTreeSet<Integer> actual = new PrologTreeSet<Integer>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

	@Test
	public void testHasNext() {

		iterator = actual.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(zero, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(one, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(two, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(three, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(four, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(five, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(six, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(seven, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(eight, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(nine, iterator.next());

		assertFalse(iterator.hasNext());

	}

	@Test
	public void testNext() {

		iterator = actual.iterator();
		assertTrue(iterator.hasNext());
		assertEquals(zero, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(one, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(two, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(three, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(four, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(five, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(six, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(seven, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(eight, iterator.next());
		assertTrue(iterator.hasNext());
		assertEquals(nine, iterator.next());

		assertFalse(iterator.hasNext());

	}

	@Test
	public void testRemove() {

		iterator = actual.iterator();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		iterator.next();
		iterator.remove();

		assertFalse(iterator.hasNext());
		assertEquals(expected, actual);

	}

}
