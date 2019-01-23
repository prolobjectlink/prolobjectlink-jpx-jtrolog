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
package org.worklogic.performance;

import org.logicware.prolog.Prolog;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.jtrolog.JTrolog;
import org.worklogic.db.ContainerFactory;
import org.worklogic.db.StoragePool;
import org.worklogic.db.etc.Settings;
import org.worklogic.domain.geometry.Point;
import org.worklogic.prolog.jtrolog.JTrologContainerFactory;

public class StoragePoolPerformanceTest {

	static final Class<? extends ContainerFactory> driver = JTrologContainerFactory.class;
	static final Class<? extends PrologProvider> engine = JTrolog.class;
	static final PrologProvider prolog = Prolog.newProvider(engine);

	public static void main(String[] args) {

		StoragePool pool = new Settings(driver).createStoragePool("stress", "test");

		Point[] array = new Point[100000];
		for (int i = 0; i < array.length; i++) {
			// array[i] = new Point("" + i + "", i, i);
			array[i] = new Point("a", i, i);
		}

		// bulk addition
		long startTimeMillis = System.currentTimeMillis();
		pool.insert(array);
		pool.flush();
		// pool.clear();
		// pool.close();
		long endTimeMillis = System.currentTimeMillis();
		float durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Bulk Add Duration: " + durationSeconds + " seconds");
		System.out.println();

		// contains
		startTimeMillis = System.currentTimeMillis();
		// pool.open();
		System.out.println(pool.contains("'" + Point.class.getName() + "'(a, 999.0, 999.0)"));
		// pool.clear();
		// pool.close();
		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Contains Duration: " + durationSeconds + " seconds");
		System.out.println();

		// find all
		startTimeMillis = System.currentTimeMillis();
		// pool.open();
		System.out.println(pool.findAll(Point.class).size());
		// pool.clear();
		// pool.close();
		endTimeMillis = System.currentTimeMillis();
		durationSeconds = (endTimeMillis - startTimeMillis) / 1000F;
		System.out.println("Find All Duration: " + durationSeconds + " seconds");
		System.out.println();

	}

}
