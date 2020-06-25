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


/**
 * Check
 * 
 * @author Andre Santos
 * @version 2013-12-13
 */

public final class Check {

    /*************************************************************************\
     *  Fields
    \*************************************************************************/

    private static final String NULL = "object is null";
    private static final String NULL_ARG = "argument is null";
    private static final String FALSE = "predicate is false";



    /*************************************************************************\
     *  Constructors
    \*************************************************************************/

    /**
     *  Empty constructor of objects of class Check.
     */
    private Check() {
        throw new AssertionError();
    }



    /*************************************************************************\
     *  Predicates
    \*************************************************************************/

    /** */
    public static boolean isNull(final Object o) {
        return o == null;
    }



    /*************************************************************************\
     *  Public Methods
    \*************************************************************************/

    /** */
    public static void checkNotNull(final Object o) {
        checkNotNull(NULL, o);
    }

    /** */
    public static void checkNotNull(final String msg, final Object o) {
        if (o == null) { throw new NullPointerException(msg); }
    }

    /** */
    public static void checkNoNulls(final Iterable<? extends Object> os) {
        checkNoNulls(NULL, os);
    }

    /** */
    public static void checkNoNulls(final String msg,
            final Iterable<? extends Object> os) {
        checkNotNull(msg, os);
        for (final Object o: os) {
            checkNotNull(msg, o);
        }
    }

    /** */
    public static void checkArgument(final Object o) {
        checkArgument(NULL_ARG, o != null);
    }

    /** */
    public static void checkArgument(final boolean predicate) {
        checkArgument(FALSE, predicate);
    }

    /** */
    public static void checkArgument(final String msg, final Object o) {
        checkArgument(msg, o != null);
    }

    /** */
    public static void checkArgument(final String msg,
            final boolean predicate) {
        if (!predicate) { throw new IllegalArgumentException(msg); }
    }
}
