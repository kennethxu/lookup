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

import com.sharneng.lookup.fluent.Defined;
import com.sharneng.lookup.fluent.Indexed;
import com.sharneng.lookup.fluent.Sourced;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
final class LookupBuilder<E, T> implements Sourced<E, T> {
    private enum Duplication {
        FIRST,
        LAST,
        FAIL
    }

    private class Indexer implements Indexed<E, Lookup<T>> {

        @SuppressWarnings("unchecked")
        @Override
        public Lookup<T> index() {
            return (Lookup<T>) build();
        }

        @SuppressWarnings("unchecked")
        @Override
        public Indexed<E, Lookup<Lookup<T>>> by(String property) {
            return (Indexed<E, Lookup<Lookup<T>>>) (Indexed<E, ?>) LookupBuilder.this.by(property);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Defined<Lookup<?>> by(String... properties) {
            for (String s : properties)
                LookupBuilder.this.by(s);
            return (Defined<Lookup<?>>) (Defined<?>) this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public Indexed<E, Lookup<Lookup<T>>> by(Converter<E, Object> converter) {
            return (Indexed<E, Lookup<Lookup<T>>>) (Indexed<E, ?>) LookupBuilder.this.by(converter);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Defined<Lookup<?>> by(Converter<E, Object>... converters) {
            for (Converter<E, Object> converter : converters)
                LookupBuilder.this.by(converter);
            return (Defined<Lookup<?>>) (Defined<?>) this;
        }

    }

    private Duplication duplication = Duplication.FAIL;
    @CheckForNull
    private T defaultValue;
    private Lookup<?>[] chain;
    private Object[] keys;
    private final Collection<? extends E> source;
    @SuppressWarnings("unchecked")
    private Converter<E, T> selectConverter = (Converter<E, T>) Utils.toSelf();
    private int keyCount;
    private List<Converter<E, Object>> converters = new ArrayList<Converter<E, Object>>();
    private Indexer indexer = new Indexer();

    public LookupBuilder(@CheckForNull final Collection<? extends E> source) {
        this.source = source == null ? Collections.<E> emptyList() : source;
    }

    @Override
    public Sourced<E, T> defaultTo(@CheckForNull T defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public <Q> Sourced<E, Q> select(Class<Q> clazz, String expression) {
        return select(new OgnlConverter<E, Q>(clazz, expression));
    }

    @Override
    public <Q> Sourced<E, Q> select(Converter<E, Q> converter) {
        @SuppressWarnings("unchecked")
        final LookupBuilder<E, Q> that = (LookupBuilder<E, Q>) this;
        that.selectConverter = converter;
        return that;
    }

    @Override
    public Indexed<E, Lookup<T>> by(Converter<E, Object> converter) {
        converters.add(converter);
        return indexer;
    }

    @Override
    public Indexed<E, Lookup<T>> by(String property) {
        return by(new OgnlConverter<E, Object>(Object.class, property));
    }

    @Override
    public Defined<Lookup<?>> by(String... properties) {
        return indexer.by(properties);
    }

    @Override
    public Defined<Lookup<?>> by(Converter<E, Object>... converters) {
        return indexer.by(converters);
    }

    @Override
    public Sourced<E, T> notEmpty() {
        if (source.size() == 0) throw new LookupBuildException("source collection must not be empty");
        return this;
    }

    @Override
    public Sourced<E, T> useFirstOnDuplicate() {
        duplication = Duplication.FIRST;
        return this;
    }

    @Override
    public Sourced<E, T> useLastOnDuplicate() {
        duplication = Duplication.LAST;
        return this;
    }

    private Lookup<?> build() {
        keyCount = converters.size();
        this.chain = buildChain();
        this.keys = new Object[keyCount];
        return (keyCount > 1 ? multiLevel(source, 0) : lastLevel(source, converters.get(0)));
    }

    private Lookup<?>[] buildChain() {
        Lookup<?>[] chain = new Lookup<?>[keyCount];
        Lookup<?> lookup = new EmptyLookup<T>(defaultValue);
        chain[0] = lookup;
        for (int i = 1; i < keyCount; i++) {
            lookup = new EmptyLookup<Object>(lookup);
            chain[i] = lookup;
        }
        return chain;
    }

    private Lookup<?> multiLevel(final Collection<? extends E> values, int index) {

        Converter<E, Object> converter = converters.get(index);
        if (++index == keyCount) return lastLevel(values, converter); // last one

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
            lookupMap.put(key, multiLevel(entry.getValue(), index));
        }

        return new MapBasedLookup<Lookup<?>>(lookupMap, chain[keyCount - index - 1]);
    }

    private Lookup<T> lastLevel(final Collection<? extends E> values, Converter<E, Object> converter) {
        final Map<Object, T> map = new HashMap<Object, T>();
        for (E e : values) {
            T value = selectConverter.convert(e);
            final Object key = converter.convert(e);
            if (duplication == Duplication.LAST || !map.containsKey(key)) {
                map.put(key, value);
            } else if (duplication == Duplication.FAIL) {
                keys[keys.length - 1] = key;
                throw new DuplicateKeyException(value, map.get(key), keys);
            }
        }
        return new MapBasedLookup<T>(map, defaultValue);
    }

}
