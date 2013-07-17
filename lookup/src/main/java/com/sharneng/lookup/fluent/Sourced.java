/*
 * Copyright (c) 2013 Original Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharneng.lookup.fluent;

import com.sharneng.lookup.Converter;

/**
 * Represent a stage of the fluent API that the source data is provided for building a
 * {@link com.sharneng.lookup.Lookup} instance.
 * 
 * @author Kenneth Xu
 * 
 * @param <E>
 *            type of the source data
 * @param <T>
 *            the element type of the lookup being built
 */
public interface Sourced<E, T> extends Selected<E, T> {

    /**
     * {@inheritDoc}
     */
    Sourced<E, T> notEmpty();

    /**
     * {@inheritDoc}
     */
    Sourced<E, T> useFirstOnDuplicate();

    /**
     * {@inheritDoc}
     */
    Sourced<E, T> useLastOnDuplicate();

    /**
     * Specifies the expression to compute a value from the source data, the computed value will be those returned from
     * the built lookup.
     * 
     * @param clazz
     *            the class of the computed value of the expression
     * @param expression
     *            the expression to compute the value to be returned by the built lookup
     * @param <Q>
     *            the type of the computed value of the expression
     * @return this instance to continue fluent API call
     * 
     */
    <Q> Selected<E, Q> select(Class<Q> clazz, String expression);

    /**
     * Specifies the converter to compute a value from the source data, the computed value will be those returned from
     * the built lookup.
     * 
     * @param converter
     *            the converter to compute the value to be returned by the built lookup
     * @param <Q>
     *            the type of the computed value of the converter
     * @return this instance to continue fluent API call
     */
    <Q> Selected<E, Q> select(Converter<E, Q> converter);
}
