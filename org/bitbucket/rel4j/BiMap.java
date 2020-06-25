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

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Provides an implementation of a standard mutable binary relation,
 * based on bidirectional maps.
 * This implementation handles non-determinism, and it does not accept
 * {@code null} keys or values.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public final class BiMap<A, B> extends AbstractMutableRelation<A, B> {

    /**************************************************************************\
     *  Attributes
    \**************************************************************************/

    /** The Map storages. */
    private final Map<A, Set<B>> keyValue;
    private final Map<B, Set<A>> valueKey;

    /** The number of key-value entries. */
    private transient int size;

    /** The non-simple arrow counter. */
    private transient int nonSimple;

    /** The non-injective arrow counter. */
    private transient int nonInjective;



    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Empty constructor of class BiMap.
     */
    public BiMap() {
        keyValue = new HashMap<A, Set<B>>();
        valueKey = new HashMap<B, Set<A>>();
    }


    /**
     *  Parameter constructor of class BiMap.
     */
    public BiMap(final int initialCapacity) {
        assert initialCapacity > 0;
        keyValue = new HashMap<A, Set<B>>(initialCapacity);
        valueKey = new HashMap<B, Set<A>>(initialCapacity);
    }


    /**
     *  Parameter constructor of class BiMap.
     */
    public BiMap(final Domain<A> dom, final Domain<B> ran) {
        super(dom, ran);
        keyValue = new HashMap<A, Set<B>>();
        valueKey = new HashMap<B, Set<A>>();
    }


    /**
     *  Parameter constructor of class BiMap.
     */
    public BiMap(final int initialCapacity,
            final Domain<A> dom, final Domain<B> ran) {
        super(dom, ran);
        keyValue = new HashMap<A, Set<B>>(initialCapacity);
        valueKey = new HashMap<B, Set<A>>(initialCapacity);
    }



    /* ************************************************************************\
     *  Getters
    \* ************************************************************************/

    @Override
    public int size() {
        return size;
    }


    @Override
    public Set<A> keys() {
        return Collections.unmodifiableSet(keyValue.keySet());
    }


    @Override
    public Set<A> keysOf(final Object b) {
        return Collections.unmodifiableSet(Sets.id(valueKey.get(b)));
    }


    @Override
    public A keyOf(final Object b) {
        final Set<A> as = valueKey.get(b);
        if (as == null || as.size() > 1) { return null; }
        return as.iterator().next();
    }


    @Override
    public Set<A> keysFor(Iterable<?> bs) {
        if (bs == null) { bs = Collections.emptySet(); }
        final Set<A> set = Sets.<A>empty();
        for (final Object b: bs) {
            final Set<A> keys = valueKey.get(b);
            if (keys == null) { continue; }
            set.addAll(keys);
        }
        return Collections.unmodifiableSet(set);
    }



    @Override
    public Set<B> values() {
        return Collections.unmodifiableSet(valueKey.keySet());
    }


    @Override
    public Set<B> valuesOf(final Object a) {
        return Collections.unmodifiableSet(Sets.id(keyValue.get(a)));
    }


    @Override
    public B valueOf(final Object a) {
        final Set<B> bs = keyValue.get(a);
        if (bs == null || bs.size() > 1) { return null; }
        return bs.iterator().next();
    }


    @Override
    public Set<B> valuesFor(Iterable<?> as) {
        if (as == null) { as = Collections.emptySet(); }
        final Set<B> set = Sets.<B>empty();
        for (final Object a: as) {
            Set<B> values = keyValue.get(a);
            if (values == null) { continue; }
            set.addAll(values);
        }
        return Collections.unmodifiableSet(set);
    }


    @Override
    public Iterator<Pair<A, B>> iterator() {
        return new BiMapIterator();
    }



    /* ************************************************************************\
     *  Predicates
    \* ************************************************************************/

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    @Override
    public boolean isSimple() {
        return nonSimple == 0;
    }

    @Override
    public boolean isInjective() {
        return nonInjective == 0;
    }


    @Override
    public boolean contains(final Object a, final Object b) {
        if (a == null || b == null) { return false; }
        final Set<B> bs = keyValue.get(a);
        return bs != null && bs.contains(b);
    }


    @Override
    public boolean containsKey(final Object a) {
        if (a == null) { return false; }
        return keyValue.get(a) != null;
    }


    @Override
    public boolean containsValue(final Object b) {
        if (b == null) { return false; }
        return valueKey.get(b) != null;
    }



    /* ***********************************************************************\
     *  Insertion Methods
    \* ***********************************************************************/

    @Override
    public boolean put(final A a, final B b) {
        if (a == null || b == null) { return false; }
        return doPut(a, b);
    }


    @Override
    public boolean putAllKeys(final Iterable<? extends A> as, final B b) {
        if (as == null || b == null) { return false; }
        boolean modified = false;
        for (final A a: as) {
            if (a != null) {
                modified |= doPut(a, b);
            }
        }
        return modified;
    }


    @Override
    public boolean putAllValues(final A a, final Iterable<? extends B> bs) {
        if (a == null || bs == null) { return false; }
        boolean modified = false;
        for (final B b: bs) {
            if (b != null) {
                modified |= doPut(a, b);
            }
        }
        return modified;
    }


    @Override
    public boolean putAll(final Iterable<? extends A> as,
            final Iterable<? extends B> bs) {
        if (as == null || bs == null) { return false; }
        boolean modified = false;
        for (final A a: as) {
            if (a == null) { continue; }
            for (final B b: bs) {
                if (b != null) {
                    modified |= doPut(a, b);
                }
            }
        }
        return modified;
    }



    /* ***********************************************************************\
     *  Removal Methods
    \* ***********************************************************************/

    @Override
    public boolean remove(final Object a, final Object b) {
        if (a == null || b == null) { return false; }
        Set<B> bs = keyValue.get(a);
        if (bs != null && bs.remove(b)) {
            --size;
            if (bs.isEmpty()) { keyValue.remove(a); }
            else { --nonSimple; }
            final Set<A> as = valueKey.get(b);
            as.remove(a);
            if (as.isEmpty()) { valueKey.remove(b); }
            else { --nonInjective; }
            return true;
        }
        return false;
    }


    @Override
    public Set<B> removeKey(final Object a) {
        final Set<B> set = doRemoveKey(a);
        return set == null ? Sets.<B>empty() : set;
    }


    @Override
    public Set<B> removeKeys(Iterable<?> as) {
        if (as == null) { as = Collections.emptySet(); }
        final Set<B> set = Sets.<B>empty();
        for (final Object a: as) {
            final Set<B> bs = doRemoveKey(a);
            if (bs != null) { set.addAll(bs); }
        }
        return set;
    }


    @Override
    public Set<A> removeValue(final Object b) {
        final Set<A> set = doRemoveValue(b);
        return set == null ? Sets.<A>empty() : set;
    }


    @Override
    public Set<A> removeValues(Iterable<?> bs) {
        if (bs == null) { bs = Collections.emptySet(); }
        final Set<A> set = Sets.<A>empty();
        for (final Object b: bs) {
            final Set<A> as = doRemoveValue(b);
            if (as != null) { set.addAll(as); }
        }
        return set;
    }


    @Override
    public void clear() {
        size            = 0;
        nonSimple       = 0;
        nonInjective    = 0;
        keyValue.clear();
        valueKey.clear();
    }



    /*************************************************************************\
     *  Private Methods
    \*************************************************************************/

    /** */
    private boolean doPut(final A a, final B b) {
        Set<B> bs = keyValue.get(a);
        if (bs == null) { keyValue.put(a, bs = Sets.<B>empty()); }
        if (bs.add(b)) {
            ++size;
            if (bs.size() > 1) { ++nonSimple; }
            Set<A> as = valueKey.get(b);
            if (as == null) { valueKey.put(b, as = Sets.<A>empty()); }
            as.add(a);
            if (as.size() > 1) { ++nonInjective; }
            return true;
        }
        return false;
    }


    /** */
    private Set<B> doRemoveKey(final Object a) {
        if (a == null) { return null; }
        final Set<B> bs = keyValue.remove(a);
        if (bs != null) {
            size -= bs.size();
            nonSimple -= bs.size() - 1;
            for (final B b: bs) {
                final Set<A> as = valueKey.get(b);
                as.remove(a);
                if (as.isEmpty()) { valueKey.remove(b); }
                else { --nonInjective; }
            }
        }
        return bs;
    }


    /** */
    private Set<A> doRemoveValue(final Object b) {
        if (b == null) { return null; }
        final Set<A> as = valueKey.remove(b);
        if (as != null) {
            size -= as.size();
            nonInjective -= as.size() - 1;
            for (final A a: as) {
                final Set<B> bs = keyValue.get(a);
                bs.remove(b);
                if (bs.isEmpty()) { keyValue.remove(a); }
                else { --nonSimple; }
            }
        }
        return as;
    }



    /**************************************************************************\
     *  Nested Classes
    \**************************************************************************/

    /** */
    private final class BiMapIterator implements Iterator<Pair<A, B>> {
        private final Iterator<A> keyset;
        private A currentKey;
        private Iterator<B> values;
        private B currentValue;

        /** */
        BiMapIterator() {
            keyset = keyValue.keySet().iterator();
        }


        /** */
        @Override
        public boolean hasNext() {
            if (currentKey == null || !values.hasNext()) {
                return keyset.hasNext();
            }
            return true;
        }

        /** */
        @Override
        public Pair<A, B> next() {
            if (currentKey == null || !values.hasNext()) {
                currentKey = keyset.next();
                values = keyValue.get(currentKey).iterator();
            }
            currentValue = values.next();
            return new Pair<A, B>(currentKey, currentValue);
        }

        /** */
        @Override
        public void remove() {
            if (currentKey == null) {
                throw new IllegalStateException(
                        "The next method has not yet been called.");
            }
            values.remove();
            --size;
            if (keyValue.get(currentKey).isEmpty()) {
                keyset.remove();
            } else {
                --nonSimple;
            }
            final Set<A> as = valueKey.get(currentValue);
            as.remove(currentKey);
            if (as.isEmpty()) { valueKey.remove(currentValue); }
            else { --nonInjective; }
        }
    }
}
