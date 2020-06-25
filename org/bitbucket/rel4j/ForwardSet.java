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
 * ForwardSet
 * 
 * @author Andre Santos
 * @since 0.2
 * @version 0.2
 */

final class ForwardSet<E> implements MSet<E> {

    /**************************************************************************\
     *  Attributes
    \**************************************************************************/

    private final Set<E> set;



    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Parameter constructor of class ForwardSet.
     */
    ForwardSet(final Set<E> set) {
        assert set != null;
        this.set = set;
    }



    /* ************************************************************************\
     *  Getters
    \* ************************************************************************/

    @Override
    public Cardinal cardinality() {
        return Cardinal.of(set.size());
    }

    @Override
    public Iterator<E> iterator() {
        return set.iterator();
    }


    @Override
    public int hashCode() {
        return set.hashCode();
    }



    /* ************************************************************************\
     *  Predicates
    \* ************************************************************************/

    @Override
    public boolean isEmpty() {
        return set.isEmpty();
    }

    @Override
    public boolean isFinite() {
        return true;
    }

    @Override
    public boolean contains(final Object o) {
        return set.contains(o);
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof MSet)) { return false; }
        final MSet<?> n = (MSet<?>) o;
        if (!this.cardinality().equals(n.cardinality())) { return false; }
        for (final E e: this) {
            if (!n.contains(e)) { return false; }
        }
        return true;
    }
}
