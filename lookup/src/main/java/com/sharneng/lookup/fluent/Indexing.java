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
import com.sharneng.lookup.Lookup;

/**
 * Represent a status of the fluent API that the indexing is allowed.
 * 
 * @author Kenneth Xu
 * 
 * @param <E>
 *            the type of source data
 * @param <T>
 *            the element type of the lookup being built
 */
public interface Indexing<E, T> {

    /**
     * Index the source data by the property or computed value specified by the expression.
     * 
     * @param expression
     *            the OGNL expression to index the source data
     * @return an instance of {@link Indexing} that can be further indexed
     */
    Indexed<E, Lookup<T>> by(String expression);

    /**
     * Index the source data by the properties and/or computed values specified by multiple expressions.
     * <p>
     * Avoid this method unless you don't know now many levels of index you want to create. {@link #by(String)
     * by("index1")}.{@link #by(String) by("index2")}.{@link Defined#index()} is a better alternative than
     * {@link #by(String...) by("index1", "index2")}.{@link Defined#index()} because former returns type
     * {@code Lookup<Lookup<Something>>} v.s. the other returns just {@code Lookup<?>}.
     * 
     * @param expressions
     *            the OGNL expressions to index the source data
     * @return an instance of {@link Defined} that can be used to create the lookup
     */
    Defined<Lookup<?>> by(String... expressions);

    /**
     * Index the source data by the converter that can compute index value from the source data.
     * 
     * @param converter
     *            an instance of {@link Converter} that computes index value from source data
     * @return an instance of {@link Indexing} that can be further indexed
     */
    Indexed<E, Lookup<T>> by(Converter<E, Object> converter);

    /**
     * Index the source data by multiple converters that can compute index value from the source data.
     * <p>
     * Avoid this method unless you don't know now many levels of index you want to create. {@link #by(Converter)
     * by(c2)}.{@link #by(Converter) by(c1)}.{@link Defined#index()} is a better alternative than
     * {@link #by(Converter...) by(c1, c2)}.{@link Defined#index()} because former returns type
     * {@code Lookup<Lookup<Something>>} v.s. the other returns just {@code Lookup<?>}.
     * 
     * @param converters
     *            multiple instances of {@link Converter} that compute multilevel index values from source data
     * @return an instance of {@link Defined} that can be used to create the lookup
     */
    Defined<Lookup<?>> by(Converter<E, Object>... converters);
}
