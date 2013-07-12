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
package com.sharneng.lookup;

import javax.annotation.CheckForNull;

/**
 * A convenient abstract class that helps to implement {@link Lookup} interface.
 * 
 * @author Kenneth Xu
 * 
 * @param <T>
 *            type of the object to lookup
 */
public abstract class AbstractLookup<T> implements Lookup<T> {

    @CheckForNull
    private final T defaultValue;

    /**
     * Construct a new instance with the specified default value.
     * 
     * @param defaultValue
     *            the default value to be used by {@link #get(Object)} method.
     */
    protected AbstractLookup(@CheckForNull T defaultValue) {
        this.defaultValue = defaultValue;
    }

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
     * This implementation returns true if and only if {@code key} is not {@code null} and {@link #lookup(Object)
     * lookup(key)} is not {@code null}.
     */
    @Override
    public boolean has(@CheckForNull Object key) {
        return key != null && lookup(key) != null;
    }

    /**
     * {@inheritDoc}
     * 
     * @return this implementation returns {@link #find(Object, Object) find(key, defaultValue)}, where the
     *         {@code defaultValue} is the parameter passed to the constructor
     */
    @Override
    public T find(@CheckForNull final Object key) {
        return find(key, defaultValue);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation returns {@code defaultValue} if the {@code key} is {@code null} or {@link #lookup(Object)
     * lookup(key)} is {@code null}. Otherwise return the result of {@link #lookup(Object) lookup(key)}.
     */
    @Override
    public T find(@CheckForNull final Object key, @CheckForNull final T defaultValue) {
        final T result = key == null ? null : lookup(key);
        return result == null ? defaultValue : result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation calls {@link #find(Object, Object) find(key, defaultValue)}, where the {@code defaultValue}
     * is the parameter passed to the constructor. If the result is not {@code null}, return the result. Otherwise
     * throws {@link LookupException}.
     * 
     * @return the reference object found or the {@code defaultValue} passed to the constructor if not found
     */
    public T get(@CheckForNull final Object key) {
        final T result = find(key, defaultValue);
        if (result != null) return result;
        throw Utils.notFoundException(key);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation ensures the {@code defaultValue} is not null and then calls {@link #find(Object, Object)
     * find(key, defaultValue)}.
     */
    @Override
    public T get(@CheckForNull final Object key, final T defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException(Utils.notNull("defaultValue"));
        return find(key, defaultValue);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation triggers exception when key is null or {@link #lookup(Object)} returns null. Otherwise,
     * delegates to {@code lookup} and return the value.
     */
    public T hunt(final Object key) {
        if (key == null) throw new IllegalArgumentException(Utils.notNull("key"));
        final T value = lookup(key);
        if (value != null) return value;
        throw Utils.notFoundException(key);
    }
}
