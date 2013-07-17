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

import javax.annotation.CheckForNull;

/**
 * Represent a stage of the fluent API that the resulting member is selected for building a
 * {@link com.sharneng.lookup.Lookup} instance.
 * 
 * @author Kenneth Xu
 * 
 * @param <E>
 *            type of the source data
 * @param <T>
 *            the element type of the lookup being built
 */
public interface Selected<E, T> extends Indexing<E, T> {

    /**
     * Ensure the source data collection is neither null nor empty.
     * 
     * @return this instance for continuous fluent API call
     * @exception com.sharneng.lookup.LookupBuildException
     *                if the source collection is null or empty
     */
    Selected<E, T> notEmpty();

    /**
     * When same set of key resolve to two or more duplicated values, use first occurrence of the result.
     * 
     * @return this instance for continuous fluent API call
     */
    Selected<E, T> useFirstOnDuplicate();

    /**
     * When same set of key resolve to two or more duplicated values, use last occurrence of the result.
     * 
     * @return this instance for continuous fluent API call
     */
    Selected<E, T> useLastOnDuplicate();

    /**
     * Set the default value to be returned when the referenced value is not found in the built lookup.
     * 
     * @param defaultValue
     *            the default value to be returned when the referenced value is not found in the built lookup
     * @return this instance for continuous fluent API call
     */
    Selected<E, T> defaultTo(@CheckForNull T defaultValue);
}
