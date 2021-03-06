/*
 * #%L
 * prolobjectlink-jpx-jtrolog
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
package io.github.prolobjectlink;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;

import org.junit.Test;

import io.github.prolobjectlink.db.DatabaseClass;
import io.github.prolobjectlink.db.DatabaseField;
import io.github.prolobjectlink.domain.geometry.Point;
import io.github.prolobjectlink.domain.model.Address;
import io.github.prolobjectlink.domain.model.Employee;
import io.github.prolobjectlink.domain.model.Person;
import io.github.prolobjectlink.domain.model.link.AddressPerson;
import io.github.prolobjectlink.domain.model.link.PersonAddress;

public class DatabaseClassTest extends BaseTest {

	@Test
	public void testGenerate() {

		assertFalse(hschema.getClass(Point.class.getSimpleName()).generate().isEmpty());
		assertFalse(rschema.getClass(Person.class.getSimpleName()).generate().isEmpty());
		assertFalse(rschema.getClass(Employee.class.getSimpleName()).generate().isEmpty());

	}

	@Test
	public void testCompile() throws InstantiationException, IllegalAccessException {

		assertNotNull(hschema.getClass(Point.class.getSimpleName()).compile());
		assertNotNull(rschema.getClass(Address.class.getSimpleName()).compile());
		assertNotNull(rschema.getClass(Person.class.getSimpleName()).compile());
		assertNotNull(rschema.getClass(Employee.class.getSimpleName()).compile());

		assertTrue(hschema.getClass(Point.class.getSimpleName()).compile().length > 0);
		assertTrue(rschema.getClass(Address.class.getSimpleName()).compile().length > 0);
		assertTrue(rschema.getClass(Person.class.getSimpleName()).compile().length > 0);
		assertTrue(rschema.getClass(Employee.class.getSimpleName()).compile().length > 0);

		assertNotNull(hschema.compile().get(0).newInstance());
		assertNotNull(hschema.compile().get(1).newInstance());
		assertNotNull(hschema.compile().get(2).newInstance());
		assertNotNull(hschema.compile().get(3).newInstance());

		assertNotNull(rschema.compile().get(0).newInstance());
		assertNotNull(rschema.compile().get(1).newInstance());
		assertNotNull(rschema.compile().get(2).newInstance());
		assertNotNull(rschema.compile().get(3).newInstance());

	}

	@Test
	public void testCompareTo() {

		DatabaseClass left = rdb.getSchema().addClass("Person", "");
		DatabaseClass right = rdb.getSchema().addClass("Person", "");
		assertEquals(0, left.compareTo(right));

		left = rdb.getSchema().addClass("Person", "");
		left.addField("name", "", 0, String.class);
		right = rdb.getSchema().addClass("Person", "");
		assertEquals(1, left.compareTo(right));

		left = rdb.getSchema().addClass("Person", "");
		right = rdb.getSchema().addClass("Person", "");
		right.addField("name", "", 0, String.class);
		assertEquals(-1, left.compareTo(right));

		left = rdb.getSchema().addClass("Person", "");
		right = rdb.getSchema().addClass("Employee", "");
		assertEquals(11, left.compareTo(right));

		left = rdb.getSchema().addClass("Person", "");
		right = rdb.getSchema().addClass("Address", "");
		assertEquals(15, left.compareTo(right));

		left = rdb.getSchema().addClass("Person", "");
		right = rdb.getSchema().addClass("Department", "");
		assertEquals(12, left.compareTo(right));

	}

	@Test
	public void testGetSchema() {
		assertEquals(rdb.getSchema(), rdb.getSchema().addClass("Person", "").getSchema());
	}

	@Test
	public void testNewInstance() {
		DatabaseClass p = rdb.getSchema().addClass("Person", "");
		p.setJavaClass(Person.class);
		Object o = p.newInstance();
		assertNotNull(o);
	}

	@Test
	public void testGetJavaClass() {
		DatabaseClass p = rdb.getSchema().addClass(Person.class, "");
		assertEquals(Person.class, p.getJavaClass());
	}

	@Test
	public void testSetJavaClass() {
		DatabaseClass p = rdb.getSchema().addClass(Person.class, "");
		assertEquals(Person.class, p.getJavaClass());
	}

	@Test
	public void testIsAbstract() {
		DatabaseClass c = rdb.getSchema().addClass("Person", "");
		assertFalse(c.isAbstract());
		c.setAbstract(true);
		assertTrue(c.isAbstract());
	}

	@Test
	public void testSetAbstract() {
		DatabaseClass c = rdb.getSchema().addClass("Person", "");
		assertFalse(c.isAbstract());
		c.setAbstract(true);
		assertTrue(c.isAbstract());
		c.setAbstract(false);
		assertFalse(c.isAbstract());
	}

	@Test
	public void testGetSuperClass() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		assertEquals(dbp, dbe.getSuperClass());
	}

	@Test
	public void testSetSuperClass() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "");
		dbe.setSuperClass(dbp);
		assertEquals(dbp, dbe.getSuperClass());
	}

	@Test
	public void testHasSuperClasses() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertFalse(dbp.hasSuperClasses());
		assertTrue(dbe.hasSuperClasses());
		assertTrue(dbm.hasSuperClasses());
	}

	@Test
	public void testGetSuperClassesNames() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertTrue(dbm.getSuperClassesNames().contains("Person"));
		assertTrue(dbm.getSuperClassesNames().contains("Employee"));
	}

	@Test
	public void testGetSuperClasses() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertTrue(dbm.getSuperClasses().contains(dbp));
		assertTrue(dbm.getSuperClasses().contains(dbe));
	}

	@Test
	public void testAddSuperClass() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "");
		dbm.addSuperClass(dbe).addSuperClass(dbp);
		assertTrue(dbm.getSuperClasses().contains(dbp));
		assertTrue(dbm.getSuperClasses().contains(dbe));
	}

	@Test
	public void testRemoveSuperClass() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertTrue(dbm.getSuperClasses().contains(dbp));
		assertTrue(dbm.getSuperClasses().contains(dbe));
		dbm.removeSuperClass(dbe).removeSuperClass(dbp);
		assertFalse(dbm.getSuperClasses().contains(dbp));
		assertFalse(dbm.getSuperClasses().contains(dbe));

	}

	@Test
	public void testIsSuperClassOfString() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertTrue(dbp.isSuperClassOf("Manager"));
		assertTrue(dbe.isSuperClassOf("Manager"));
		assertNotNull(dbm);
	}

	@Test
	public void testIsSuperClassOfDatabaseClass() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertTrue(dbp.isSuperClassOf(dbm));
		assertTrue(dbe.isSuperClassOf(dbm));
	}

	@Test
	public void testGetName() {
		DatabaseClass p = rdb.getSchema().addClass(Person.class, "");
		assertEquals(Person.class.getName(), p.getName());
	}

	@Test
	public void testGetShortName() {
		DatabaseClass p = rdb.getSchema().addClass(Person.class, "");
		assertEquals("Person", p.getShortName());
	}

	@Test
	public void testSetShortName() {
		DatabaseClass p = rdb.getSchema().addClass(Person.class, "");
		p.setShortName("X");
		assertEquals("X", p.getShortName());
	}

	@Test
	public void testGetFields() {

		DatabaseClass c = rdb.getSchema().addClass("Person", "");
		DatabaseField firstName = c.addField("firstName", "", 0, String.class);
		DatabaseField middleName = c.addField("middleName", "", 1, String.class);
		DatabaseField lastName = c.addField("lastName", "", 2, String.class);
		DatabaseField id = c.addField("id", "", 3, Integer.class);

		Collection<DatabaseField> fs = c.getFields();
		assertTrue(fs.contains(firstName));
		assertTrue(fs.contains(middleName));
		assertTrue(fs.contains(lastName));
		assertTrue(fs.contains(id));

	}

	@Test
	public void testGetFieldsMap() {

		DatabaseClass c = rdb.getSchema().addClass("Person", "");
		DatabaseField firstName = c.addField("firstName", "", 0, String.class);
		DatabaseField middleName = c.addField("middleName", "", 1, String.class);
		DatabaseField lastName = c.addField("lastName", "", 2, String.class);
		DatabaseField id = c.addField("id", "", 3, Integer.class);

		Map<String, DatabaseField> fs = c.getFieldsMap();
		assertTrue(fs.containsKey("firstName"));
		assertTrue(fs.containsKey("middleName"));
		assertTrue(fs.containsKey("lastName"));
		assertTrue(fs.containsKey("id"));
		assertTrue(fs.containsValue(firstName));
		assertTrue(fs.containsValue(middleName));
		assertTrue(fs.containsValue(lastName));
		assertTrue(fs.containsValue(id));

	}

	@Test
	public void testGetField() {
		DatabaseClass p = rdb.getSchema().addClass("Person", "");
		p.addField("name", "", 0, String.class);
		assertEquals("name", p.getField("name").getName());
	}

	@Test
	public void testHasField() {
		DatabaseClass p = rdb.getSchema().addClass("Person", "");
		p.addField("name", "", 0, String.class);
		assertTrue(p.hasField("name"));
	}

	@Test
	public void testAddFieldStringClassOfQ() {

		DatabaseClass c = rdb.getSchema().addClass("Person", "");
		DatabaseField firstName = c.addField("firstName", "", 0, String.class);
		DatabaseField middleName = c.addField("middleName", "", 1, String.class);
		DatabaseField lastName = c.addField("lastName", "", 2, String.class);
		DatabaseField id = c.addField("id", "", 3, Integer.class);

		Collection<DatabaseField> fs = c.getFields();
		assertTrue(fs.contains(firstName));
		assertTrue(fs.contains(middleName));
		assertTrue(fs.contains(lastName));
		assertTrue(fs.contains(id));

	}

	@Test
	public void testAddFieldStringClassOfQDatabaseClass() {

		DatabaseClass p = rdb.getSchema().addClass("Person", "");
		DatabaseClass pa = rdb.getSchema().addClass("PersonAddress", "");
		DatabaseField address = p.addField("address", "", 0, Address.class, pa);

		assertEquals(pa, address.getLinkedClass());
		Collection<DatabaseField> pfs = p.getFields();
		assertTrue(pfs.contains(address));

		DatabaseClass a = rdb.getSchema().addClass("Address", "");
		DatabaseClass ap = rdb.getSchema().addClass("AddressPerson", "");
		DatabaseField person = a.addField("person", "", 1, Person.class, ap);

		assertEquals(ap, person.getLinkedClass());
		Collection<DatabaseField> afs = a.getFields();
		assertTrue(afs.contains(person));

	}

	@Test
	public void testAddFieldStringClassOfQClassOfQ() {

		DatabaseClass p = rdb.getSchema().addClass("Person", "");
		DatabaseClass pa = rdb.getSchema().addClass(PersonAddress.class, "");
		DatabaseField address = p.addField("address", "", 0, Address.class, PersonAddress.class);

		assertEquals(pa.getJavaClass(), address.getLinkedType());
		Collection<DatabaseField> pfs = p.getFields();
		assertTrue(pfs.contains(address));

		DatabaseClass a = rdb.getSchema().addClass("Address", "");
		DatabaseClass ap = rdb.getSchema().addClass(AddressPerson.class, "");
		DatabaseField person = a.addField("person", "", 1, Person.class, AddressPerson.class);

		assertEquals(ap.getJavaClass(), person.getLinkedType());
		Collection<DatabaseField> afs = a.getFields();
		assertTrue(afs.contains(person));

	}

	@Test
	public void testRemoveField() {
		DatabaseClass p = rdb.getSchema().addClass("Person", "");
		p.addField("name", "", 0, String.class);
		assertTrue(p.hasField("name"));
		p.removeField("name");
		assertFalse(p.hasField("name"));
	}

	@Test
	public void testIsSubClassOfString() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		dbm.isSubClassOf("Person");
		dbm.isSubClassOf("Employee");
	}

	@Test
	public void testIsSubClassOfDatabaseClass() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		dbm.isSubClassOf(dbp);
		dbm.isSubClassOf(dbe);
	}

	@Test
	public void testGetSubclasses() {
		DatabaseClass dbp = rdb.getSchema().addClass("Person", "");
		DatabaseClass dbe = rdb.getSchema().addClass("Employee", "", dbp);
		DatabaseClass dbm = rdb.getSchema().addClass("Manager", "", dbp, dbe);
		assertTrue(dbp.getSubclasses().contains(dbe));
		assertTrue(dbp.getSubclasses().contains(dbm));
	}

}
