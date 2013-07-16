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
import java.util.List;
import java.util.Map;

import javax.annotation.CheckForNull;

/**
 * Class to hold factory methods to return the implementation of {@link Lookup}.
 * 
 * @author Kenneth Xu
 * 
 */
final class FluentBuilder<E, T> {

    private T defaultValue;
    private Class<? extends E> sourceClass;
    private Class<T> selectClass;
    private Converter<E, Object>[] converters;
    private Lookup<?>[] chain;
    private Object[] keys;
    private Collection<? extends E> source;
    private List<Converter<? extends E, ? extends Object>> converterList = new ArrayList<Converter<? extends E, ? extends Object>>();
    private List<String> properties = new ArrayList<String>();

    public FluentBuilder(final Collection<? extends E> source) {
        this.source = source;
        converterList.add(null);
        properties.add(null);
    }

    public Lookup<?> build() {
        this.chain = buildChain();
        this.keys = new Object[converters.length];
        return build(source, 0);
    }

    public void addSelect(String property, Class<T> clazz) {
        properties.set(0, property);
        selectClass = clazz;
    }

    public void addIndex(String property) {
        properties.add(property);
    }

    private Lookup<?>[] buildChain() {
        Lookup<?>[] chain = new Lookup<?>[converters.length];
        Lookup<?> lookup = new EmptyLookup<T>(getDefaultValue());
        chain[0] = lookup;
        for (int i = 1; i < converters.length; i++) {
            lookup = new EmptyLookup<Object>(lookup);
            chain[i] = lookup;
        }
        return chain;
    }

    private Lookup<?> build(final Collection<? extends E> values, int index) {

        Converter<E, Object> converter = converters[index];
        if (++index == converters.length) return new MapBasedLookup<E, T>(getDefaultValue(), values, converter) {
            protected void handleDuplicate(T oldValue, T newValue, Object key) {
                keys[keys.length - 1] = key;
                throw new DuplicateKeyException(newValue, oldValue, keys);
            }
        }; // last one

        Map<Object, Collection<E>> map = new HashMap<Object, Collection<E>>();
        for (E value : values) {
            Object key = converter.convert(value);
            Collection<E> c = map.get(key);
            if (c == null) {
                c = new ArrayList<E>();
                map.put(key, c);
            }
            c.add(value);
        }

        Map<Object, Lookup<?>> lookupMap = new HashMap<Object, Lookup<?>>();
        for (Map.Entry<Object, Collection<E>> entry : map.entrySet()) {
            final Object key = entry.getKey();
            keys[index - 1] = key;
            lookupMap.put(key, build(entry.getValue(), index));
        }

        return new MapBasedLookup<Lookup<?>, Lookup<?>>(lookupMap, chain[converters.length - index - 1]);
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Collection<? extends E> getSource() {
        return source;
    }

    public void setSource(Collection<? extends E> source) {
        this.source = source;
    }

    public Class<? extends E> getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class<? extends E> sourceClass) {
        this.sourceClass = sourceClass;
    }

}
