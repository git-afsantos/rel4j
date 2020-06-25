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
import org.junit.Test;

/**
 * The test class RelationBuilderContainsTest.
 *
 * @author  André Santos
 * @version 2013-12-13
 */
public class RelationBuilderContainsTest {
    private final Integer zero = Integer.valueOf(0);
    private final Integer one = Integer.valueOf(1);

    private final BiMap<Integer, Integer> builder;

    /** Default constructor for test class RelationBuilderContainsTest */
    public RelationBuilderContainsTest() {
        builder = new BiMap<Integer, Integer>();
        builder.put(zero, one);
    }



    /** */
    @Test
    public void checkContains() {
        assertTrue(builder.contains(zero, one));
    }

    /** */
    @Test
    public void checkNotContains() {
        assertFalse(builder.contains(zero, zero));
    }


    /** */
    @Test
    public void checkContainsKey() {
        assertTrue(builder.containsKey(zero));
    }

    /** */
    @Test
    public void checkNotContainsKey() {
        assertFalse(builder.containsKey(one));
    }


    /** */
    @Test
    public void checkContainsValue() {
        assertTrue(builder.containsValue(one));
    }

    /** */
    @Test
    public void checkNotContainsValue() {
        assertFalse(builder.containsValue(zero));
    }



    /** */
    @Test
    public void containsNullKey() {
        assertFalse(builder.contains(null, one));
    }

    /** */
    @Test
    public void containsNullValue() {
        assertFalse(builder.contains(zero, null));
    }

    /** */
    @Test
    public void containsNullKeyValue() {
        assertFalse(builder.contains(null, null));
    }


    /** */
    @Test
    public void containsKeyNull() {
        assertFalse(builder.containsKey(null));
    }

    /** */
    @Test
    public void containsValueNull() {
        assertFalse(builder.containsValue(null));
    }
}
