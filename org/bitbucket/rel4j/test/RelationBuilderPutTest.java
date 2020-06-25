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

package org.bitbucket.rel4j.test;

import org.bitbucket.rel4j.BiMap;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RelationBuilderPutTest.
 *
 * @author  André Santos
 * @version 2013-12-13
 */
public class RelationBuilderPutTest {
    private final Integer zero = Integer.valueOf(0);
    private final Integer one = Integer.valueOf(1);
    private final Integer two = Integer.valueOf(2);

    private BiMap<Integer, Integer> builder;


    /** Default constructor for test class RelationBuilderPutTest */
    public RelationBuilderPutTest() {}


    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        builder = new BiMap<Integer, Integer>();
    }


    /** Affects size */
    @Test
    public void putAndCheckSize() {
        builder.put(one, two);
        assertTrue(builder.size() == 1);
    }

    /** simple -> simple */
    @Test
    public void putAndCheckSimple() {
        builder.put(one, two);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void putAndCheckInjective() {
        builder.put(one, two);
        assertTrue(builder.isInjective());
    }



    /** Does not affect size */
    @Test
    public void putRepeatedAndCheckSize() {
        builder.put(zero, one);
        builder.put(zero, one);
        assertTrue(builder.size() == 1);
    }

    /** simple -> simple */
    @Test
    public void putRepeatedAndCheckSimple() {
        builder.put(zero, one);
        builder.put(zero, one);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void putRepeatedAndCheckInjective() {
        builder.put(zero, one);
        builder.put(zero, one);
        assertTrue(builder.isInjective());
    }

    /** !simple -> !simple */
    @Test
    public void putRepeatedAndCheckNonSimple() {
        builder.put(zero, two);
        builder.put(zero, one);
        builder.put(zero, one);
        assertFalse(builder.isSimple());
    }

    /** !injective -> !injective */
    @Test
    public void putRepeatedAndCheckNonInjective() {
        builder.put(two, one);
        builder.put(zero, one);
        builder.put(zero, one);
        assertFalse(builder.isInjective());
    }

    

    /** simple -> !simple */
    @Test
    public void putNonSimpleAndCheckNonSimple() {
        builder.put(zero, two);
        builder.put(zero, one);
        assertFalse(builder.isSimple());
    }

    /** injective -> !injective */
    @Test
    public void putNonInjectiveAndCheckNonInjective() {
        builder.put(two, one);
        builder.put(zero, one);
        assertFalse(builder.isInjective());
    }



    /** */
    @Test
    public void putNullKey() {
        assertFalse(builder.put(null, one));
    }

    /** */
    @Test
    public void putNullValue() {
        assertFalse(builder.put(one, null));
    }

    /** */
    @Test
    public void putNullKeyValue() {
        assertFalse(builder.put(null, null));
    }
}
