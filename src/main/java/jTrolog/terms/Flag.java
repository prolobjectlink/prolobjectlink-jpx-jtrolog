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
/*
 * tuProlog - Copyright (C) 2001-2002  aliCE team at deis.unibo.it
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package jTrolog.terms;

/**
 * This class represents a prolog Flag
 */
@SuppressWarnings("serial")
public class Flag implements java.io.Serializable {

	private String name;
	private Struct valueList;
	private Term value;
	private boolean modifiable;

	/**
	 * Builds a Prolog flag
	 * 
	 * @param name
	 *            is the name of the flag
	 * @param valueSet
	 *            is the Prolog list of the possible values
	 * @param defValue
	 *            is the default value
	 * @param modifiable
	 *            states if the flag is modifiable
	 */
	public Flag(String name, Struct valueSet, Term defValue, boolean modifiable) {
		this.name = name;
		this.valueList = valueSet;
		this.modifiable = modifiable;
		value = defValue;
	}

	/**
	 * @return the name of the flag
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return a Prolog list of the flag's possible values
	 */
	public Struct getValueList() {
		return valueList;
	}

	/**
	 * Sets the value of a flag
	 * 
	 * @param value
	 *            new value of the flag
	 */
	public void setValue(Term value) {
		if (modifiable)
			this.value = value;
	}

	/**
	 * @return the current value of the flag
	 */
	public Term getValue() {
		return value;
	}

	/**
	 * @return true if the value is modifiable
	 */
	public boolean isModifiable() {
		return modifiable;
	}
}
