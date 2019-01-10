/*
 * #%L
 * prolobjectlink-db-jtrolog
 * %%
 * Copyright (C) 2012 - 2019 WorkLogic Project
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import org.junit.Test;
import org.worklogic.db.util.JavaMaps;

public class JavaMapsTest {

	@Test
	public void testTreeMap() {
		assertEquals(new TreeMap<Object, Object>(), JavaMaps.treeMap());
	}

	@Test
	public void testTreeMapMapOfQextendsKQextendsV() {
		assertEquals(new TreeMap<String, Object>(), JavaMaps.treeMap(new TreeMap<String, Object>()));
	}

	@Test
	public void testHashMap() {
		assertEquals(new HashMap<Object, Object>(), JavaMaps.hashMap());
	}

	@Test
	public void testHashMapInt() {
		assertEquals(new HashMap<Object, Object>(16), JavaMaps.hashMap(16));
	}

	@Test
	public void testHashMapMapOfQextendsKQextendsV() {
		assertEquals(new HashMap<Object, Object>(), JavaMaps.hashMap(new HashMap<Object, Object>()));
	}

	@Test
	public void testLinkedHashMap() {
		assertEquals(new LinkedHashMap<Object, Object>(), JavaMaps.linkedHashMap());
	}

	@Test
	public void testLinkedHashMapInt() {
		assertEquals(new LinkedHashMap<Object, Object>(16), JavaMaps.linkedHashMap(16));
	}

	@Test
	public void testLinkedHashMapMapOfQextendsKQextendsV() {
		assertEquals(new LinkedHashMap<Object, Object>(), JavaMaps.linkedHashMap(new LinkedHashMap<Object, Object>()));
	}

}
