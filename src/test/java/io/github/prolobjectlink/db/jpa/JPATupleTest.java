/*-
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package io.github.prolobjectlink.db.jpa;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import javax.persistence.TupleElement;

import org.junit.Test;

import io.github.prolobjectlink.BaseTest;
import io.github.prolobjectlink.db.jpa.JpaTuple;
import io.github.prolobjectlink.db.jpa.JpaTupleElement;
import io.github.prolobjectlink.domain.geometry.Point;
import io.github.prolobjectlink.domain.geometry.Polygon;
import io.github.prolobjectlink.domain.geometry.Segment;

public class JPATupleTest extends BaseTest {

	private TupleElement<Point> pointTupleElement = new JpaTupleElement<Point>("point", Point.class, a);
	private TupleElement<Segment> segmentTupleElement = new JpaTupleElement<Segment>("segment", Segment.class, ab);
	private TupleElement<Polygon> polygonTupleElement = new JpaTupleElement<Polygon>("triangle", Polygon.class,
			triangle);

	private List elements = Arrays.asList(pointTupleElement, segmentTupleElement, polygonTupleElement);

	private JpaTuple tuple = new JpaTuple(elements);

	@Test
	public final void testGetTupleElementOfX() {

		assertEquals(a, tuple.get(pointTupleElement));
		assertEquals(ab, tuple.get(segmentTupleElement));
		assertEquals(triangle, tuple.get(polygonTupleElement));

	}

	@Test
	public final void testGetStringClassOfX() {

		assertEquals(a, tuple.get("point", Point.class));
		assertEquals(ab, tuple.get("segment", Segment.class));
		assertEquals(triangle, tuple.get("triangle", Polygon.class));

	}

	@Test
	public final void testGetString() {

		assertEquals(a, tuple.get("point"));
		assertEquals(ab, tuple.get("segment"));
		assertEquals(triangle, tuple.get("triangle"));

	}

	@Test
	public final void testGetIntClassOfX() {

		assertEquals(a, tuple.get(0, Point.class));
		assertEquals(ab, tuple.get(1, Segment.class));
		assertEquals(triangle, tuple.get(2, Polygon.class));

	}

	@Test
	public final void testGetInt() {

		assertEquals(a, tuple.get(0));
		assertEquals(ab, tuple.get(1));
		assertEquals(triangle, tuple.get(2));

	}

	@Test
	public final void testToArray() {
		assertArrayEquals(new Object[] { a, ab, triangle }, tuple.toArray());
	}

	@Test
	public final void testGetElements() {
		assertSame(elements, tuple.getElements());
	}

}
