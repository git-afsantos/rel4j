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
 * The test class RelationBuilderClearTest.
 *
 * @author  André Santos
 * @version 2013-12-13
 */
public class RelationBuilderClearTest {
    private final Integer zero = Integer.valueOf(0);
    private final Integer one = Integer.valueOf(1);

    private BiMap<Integer, Integer> builder;


    /** Default constructor for test class RelationBuilderClearTest */
    public RelationBuilderClearTest() {}


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
    public void clearAndCheckSize() {
        builder.put(one, zero);
        builder.clear();
        assertTrue(builder.isEmpty());
    }

    /** simple */
    @Test
    public void clearAndCheckSimple() {
        builder.put(one, zero);
        builder.clear();
        assertTrue(builder.isSimple());
    }

    /** injective */
    @Test
    public void clearAndCheckInjective() {
        builder.put(one, zero);
        builder.clear();
        assertTrue(builder.isInjective());
    }



    /** Does not affect size */
    @Test
    public void clearEmptyAndCheckSize() {
        builder.clear();
        assertTrue(builder.isEmpty());
    }

    /** simple */
    @Test
    public void clearEmptyAndCheckSimple() {
        builder.clear();
        assertTrue(builder.isSimple());
    }

    /** injective */
    @Test
    public void clearEmptyAndCheckInjective() {
        builder.clear();
        assertTrue(builder.isInjective());
    }
}
