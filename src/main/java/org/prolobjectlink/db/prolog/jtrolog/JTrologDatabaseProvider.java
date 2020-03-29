/*-
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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

import org.prolobjectlink.db.prolog.PrologDatabaseEngine;
import org.prolobjectlink.db.prolog.PrologDatabaseProvider;

import io.github.prolobjectlink.prolog.jtrolog.JTrolog;
import jTrolog.engine.Prolog;

public class JTrologDatabaseProvider extends JTrolog implements PrologDatabaseProvider {

	public PrologDatabaseEngine newEngine() {
		Prolog prolog = new Prolog();
		return new JTrologDatabaseEngine(this, prolog);
	}

	public PrologDatabaseEngine newEngine(String path) {
		PrologDatabaseEngine engine = newEngine();
		engine.consult(path);
		return engine;
	}

}
