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

import static org.bitbucket.rel4j.Check.checkNotNull;


/**
 * Provides a skeletal implementation for a standard mutable relation.
 * It encapsulates {@code Domain} objects to represent its domain and
 * range types. These objects are also used to provide implementaitons
 * for {@code isEntire()} and {@code isSurjective()}.
 * 
 * @author Andre Santos
 * @version 0.1
 */

public abstract class AbstractMutableRelation<A, B>
        extends AbstractRelation<A, B> implements MutableRelation<A, B> {

    /**************************************************************************\
     *  Attributes
    \**************************************************************************/

    /** The type of this relation's domain. */
    protected final Domain<A> domainType;

    /** The type of this relation's range. */
    protected final Domain<B> rangeType;



    /**************************************************************************\
     *  Constructors and Factories
    \**************************************************************************/

    /**
     *  Empty constructor of class AbstractMutableRelation.
     */
    protected AbstractMutableRelation() {
        this.domainType = new Domain<A>();
        this.rangeType  = new Domain<B>();
    }


    /**
     *  Parameter constructor of class AbstractMutableRelation.
     */
    protected AbstractMutableRelation(
            final Domain<A> dom, final Domain<B> ran) {
        checkNotNull(dom);
        checkNotNull(ran);
        this.domainType = dom;
        this.rangeType  = ran;
    }



    /* ************************************************************************\
     *  Getters
    \* ************************************************************************/

    @Override
    public final Domain<A> getDomainType() {
        return this.domainType;
    }

    @Override
    public final Domain<B> getRangeType() {
        return this.rangeType;
    }



    /* ************************************************************************\
     *  Predicates
    \* ************************************************************************/

    @Override
    public boolean isEntire() {
        return this.domainType.isEntire(this.keys());
    }

    @Override
    public boolean isSurjective() {
        return this.rangeType.isEntire(this.values());
    }
}
