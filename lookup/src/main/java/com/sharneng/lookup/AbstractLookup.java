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
 * A convenient abstract class that helps to implement {@link Lookup&lt;T&gt;} interface.
 * 
 * @author Kenneth Xu
 * 
 * @param <T>
 *            type of the object to lookup
 */
public abstract class AbstractLookup<T> implements Lookup<T> {

    /**
     * Subclass to implements the actual lookup logic.
     * 
     * @param key
     *            the key to lookup the reference object
     * @return the reference object when found or null if not found
     */
    @CheckForNull
    protected abstract T lookup(Object key);

    /**
     * {@inheritDoc}
     * <p>
     * This implementation triggers exception when key is null or {@link #lookup(Object)} returns null. Otherwise,
     * delegates to {@code lookup} and return the value.
     */
    public T get(final Object key) {
        if (key == null) throw new IllegalArgumentException("key must not be null");
        final T value = lookup(key);
        if (value != null) return value;
        throw new LookupException("Value not found for given key " + key);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation calls {@link #lookup(Object)} if key is not null or otherwise return null.
     */
    @CheckForNull
    public T safeGet(@Nullable final Object key) {
        return key == null ? null : lookup(key);
    }
}
