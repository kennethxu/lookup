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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.CheckForNull;

/**
 * Class to hold factory methods to return the implementation of {@link Lookup}.
 * 
 * @author Kenneth Xu
 * 
 */
final class LookupBuilder<T> {

    private final T defaultValue;
    private final Converter<T, Object>[] converters;
    private final Lookup<?>[] chain;
    private final Object[] keys;

    public LookupBuilder(@CheckForNull T defaultValue, final Converter<T, Object>[] converters) {
        this.defaultValue = defaultValue;
        this.converters = converters;
        this.chain = buildChain();
        this.keys = new Object[converters.length];
    }

    public LookupBuilder(@CheckForNull T defaultValue, final Class<? extends T> clazz, final String... properties) {
        this(defaultValue, Utils.toConverters(clazz, properties));
    }

    public Lookup<?> build(final Collection<? extends T> values) {
        return build(values, 0);
    }

    private Lookup<?>[] buildChain() {
        Lookup<?>[] chain = new Lookup<?>[converters.length];
        Lookup<?> lookup = new EmptyLookup<T>(defaultValue);
        chain[0] = lookup;
        for (int i = 1; i < converters.length; i++) {
            lookup = new EmptyLookup<Object>(lookup);
            chain[i] = lookup;
        }
        return chain;
    }

    private Lookup<?> build(final Collection<? extends T> values, int index) {

        Converter<T, Object> converter = converters[index];
        if (++index == converters.length) return new MapBasedLookup<T>(defaultValue, values, converter) {
            protected void handleDuplicate(T oldValue, T newValue, Object key) {
                keys[keys.length - 1] = key;
                throw new DuplicateKeyException(newValue, oldValue, keys);
            }
        }; // last one

        Map<Object, Collection<T>> map = new HashMap<Object, Collection<T>>();
        for (T value : values) {
            Object key = converter.convert(value);
            Collection<T> c = map.get(key);
            if (c == null) {
                c = new ArrayList<T>();
                map.put(key, c);
            }
            c.add(value);
        }

        Map<Object, Lookup<?>> lookupMap = new HashMap<Object, Lookup<?>>();
        for (Map.Entry<Object, Collection<T>> entry : map.entrySet()) {
            final Object key = entry.getKey();
            keys[index - 1] = key;
            lookupMap.put(key, build(entry.getValue(), index));
        }

        return new MapBasedLookup<Lookup<?>>(lookupMap, chain[converters.length - index - 1]);
    }

}
