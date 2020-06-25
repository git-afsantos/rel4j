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
 * This implementation enforces a Simple relation, and it does not accept
 * {@code null} keys or values.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public final class SimpleBiMap<A, B> extends AbstractMutableRelation<A, B> {

    /**************************************************************************\
     *  Attributes
    \**************************************************************************/

    /** The Map storages. */
    private final Map<A, B> keyValue;
    private final Map<B, Set<A>> valueKey;

    /** The number of key-value entries. */
    private transient int size;

    /** The non-injective arrow counter. */
    private transient int nonInjective;



    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Empty constructor of class SimpleBiMap.
     */
    public SimpleBiMap() {
        keyValue = new HashMap<A, B>();
        valueKey = new HashMap<B, Set<A>>();
    }


    /**
     *  Parameter constructor of class SimpleBiMap.
     */
    public SimpleBiMap(final int initialCapacity) {
        assert initialCapacity > 0;
        keyValue = new HashMap<A, B>(initialCapacity);
        valueKey = new HashMap<B, Set<A>>(initialCapacity);
    }


    /**
     *  Parameter constructor of class SimpleBiMap.
     */
    public SimpleBiMap(final Domain<A> dom, final Domain<B> ran) {
        super(dom, ran);
        keyValue = new HashMap<A, B>();
        valueKey = new HashMap<B, Set<A>>();
    }


    /**
     *  Parameter constructor of class SimpleBiMap.
     */
    public SimpleBiMap(final int initialCapacity,
            final Domain<A> dom, final Domain<B> ran) {
        super(dom, ran);
        keyValue = new HashMap<A, B>(initialCapacity);
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
        return Sets.cappedSingleton(keyValue.get(a));
    }


    @Override
    public B valueOf(final Object a) {
        return keyValue.get(a);
    }


    @Override
    public Set<B> valuesFor(Iterable<?> as) {
        if (as == null) { as = Collections.emptySet(); }
        final Set<B> set = Sets.<B>empty();
        for (final Object a: as) {
            B value = keyValue.get(a);
            if (value != null) {
                set.add(value);
            }
        }
        return Collections.unmodifiableSet(set);
    }


    @Override
    public Iterator<Pair<A, B>> iterator() {
        return new SimpleBiMapIterator();
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
        return true;
    }

    @Override
    public boolean isInjective() {
        return nonInjective == 0;
    }


    @Override
    public boolean contains(final Object a, final Object b) {
        if (a == null || b == null) { return false; }
        return b.equals(keyValue.get(a));
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
        final B prev = keyValue.get(a);
        if (prev != null && prev.equals(b)) {
            --size;
            keyValue.remove(a);
            removeValueKey(b, a);
            return true;
        }
        return false;
    }


    @Override
    public Set<B> removeKey(final Object a) {
        final B b = doRemoveKey(a);
        return b == null ? Sets.<B>empty() : Sets.singleton(b);
    }


    @Override
    public Set<B> removeKeys(Iterable<?> as) {
        if (as == null) { as = Collections.emptySet(); }
        final Set<B> set = Sets.<B>empty();
        for (final Object a: as) {
            final B b = doRemoveKey(a);
            if (b != null) { set.add(b); }
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
        nonInjective    = 0;
        keyValue.clear();
        valueKey.clear();
    }



    /**************************************************************************\
     *  Private Methods
    \**************************************************************************/

    /** */
    private boolean doPut(final A a, final B b) {
        final B prev = keyValue.put(a, b);
        if (prev == null) {
            ++size;
        } else if (prev.equals(b)) {
            return false;
        } else {
            removeValueKey(prev, a);
        }
        Set<A> as = valueKey.get(b);
        if (as == null) { valueKey.put(b, as = Sets.<A>empty()); }
        as.add(a);
        if (as.size() > 1) { ++nonInjective; }
        return true;
    }


    /** */
    private B doRemoveKey(final Object a) {
        if (a == null) { return null; }
        final B b = keyValue.remove(a);
        if (b != null) {
            --size;
            removeValueKey(b, a);
        }
        return b;
    }


    /** */
    private Set<A> doRemoveValue(final Object b) {
        if (b == null) { return null; }
        final Set<A> as = valueKey.remove(b);
        if (as != null) {
            size -= as.size();
            nonInjective -= as.size() - 1;
            for (final A a: as) {
                keyValue.remove(a);
            }
        }
        return as;
    }


    /** */
    private void removeValueKey(final Object b, final Object a) {
        final Set<A> as = valueKey.get(b);
        as.remove(a);
        if (as.isEmpty()) { valueKey.remove(b); }
        else { --nonInjective; }
    }



    /**************************************************************************\
     *  Nested Classes
    \**************************************************************************/

    /** */
    private final class SimpleBiMapIterator
            implements Iterator<Pair<A, B>> {
        private final Iterator<Map.Entry<A, B>> iterator;
        private Map.Entry<A, B> current;

        /** */
        SimpleBiMapIterator() {
            iterator = keyValue.entrySet().iterator();
        }


        /** */
        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        /** */
        @Override
        public Pair<A, B> next() {
            current = iterator.next();
            return new Pair<A, B>(current.getKey(), current.getValue());
        }

        /** */
        @Override
        public void remove() {
            iterator.remove();
            --size;
            removeValueKey(current.getValue(), current.getKey());
        }
    }
}
