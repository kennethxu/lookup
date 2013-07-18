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

class EmptyLookup<T> implements Lookup<T> {
    @CheckForNull
    private final T defaultValue;

    EmptyLookup(@CheckForNull T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean has(Object key) {
        return false;
    }

    @Override
    public T find(Object key) {
        return defaultValue;
    }

    @Override
    public T find(@CheckForNull Object key, @CheckForNull T defaultValue) {
        return defaultValue;
    }

    @Override
    public T get(Object key) {
        if (defaultValue != null) return defaultValue;
        throw Utils.notFoundException(key);
    }

    @Override
    public T get(@CheckForNull Object key, T defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException(Utils.notNull("defaultValue"));
        return defaultValue;
    }

    @Override
    public T hunt(Object key) {
        throw (key == null) ? new IllegalArgumentException(Utils.notNull("key")) : Utils.notFoundException(key);
    }
}
