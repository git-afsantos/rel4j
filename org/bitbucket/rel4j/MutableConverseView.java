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
 * MutableConverseView
 * 
 * @author Andre Santos
 * @version 2014-08-01
 */

final class MutableConverseView<B, A>
        extends ConverseView<B, A> implements MutableRelation<B, A> {

    /*************************************************************************\
     *  Constructors and Factories
    \*************************************************************************/

    /**
     *  Parameter constructor of objects of class MutableConverseView.
     */
    MutableConverseView(final MutableRelation<A, B> original) {
        super(original);
    }



    /*************************************************************************\
     *  Getters
    \*************************************************************************/

    /** */
    @Override
    protected MutableRelation<A, B> relation() {
        return (MutableRelation<A, B>) super.relation();
    }


    /** */
    @Override
    public Iterator<Pair<B, A>> iterator() {
        return new MutableConverseIterator<B, A>(relation().iterator());
    }



    /*************************************************************************\
     *  Insertion Methods
    \*************************************************************************/

    /** */
    @Override
    public boolean put(final B b, final A a) {
        return relation().put(a, b);
    }

    /** */
    @Override
    public boolean putAllKeys(final Iterable<? extends B> bs, final A a) {
        return relation().putAllValues(a, bs);
    }

    /** */
    @Override
    public boolean putAllValues(final B b, final Iterable<? extends A> as) {
        return relation().putAllKeys(as, b);
    }

    /** */
    @Override
    public boolean putAll(final Iterable<? extends B> bs,
            final Iterable<? extends A> as) {
        return relation().putAll(as, bs);
    }



    /*************************************************************************\
     *  Removal Methods
    \*************************************************************************/


    /** */
    @Override
    public boolean remove(final Object b, final Object a) {
        return relation().remove(a, b);
    }

    /** */
    @Override
    public Set<A> removeKey(final Object b) {
        return relation().removeValue(b);
    }

    /** */
    @Override
    public Set<A> removeKeys(final Iterable<?> bs) {
        return relation().removeValues(bs);
    }

    /** */
    @Override
    public Set<B> removeValue(final Object a) {
        return relation().removeKey(a);
    }

    /** */
    @Override
    public Set<B> removeValues(final Iterable<?> as) {
        return relation().removeKeys(as);
    }


    /** */
    @Override
    public void clear() {
        relation().clear();
    }



    /*************************************************************************\
     *  Nested Classes
    \*************************************************************************/

    /** */
    private static class MutableConverseIterator<B, A> extends ConverseIterator<B, A> {
        MutableConverseIterator(final Iterator<Pair<A, B>> i) {
            super(i);
        }

        @Override
        public void remove() {
            super.iterator.remove();
        }
    }
}
