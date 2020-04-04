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
package org.prolobjectlink.performance;

import org.prolobjectlink.db.prolog.jtrolog.JTrologContainerFactory;
import org.prolobjectlink.domain.geometry.Point;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.Storage;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.prolog.Prolog;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.jtrolog.JTrolog;

public class MainPerformanceTest {

	// private static ObjectCache cache;
	private static Storage store;

	private static final int instanceNumber = 5000;

	private static final String LOCATION = "performance";
	// private static final String ROOT = "data" + File.separator + "test";

	protected static final Class<? extends ContainerFactory> driver = JTrologContainerFactory.class;

	// protected static final Class<?> engine = JBPEPrologEngine.class;
	// protected static final Class<?> engine = JLogProvider.class;
	protected static final Class<? extends PrologProvider> engine = JTrolog.class;
	// protected static final Class<?> engine = TuPrologProvider.class;
	// protected static final Class<?> engine = SwiPrologProvider.class;
	// protected static final Class<?> engine = ZPrologProvider.class;

	protected static final PrologProvider prolog = Prolog.getProvider(engine);

	public MainPerformanceTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// cache = Prolobjectlink.create(ENGINE).createCache();
		store = new Settings(driver).createStorage(LOCATION);

		Point[] array = new Point[instanceNumber];
		for (int i = 0; i < array.length; i++) {
			// array[i] = new Point("" + i + "", i, i);
			array[i] = new Point("a", i, i);
		}

		// bulk addition
		long startTimeMillis = System.currentTimeMillis();

		// cache.insert(array);
		store.insert(array);
		store.flush();

		long endTimeMillis = System.currentTimeMillis();
		float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Bulk Add Duration: " + durationSeconds + " seconds");
		System.out.println();

		// contains
		startTimeMillis = System.currentTimeMillis();

		int last = instanceNumber - 1;
		// cache.contains(new Point("" + last + "", last, last));
		// store.contains(new Point("" + last + "", last, last));
		// System.out.println(cache.contains(new Point("" + last + "", last,
		// last)));
		// System.out.println(store.contains(new Point("a", last, last)));

		System.out.println(store.contains("'" + Point.class.getName() + "'(a, 999.0, 999.0)"));

		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Contains Duration: " + durationSeconds + " seconds");
		System.out.println();

		// find all
		startTimeMillis = System.currentTimeMillis();

		// cache.findAll(Point.class);
		// store.findAll(Point.class);
		// System.out.println(cache.findAll(Point.class).size());
		System.out.println(store.findAll(Point.class).size());

		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Find All Duration: " + durationSeconds + " seconds");
		System.out.println();

	}

}
