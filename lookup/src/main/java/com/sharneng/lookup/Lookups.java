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

import com.sharneng.lookup.fluent.Sourced;

import java.util.Collection;
import java.util.Map;

import javax.annotation.CheckForNull;

/**
 * Class to hold factory methods to return the implementation of {@link Lookup}.
 * 
 * @author Kenneth Xu
 * 
 */
public final class Lookups {

    private Lookups() {
    }

    /**
     * Specify the source data for the lookup to be built.
     * 
     * @param source
     *            a collection of source data
     * @param <T>
     *            they type of the source data
     * @return a fluent API interface to be continue building the lookup
     */
    public static <T> Sourced<T, T> from(@CheckForNull Collection<? extends T> source) {
        return new LookupBuilder<T, T>(source);
    }

    /**
     * Creates a lookup based on given map. This is equivalent to {@link #create(Map, Object) create(map, null)}.
     * <p>
     * Please note that the created lookup uses a copy of the map passed to ensure the thread safety and performance. So
     * subsequent change to the map doesn't affect the create lookup object.
     * 
     * @param map
     *            the map to create the lookup
     * 
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} that is backed by given map
     * 
     */
    public static <T> Lookup<T> create(final Map<? extends Object, ? extends T> map) {
        return create(map, null);
    }

    /**
     * Creates a lookup based on given map.
     * <p>
     * Please note that the created lookup uses a copy of the map passed to ensure the thread safety and performance. So
     * subsequent change to the map doesn't affect the create lookup object.
     * 
     * @param map
     *            the map to create the lookup
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
     * 
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} that is backed by given map
     * 
     */
    public static <T> Lookup<T> create(final Map<? extends Object, ? extends T> map, @CheckForNull T defaultValue) {
        if (map == null) throw new IllegalArgumentException(Utils.notNull("map"));
        return new MapBasedLookup<T>(map, defaultValue);
    }

    /* values, string */

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
        return create(null, values, property);
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
        return create(null, values, property1, property2);
        // return Utils.toLookup2(create(values, new String[] { property1, property2 }));
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
        return create(null, values, properties);
        // checkValues(values);
        // return new LookupBuilder<T>(null, getElementClass(values), properties).build(values);
    }

    /* default, values, string */

    /**
     * Create a lookup for objects in the given collection indexed by given property. The created lookup has default
     * value returned when {@link Lookup#get(Object)} cannot find a reference object.
     * <p>
     * The class where index properties are searched for is determined by the actual type of {@code defaultValue} if it
     * is not null. Otherwise, the actual type of first non {@code null} element in values collection is used.
     * <p>
     * When {@code defaultValue} is not null, this is equivalent to {@link #create(Object, Collection, Class, String)
     * create(defaultValue, values, defaultValue.getClass(), property)}. Otherwise this is equivalent to
     * {@link #create(Collection, String) create(values, property)}.
     * 
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
     * @param values
     *            a collection of objects that can be looked up.
     * @param property
     *            the index property
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} indexed by the specified property
     */
    public static <T> Lookup<T> create(@CheckForNull T defaultValue, final Collection<? extends T> values,
            final String property) {
        return from(values).defaultTo(defaultValue).by(property).index();
        // checkValues(values);
        // if (property == null) throw new IllegalArgumentException(Utils.notNull("property"));
        // return new MapBasedLookup<T>(defaultValue, values, new OgnlConverter<T, Object>(Object.class, property));
    }

    /**
     * Create a two level lookup for objects in the given collection indexed by given properties.The created lookup has
     * default value returned when {@link Lookup#get(Object)} cannot find a reference object.
     * <p>
     * The class where index properties are searched for is determined by the actual type of {@code defaultValue} if it
     * is not null. Otherwise, the actual type of first non {@code null} element in values collection is used.
     * <p>
     * When {@code defaultValue} is not null, this is equivalent to
     * {@link #create(Object, Collection, Class, String, String) create(defaultValue, values, defaultValue.getClass(),
     * property1, property2)}. Otherwise this is equivalent to {@link #create(Collection, String, String) create(values,
     * property1, property2)}.
     * 
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
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
    public static <T> Lookup<Lookup<T>> create(@CheckForNull T defaultValue, final Collection<? extends T> values,
            final String property1, final String property2) {
        return from(values).defaultTo(defaultValue).by(property1).by(property2).index();
        // return Utils.toLookup2(create(defaultValue, values, new String[] { property1, property2 }));
    }

    /**
     * Create multilevel lookup for objects in the given collection indexed by given properties. The created lookup has
     * default value returned when {@link Lookup#get(Object)} cannot find a reference object.
     * <p>
     * The class where index properties are searched for is determined by the actual type of {@code defaultValue} if it
     * is not null. Otherwise, the actual type of first non {@code null} element in values collection is used.
     * <p>
     * When {@code defaultValue} is not null, this is equivalent to
     * {@link #create(Object, Collection, Class, String...) create(defaultValue, values, defaultValue.getClass(),
     * properties)}. Otherwise this is equivalent to {@link #create(Collection, String...) create(values, properties)}.
     * 
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
     * @param values
     *            a collection of objects that can be looked up.
     * @param properties
     *            the index properties
     * 
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of multilevel {@link Lookup} indexed by the specified properties
     */
    public static <T> Lookup<?> create(@CheckForNull T defaultValue, final Collection<? extends T> values,
            final String... properties) {
        return from(values).defaultTo(defaultValue).by(properties).index();
    }
}
