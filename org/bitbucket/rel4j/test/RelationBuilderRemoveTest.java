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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class RelationBuilderRemoveTest.
 *
 * @author  André Santos
 * @version 2013-12-13
 */
public class RelationBuilderRemoveTest {
    private final Integer zero = Integer.valueOf(0);
    private final Integer one = Integer.valueOf(1);
    private final Integer two = Integer.valueOf(2);

    private final BiMap<Integer, Integer> builder =
        new BiMap<Integer, Integer>();


    /** Default constructor for test class RelationBuilderRemoveTest */
    public RelationBuilderRemoveTest() {}


    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp() {
        builder.put(one, two);
    }


    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown() {
        builder.clear();
    }



    /** Removing affects size. */
    @Test
    public void removeAndCheckSize() {
        builder.remove(one, two);
        assertTrue(builder.isEmpty());
    }

    /** simple -> simple */
    @Test
    public void removeAndCheckSimple() {
        builder.remove(one, two);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void removeAndCheckInjective() {
        builder.remove(one, two);
        assertTrue(builder.isInjective());
    }



    /** Does not affect size */
    @Test
    public void removeNonExistantAndCheckSize() {
        builder.remove(zero, one);
        assertFalse(builder.isEmpty());
    }

    /** simple -> simple */
    @Test
    public void removeNonExistantAndCheckSimple() {
        builder.remove(zero, one);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void removeNonExistantAndCheckInjective() {
        builder.remove(zero, one);
        assertTrue(builder.isInjective());
    }

    /** !simple -> !simple */
    @Test
    public void removeNonExistantAndCheckNonSimple() {
        builder.put(one, zero);
        builder.remove(zero, one);
        assertFalse(builder.isSimple());
    }

    /** !injective -> !injective */
    @Test
    public void removeNonExistantAndCheckNonInjective() {
        builder.put(zero, two);
        builder.remove(zero, one);
        assertFalse(builder.isInjective());
    }

    

    /** !simple -> simple */
    @Test
    public void removeNonSimpleAndCheckSimple() {
        builder.put(one, zero);
        builder.remove(one, two);
        assertTrue(builder.isSimple());
    }

    /** !injective -> injective */
    @Test
    public void removeNonInjectiveAndCheckInjective() {
        builder.put(zero, two);
        builder.remove(one, two);
        assertTrue(builder.isInjective());
    }



    /** Affects size */
    @Test
    public void removeKeyAndCheckSize() {
        builder.removeKey(one);
        assertTrue(builder.isEmpty());
    }

    /** simple -> simple */
    @Test
    public void removeKeyAndCheckSimple() {
        builder.removeKey(one);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void removeKeyAndCheckInjective() {
        builder.removeKey(one);
        assertTrue(builder.isInjective());
    }



    /** Does not affect size */
    @Test
    public void removeNonExistantKeyAndCheckSize() {
        builder.removeKey(zero);
        assertFalse(builder.isEmpty());
    }

    /** simple -> simple */
    @Test
    public void removeNonExistantKeyAndCheckSimple() {
        builder.removeKey(zero);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void removeNonExistantKeyAndCheckInjective() {
        builder.removeKey(zero);
        assertTrue(builder.isInjective());
    }

    /** !simple -> !simple */
    @Test
    public void removeNonExistantKeyAndCheckNonSimple() {
        builder.put(one, zero);
        builder.removeKey(zero);
        assertFalse(builder.isSimple());
    }

    /** !injective -> !injective */
    @Test
    public void removeNonExistantKeyAndCheckNonInjective() {
        builder.put(zero, two);
        builder.removeKey(two);
        assertFalse(builder.isInjective());
    }

    

    /** !simple -> simple */
    @Test
    public void removeNonSimpleKeyAndCheckSimple() {
        builder.put(one, zero);
        builder.removeKey(one);
        assertTrue(builder.isSimple());
    }

    /** !injective -> injective */
    @Test
    public void removeNonInjectiveKeyAndCheckInjective() {
        builder.put(zero, two);
        builder.removeKey(one);
        assertTrue(builder.isInjective());
    }



    /** Affects size */
    @Test
    public void removeValueAndCheckSize() {
        builder.removeValue(two);
        assertTrue(builder.isEmpty());
    }

    /** simple -> simple */
    @Test
    public void removeValueAndCheckSimple() {
        builder.removeValue(two);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void removeValueAndCheckInjective() {
        builder.removeValue(two);
        assertTrue(builder.isInjective());
    }



    /** Does not affect size */
    @Test
    public void removeNonExistantValueAndCheckSize() {
        builder.removeValue(zero);
        assertFalse(builder.isEmpty());
    }

    /** simple -> simple */
    @Test
    public void removeNonExistantValueAndCheckSimple() {
        builder.removeValue(zero);
        assertTrue(builder.isSimple());
    }

    /** injective -> injective */
    @Test
    public void removeNonExistantValueAndCheckInjective() {
        builder.removeValue(zero);
        assertTrue(builder.isInjective());
    }

    /** !simple -> !simple */
    @Test
    public void removeNonExistantValueAndCheckNonSimple() {
        builder.put(one, zero);
        builder.removeValue(one);
        assertFalse(builder.isSimple());
    }

    /** !injective -> !injective */
    @Test
    public void removeNonExistantValueAndCheckNonInjective() {
        builder.put(zero, two);
        builder.removeValue(one);
        assertFalse(builder.isInjective());
    }

    

    /** !simple -> simple */
    @Test
    public void removeNonSimpleValueAndCheckSimple() {
        builder.put(one, zero);
        builder.removeValue(two);
        assertTrue(builder.isSimple());
    }

    /** !injective -> injective */
    @Test
    public void removeNonInjectiveValueAndCheckInjective() {
        builder.put(zero, two);
        builder.removeValue(two);
        assertTrue(builder.isInjective());
    }



    /** */
    @Test
    public void removeKeyNull() {
        assertTrue(builder.removeKey(null).isEmpty());
    }

    /** */
    @Test
    public void removeValueNull() {
        assertTrue(builder.removeValue(null).isEmpty());
    }

    /** */
    @Test
    public void removeNullKey() {
        assertFalse(builder.remove(null, two));
    }

    /** */
    @Test
    public void removeNullValue() {
        assertFalse(builder.remove(one, null));
    }

    /** */
    @Test
    public void removeNullKeyValue() {
        assertFalse(builder.remove(null, null));
    }
}
