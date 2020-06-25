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


/**
 * This class represents an immutable pair of objects.
 * It is useful when dealing with binary relations, since a binary relation
 * can be viewed as a set of pairs.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public final class Pair<A, B> {

    /*************************************************************************\
     *  Attributes
    \*************************************************************************/

    private final A first;
    private final B second;



    /*************************************************************************\
     *  Constructors
    \*************************************************************************/

    /**
     *  Empty constructor of objects of class Pair.
     *  Builds a new Pair of {@code (null, null)}.
     */
    public Pair() { this(null, null); }


    /**
     *  Parameter constructor of objects of class Pair.
     *  
     *  @param a The first component of the pair.
     *  @param b The second component of the pair.
     */
    public Pair(final A a, final B b) {
        first = a;
        second = b;
    }



    /*************************************************************************\
     *  Getters
    \*************************************************************************/

    /**
     * Returns the first component of the pair.
     */
    public A first() {
        return first;
    }

    /**
     * Returns the second component of the pair.
     */
    public B second() {
        return second;
    }

    /**
     * Returns the arity of the tuple (the constant {@code 2}).
     */
    public int arity() {
        return 2;
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
        if (!(o instanceof Pair)) { return false; }
        final Pair<?, ?> n = (Pair<?, ?>) o;
        return (first == null ? n.first == null : first.equals(n.first))
            && (second == null ? n.second == null : second.equals(n.second));
    }


    /**
     *  Contract:
     *      Consistency: successive calls return the same code,
     *          assuming no modification of the equality fields.
     *      Function: two equal objects have the same (unique) hash code.
     *
     *  Formula:
     *      (first()    == null ? 0 : first().hashCode()) ^
     *      (second()   == null ? 0 : second().hashCode())
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^
                (second == null ? 0 : second.hashCode());
    }


    /**
     *  Returns a string representation of the object.
     */
    @Override
    public String toString() {
        final String s1 = first.toString();
        final String s2 = second.toString();
        return new StringBuilder(4 + s1.length() + s2.length())
            .append('(')
            .append(s1)
            .append(',')
            .append(s2)
            .append(')')
            .toString();
    }
}
