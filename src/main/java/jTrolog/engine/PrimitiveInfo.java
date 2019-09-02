/*
 * #%L
 * prolobjectlink-jpx-jtrolog
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package jTrolog.engine;

import jTrolog.parser.Parser;
import jTrolog.lib.Library;

import java.lang.reflect.Method;

/**
 * Wrapper for primitive library methods
 * 
 * @author ivar.orstavik@hist.no
 */
class PrimitiveInfo {

	final Method method;
	final Library source;
	final String key;

	PrimitiveInfo(Library lib, Method m, String functor, int arity) {
		key = Parser.wrapAtom(functor) + "/" + arity;
		source = lib;
		method = m;
	}

	public String toString() {
		return "[ primitive: method " + method.getName() + " - " + source.getClass().getName() + " ]\n";
	}
}
