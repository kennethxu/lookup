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

import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;
import com.sharneng.lookup.testdata.LookupMatcher;

import org.hamcrest.Matcher;

public class LookupBuilderMultiKeyTest {

    private static final Lookup<CountyCode> argumentDefault = new EmptyLookup<CountyCode>(CountyCode.DEFAULT);
    private static final CountyCode found = new CountyCode(1081, "Alabama", "Lee");
    private static final Matcher<Lookup<CountyCode>> foundMatcher = new LookupMatcher(found.getCounty(), found,
            "a lookup for state Alabama that can find county Lee");
    private static final Matcher<Lookup<CountyCode>> defaultMatcher = new LookupMatcher("Greene", CountyCode.DEFAULT,
            "an empty lookup");

    private static Lookup<Lookup<CountyCode>> newDoubleKeyLookup(CountyCode instanceDefault) {
        LookupBuilder<CountyCode, CountyCode> builder = new LookupBuilder<CountyCode, CountyCode>(CountyCode.codes);
        return builder.defaultTo(instanceDefault).by("state").by("county").index();
    }

    public static class FirstLevelFound extends LookupWithDefaultFoundTest<Lookup<CountyCode>> {
        public FirstLevelFound() {
            super(found.getState(), argumentDefault, defaultMatcher, foundMatcher);
        }

        @Override
        protected Lookup<Lookup<CountyCode>> newLookup() {
            return newDoubleKeyLookup(CountyCode.DEFAULT);
        }
    }

    public static class FirstLevelNotFound extends LookupWithDefaultNotFoundTest<Lookup<CountyCode>> {
        public FirstLevelNotFound() {
            super("NoState", argumentDefault, defaultMatcher);
        }

        @Override
        protected Lookup<Lookup<CountyCode>> newLookup() {
            return newDoubleKeyLookup(CountyCode.DEFAULT);
        }
    }

    public static class LastLevelWithDefaultFound extends LookupWithDefaultFoundTest<CountyCode> {
        public LastLevelWithDefaultFound() {
            super(found.getCounty(), CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT), equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return newDoubleKeyLookup(CountyCode.DEFAULT).get(found.getState());
        }
    }

    public static class LastLevelWithDefaultNotFound extends LookupWithDefaultNotFoundTest<CountyCode> {
        public LastLevelWithDefaultNotFound() {
            super("NoState", CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return newDoubleKeyLookup(CountyCode.DEFAULT).get(found.getState());
        }
    }

    public static class LastLevelWithoutDefaultFound extends LookupWithoutDefaultFoundTest<CountyCode> {
        public LastLevelWithoutDefaultFound() {
            super(found.getCounty(), CountyCode.DEFAULT, equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return newDoubleKeyLookup(null).get(found.getState());
        }
    }

    public static class LastLevelWithoutDefaultNotFound extends LookupWithoutDefaultNotFoundTest<CountyCode> {
        public LastLevelWithoutDefaultNotFound() {
            super("NoState", CountyCode.DEFAULT);
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return newDoubleKeyLookup(null).get(found.getState());
        }
    }

}
