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

public abstract class LookupWithoutDefaultNotFoundTest {

    protected static final String DEFAULT_PARAM = "defaultParam";
    protected static final String FOUND = "found";
    protected static final String DEFAULT = "default";
    protected static final String KEY = "key";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected abstract Lookup<String> newLookup();

    @Test
    public void has_returnsFalse_whenNotFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.has(KEY), equalTo(false));
    }

    @Test
    public void has_returnsFalse_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.has(null), equalTo(false));
    }

    @Test
    public void find_returnsConstructorDefault_whenNotFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(KEY), nullValue());
    }

    @Test
    public void find_returnsConstructorDefault_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(null), nullValue());
    }

    @Test
    public void findWithDefault_returnsDefaultParam_whenNotFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(KEY, null), nullValue());
        assertThat(lookup.find(KEY, DEFAULT_PARAM), equalTo(DEFAULT_PARAM));
    }

    @Test
    public void findWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(null, null), nullValue());
        assertThat(lookup.find(null, DEFAULT_PARAM), equalTo(DEFAULT_PARAM));
    }

    @Test
    public void get_Chokes_onNullKeyWithoutDefault() {
        Lookup<String> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.get(null);
    }

    @Test
    public void get_Chokes_whenNotFoundWithoutDefault() {
        Lookup<String> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.get(KEY);
    }

    @Test
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void getWithDefault_Chokes_onNullDefaultParam() {
        Lookup<String> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.get("d", null);
    }

    @Test
    public void getWithDefault_returnsDefaultParam_whenNotFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.get(KEY, DEFAULT_PARAM), equalTo(DEFAULT_PARAM));
    }

    @Test
    public void getWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.get(null, DEFAULT_PARAM), equalTo(DEFAULT_PARAM));
    }

    @Test
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void hunt_Chokes_onNullKeyWithoutDefault() {
        Lookup<String> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.hunt(null);
    }

    @Test
    public void hunt_Chokes_whenNotFoundWithoutDefault() {
        Lookup<String> lookup = newLookup();
        exception.expect(LookupException.class);

        lookup.hunt(KEY);
    }

}
