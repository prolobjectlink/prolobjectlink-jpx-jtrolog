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
package org.prolobjectlink.db.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.prolobjectlink.BaseTest;
import org.prolobjectlink.domain.geometry.Point;
import org.prolobjectlink.domain.geometry.Segment;

public class JPATupleElementTest extends BaseTest {

	private JpaTupleElement<Point> pointTupleElement = new JpaTupleElement<Point>("point", Point.class);
	private JpaTupleElement<Segment> segmentTupleElement = new JpaTupleElement<Segment>("segment", Segment.class);

	@Test
	public final void testHashCode() {
		assertEquals(new JpaTupleElement<Point>("point", Point.class).hashCode(), pointTupleElement.hashCode());
		assertEquals(new JpaTupleElement<Segment>("segment", Segment.class).hashCode(), segmentTupleElement.hashCode());
	}

	@Test
	public final void testGetJavaType() {
		assertEquals(Point.class, pointTupleElement.getJavaType());
		assertEquals(Segment.class, segmentTupleElement.getJavaType());
	}

	@Test
	public final void testGetAlias() {
		assertEquals("point", pointTupleElement.getAlias());
		assertEquals("segment", segmentTupleElement.getAlias());
	}

	@Test
	public final void testGetValue() {
		assertNull(pointTupleElement.getValue());
		assertNull(segmentTupleElement.getValue());
	}

	@Test
	public final void testEqualsObject() {
		assertEquals(new JpaTupleElement<Point>("point", Point.class), pointTupleElement);
		assertEquals(new JpaTupleElement<Segment>("segment", Segment.class), segmentTupleElement);
	}

}
