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

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Sets
 * 
 * @author Andre Santos
 * @version 2014-05-20
 */

public final class Sets {

    /*************************************************************************\
     *  Constructors
    \*************************************************************************/

    /**
     *  Empty constructor of objects of class Sets.
     */
    private Sets() {
        throw new AssertionError();
    }



    /*************************************************************************\
     *  Public Methods
    \*************************************************************************/

    /** */
    public static <E> Set<E> union(
            final Set<E> s1,
            final Set<? extends E> s2) {
        final Set<E> set = copy(s1);
        set.addAll(id(s2));
        return set;
    }


    /** */
    public static <E> Set<E> difference(
            final Set<E> s1,
            final Set<? extends E> s2) {
        final Set<E> set = copy(s1);
        set.removeAll(id(s2));
        return set;
    }


    /** */
    public static <E> Set<E> intersection(
            final Set<E> s1,
            final Set<? extends E> s2) {
        final Set<E> set = copy(s1);
        set.retainAll(id(s2));
        return set;
    }



    /*************************************************************************\
     *  Private Methods
    \*************************************************************************/

    /** */
    static <E> Set<E> copy(final Set<E> original) {
        if (original == null) { return new HashSet<E>(); }
        return new HashSet<E>(original);
    }


    /** */
    static <E> Set<E> empty() {
        return new HashSet<E>();
    }


    /** */
    static <E> Set<E> id(final Set<E> set) {
        if (set == null) { return new HashSet<E>(); }
        return set;
    }


    /** */
    static <E> Set<E> singleton(final E e) {
        final Set<E> set = new HashSet<E>();
        set.add(e);
        return set;
    }


    /** */
    static <E> Set<E> cappedSingleton(final E e) {
        return new CappedSingleton<E>(e);
    }



    /*************************************************************************\
     *  Nested Classes
    \*************************************************************************/

    /** */
    private static final class CappedSingleton<E> extends AbstractSet<E> {
        private final E element;

        /** */
        CappedSingleton(E e) {
            element = e;
        }

        /** */
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        /** */
        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException();
        }

        /** */
        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        /** */
        @Override
        public boolean contains(Object o) {
            return element.equals(o);
        }

        /** */
        @Override
        public boolean containsAll(Collection<?> c) {
            if (c.size() != 1) { return false; }
            return c.contains(element);
        }

        /** */
        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        /** */
        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        /** */
        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        /** */
        @Override
        public int size() {
            return 1;
        }

        /** */
        @Override
        public Object[] toArray() {
            return new Object[]{ element };
        }

        /** */
        @Override
        @SuppressWarnings("unchecked")
        public <T> T[] toArray(T[] a) {
            if (a.length < 1) {
                return (T[]) Arrays.copyOf(toArray(), 1, a.getClass());
            }
            System.arraycopy(toArray(), 0, a, 0, 1);
            if (a.length > 1) {
                a[1] = null;
            }
            return a;
        }

        /** */
        @Override
        public Iterator<E> iterator() {
            return new SingletonIterator();
        }


        /** */
        private final class SingletonIterator implements Iterator<E> {
            private boolean iterated;

            /** */
            @Override
            public boolean hasNext() {
                return !iterated;
            }

            /** */
            @Override
            public E next() {
                if (iterated) {
                    throw new NoSuchElementException();
                }
                iterated = true;
                return element;
            }

            /** */
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
    }
}
