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

import static org.bitbucket.rel4j.Check.checkArgument;


/**
 * Represents the cardinality of a set.
 * 
 * @author Andre Santos
 * @since 0.2
 * @version 0.2
 */

public abstract class Cardinal {

    /**************************************************************************\
     *  Fields
    \**************************************************************************/

    private static final Cardinal NATURALS = new N0();
    private static final Cardinal REALS = new C();



    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Empty constructor of class Cardinal.
     */
    private Cardinal() {}


    /**
     * Returns a cardinality of the given non-negative value.
     * 
     * @param value the value of the cardinality
     * @return a cardinality with the given value
     * @throws IllegalArgumentException if the value is negative
     */
    public static Cardinal of(final int value) {
        checkArgument("value must be non-negative", value >= 0);
        return new Finite(value);
    }

    /**
     * Returns the cardinality of the natural numbers.
     * This is a countably infinite cardinality.
     * 
     * @return a countably infinite cardinality
     */
    public static Cardinal ofNaturals() {
        return NATURALS;
    }

    /**
     * Returns the cardinality of the real numbers.
     * This is an uncountably infinite cardinality.
     * 
     * @return an uncountably infinite cardinality
     */
    public static Cardinal ofReals() {
        return REALS;
    }



    /**************************************************************************\
     *  Getters
    \**************************************************************************/

    /** */
    public abstract int value();



    /**************************************************************************\
     *  Predicates
    \**************************************************************************/

    /** */
    public abstract boolean isFinite();

    /** */
    public abstract boolean isCountable();



    /**************************************************************************\
     *  Nested Classes
    \**************************************************************************/

    /**
     * Represents a finite cardinality.
     */
    private static final class Finite extends Cardinal {
        private final int value;

        Finite(int value) {
            assert value >= 0;
            this.value = value;
        }

        @Override
        public int value() {
            return value;
        }

        @Override
        public boolean isFinite() {
            return true;
        }

        @Override
        public boolean isCountable() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) { return true; }
            if (!(o instanceof Finite)) { return false; }
            return value == ((Finite) o).value;
        }

        @Override
        public int hashCode() {
            return value;
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }
    }


    /**
     * Represents the cardinality of the natural numbers.
     */
    private static final class N0 extends Cardinal {
        @Override
        public int value() {
            throw new NoSuchValueException("infinite cardinality");
        }

        @Override
        public boolean isFinite() {
            return false;
        }

        @Override
        public boolean isCountable() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof N0;
        }

        @Override
        public int hashCode() {
            return -1;
        }

        @Override
        public String toString() {
            return "N0";
        }
    }


    /**
     * Represents the cardinality of the real numbers.
     */
    private static final class C extends Cardinal {
        @Override
        public int value() {
            throw new NoSuchValueException("infinite cardinality");
        }

        @Override
        public boolean isFinite() {
            return false;
        }

        @Override
        public boolean isCountable() {
            return false;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof C;
        }

        @Override
        public int hashCode() {
            return Integer.MIN_VALUE;
        }

        @Override
        public String toString() {
            return "C";
        }
    }


    /**
     * Exception to be thrown when trying to convert infinite values
     * to finite values.
     */
    private static final class NoSuchValueException extends RuntimeException {
        private static final long serialVersionUID = 984254356648L;

        NoSuchValueException() {
            super();
        }

        NoSuchValueException(final String message) {
            super(message);
        }
    }
}
