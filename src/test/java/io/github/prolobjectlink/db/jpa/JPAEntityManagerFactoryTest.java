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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Ignore;
import org.junit.Test;

import io.github.prolobjectlink.BaseTest;
import io.github.prolobjectlink.db.jpa.JpaEntityManagerFactory;
import io.github.prolobjectlink.domain.model.Person;

public class JPAEntityManagerFactoryTest extends BaseTest {

	@Test
	public final void testCreateEntityManager() {
		assertNotNull(JPA_EMF);
		assertNotNull(JPA_EMF.createEntityManager());
	}

	@Test
	public final void testCreateEntityManagerMap() {
		assertNotNull(JPA_EMF);
		assertNotNull(JPA_EMF.createEntityManager(new HashMap<String, Object>()));
	}

	@Test
	public final void testCreateEntityManagerSynchronizationType() {
		assertEquals(JPA_EM, JPA_EMF.createEntityManager(SynchronizationType.UNSYNCHRONIZED));
	}

	@Test
	@Ignore
	public final void testCreateEntityManagerSynchronizationTypeMap() {
		assertEquals(JPA_EM, JPA_EMF.createEntityManager(SynchronizationType.UNSYNCHRONIZED, properties));
	}

	@Test
	public final void testGetCriteriaBuilder() {
		CriteriaBuilder cb = JPA_EMF.getCriteriaBuilder();
		CriteriaQuery<Person> q = cb.createQuery(Person.class);
		Root<Person> p = q.from(Person.class);
		q.select(p).orderBy(cb.desc(p.get("id")));
		assertEquals("SELECT p FROM Person p ORDER BY p.id DESC", "" + q + "");
	}

	@Test
	public final void testGetMetamodel() {
		assertNotNull(JPA_EMF.getMetamodel());
	}

	@Test
	public final void testIsOpen() {
		assertTrue(JPA_EMF.isOpen());
	}

	@Test
	public final void testClose() {
		JPA_EMF.close();
		assertFalse(JPA_EMF.isOpen());
	}

	@Test
	@Ignore
	public final void testGetProperties() {
		assertEquals(properties, JPA_EMF.getProperties());
	}

	@Test
	public final void testGetCache() {
		assertNotNull(JPA_EMF.getCache());
	}

	@Test
	public final void testGetPersistenceUnitUtil() {
		assertNotNull(JPA_EMF.getPersistenceUnitUtil());
	}

	@Test
	public final void testAddNamedQuery() {
		JPA_EMF.addNamedQuery("allPersons", JPA_EM.createQuery("SELECT p FROM Person p"));
		assertEquals(1, JPA_EMF.unwrap(JpaEntityManagerFactory.class).getNamedQueries().size());
	}

	@Test
	public final void testUnwrap() {
		assertSame(JPA_EMF, JPA_EMF.unwrap(JpaEntityManagerFactory.class));
	}

	@Test
	public final void testAddNamedEntityGraph() {
		JPA_EMF.addNamedEntityGraph("Person", JPA_EM.createEntityGraph(Person.class));
		assertEquals(1, JPA_EMF.unwrap(JpaEntityManagerFactory.class).getNamedEntityGraphs().size());
	}

}
