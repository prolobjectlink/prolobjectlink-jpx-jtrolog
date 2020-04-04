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
package org.prolobjectlink.db.prolog.jtrolog;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.HierarchicalCache;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.prolog.PrologHierarchicalCache;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

public class JTrologHierarchicalCache extends PrologHierarchicalCache implements HierarchicalCache {

	public JTrologHierarchicalCache(PrologProvider provider, Settings settings, ContainerFactory containerFactory) {
		super(provider, settings, new JTrologContainerFactory(settings));
	}

	public JTrologHierarchicalCache(PrologProvider provider, Settings settings, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory) {
		super(provider, settings, converter, new JTrologContainerFactory(settings));
	}

}
