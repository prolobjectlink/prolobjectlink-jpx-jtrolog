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
package org.worklogic.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.worklogic.BaseTest;
import org.worklogic.domain.classes.OuterInnerClass;

public class InnerClassPersistenceTest extends BaseTest {

	@Test
	public final void testOuterClass() {

		storage.getTransaction().begin();
		storage.insert(new OuterInnerClass(60));
		assertTrue(storage.contains(new OuterInnerClass(60)));
		assertEquals(new OuterInnerClass(60), storage.find(new OuterInnerClass(60)));
		storage.delete(new OuterInnerClass(60));
		assertFalse(storage.contains(new OuterInnerClass(60)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testPublicInnerClass() {

		storage.getTransaction().begin();
		storage.insert(new OuterInnerClass(60).new PublicInnerClass(100));
		assertTrue(storage.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		assertEquals(new OuterInnerClass(60).new PublicInnerClass(100),
				storage.find(new OuterInnerClass(60).new PublicInnerClass(100)));
		storage.delete(new OuterInnerClass(60).new PublicInnerClass(100));
		assertFalse(storage.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	public final void testPublicInnerClass2() {

		storageManager.getTransaction().begin();
		storageManager.insert(new OuterInnerClass(60).new PublicInnerClass(100));
		assertTrue(storageManager.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		assertEquals(new OuterInnerClass(60).new PublicInnerClass(100),
				storageManager.createQuery((new OuterInnerClass(60).new PublicInnerClass(100))).getSolution());
		storageManager.delete(new OuterInnerClass(60).new PublicInnerClass(100));
		assertFalse(storageManager.contains(new OuterInnerClass(60).new PublicInnerClass(100)));
		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

	@Test
	public final void testPrivateInnerClass() {

		storage.getTransaction().begin();
		storage.insert(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertTrue(storage.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		assertEquals(new OuterInnerClass(60).newPrivateInnerClass(100),
				storage.find(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storage.delete(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertFalse(storage.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storage.getTransaction().commit();
		storage.getTransaction().close();

	}

	@Test
	@Ignore
	public final void testPrivateInnerClass2() {

		storageManager.getTransaction().begin();
		storageManager.insert(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertTrue(storageManager.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		assertEquals(new OuterInnerClass(60).newPrivateInnerClass(100),
				storageManager.createQuery(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storageManager.delete(new OuterInnerClass(60).newPrivateInnerClass(100));
		assertFalse(storageManager.contains(new OuterInnerClass(60).newPrivateInnerClass(100)));
		storageManager.getTransaction().commit();
		storageManager.getTransaction().close();

	}

}
