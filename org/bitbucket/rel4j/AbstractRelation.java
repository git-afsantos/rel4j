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
 * Provides a skeletal implementation for a binary relation.
 * This class abides by the contracts of {@code equals(Object)},
 * {@code in(Relation)} and {@code strictlyIn(Rleation)}.
 * Additionally, it provides template implementations for
 * {@code contains(Object, Object)}, {@code containsKey(Object)}
 * and {@code containsValue(Object)}.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public abstract class AbstractRelation<A, B> implements Relation<A, B> {

    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Empty constructor of class AbstractRelation.
     */
    protected AbstractRelation() {}



    /**************************************************************************\
     *  Predicates
    \**************************************************************************/

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
    @Override
    public final boolean in(final Relation<?, ?> r) {
        if (this == r) { return true; }
        if (r == null || this.size() > r.size()) { return false; }
        for (final Pair<A, B> p: this) {
            if (!r.contains(p.first(), p.second())) { return false; }
        }
        return true;
    }


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
    @Override
    public final boolean strictlyIn(final Relation<?, ?> r) {
        if (r == null) { return false; }
        return this.in(r) && this.size() < r.size();
    }


    /**
     * Determines whether the given key-value pair is present in the relation.
     */
    @Override
    public boolean contains(final Object a, final Object b) {
        if (a == null || b == null) { return false; }
        final Set<B> bs = this.valuesOf(a);
        return bs != null && bs.contains(b);
    }


    /**
     * Determines whether the given key is present in the relation.
     */
    @Override
    public boolean containsKey(final Object a) {
        if (a == null) { return false; }
        return this.valuesOf(a) != null;
    }


    /**
     * Determines whether the given value is present in the relation.
     */
    @Override
    public boolean containsValue(final Object b) {
        if (b == null) { return false; }
        return this.keysOf(b) != null;
    }



    /**************************************************************************\
     *  Equals, HashCode & ToString
    \**************************************************************************/

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
    public final boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof Relation)) { return false; }
        final Relation<?, ?> r = (Relation<?, ?>) o;
        return this.in(r) && r.in(this);
    }


    /**
     *  Contract:
     *      Consistency: successive calls return the same code,
     *          assuming no modification of the equality fields.
     *      Function: two equal objects have the same (unique) hash code.
     *
     *  Formula:
     *  <pre>{@code
     *     int hash = 0;
     *     // for every (a, b):
     *     //   hash = 31 * hash + (a.hashCode() ^ b.hashCode());
     *     if (hash == 0 && !this.isEmpty()) { hash = 1; }
     *     return hash;}
     * </pre>
     */
    @Override
    public final int hashCode() {
        int hash = 0;
        int aHash = 0;
        for (final A a: this.keys()) {
            aHash = a.hashCode();
            for (final B b: this.valuesOf(a)) {
                hash = 31 * hash + (aHash ^ b.hashCode());
            }
        }
        if (hash == 0 && !this.isEmpty()) {
            hash = 1;
        }
        return hash;
    }


    /**
     *  Returns a string representation of the object.
     */
    @Override
    public String toString() {
        boolean sep = false;
        final StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (final A a: this.keys()) {
            final String as = a.toString();
            for (final B b: this.valuesOf(a)) {
                if (sep) { sb.append(','); }
                sb.append('(')
                    .append(as)
                    .append(',')
                    .append(b)
                    .append(')');
                sep = true;
            }
        }
        sb.append('}');
        return sb.toString();
    }
}
