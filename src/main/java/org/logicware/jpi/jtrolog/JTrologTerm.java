/*
 * #%L
 * prolobjectlink-jtrolog
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpi.jtrolog;

import java.util.Iterator;

import org.logicware.jpi.AbstractTerm;
import org.logicware.jpi.NumberExpectedError;
import org.logicware.jpi.PrologNumber;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

import jTrolog.engine.Prolog;
import jTrolog.terms.Double;
import jTrolog.terms.Float;
import jTrolog.terms.Int;
import jTrolog.terms.Long;
import jTrolog.terms.Number;
import jTrolog.terms.Struct;
import jTrolog.terms.StructAtom;
import jTrolog.terms.Term;
import jTrolog.terms.Var;

public abstract class JTrologTerm extends AbstractTerm implements PrologTerm {

    // variable index
    protected int vIndex;
    protected Term value;
    // protected static int vIndexer;

    static final String SIMPLE_ATOM_REGEX = "\\.|\\?|#|[a-z][A-Za-z0-9_]*";

    protected JTrologTerm(int type, PrologProvider provider) {
	super(type, provider);
    }

    protected JTrologTerm(int type, PrologProvider provider, Term value) {
	super(type, provider);
	this.value = value;
    }

    /**
     * Variable constructor
     * 
     * @param type
     * @param provider
     * @param n
     */
    protected JTrologTerm(int type, PrologProvider provider, String name, int n) {
	this(type, provider, new Var(name, n));
	this.vIndex = n;
    }

    protected final void checkNumberType(PrologTerm term) {
	if (!term.isNumber()) {
	    throw new NumberExpectedError(term);
	}
    }

    public final boolean isAtom() {
	return value instanceof StructAtom;
    }

    public final boolean isNumber() {
	return value instanceof Number;
    }

    public final boolean isFloat() {
	return value instanceof Float && !isDouble();
    }

    public final boolean isDouble() {
	return value instanceof Double;
    }

    public final boolean isInteger() {
	return value instanceof Int && !isLong();
    }

    public final boolean isLong() {
	return value instanceof Long;
    }

    public final boolean isVariable() {
	return value instanceof Var;
    }

    public final boolean isList() {
	if (value == Term.emptyList) {
	    return true;
	} else if (value instanceof Struct) {
	    Struct s = (Struct) value;
	    return (s.name.equals(".") && s.arity == 2);
	}
	return false;
    }

    public final boolean isStructure() {
	if (!isAtom() && !isList()) {
	    return value instanceof Struct;
	}
	return false;
    }

    public final boolean isNil() {
	if (!isVariable() && !isNumber()) {
	    return hasIndicator("nil", 0);
	}
	return false;
    }

    public final boolean isEmptyList() {
	return value == Term.emptyList;
    }

    public final boolean isEvaluable() {
	Prolog prolog = new Prolog();
	Iterator<?> i = prolog.getCurrentOperators();
	while (i.hasNext()) {
	    Object object = (Object) i.next();
	    boolean valueIsStrct = (value instanceof Struct);
	    boolean objectIsStrct = (object instanceof Struct);
	    if (valueIsStrct && objectIsStrct) {
		Struct op = (Struct) object;
		Struct vop = (Struct) value;
		if (op.arity == 3) {
		    if (((StructAtom) op.getArg(2)).name.equals(vop.name)) {
			return true;
		    }
		}
	    }
	}
	return false;
    }

    public final boolean isAtomic() {
	return !isCompound();
    }

    public final boolean isCompound() {
	return isList() || isStructure();
    }

    public abstract String getIndicator();

    public abstract boolean hasIndicator(String functor, int arity);

    public abstract int getArity();

    public abstract String getFunctor();

    public abstract PrologTerm[] getArguments();

    public final boolean unify(PrologTerm term) {
	Term otherTerm = fromTerm(term, Term.class);
	return Prolog.match(value, otherTerm);
    }

    public final int compareTo(PrologTerm term) {
	int termType = term.getType();

	if ((type >> 8) < (termType >> 8)) {
	    return -1;
	} else if ((type >> 8) > (termType >> 8)) {
	    return 1;
	}

	switch (type) {
	case ATOM_TYPE:

	    // alphabetic functor comparison
	    StructAtom atom = (StructAtom) value;
	    int result = atom.name.compareTo(term.getFunctor());
	    if (result < 0) {
		return -1;
	    } else if (result > 0) {
		return 1;
	    }
	    break;

	case FLOAT_TYPE: {

	    checkNumberType(term);
	    float thisValue = ((Number) value).floatValue();
	    float otherValue = ((PrologNumber) term).getFloatValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case LONG_TYPE: {

	    checkNumberType(term);
	    long thisValue = ((Number) value).longValue();
	    long otherValue = ((PrologNumber) term).getLongValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case DOUBLE_TYPE: {

	    checkNumberType(term);
	    double thisValue = ((Number) value).doubleValue();
	    double otherValue = ((PrologNumber) term).getDoubleValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case INTEGER_TYPE: {

	    checkNumberType(term);
	    int thisValue = ((Number) value).intValue();
	    int otherValue = ((PrologNumber) term).getIntValue();

	    if (thisValue < otherValue) {
		return -1;
	    } else if (thisValue > otherValue) {
		return 1;
	    }

	}
	    break;

	case LIST_TYPE:
	case EMPTY_TYPE:
	case STRUCTURE_TYPE:
	case EXPRESSION_TYPE:

	    PrologTerm thisCompound = this;
	    PrologTerm otherCompound = term;

	    // comparison by arity
	    if (thisCompound.getArity() < otherCompound.getArity()) {
		return -1;
	    } else if (thisCompound.getArity() > otherCompound.getArity()) {
		return 1;
	    }

	    // alphabetic functor comparison
	    result = thisCompound.getFunctor().compareTo(otherCompound.getFunctor());
	    if (result < 0) {
		return -1;
	    } else if (result > 0) {
		return 1;
	    }

	    // arguments comparison
	    PrologTerm[] thisArguments = thisCompound.getArguments();
	    PrologTerm[] otherArguments = otherCompound.getArguments();

	    for (int i = 0; i < thisArguments.length; i++) {
		PrologTerm thisArgument = thisArguments[i];
		PrologTerm otherArgument = otherArguments[i];
		if (thisArgument != null && otherArgument != null) {
		    result = thisArgument.compareTo(otherArgument);
		    if (result != 0) {
			return result;
		    }
		}
	    }
	    break;

	case VARIABLE_TYPE:

	    PrologTerm thisVariable = this;
	    PrologTerm otherVariable = (PrologTerm) term;
	    if (thisVariable.hashCode() < otherVariable.hashCode()) {
		return -1;
	    } else if (thisVariable.hashCode() > otherVariable.hashCode()) {
		return 1;
	    }
	    break;

	}

	return 0;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + type;
	// result = prime * result + ((value == null) ? 0 : value.hashCode());
	result = prime * result + ((value == null) ? 0 : value.toString().hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	JTrologTerm other = (JTrologTerm) obj;
	if (type != other.type)
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	return true;
    }

    @Override
    public final String toString() {
	if (value instanceof Struct) {
	    Struct s = (Struct) value;
	    String n = s.name;
	    if (!isEmptyList()) {
		if (!n.startsWith("'") && !n.endsWith("'")) {
		    if (!n.matches(SIMPLE_ATOM_REGEX)) {
			return "'" + n + "'";
		    }
		}
	    }
	}
	return "" + value + "";
    }

    @Override
    public abstract PrologTerm clone();

}
