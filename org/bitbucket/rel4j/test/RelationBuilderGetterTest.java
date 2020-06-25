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

import java.util.HashSet;
import java.util.Set;

/**
 * The test class RelationBuilderGetterTest.
 *
 * @author  André Santos
 * @version 2013-12-13
 */
public class RelationBuilderGetterTest {
    private final Integer zero = Integer.valueOf(0);
    private final Integer one = Integer.valueOf(1);

    private final BiMap<Integer, Integer> builder;

    private final Set<Integer> set;

    /** Default constructor for test class RelationBuilderGetterTest */
    public RelationBuilderGetterTest() {
        builder = new BiMap<Integer, Integer>();
        builder.put(zero, zero);
        builder.put(zero, one);
        builder.put(one, zero);
        builder.put(one, one);
        set = new HashSet<Integer>(2);
        set.add(zero);
        set.add(one);
    }


    /** */
    @Test
    public void keys() {
        assertEquals(set, builder.keys());
    }

    /** */
    @Test
    public void values() {
        assertEquals(set, builder.values());
    }



    /** */
    @Test
    public void keysForValue() {
        assertEquals(set, builder.keysOf(zero));
    }

    /** */
    @Test
    public void keysForValues() {
        assertEquals(set, builder.keysFor(set));
    }


    /** */
    @Test
    public void valuesForKey() {
        assertEquals(set, builder.valuesOf(zero));
    }

    /** */
    @Test
    public void valuesForKeys() {
        assertEquals(set, builder.valuesFor(set));
    }



    /** */
    @Test
    public void keysForNonExistantValue() {
        assertTrue(builder.keysOf(Integer.valueOf(2)).isEmpty());
    }

    /** */
    @Test
    public void valuesForNonExistantKey() {
        assertTrue(builder.valuesOf(Integer.valueOf(2)).isEmpty());
    }



    /** */
    @Test
    public void keysForNull() {
        assertTrue(builder.keysOf(null).isEmpty());
    }

    /** */
    @Test
    public void valuesForNull() {
        assertTrue(builder.valuesOf(null).isEmpty());
    }
}
