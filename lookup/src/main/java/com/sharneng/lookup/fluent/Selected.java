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

/**
 * Represent a stage of the fluent API that the resulting member is selected for building a
 * {@link com.sharneng.lookup.Lookup} instance.
 * 
 * @author Kenneth Xu
 * 
 * @param <T>
 * @param <P>
 */
public interface Selected<T, P> extends Indexing<P> {

    // Selected<T, P> of(Class<? extends T> clazz);

    Selected<T, P> unqiue();

    Selected<T, P> defaultTo(P defaultValue);
}