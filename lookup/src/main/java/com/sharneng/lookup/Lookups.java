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

    /* source, string */

    /**
     * Create a lookup for objects in the given collection indexed by the value computed from given expression.
     * <p>
     * This is equivalent to {@code from(source).notEmpty().by(expression).index()}.
     * 
     * @param source
     *            a collection of source data that can be looked up.
     * @param expression
     *            the index expression that can compute the index value from the source data
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} indexed by the specified expression
     */
    public static <T> Lookup<T> create(final Collection<? extends T> source, final String expression) {
        return create(null, source, expression);
    }

    /**
     * Create a two level lookup for objects in the given collection indexed by the values computed from the given
     * expressions.
     * <p>
     * This is equivalent to {@code from(source).notEmpty().by(expression1).by(expression2).index()}.
     * 
     * @param source
     *            a collection of objects that can be looked up.
     * @param expression1
     *            the first index expression
     * @param expression2
     *            the second index expression
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} of {@code Lookup} indexed by the specified expressions in order
     */
    public static <T> Lookup<Lookup<T>> create(final Collection<? extends T> source, final String expression1,
            final String expression2) {
        return create(null, source, expression1, expression2);
    }

    /**
     * Create multilevel lookup for objects in the given collection indexed by the values computed from the given
     * expressions.
     * <p>
     * This is equivalent to {@code from(source).notEmpty().by(expressions).index()}.
     * 
     * @param source
     *            a collection of objects that can be looked up.
     * @param expressions
     *            the index expressions
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of multilevel {@link Lookup} indexed by the specified expressions
     */
    public static <T> Lookup<?> create(final Collection<? extends T> source, final String... expressions) {
        return create(null, source, expressions);
    }

    /* default, source, string */

    /**
     * Create a lookup for objects in the given collection indexed by the value computed from given expression. The
     * created lookup has default value returned when {@link Lookup#get(Object)} cannot find a reference object.
     * <p>
     * This is equivalent to {@code from(source).notEmpty().defaultTo(defaultValue).by(expression).index()}.
     * 
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
     * @param source
     *            a collection of objects that can be looked up.
     * @param expression
     *            the index expression
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} indexed by the specified expression
     */
    public static <T> Lookup<T> create(@CheckForNull T defaultValue, final Collection<? extends T> source,
            final String expression) {
        return from(source).notEmpty().defaultTo(defaultValue).by(expression).index();
    }

    /**
     * Create a two level lookup for objects in the given collection indexed by the values computed from the given
     * expressions. The created lookup has default value returned when {@link Lookup#get(Object)} cannot find a
     * reference object.
     * <p>
     * This is equivalent to
     * {@code from(source).notEmpty().defaultTo(defaultValue).by(expression1).by(expression2).index()}.
     * 
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
     * @param source
     *            a collection of objects that can be looked up.
     * @param expression1
     *            the first index expression
     * @param expression2
     *            the second index expression
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of {@link Lookup} of {@code Lookup} indexed by the specified expressions in order
     */
    public static <T> Lookup<Lookup<T>> create(@CheckForNull T defaultValue, final Collection<? extends T> source,
            final String expression1, final String expression2) {
        return from(source).notEmpty().defaultTo(defaultValue).by(expression1).by(expression2).index();
    }

    /**
     * Create multilevel lookup for objects in the given collection indexed by the values computed from the given
     * expressions. The created lookup has default value returned when {@link Lookup#get(Object)} cannot find a
     * reference object.
     * <p>
     * This is equivalent to {@code from(source).notEmpty().defaultTo(defaultValue).by(expressions).index()}.
     * 
     * @param defaultValue
     *            the default value to be used if the lookup object is not found
     * @param source
     *            a collection of objects that can be looked up.
     * @param expressions
     *            the index expressions
     * 
     * @param <T>
     *            type of the reference object to be looked up
     * @return an implementation of multilevel {@link Lookup} indexed by the specified expressions
     */
    public static <T> Lookup<?> create(@CheckForNull T defaultValue, final Collection<? extends T> source,
            final String... expressions) {
        return from(source).notEmpty().defaultTo(defaultValue).by(expressions).index();
    }
}
