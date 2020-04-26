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
package io.github.prolobjectlink.db.prolog.jtrolog;

import io.github.prolobjectlink.prolog.jtrolog.JTrolog;
import io.github.prolobjectlink.web.prolog.PrologWebEngine;
import io.github.prolobjectlink.web.prolog.PrologWebProvider;
import jTrolog.engine.Prolog;

public class JTrologWebProvider extends JTrolog implements PrologWebProvider {

	public PrologWebEngine newEngine() {
		Prolog prolog = new Prolog();
		return new JTrologWebEngine(this, prolog);
	}

	public PrologWebEngine newEngine(String path) {
		PrologWebEngine engine = newEngine();
		engine.consult(path);
		return engine;
	}

}
