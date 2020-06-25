/*
 *  Copyright (c) 2013 Andre Santos
 *
 *  Permission is hereby granted, free of charge,
 *  to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use, copy,
 *  modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included
 *  in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 *  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 *  CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 *  TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 *  OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.bitbucket.rel4j;

import static org.bitbucket.rel4j.Check.checkNotNull;

import java.util.Set;

/**
 * Represents a domain, an abstract set of elements of a given type.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public final class Domain<T> {

    /*************************************************************************\
     *  Attributes
    \*************************************************************************/

    /** The enumerator that checks whether a set represents this domain. */
    private final DomainEnumerator<T> enumerator;



    /*************************************************************************\
     *  Constructors
    \*************************************************************************/

    /**
     *  Empty constructor of objects of class {@code Domain}.
     */
    public Domain() {
        enumerator = new NullEnumerator<T>();
    }


    /**
     *  Parameter constructor of objects of class {@code Domain}.
     *  
     *  @throws NullPointerException if {@code enumerator} is {@code null}.
     */
    public Domain(final DomainEnumerator<T> enumerator) {
        checkNotNull(enumerator);
        this.enumerator = enumerator;
    }



    /*************************************************************************\
     *  Getters
    \*************************************************************************/

    /**
     * Returns the enumerator associated with this domain.
     */
    public DomainEnumerator<T> getEnumerator() {
        return enumerator;
    }



    /*************************************************************************\
     *  Predicates
    \*************************************************************************/

    /**
     * Checks whether the given set represents this domain.
     * 
     * @throws NullPointerException if {@code set} is {@code null}.
     */
    public boolean isEntire(final Set<? extends T> set) {
        checkNotNull(set);
        return enumerator.isEntire(set);
    }



    /*************************************************************************\
     *  Equals, HashCode & ToString
    \*************************************************************************/

    /**
     *  Equivalence relation.
     *  Contract (for any non-null reference values x, y, and z):
     *      Reflexive: x.equals(x).
     *      Symmetric: x.equals(y) iff y.equals(x).
     *      Transitive: if x.equals(y) and y.equals(z), then x.equals(z).
     *      Consistency: successive calls return the same result,
     *          assuming no modification of the equality fields.
     *      x.equals(null) should return false.
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Domain)) { return false; }
        final Domain<?> n = (Domain<?>) o;
        return enumerator.equals(n.enumerator);
    }


    /**
     *  Contract:
     *      Consistency: successive calls return the same code,
     *          assuming no modification of the equality fields.
     *      Function: two equal objects have the same (unique) hash code.
     *      (Optional) Injection: unequal objects have different hash codes.
     *
     *  Common practices:
     *      boolean: calculate (f ? 0 : 1);
     *      byte, char, short or int: calculate (int) f;
     *      long: calculate (int) (f ^ (f >>> 32));
     *      float: calculate Float.floatToIntBits(f);
     *      double: calculate Double.doubleToLongBits(f)
     *          and handle the return value like every long value;
     *      Object: use (f == null ? 0 : f.hashCode());
     *      Array: recursion and combine the values.
     *
     *  Formula:
     *      hash = prime * hash + codeForField
     */
    @Override
    public int hashCode() {
        return enumerator.hashCode();
    }


    /**
     *  Returns a string representation of the object.
     */
    @Override
    public String toString() {
        return "Domain";
    }



    /*************************************************************************\
     *  Nested Classes
    \*************************************************************************/

    /**
     * This enumerator always returns {@code false} for its
     * {@code isEntire} method.
     * It is the default enumerator, when none is supplied.
     */
    private static final class NullEnumerator<T>
            implements DomainEnumerator<T> {
        /**
         * Returns {@code false}.
         * It is assumed, by default, that domains are potentially
         * infinite sets.
         * 
         * @throws NullPointerException if the given set is {@code null}.
         */
        @Override
        public boolean isEntire(final Set<? extends T> set) {
            checkNotNull(set);
            return false;
        }


        /** */
        @Override
        public boolean equals(final Object o) {
            return this == o || o instanceof NullEnumerator;
        }

        /** */
        @Override
        public int hashCode() {
            return 17;
        }
    }
}
