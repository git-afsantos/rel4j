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
 * AbstractView
 * 
 * @author afs
 * @version 2014-08-03
 */

abstract class AbstractView<A, B, K, V> extends AbstractRelation<A, B> {

    /*************************************************************************\
     *  Getters
    \*************************************************************************/

    /** */
    protected abstract Relation<K, V> relation();



    /*************************************************************************\
     *  Predicates
    \*************************************************************************/

    /** */
    @Override
    public boolean isEmpty() {
        return this.relation().isEmpty();
    }


    /** */
    @Override
    public boolean isSimple() {
        return this.relation().isSimple();
    }

    /** */
    @Override
    public boolean isInjective() {
        return this.relation().isInjective();
    }

    /** */
    @Override
    public boolean isEntire() {
        return this.relation().isEntire();
    }

    /** */
    @Override
    public boolean isSurjective() {
        return this.relation().isSurjective();
    }
}
