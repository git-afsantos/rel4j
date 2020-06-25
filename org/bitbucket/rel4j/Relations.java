/*
 *  Copyright (c) 2013 André Santos
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;


/**
 * Provides useful functions to produce, combine or transform relations.
 * 
 * @author Andre Santos
 * @since 0.1
 * @version 0.2
 */

public final class Relations {

    /*************************************************************************\
     *  Fields
    \*************************************************************************/

    /**
     * The bottom relation. This is an immutable empty relation.
     */
    public static final Relation<?, ?> BOTTOM = new Bottom();



    /*************************************************************************\
     *  Constructors
    \*************************************************************************/

    /**
     *  Empty constructor of objects of class Relations.
     */
    private Relations() {
        throw new AssertionError();
    }



    /*************************************************************************\
     *  Class Transformation Functions
    \*************************************************************************/

    /**
     * Creates an immutable <b>copy</b> of the given relation.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> Relation<A, B> immutable(Relation<A, B> r) {
        r = id(r);
        if (r.isSimple() && r.isInjective()) {
            return new ImmutableView<A, B>(toOneToOne(r));
        } else if (r.isSimple()) {
            return new ImmutableView<A, B>(toSimple(r));
        } else if (r.isInjective()) {
            return new ImmutableView<A, B>(toInjective(r));
        }
        return new ImmutableView<A, B>(copy(r));
    }



    /*************************************************************************\
     *  Predicates
    \*************************************************************************/

    /** */
//     public static <A, B> boolean equal(
//             final Relation<A, B> r1,
//             final Relation<? extends A, ? extends B> r2) {
//         if (r1 == r2) { return true; }
//         if (r1.size() != r2.size()) { return false; }
//         if (!r1.getDomainType().equals(r2.getDomainType())) { return false; }
//         if (!r1.getRangeType().equals(r2.getRangeType())) { return false; }
//         for (A a: r1.domain()) {
//             if (!r2.containsKey(a)) { return false; }
//             if (!r1.values(a).equals(r2.values(a))) { return false; }
//         }
//         for (A a: r2.domain()) {
//             if (!r1.containsKey(a)) { return false; }
//             if (!r2.values(a).equals(r1.values(a))) { return false; }
//         }
//         return true;
//     }


    /** */
//     public static <A, B> boolean in(
//             final Relation<A, B> r1,
//             final Relation<? extends A, ? extends B> r2) {
//         if (r1 == r2) { return true; }
//         if (r1.size() > r2.size()) { return false; }
//         for (A a: r1.domain()) {
//             for (B b: r1.values(a)) {
//                 if (!r2.contains(a, b)) { return false; }
//             }
//         }
//         return true;
//     }



    /*************************************************************************\
     *  Public Methods
    \*************************************************************************/

    /**
     * Returns a <b>view</b> of the given relation, representing its converse.
     * Changes to the original relation affect this view.
     * Changes to this view also affect the original relation.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<B, A> converseView(
            final MutableRelation<A, B> r) {
        return new MutableConverseView<B, A>(nonNullCopy(r));
    }

    /**
     * Returns an <b>unmodifiable view</b> of the given relation,
     * representing its converse.
     * Changes to the original relation affect this view.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> Relation<B, A> converseView(final Relation<A, B> r) {
        return new ConverseView<B, A>(nonNullCopy(r));
    }


    /**
     * Creates a <b>copy</b> of the given relation,
     * representing its converse.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<B, A> converse(
            final Relation<A, B> r) {
        return new MutableConverseView<B, A>(nonNullCopy(r));
    }


    /**
     * Creates a <b>new</b> relation representing the composition
     * of the given relations.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, C> MutableRelation<A, C> compose(
            final Relation<A, ?> r1, final Relation<?, C> r2) {
        return compose(r1, r2, false);
    }

    /**
     * Creates a <b>new</b> relation representing the composition of
     * the given relations.
     * A {@code null} argument behaves as if an empty relation was passed.
     * 
     * @param optimize If {@code true}, the new relation will enforce
     * simplicity or injectivity, if possible.
     */
    public static <A, C> MutableRelation<A, C> compose(
            Relation<A, ?> r1, Relation<?, C> r2, final boolean optimize) {
        r1 = id(r1);
        r2 = id(r2);
        final Domain<A> dom = r1.getDomainType();
        final Domain<C> ran = r2.getRangeType();
        MutableRelation<A, C> builder = null;
        if (optimize) {
            if (r1.isSimple() && r1.isInjective()
                    && r2.isSimple() && r2.isInjective()) {
                builder = new OneToOneBiMap<A, C>(dom, ran);
            } else if (r1.isSimple() && r2.isSimple()) {
                builder = new SimpleBiMap<A, C>(dom, ran);
            } else if (r1.isInjective() && r2.isInjective()) {
                builder = converseView(new SimpleBiMap<C, A>(ran, dom));
            } else {
                builder = new BiMap<A, C>(dom, ran);
            }
        } else {
            builder = new BiMap<A, C>(dom, ran);
        }
        for (final A a: r1.keys()) {
            for (final Object b: r1.valuesOf(a)) {
                if (r2.containsKey(b)) {
                    for (final C c: r2.valuesOf(b)) {
                        builder.put(a, c);
                    }
                }
            }
        }
        return builder;
    }


    /**
     * Creates a <b>new</b> relation representing the division between
     * the given relations {@code (r1/r2)}.
     * This is an approximation to real relation division.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<B, A> divide(
            Relation<?, A> r1, Relation<?, B> r2) {
        r1 = id(r1);
        r2 = id(r2);
        final Domain<B> dom = r2.getRangeType();
        final Domain<A> ran = r1.getRangeType();
        MutableRelation<B, A> builder = new BiMap<B, A>(dom, ran);
        for (final B b: r2.values()) {
            for (final A a: r1.values()) {
                boolean include = true;
                for (final Object k: r2.keysOf(b)) {
                    if (!r1.contains(k, a)) {
                        include = false;
                        break;
                    }
                }
                if (include) {
                    builder.put(b, a);
                }
            }
        }
        return builder;
    }


    /**
     * Creates a <b>new</b> relation representing the left division between
     * the given relations {@code (r1\r2)}.
     * This is an approximation to real relation division.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<B, A> divideLeft(
            Relation<A, ?> r1, Relation<B, ?> r2) {
        r1 = id(r1);
        r2 = id(r2);
        final Domain<B> dom = r2.getDomainType();
        final Domain<A> ran = r1.getDomainType();
        MutableRelation<B, A> builder = new BiMap<B, A>(dom, ran);
        for (final A a: r1.keys()) {
            for (final B b: r2.keys()) {
                boolean include = true;
                for (final Object v: r1.valuesOf(a)) {
                    if (!r2.contains(b, v)) {
                        include = false;
                        break;
                    }
                }
                if (include) {
                    builder.put(b, a);
                }
            }
        }
        return builder;
    }


    /**
     * Creates a <b>new</b> relation representing the implication between
     * the given relations {@code (r1 => r2)}.
     * This is an approximation to real relation implication.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<A, B> imply(
            Relation<A, B> r1, Relation<?, ?> r2) {
        r1 = id(r1);
        r2 = id(r2);
        final Domain<A> dom = r1.getDomainType();
        final Domain<B> ran = r1.getRangeType();
        MutableRelation<A, B> builder = new BiMap<A, B>(dom, ran);
        for (final Pair<A, B> p: r1) {
            if (r2.contains(p.first(), p.second())) {
                builder.put(p.first(), p.second());
            }
        }
        return builder;
    }


    /**
     * Creates a <b>new</b> relation representing the kernel
     * of the given relation.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<A, A> kernel(Relation<A, B> r) {
        r = id(r);
        if (r.isEmpty()) {
            return new BiMap<A, A>(r.getDomainType(), r.getDomainType());
        }
        return compose(r, converseView(r));
    }


    /**
     * Creates a <b>new</b> relation representing the image
     * of the given relation.
     * A {@code null} argument behaves as if an empty relation was passed.
     */
    public static <A, B> MutableRelation<B, B> image(Relation<A, B> r) {
        r = id(r);
        if (r.isEmpty()) {
            return new BiMap<B, B>(r.getRangeType(), r.getRangeType());
        }
        return compose(converseView(r), r);
    }


    /**
     * Creates a relation holding all possible arrows between the elements
     * of the given iterable.
     * A {@code null} argument yields an empty relation.
     */
    public static <A> MutableRelation<A, A> top(final Iterable<A> as) {
        return top(as, as);
    }


    /**
     * Creates a relation holding all possible arrows between the elements
     * of the given iterables.
     * {@code null} arguments yield an empty relation.
     */
    public static <A, B> MutableRelation<A, B> top(
            final Iterable<A> as, final Iterable<B> bs) {
        MutableRelation<A, B> r = new BiMap<A, B>();
        if (as != null && bs != null) {
            for (final A a: as) {
                for (final B b: bs) {
                    r.put(a, b);
                }
            }
        }
        return r;
    }


    /**
     * Creates a relation holding all arrows from the elements
     * of the given iterable to themselves.
     * A {@code null} argument yields an empty relation.
     */
    public static <A> MutableRelation<A, A> identity(final Iterable<A> as) {
        MutableRelation<A, A> r = new BiMap<A, A>();
        if (as != null) {
            for (final A a: as) {
                r.put(a, a);
            }
        }
        return r;
    }


    /** */
//     public static <A, B> Set<Pair<A, B>> toSet(final Relation<A, B> r) {
//         checkNotNull(r);
//         Set<Pair<A, B>> set = Sets.<Pair<A, B>>empty();
//         for (A a: r.domain()) {
//             for (B b: r.values(a)) {
//                 set.add(new Pair<A, B>(a, b));
//             }
//         }
//         return set;
//     }



    /*************************************************************************\
     *  Private Methods
    \*************************************************************************/

    /** */
    private static <A, B> MutableRelation<A, B> toOneToOne(Relation<A, B> r) {
        final MutableRelation<A, B> s = new OneToOneBiMap<A, B>(r.size(),
                r.getDomainType(), r.getRangeType());
        for (final A a: r.keys()) {
            s.put(a, r.valueOf(a));
        }
        return s;
    }


    /** */
    private static <A, B> MutableRelation<A, B> toSimple(Relation<A, B> r) {
        final MutableRelation<A, B> s = new SimpleBiMap<A, B>(r.size(),
                r.getDomainType(), r.getRangeType());
        for (final A a: r.keys()) {
            s.put(a, r.valueOf(a));
        }
        return s;
    }


    /** */
    private static <A, B> Relation<A, B> toInjective(Relation<A, B> r) {
        final MutableRelation<B, A> s = new SimpleBiMap<B, A>(r.size(),
                r.getRangeType(), r.getDomainType());
        for (final B b: r.values()) {
            s.put(b, r.keyOf(b));
        }
        return converseView(s);
    }


    /** */
    private static <A, B> MutableRelation<A, B> copy(Relation<A, B> r) {
        MutableRelation<A, B> s = new BiMap<A, B>(r.size(),
                r.getDomainType(), r.getRangeType());
        for (final A a: r.keys()) {
            s.putAllValues(a, r.valuesOf(a));
        }
        return s;
    }


    /** */
    private static <A, B> Relation<A, B> id(Relation<A, B> r) {
        if (r == null) { return new BiMap<A, B>(); }
        return r;
    }


    /** */
    private static <A, B> MutableRelation<A, B> nonNullCopy(
            Relation<A, B> r) {
        if (r == null) { return new BiMap<A, B>(); }
        return copy(r);
    }



    /*************************************************************************\
     *  Nested Classes
    \*************************************************************************/

    /** */
    private static final class Bottom implements Relation<Object, Object> {
        private final Domain<Object> domain = new Domain<Object>();

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Set<Object> keys() {
            return Collections.<Object>emptySet();
        }

        @Override
        public Set<Object> keysOf(Object b) {
            return Collections.<Object>emptySet();
        }

        @Override
        public Object keyOf(Object b) {
            return null;
        }

        @Override
        public Set<Object> keysFor(Iterable<?> bs) {
            return new HashSet<Object>();
        }

        @Override
        public Set<Object> values() {
            return Collections.<Object>emptySet();
        }

        @Override
        public Set<Object> valuesOf(Object a) {
            return Collections.<Object>emptySet();
        }

        @Override
        public Object valueOf(Object a) {
            return null;
        }

        @Override
        public Set<Object> valuesFor(Iterable<?> as) {
            return new HashSet<Object>();
        }

        @Override
        public Domain<Object> getDomainType() {
            return domain;
        }

        @Override
        public Domain<Object> getRangeType() {
            return domain;
        }

        @Override
        public Iterator<Pair<Object, Object>> iterator() {
            return new EmptyIterator();
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean contains(Object a, Object b) {
            return false;
        }

        @Override
        public boolean containsKey(Object a) {
            return false;
        }

        @Override
        public boolean containsValue(Object b) {
            return false;
        }

        @Override
        public boolean isSimple() {
            return true;
        }

        @Override
        public boolean isInjective() {
            return true;
        }

        @Override
        public boolean isEntire() {
            return false;
        }

        @Override
        public boolean isSurjective() {
            return false;
        }

        @Override
        public boolean in(Relation<?, ?> r) {
            return r != null;
        }

        @Override
        public boolean strictlyIn(Relation<?, ?> r) {
            return in(r) && !r.isEmpty();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (!(o instanceof Relation)) { return false; }
            Relation<?, ?> r = (Relation<?, ?>) o;
            return r.isEmpty();
        }

        @Override
        public int hashCode() {
            return 19;
        }


        private static final class EmptyIterator
                implements Iterator<Pair<Object, Object>> {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Pair<Object, Object> next() {
                throw new NoSuchElementException("Empty Iterator");
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Empty Iterator");
            }
        }
    }
}
