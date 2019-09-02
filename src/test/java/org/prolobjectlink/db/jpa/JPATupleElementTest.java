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
