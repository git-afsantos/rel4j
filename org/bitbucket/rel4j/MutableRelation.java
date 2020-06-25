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

import java.util.Set;


/**
 * Representation of a <b>mutable</b> binary relation.
 * This interface adds methods to the {@code Relation} interface that allow
 * an user to modify an existing relation, by adding or removing arrows.
 * 
 * As declared in the {@code Relation} interface, a binary relation does not
 * allow {@code null} keys or values.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public interface MutableRelation<A, B> extends Relation<A, B> {

    /*************************************************************************\
     *  Insertion Methods
    \*************************************************************************/

    /**
     * Inserts the given key-value pair into the relation.
     * 
     * @return {@code true} if the relation was modified.
     */
    boolean put(A a, B b);

    /**
     * Inserts arrows into the relation from all given keys
     * to the given value.
     * 
     * @return {@code true} if the relation was modified.
     */
    boolean putAllKeys(Iterable<? extends A> as, B b);

    /**
     * Inserts arrows into the relation from the given key
     * to all given values.
     * 
     * @return {@code true} if the relation was modified.
     */
    boolean putAllValues(A a, Iterable<? extends B> bs);

    /**
     * Inserts arrows into the relation from all given keys
     * to all given values.
     * 
     * @return {@code true} if the relation was modified.
     */
    boolean putAll(Iterable<? extends A> as, Iterable<? extends B> bs);



    /*************************************************************************\
     *  Removal Methods
    \*************************************************************************/

    /**
     * Removes the given key-value pair from the relation.
     * 
     * @return {@code true} if the relation was modified.
     */
    boolean remove(Object a, Object b);

    /**
     * Removes all arrows in the relation from the given key.
     * 
     * @return The set of values to which the key had arrows.
     */
    Set<B> removeKey(Object a);

    /**
     * Removes all arrows in the relation from the given keys.
     * 
     * @return The union of sets of values to which the keys had arrows.
     */
    Set<B> removeKeys(Iterable<?> as);

    /**
     * Removes all arrows in the relation to the given value.
     * 
     * @return The set of keys from which the value had arrows.
     */
    Set<A> removeValue(Object b);

    /**
     * Removes all arrows in the relation to the given values.
     * 
     * @return The union of sets of keys from which the values had arrows.
     */
    Set<A> removeValues(Iterable<?> bs);

    /**
     * Removes all arrows in the relation.
     */
    void clear();
}
