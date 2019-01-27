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
package org.worklogic.util;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

import org.junit.Test;
import org.worklogic.db.util.JavaSets;

public class JavaSetsTest {

	@Test
	public void testTreeSet() {
		assertEquals(new TreeSet<Object>(), JavaSets.treeSet());
	}

	@Test
	public void testTreeSetCollectionOfQextendsT() {
		assertEquals(new TreeSet<String>(), JavaSets.treeSet(new TreeSet<String>()));
	}

	@Test
	public void testHashSet() {
		assertEquals(new HashSet<Object>(), JavaSets.hashSet());
	}

	@Test
	public void testLinkedHashSet() {
		assertEquals(new LinkedHashSet<Object>(), JavaSets.linkedHashSet());
	}

	@Test
	public void testHashSetInt() {
		assertEquals(new HashSet<Object>(16), JavaSets.hashSet(16));
	}

	@Test
	public void testLinkedHashSetInt() {
		assertEquals(new LinkedHashSet<Object>(16), JavaSets.linkedHashSet(16));
	}

	@Test
	public void testHashSetCollectionOfQextendsT() {
		assertEquals(new HashSet<Object>(), JavaSets.hashSet(new HashSet<Object>()));
	}

}
