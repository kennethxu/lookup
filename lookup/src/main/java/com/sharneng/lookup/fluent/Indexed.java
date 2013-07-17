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

import com.sharneng.lookup.Lookup;

/**
 * Represent a indexed stage of the fluent API that enough information is collected to create a new
 * {@link com.sharneng.lookup.Lookup} instance, or further indexing can be added to it.
 * 
 * @author Kenneth Xu
 * 
 * @param <E>
 *            The type of the source data to create the lookup
 * @param <T>
 *            The type of the lookup to be built
 */
public interface Indexed<E, T extends Lookup<?>> extends Defined<T>, Indexing<E, T> {
}
