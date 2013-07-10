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

/**
 * Class to hold factory methods to return the implementation of {@link Lookup}.
 * 
 * @author Kenneth Xu
 * 
 */
public final class Lookups {

    /**
     * Lookup API supports no more than 10 levels of lookups.
     */
    public static final int LEVEL_LIMIT = 10;

    private Lookups() {
    }

    /**
     * Creates a lookup based on given map.
     * 
     * @param map
     *            the map to create the lookup
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} that is backed by given map
     * 
     */
    public static <T> Lookup<T> create(final Map<? extends Object, ? extends T> map) {
        if (map == null) throw new IllegalArgumentException(Utils.notNull("map"));
        return new MapBasedLookup<T>(map, null);
    }

    /**
     * Create a lookup for objects in the given collection indexed by given property.
     * <p>
     * The class where the index property is searched for is determined by the first non-null element in the collection.
     * Other than that, this is equivalent to {@link #create(Collection, Class, String)}.
     * 
     * @param values
     *            a collection of objects that can be looked up.
     * @param property
     *            the index property
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} indexed by the specified property
     */
    public static <T> Lookup<T> create(final Collection<? extends T> values, final String property) {
        checkValues(values);
        if (property == null) throw new IllegalArgumentException(Utils.notNull("property"));
        return create(values, getElementClass(values), property);
    }

    /**
     * Create a two level lookup for objects in the given collection indexed by given properties.
     * <p>
     * The class where the index properties are searched for is determined by the first non-null element in the
     * collection. Other than that, this is equivalent to {@link #create(Collection, Class, String, String)}.
     * 
     * @param values
     *            a collection of objects that can be looked up.
     * @param property1
     *            the first index property
     * @param property2
     *            the second index property
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} of {@code Lookup} indexed by the specified properties in order
     */
    public static <T> Lookup<Lookup<T>> create(final Collection<? extends T> values, final String property1,
            final String property2) {
        final Lookup<?> lookup = create(values, new String[] { property1, property2 });
        @SuppressWarnings("unchecked")
        final Lookup<Lookup<T>> result = (Lookup<Lookup<T>>) lookup;
        return result;
    }

    /**
     * Create multilevel lookup for objects in the given collection indexed by given properties.
     * <p>
     * The class where the index properties are searched for is determined by the first non-null element in the
     * collection. Other than that, it is equivalent to {@link #create(Collection, Class, String...)}.
     * 
     * @param values
     *            a collection of objects that can be looked up.
     * @param properties
     *            the index properties
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of multilevel {@link Lookup} indexed by the specified properties
     */
    public static <T> Lookup<?> create(final Collection<? extends T> values, final String... properties) {
        checkValues(values);
        return create(values, getElementClass(values), properties);
    }

    /**
     * Create a {@link Lookup} for objects in the given collection indexed by given property defined on given class.
     * 
     * @param values
     *            a collection of objects that can be looked up
     * @param clazz
     *            the class where the index property is searched for
     * @param property
     *            the index property
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} indexed by the specified property
     */
    public static <T> Lookup<T> create(final Collection<? extends T> values, final Class<T> clazz, final String property) {
        checkValues(values);
        if (clazz == null) throw new IllegalArgumentException(Utils.notNull("clazz"));
        if (property == null) throw new IllegalArgumentException(Utils.notNull("property"));
        return create(values, null, new PropertyConverter<T>(clazz, property));
    }

    /**
     * Create a two level lookup for objects in the given collection indexed by given properties defined on given class.
     * 
     * @param values
     *            a collection of objects that can be looked up.
     * @param clazz
     *            the class where the index property is searched for
     * @param property1
     *            the first index property
     * @param property2
     *            the second index property
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} of {@code Lookup} indexed by the specified properties in order
     */
    public static <T> Lookup<Lookup<T>> create(final Collection<? extends T> values, final Class<T> clazz,
            final String property1, final String property2) {
        final Lookup<?> lookup = create(values, clazz, new String[] { property1, property2 });
        @SuppressWarnings("unchecked")
        final Lookup<Lookup<T>> result = (Lookup<Lookup<T>>) lookup;
        return result;
    }

    /**
     * Create multilevel lookup for objects in the given collection indexed by given properties defined on given class.
     * 
     * @param values
     *            a collection of objects that can be looked up.
     * @param clazz
     *            the class where the index property is searched for
     * @param properties
     *            the index properties
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of multilevel {@link Lookup} indexed by the specified properties
     */
    public static <T> Lookup<?> create(final Collection<? extends T> values, final Class<T> clazz,
            final String... properties) {
        checkValues(values);
        if (clazz == null) throw new IllegalArgumentException(Utils.notNull("clazz"));
        if (properties == null) throw new IllegalArgumentException(Utils.notNull("properties"));
        if (properties.length == 0) throw new IllegalArgumentException("At least one property must be supplied");
        @SuppressWarnings("unchecked")
        final Converter<T, Object>[] converters = new Converter[properties.length];
        for (int i = 0; i < properties.length; i++) {
            if (properties[i] == null) throw new IllegalArgumentException(Utils.notNull("property" + (i + 1)));
            converters[i] = new PropertyConverter<T>(clazz, properties[i]);
        }
        return createMultiLookup(values, null, 0, converters);
    }

    static <T> Lookup<T> create(final Collection<? extends T> values, T defaultValue,
            final Converter<T, Object> converter) {
        checkValues(values);
        if (converter == null) throw new IllegalArgumentException(Utils.notNull("converter"));
        return createPrivate(values, defaultValue, converter);
    }

    static <T> Lookup<Lookup<T>> create(final Collection<? extends T> values, T defaultValue,
            final Converter<T, Object> converter1, final Converter<T, Object> converter2) {
        @SuppressWarnings("unchecked")
        Lookup<Lookup<T>> result = (Lookup<Lookup<T>>) create(values, new Converter[] { converter1, converter2 });
        return result;
    }

    static <T> Lookup<?> create(final Collection<? extends T> values, T defaultValue,
            final Converter<T, Object>... converters) {
        checkValues(values);
        checkConverters(converters);
        return createMultiLookup(values, defaultValue, 0, converters);
    }

    private static <T> Class<T> getElementClass(final Collection<? extends T> values) {
        for (T value : values) {
            if (value != null) {
                @SuppressWarnings("unchecked")
                final Class<T> clazz = (Class<T>) value.getClass();
                return clazz;
            }
        }
        throw new IllegalArgumentException("Argument values collection must contain non-null element");
    }

    private static <T> Lookup<T> createPrivate(final Collection<? extends T> values, T defaultValue,
            final Converter<T, Object> converter) {
        return new MapBasedLookup<T>(values, defaultValue, converter);
    }

    private static <T> Lookup<?> createMultiLookup(final Collection<? extends T> values, T defaultValue, int index,
            final Converter<T, Object>[] converters) {

        Converter<T, Object> converter = converters[index];
        if (++index == converters.length) return createPrivate(values, defaultValue, converter); // last one

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
            lookupMap.put(entry.getKey(), createMultiLookup(entry.getValue(), defaultValue, index, converters));
        }

        return new MultiLevelLookup<Lookup<?>>(lookupMap, converters.length - index - 1);
    }

    private static void checkValues(final Collection<?> values) {
        if (values == null) throw new IllegalArgumentException(Utils.notNull("values"));
        if (values.size() == 0) throw new IllegalArgumentException("Argument values collection must not be empty");
    }

    private static void checkConverters(final Converter<?, Object>[] converters) {
        if (converters == null) throw new IllegalArgumentException(Utils.notNull("converters"));
        if (converters.length == 0) throw new IllegalArgumentException("At least one converter must be supplied");
        for (int i = 0; i < converters.length; i++) {
            if (converters[i] == null) throw new IllegalArgumentException(Utils.notNull("converter" + (i + 1)));
        }
    }
}
