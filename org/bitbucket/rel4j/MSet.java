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

import java.util.Iterator;


/**
 * A mathematical Set.
 * 
 * @author Andre Santos
 * @since 0.2
 * @version 0.2
 */

public interface MSet<E> extends Iterable<E> {
    /**
     * Returns the number of elements in this set (its cardinality).
     * If this set contains more than {@code Integer.MAX_VALUE} elements,
     * returns a cardinality of {@code Integer.MAX_VALUE}.
     * If this set is infinite, returns an infinite cardinality.
     * 
     * @return the number of elements in this set (its cardinality)
     */
    Cardinal cardinality();

    /**
     * Returns {@code true} if this set contains no elements.
     * 
     * @return true if this set contains no elements
     */
    boolean isEmpty();

    /**
     * Returns {@code true} if this set's cardinality is finite.
     * 
     * @return true if this set is finite
     */
    boolean isFinite();

    /**
     * Returns {@code true} if this set contains the specified element.
     * More formally, returns {@code true} if and only if this set contains
     * an element {@code e} such that {@code o.equals(e)}.
     * 
     * @param o non-null element whose presence in this set is to be tested
     * @return true if this set contains the specified element
     */
    boolean contains(Object o);

    /**
     * Compares the specified object with this set for equality.
     * Returns {@code true} if the specified object is also a set,
     * the two sets have the same cardinality,
     * and every member of the specified set is contained in this set
     * (or equivalently, every member of this set is contained in the specified set).
     * This definition ensures that the equals method works properly across different
     * implementations of the set interface.
     * 
     * @param o object to be compared for equality with this set
     * @return true if the specified object is equal to this set
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns the hash code value for this set.
     * The hash code of a set is defined to be the sum of the hash codes
     * of the elements in the set.
     * This ensures that {@code s1.equals(s2)} implies that
     * {@code s1.hashCode()==s2.hashCode()} for any two sets
     * {@code s1} and {@code s2}, as required by the general contract
     * of {@code Object.hashCode()}.
     * 
     * @return the hash code value for this set
     */
    @Override
    int hashCode();

    /**
     * Returns an iterator over the elements in this set.
     * The elements are returned in no particular order
     * (unless this set is an instance of some class that provides a guarantee).
     * <b>This operation is optional for infinite sets</b>.
     * 
     * @return an iterator over the elements in this set
     */
    @Override
    Iterator<E> iterator();
}
