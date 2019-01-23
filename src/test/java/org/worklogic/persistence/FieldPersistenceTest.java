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
package org.worklogic.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.worklogic.BaseTest;
import org.worklogic.domain.classes.StaticFieldClass;

public class FieldPersistenceTest extends BaseTest {

	@Test
	public final void testStatic() {

		storage.getTransaction().begin();
		storage.insert(new StaticFieldClass());
		assertTrue(storage.contains(StaticFieldClass.class));
		assertTrue(storage.contains(new StaticFieldClass()));
		assertEquals(new StaticFieldClass(), storage.find(new StaticFieldClass()));
		storage.delete(new StaticFieldClass());
		assertFalse(storage.contains(StaticFieldClass.class));
		assertFalse(storage.contains(new StaticFieldClass()));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

}
