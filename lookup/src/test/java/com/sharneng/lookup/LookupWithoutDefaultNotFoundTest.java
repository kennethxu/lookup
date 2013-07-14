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

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class LookupWithoutDefaultNotFoundTest<T> {

    private final T argumentDefault;
    private final Object key;

    protected LookupWithoutDefaultNotFoundTest(Object key, T argumentDefault) {
        this.key = key;
        this.argumentDefault = argumentDefault;
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

        assertThat(lookup.find(key), nullValue());
    }

    @Test
    public void find_returnsConstructorDefault_onNullKey() {
        Lookup<T> lookup = newLookup();

        assertThat(lookup.find(null), nullValue());
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
    public void get_Chokes_onNullKeyWithoutDefault() {
        Lookup<T> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.get(null);
    }

    @Test
    public void get_Chokes_whenNotFoundWithoutDefault() {
        Lookup<T> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.get(key);
    }

    @Test
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
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
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void hunt_Chokes_onNullKeyWithoutDefault() {
        Lookup<T> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.hunt(null);
    }

    @Test
    public void hunt_Chokes_whenNotFoundWithoutDefault() {
        Lookup<T> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.hunt(key);
    }

}
