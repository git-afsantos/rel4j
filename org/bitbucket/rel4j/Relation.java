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
 * Representation of a binary relation, from some type {@code A} to another
 * type {@code B}. A binary relation may be able handle non-determinism,
 * depending on the implementation. Some implementations may opt to force
 * simplicity (resp. injectivity), meaning that, for a given key, there is,
 * at most, only one value (resp. vice-versa).
 * 
 * In essence, a Relation is a data structure used to store
 * arrows (correspondences, mappings), from
 * elements of {@code A} to elements of {@code B}.
 * 
 * In this context, a key is said to be an element of {@code A}, the domain,
 * while a value is said to be an element of {@code B}, the range.
 * 
 * A binary relation is not able to store the {@code null} key or value.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public interface Relation<A, B> extends Iterable<Pair<A, B>> {
    //enum Property {
        //INJECTIVE, ENTIRE, SIMPLE, SURJECTIVE, BIJECTIVE, REPRESENTATION,
        //FUNCTION, ABSTRACTION, INJECTION, SURJECTION, BIJECTION
    //}



    /*************************************************************************\
     *  Getters
    \*************************************************************************/

    /**
     * Returns the number of arrows present in the relation.
     */
    int size();


    /**
     * Returns the set of all keys present in the relation.
     * This set is unmodifiable.
     * 
     * Note: this operation is a {@code Set} view of the relation's domain.
     */
    Set<A> keys();

    /**
     * Returns the set of keys present in the relation, for a given value.
     * The set is empty if the value is not present in the relation.
     * This set is unmodifiable.
     */
    Set<A> keysOf(Object b);

    /**
     * Returns the only key present in the relation, for a given value.
     * Returns {@code null} if either the value is not present or it has
     * multiple keys.
     */
    A keyOf(Object b);

    /**
     * Returns the set of keys present in the relation, for all given values.
     * This set is an union of keys, not an intersection.
     * This set is empty if none of the values is present in the relation,
     * or if the iterable is {@code null}.
     * This set is unmodifiable.
     */
    Set<A> keysFor(Iterable<?> bs);


    /**
     * Returns the set of all values present in the relation.
     * This set is unmodifiable.
     * 
     * Note: this operation is a {@code Set} view of the relation's range.
     */
    Set<B> values();

    /**
     * Returns the set of values present in the relation, for a given key.
     * The set is empty if the key is not present in the relation.
     * This set is unmodifiable.
     */
    Set<B> valuesOf(Object a);

    /**
     * Returns the only value present in the relation, for a given key.
     * Returns {@code null} if either the key is not present or it has
     * multiple values.
     */
    B valueOf(Object a);

    /**
     * Returns the set of values present in the relation, for all given keys.
     * This set is an union of values, not an intersection.
     * This set is empty if none of the keys is present in the relation,
     * or if the iterable is {@code null}.
     * This set is unmodifiable.
     */
    Set<B> valuesFor(Iterable<?> as);


    /**
     * Returns the {@code Domain} that represents the domain type of
     * this relation.
     */
    Domain<A> getDomainType();

    /**
     * Returns the {@code Domain} that represents the range type of
     * this relation.
     */
    Domain<B> getRangeType();



    /*************************************************************************\
     *  Predicates
    \*************************************************************************/

    /**
     * Determines whether the relation is empty.
     */
    boolean isEmpty();


    /**
     * Determines whether the given key-value pair is present in the relation.
     */
    boolean contains(Object a, Object b);

    /**
     * Determines whether the given key is present in the relation.
     */
    boolean containsKey(Object a);

    /**
     * Determines whether the given value is present in the relation.
     */
    boolean containsValue(Object b);


    /** */
    // boolean is(Property p);

    /**
     * Determines whether the relation is Simple.
     * If it is, there is, at most, <b>one</b> value for every present key.
     */
    boolean isSimple();

    /**
     * Determines whether the relation is Injective.
     * If it is, there is, at most, <b>one</b> key for every present value.
     */
    boolean isInjective();

    /**
     * Determines whether the relation is Entire.
     * If it is, this relation's domain ({@code keys()}) contains all
     * elements that belong to the {@code Domain} that represents
     * this relation's domain type.
     */
    boolean isEntire();

    /**
     * Determines whether the relation is Surjective.
     * If it is, this relation's range ({@code values()}) contains all
     * elements that belong to the {@code Domain} that represents
     * this relation's range type.
     */
    boolean isSurjective();


    /**
     * Determines whether this relation is contained in the given relation.
     * If it is, every pair {@code (a, b)} in this relation must also be
     * present in the given relation.
     * 
     * <pre>{@code
     *     if (this == r) { return true; }
     *     if (this.size() > r.size()) { return false; }
     *     // for every (a, b) in this:
     *     //   if (a, b) not in r: return false}
     * </pre>
     */
    boolean in(Relation<?, ?> r);

    /**
     * Determines whether this relation is <b>strictly</b> contained
     * in the given relation.
     * If it is, every pair {@code (a, b)} in this relation must also be
     * present in the given relation, but the given relation has some pair
     * {@code (a, b)} that this relation does not.
     * <pre>{@code
     *     if (this == r) { return true; }
     *     if (this.size() >= r.size()) { return false; }
     *     // for every (a, b) in this:
     *     //   if (a, b) not in r: return false}
     * </pre>
     */
    boolean strictlyIn(Relation<?, ?> r);



    /*************************************************************************\
     *  Equals & HashCode
    \*************************************************************************/


    /**
     * Determines whether this relation is equal to the given object.
     * Two relations are equal if, and only if, they contain exactly
     * the same key-value pairs.
     * 
     * <pre>{@code
     *     if (this == o) { return true; }
     *     if (!(o instanceof Relation)) { return false; }
     *     // for every (a, b):
     *     //   (a, b) in this == (a, b) in o}
     * </pre>
     */
    @Override
    boolean equals(Object o);


    /**
     * Returns an hash code for this relation, consistent with the
     * redefined {@code equals(Object)}.
     * 
     * <pre>{@code
     *     int hash = 0;
     *     // for every (a, b):
     *     //   hash = 31 * hash + (a.hashCode() ^ b.hashCode());
     *     if (hash == 0 && !this.isEmpty()) { hash = 1; }
     *     return hash;}
     * </pre>
     */
    @Override
    int hashCode();
}
