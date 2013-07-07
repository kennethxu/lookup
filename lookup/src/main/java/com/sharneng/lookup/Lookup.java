/*
 * Copyright (c) 2011 Original Authors
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
package com.sharneng.lookup;

import javax.annotation.CheckForNull;
import javax.annotation.Nullable;

/**
 * The primary interface to support lookup of a object by some index.
 * 
 * @author Kenneth Xu
 * 
 * @param <T>
 *            the type of the object to be looked up.
 */
public interface Lookup<T> {

    /**
     * Lookup the reference object by given key.
     * 
     * @param key
     *            the key to lookup the object
     * @return the reference object found.
     * @IllegalArgumentException when key is null;
     * @LookupException if failed to lookup the reference object.
     */
    T get(Object key);

    /**
     * Lookup the reference object by given key. Return null if not found.
     * <p>
     * This is different from {@link #get(Object)} by not throwing exception.
     * 
     * @param key
     *            the key to lookup the object.
     * @return the reference object found or null if not found.
     */
    @CheckForNull
    T safeGet(@Nullable Object key);
}
