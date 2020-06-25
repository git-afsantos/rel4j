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
import org.bitbucket.rel4j.Relation;
import org.bitbucket.rel4j.Relations;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The test class RelationsTest.
 *
 * @author  André Santos
 * @version 2013-12-13
 */
public class RelationsTest {
    private final BiMap<Character, Integer> r1;
    private final BiMap<Integer, String> r2;

    /** Default constructor for test class RelationsTest */
    public RelationsTest() {
        r1 = new BiMap<Character, Integer>();
        r1.put(Character.valueOf('a'), Integer.valueOf(0));
        r1.put(Character.valueOf('a'), Integer.valueOf(1));
        r1.put(Character.valueOf('a'), Integer.valueOf(2));
        r1.put(Character.valueOf('b'), Integer.valueOf(1));
        r1.put(Character.valueOf('c'), Integer.valueOf(2));
        r2 = new BiMap<Integer, String>();
        r2.put(Integer.valueOf(0), "a");
        r2.put(Integer.valueOf(1), "a");
        r2.put(Integer.valueOf(1), "b");
        r2.put(Integer.valueOf(3), "x");
    }


    /** */
    @Test
    public void testCompose() {
        BiMap<Character, String> r3 = new BiMap<>();
        r3.put(Character.valueOf('a'), "a");
        r3.put(Character.valueOf('a'), "b");
        r3.put(Character.valueOf('b'), "b");
        r3.put(Character.valueOf('b'), "a");
        assertEquals(r3, Relations.compose(r1, r2));
    }


    /** */
    @Test
    public void testKernel() {
        Character a = Character.valueOf('a');
        Character b = Character.valueOf('b');
        Character c = Character.valueOf('c');
        BiMap<Character, Character> r3 = new BiMap<>();
        r3.put(a, a);
        r3.put(a, b);
        r3.put(a, c);
        r3.put(b, b);
        r3.put(b, a);
        r3.put(c, c);
        r3.put(c, a);
        assertEquals(r3, Relations.kernel(r1));
    }


    /** */
    @Test
    public void testImage() {
        Integer zero = Integer.valueOf(0);
        Integer one = Integer.valueOf(1);
        Integer two = Integer.valueOf(2);
        BiMap<Integer, Integer> r3 = new BiMap<>();
        r3.put(zero, zero);
        r3.put(zero, one);
        r3.put(zero, two);
        r3.put(one, zero);
        r3.put(one, one);
        r3.put(one, two);
        r3.put(two, zero);
        r3.put(two, one);
        r3.put(two, two);
        assertEquals(r3, Relations.image(r1));
    }
}
