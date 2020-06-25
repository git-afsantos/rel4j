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
import java.util.Set;


/**
 * ImmutableView
 * 
 * @author afs
 * @version 2014
 */

final class ImmutableView<A, B> extends AbstractView<A, B, A, B> {

    /**************************************************************************\
     *  Attributes
    \**************************************************************************/

    /** The original relation. */
    private final Relation<A, B> original;

    /** Cached result: The number of key-value entries. */
    private transient final int size;

    /** Cached result: whether this relation is entire. */
    private transient final boolean entire;

    /** Cached result: whether this relation is surjective. */
    private transient final boolean surjective;



    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Parameter constructor of class ImmutableView.
     */
    ImmutableView(final Relation<A, B> r) {
        assert r != null;
        original    = r;
        size        = r.size();
        entire      = r.isEntire();
        surjective  = r.isSurjective();
    }



    /**************************************************************************\
     *  Getters
    \**************************************************************************/

    /** */
    @Override
    protected Relation<A, B> relation() {
        return original;
    }


    /** */
    @Override
    public int size() {
        return size;
    }


    /** */
    @Override
    public Domain<A> getDomainType() {
        return original.getDomainType();
    }

    /** */
    @Override
    public Domain<B> getRangeType() {
        return original.getRangeType();
    }


    /** */
    @Override
    public Set<A> keys() {
        return original.keys();
    }


    /** */
    @Override
    public Set<A> keysOf(final Object b) {
        return original.keysOf(b);
    }


    /** */
    @Override
    public A keyOf(final Object b) {
        return original.keyOf(b);
    }


    /** */
    @Override
    public Set<A> keysFor(final Iterable<?> bs) {
        return original.keysFor(bs);
    }



    /** */
    @Override
    public Set<B> values() {
        return original.values();
    }


    /** */
    @Override
    public Set<B> valuesOf(final Object a) {
        return original.valuesOf(a);
    }


    /** */
    @Override
    public B valueOf(final Object a) {
        return original.valueOf(a);
    }


    /** */
    @Override
    public Set<B> valuesFor(final Iterable<?> as) {
        return original.valuesFor(as);
    }


    /** */
    @Override
    public Iterator<Pair<A, B>> iterator() {
        return new ImmutableIterator<Pair<A, B>>(original.iterator());
    }



    /**************************************************************************\
     *  Predicates
    \**************************************************************************/

    /** */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /** */
    @Override
    public boolean isEntire() {
        return entire;
    }

    /** */
    @Override
    public boolean isSurjective() {
        return surjective;
    }


    /** */
    @Override
    public boolean contains(final Object a, final Object b) {
        return original.contains(a, b);
    }


    /** */
    @Override
    public boolean containsKey(final Object a) {
        return original.containsKey(a);
    }


    /** */
    @Override
    public boolean containsValue(final Object b) {
        return original.containsValue(b);
    }



    /**************************************************************************\
     *  Nested Classes
    \**************************************************************************/

    /** */
    private static final class ImmutableIterator<T> implements Iterator<T> {
        private final Iterator<T> iterator;

        ImmutableIterator(final Iterator<T> i) {
            iterator = i;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public T next() {
            return iterator.next();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Immutable Iterator");
        }
    }
}
