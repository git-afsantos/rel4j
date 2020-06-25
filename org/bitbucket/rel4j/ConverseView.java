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
 * ConverseView
 * 
 * @author Andre Santos
 * @version 2014-08-01
 */

class ConverseView<B, A> extends AbstractView<B, A, A, B> {

    /*************************************************************************\
     *  Attributes
    \*************************************************************************/

    private final Relation<A, B> original;



    /*************************************************************************\
     *  Constructors and Factories
    \*************************************************************************/

    /** */
    protected ConverseView(final Relation<A, B> r) {
        assert r != null;
        original = r;
    }



    /*************************************************************************\
     *  Getters
    \*************************************************************************/

    /** */
    @Override
    protected Relation<A, B> relation() {
        return original;
    }


    /** */
    @Override
    public final int size() {
        return original.size();
    }


    /** */
    @Override
    public final Domain<B> getDomainType() {
        return original.getRangeType();
    }

    /** */
    @Override
    public final Set<B> keys() {
        return original.values();
    }

    /** */
    @Override
    public final Set<B> keysOf(final Object a) {
        return original.valuesOf(a);
    }

    /** */
    @Override
    public final B keyOf(final Object a) {
        return original.valueOf(a);
    }

    /** */
    @Override
    public final Set<B> keysFor(final Iterable<?> as) {
        return original.valuesFor(as);
    }


    /** */
    @Override
    public final Domain<A> getRangeType() {
        return original.getDomainType();
    }

    /** */
    @Override
    public final Set<A> values() {
        return original.keys();
    }

    /** */
    @Override
    public final Set<A> valuesOf(final Object b) {
        return original.keysOf(b);
    }

    /** */
    @Override
    public final A valueOf(final Object b) {
        return original.keyOf(b);
    }

    /** */
    @Override
    public final Set<A> valuesFor(final Iterable<?> bs) {
        return original.keysFor(bs);
    }


    /** */
    @Override
    public Iterator<Pair<B, A>> iterator() {
        return new ConverseIterator<B, A>(original.iterator());
    }



    /*************************************************************************\
     *  Predicates
    \*************************************************************************/

    /** */
    @Override
    public final boolean isSimple() {
        return original.isInjective();
    }

    /** */
    @Override
    public final boolean isInjective() {
        return original.isSimple();
    }

    /** */
    @Override
    public final boolean isEntire() {
        return original.isSurjective();
    }

    /** */
    @Override
    public final boolean isSurjective() {
        return original.isEntire();
    }



    /*************************************************************************\
     *  Nested Classes
    \*************************************************************************/

    /** */
    protected static class ConverseIterator<B, A>
            implements Iterator<Pair<B, A>> {
        protected final Iterator<Pair<A, B>> iterator;

        ConverseIterator(final Iterator<Pair<A, B>> i) {
            iterator = i;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Pair<B, A> next() {
            final Pair<A, B> p = iterator.next();
            return new Pair<B, A>(p.second(), p.first());
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Immutable Iterator");
        }
    }
}
