/*
 * #%L
 * prolobjectlink-jpx-jtrolog
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
package org.prolobjectlink.db.prolog.jtrolog;

import org.prolobjectlink.db.HierarchicalCache;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.prolog.PrologContainerFactory;

import io.github.prolobjectlink.prolog.jtrolog.JTrolog;

public final class JTrologContainerFactory extends PrologContainerFactory {

	public JTrologContainerFactory(Settings settings) {
		super(settings, new JTrologDatabaseProvider());
	}

	public HierarchicalCache createHierarchicalCache() {
		return new JTrologHierarchicalCache(getProvider(), getSettings(), this);
	}

}
