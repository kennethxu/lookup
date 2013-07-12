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

public abstract class LookupWithDefaultFoundTest {

    protected static final String DEFAULT_PARAM = "defaultParam";
    protected static final String FOUND = "found";
    protected static final String DEFAULT = "default";
    protected static final String KEY = "key";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    protected abstract Lookup<String> newLookup();

    @Test
    public void has_returnsTrue_whenFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.has(KEY), equalTo(true));
    }

    @Test
    public void has_returnsFalse_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.has(null), equalTo(false));
    }

    @Test
    public void find_returnsValue_whenFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(KEY), equalTo(FOUND));
    }

    @Test
    public void find_returnsConstructorDefault_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(null), equalTo(DEFAULT));
    }

    @Test
    public void findWithDefault_returnsValue_whenFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(KEY, null), equalTo(FOUND));
        assertThat(lookup.find(KEY, "d"), equalTo(FOUND));
    }

    @Test
    public void findWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.find(null, null), nullValue());
        assertThat(lookup.find(null, DEFAULT_PARAM), equalTo(DEFAULT_PARAM));
    }

    @Test
    public void get_returnsValue_whenFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.get(KEY), equalTo(FOUND));
    }

    @Test
    public void get_returnsConstructorDefault_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.get(null), equalTo(DEFAULT));
    }

    @Test
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void getWithDefault_Chokes_onNullDefaultParam() {
        Lookup<String> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.get("d", null);
    }

    @Test
    public void getWithDefault_returnsValue_whenFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.get(KEY, "d"), equalTo(FOUND));
    }

    @Test
    public void getWithDefault_returnsDefaultParam_onNullKey() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.get(null, DEFAULT_PARAM), equalTo(DEFAULT_PARAM));
    }

    @Test
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void hunt_Chokes_onNullKeyWithDefault() {
        Lookup<String> lookup = newLookup();
        exception.expect(IllegalArgumentException.class);

        lookup.hunt(null);
    }

    @Test
    public void hunt_returnsValue_whenFound() {
        Lookup<String> lookup = newLookup();

        assertThat(lookup.hunt(KEY), equalTo(FOUND));
    }

}