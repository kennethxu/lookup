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

public abstract class LookupWithDefaultFoundTest<T> {

    private final T argumentDefault;
    private final Matcher<T> foundMatcher;
    private final Matcher<T> defaultMatcher;
    private final Object key;

    protected LookupWithDefaultFoundTest(Object key, T argumentDefault, Matcher<T> defaultMatcher, Matcher<T> foundMatcher) {
        this.key = key;
        this.argumentDefault = argumentDefault;
        this.defaultMatcher = defaultMatcher;
        this.foundMatcher = foundMatcher;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected abstract Lookup<T> newLookup();

    @Test
    public void has_returnsTrue_whenFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.has(key), equalTo(true));
    }

    @Test
    public void has_returnsFalse_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.has(null), equalTo(false));
    }

    @Test
    public void find_returnsValue_whenFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(key), foundMatcher);
    }

    @Test
    public void find_returnsConstructorDefault_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(null), defaultMatcher);
    }

    @Test
    public void findWithDefault_returnsValue_whenFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(key, null), foundMatcher);
        assertThat(lookup.find(key, argumentDefault), foundMatcher);
    }

    @Test
    public void findWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(null, null), nullValue());
        assertThat(lookup.find(null, argumentDefault), equalTo(argumentDefault));
    }

    @Test
    public void get_returnsValue_whenFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.get(key), foundMatcher);
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
    public void getWithDefault_returnsValue_whenFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.get(key, argumentDefault), foundMatcher);
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
    public void hunt_returnsValue_whenFound() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.hunt(key), foundMatcher);
    }

}
