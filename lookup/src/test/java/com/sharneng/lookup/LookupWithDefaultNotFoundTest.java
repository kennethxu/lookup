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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class LookupWithDefaultNotFoundTest<T> {

    private final T argumentDefault;
    private final Matcher<T> defaultMatcher;
    private final Object key;

    protected LookupWithDefaultNotFoundTest(Object key, T argumentDefault, Matcher<T> defaultMatcher) {
        this.key = key;
        this.argumentDefault = argumentDefault;
        this.defaultMatcher = defaultMatcher;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected abstract Lookup<T> newLookup();

    @Test
    public void has_returnsFalse_whenNotFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.has(key), equalTo(false));
    }

    @Test
    public void has_returnsFalse_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.has(null), equalTo(false));
    }

    @Test
    public void find_returnsConstructorDefault_whenNotFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(key), defaultMatcher);
    }

    @Test
    public void find_returnsConstructorDefault_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(null), defaultMatcher);
    }

    @Test
    public void findWithDefault_returnsDefaultParam_whenNotFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(key, null), nullValue());
        assertThat(lookup.find(key, argumentDefault), equalTo(argumentDefault));
    }

    @Test
    public void findWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(null, null), nullValue());
        assertThat(lookup.find(null, argumentDefault), equalTo(argumentDefault));
    }

    @Test
    public void get_returnsConstructorDefault_whenNotFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.get(key), defaultMatcher);
    }

    @Test
    public void get_returnsConstructorDefault_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.get(null), defaultMatcher);
    }

    @Test
    @SuppressFBWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void getWithDefault_Chokes_onNullDefaultParam() {
        Lookup<T> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.get("d", null);
    }

    @Test
    public void getWithDefault_returnsDefaultParam_whenNotFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.get(key, argumentDefault), equalTo(argumentDefault));
    }

    @Test
    public void getWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.get(null, argumentDefault), equalTo(argumentDefault));
    }

    @Test
    @SuppressFBWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void hunt_Chokes_onNullKeyWithDefault() {
        Lookup<T> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.hunt(null);
    }

    @Test
    public void hunt_Chokes_whenNotFoundWithDefault() {
        Lookup<T> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.hunt(key);
    }

}
