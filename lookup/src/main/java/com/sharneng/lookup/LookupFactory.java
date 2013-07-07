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
public final class LookupFactory {

    private LookupFactory() {
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
    public static <T> Lookup<T> create(Map<? extends Object, ? extends T> map) {
        if (map == null) throw new IllegalArgumentException(notNull("map"));
        return new MapBasedLookup<T>(map);
    }

    /**
     * Create a lookup for objects in the given collection indexed by given property.
     * <p>
     * The class where the index property is searched for is determined by the first element in the collection.
     * 
     * @param values
     *            a collection of objects that can be looked up.
     * @param property
     *            the index property
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} indexed by the specified property
     */
    public static <T> Lookup<T> create(Collection<? extends T> values, String property) {
        return create(values, getElementClass(values), property);
    }

    public static <T> Lookup<Lookup<T>> create(Collection<? extends T> values, String property1, String property2) {
        return create(values, getElementClass(values), property1, property2);
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
    public static <T> Lookup<T> create(Collection<? extends T> values, Class<T> clazz, String property) {
        return create(values, new PropertyConverter<T>(clazz, property));
    }

    public static <T> Lookup<Lookup<T>> create(Collection<? extends T> values, Class<T> clazz, String property1,
            String property2) {
        return create(values, new PropertyConverter<T>(clazz, property2), new PropertyConverter<T>(clazz, property1));
    }

    public static <T> Lookup<?> create(Collection<? extends T> values, Class<T> clazz, String... properties) {
        @SuppressWarnings("unchecked")
        Converter<T, Object>[] converters = new Converter[properties.length];
        int i = 0;
        for (String property : properties) {
            converters[i++] = new PropertyConverter<T>(clazz, property);
        }
        return create(values, converters);
    }

    public static <T> Lookup<T> create(Collection<? extends T> values, Converter<T, Object> converter) {
        if (values == null) throw new IllegalArgumentException("values must not be null");
        if (converter == null) throw new IllegalArgumentException("converter must not be null");
        if (values.size() < 10) return new CollectionBasedLookup<T>(values, converter);
        else return new MapBasedLookup<T>(values, converter);
    }

    public static <T> Lookup<Lookup<T>> create(Collection<? extends T> values, Converter<T, Object> converter1,
            Converter<T, Object> converter2) {
        if (values == null) throw new IllegalArgumentException("values must not be null");
        if (converter1 == null) throw new IllegalArgumentException("converter must not be null");
        @SuppressWarnings("unchecked")
        Lookup<Lookup<T>> result = (Lookup<Lookup<T>>) create(values, new Converter[] { converter1, converter2 });
        return result;
    }

    public static <T> Lookup<?> create(Collection<? extends T> values, Converter<T, Object>... converters) {
        return createMultiLookup(values, 0, converters);
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> getElementClass(Collection<? extends T> values) {
        return (Class<T>) values.iterator().next().getClass();
    }

    private static <T> Lookup<?> createMultiLookup(Collection<? extends T> values, int index,
            Converter<T, Object>[] converters) {
        Converter<T, Object> converter = converters[index];
        if (++index == converters.length) return create(values, converter); // last one

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
            lookupMap.put(entry.getKey(), createMultiLookup(entry.getValue(), index, converters));
        }
        return new MapBasedLookup<Lookup<?>>(lookupMap);
    }

    private static String notNull(String argumentName) {
        return "Argument " + argumentName + " must not be null.";
    }
}
