/*-
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.jpa;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.List;

import javax.persistence.TupleElement;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Polygon;
import org.prolobjectlink.domain.geometry.Segment;

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
