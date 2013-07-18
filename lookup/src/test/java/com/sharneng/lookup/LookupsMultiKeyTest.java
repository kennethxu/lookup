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

import static org.hamcrest.Matchers.equalTo;

import com.sharneng.lookup.testdata.CountyCode;
import com.sharneng.lookup.testdata.LookupMatcher;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressFBWarnings("NP_NONNULL_PARAM_VIOLATION")
public class LookupsMultiKeyTest {
    private static final Lookup<CountyCode> argumentDefault = new EmptyLookup<CountyCode>(CountyCode.DEFAULT);
    private static final CountyCode found = new CountyCode(1081, "Alabama", "Lee");
    private static final Matcher<Lookup<CountyCode>> foundMatcher = new LookupMatcher(found.getCounty(), found,
            "a lookup for state Alabama that can find county Lee");
    private static final Matcher<Lookup<CountyCode>> defaultMatcher = new LookupMatcher("Greene", CountyCode.DEFAULT,
            "an empty lookup");
    private static final Converter<CountyCode, Object> toState = new Converter<CountyCode, Object>() {
        @Override
        public Object convert(CountyCode source) {
            return source.getState();
        }
    };
    private static final Converter<CountyCode, Object> toCounty = new Converter<CountyCode, Object>() {
        @Override
        public Object convert(CountyCode source) {
            return source.getCounty();
        }
    };

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private static final Map<Integer, String> map = new HashMap<Integer, String>();
    static {
        map.put(1, "A");
        map.put(2, "B");
    }

    @Test
    public void create_chokes_onNullExpression() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("expression");
        exception.expectMessage("2nd");

        Lookups.create(CountyCode.codes, "state", (String) null);
    }

    @Test
    public void createWithDefault_chokes_onNullExpression() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("expression");
        exception.expectMessage("2nd");

        Lookups.create(CountyCode.DEFAULT, CountyCode.codes, "state", (String) null);
    }

    @Test
    public void create_chokes_onNullSource() {
        exception.expect(LookupBuildException.class);
        exception.expectMessage("source");

        Lookups.create((Collection<CountyCode>) null, "state", "county");
    }

    @Test
    public void create_chokes_onEmptySource() {
        exception.expect(LookupBuildException.class);
        exception.expectMessage("source");

        Lookups.create(new ArrayList<CountyCode>(), "state", "county");
    }

    @Test
    public void create_chokes_onNullConverter() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("converter");
        exception.expectMessage("2nd");

        Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT).by(toState)
                .by((Converter<CountyCode, Object>) null);
    }

    @Test
    @SuppressFBWarnings("NP_NULL_PARAM_DEREF_ALL_TARGETS_DANGEROUS")
    public void create_chokes_onNullConverters() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("converters");

        Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT).by((Converter<CountyCode, Object>[]) null);
    }

    @Test
    public void create_chokes_onEmptyConverters() {
        @SuppressWarnings("unchecked")
        final Converter<CountyCode, Object>[] converters = (Converter<CountyCode, Object>[]) (new Converter<?, ?>[0]);
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("converter");

        Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT).by(converters);
    }

    @Test
    public void create_chokes_onDuplicateSource() {
        exception.expect(DuplicateKeyException.class);

        Lookups.create(CountyCode.DEFAULT, CountyCode.dupCodes, "state", "county");
    }

    public static class FirstLevelFound extends LookupWithDefaultFoundTest<Lookup<CountyCode>> {
        public FirstLevelFound() {
            super(found.getState(), argumentDefault, defaultMatcher, foundMatcher);
        }

        @Override
        protected Lookup<Lookup<CountyCode>> newLookup() {
            return Lookups.create(CountyCode.DEFAULT, CountyCode.codes, "state", "county");
        }
    }

    public static class FirstLevelNotFound extends LookupWithDefaultNotFoundTest<Lookup<CountyCode>> {
        public FirstLevelNotFound() {
            super("NoState", argumentDefault, defaultMatcher);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Lookup<Lookup<CountyCode>> newLookup() {
            return (Lookup<Lookup<CountyCode>>) Lookups.create(CountyCode.DEFAULT, CountyCode.codes, new String[] {
                    "state", "county" });
        }
    }

    public static class LastLevelWithDefaultFound extends LookupWithDefaultFoundTest<CountyCode> {
        public LastLevelWithDefaultFound() {
            super(found.getCounty(), CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT), equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT).by(toState).by(toCounty).index()
                    .get(found.getState());
        }
    }

    public static class LastLevelWithDefaultNotFound extends LookupWithDefaultNotFoundTest<CountyCode> {
        public LastLevelWithDefaultNotFound() {
            super("NoState", CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return Lookups.create(CountyCode.DEFAULT, CountyCode.codes, "state", "county").get(found.getState());
        }
    }

    public static class LastLevelWithoutDefaultFound extends LookupWithoutDefaultFoundTest<CountyCode> {
        public LastLevelWithoutDefaultFound() {
            super(found.getCounty(), CountyCode.DEFAULT, equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return Lookups.create(CountyCode.codes, "state", "county").get(found.getState());
        }
    }

    public static class LastLevelWithoutDefaultNotFound extends LookupWithoutDefaultNotFoundTest<CountyCode> {
        public LastLevelWithoutDefaultNotFound() {
            super("NoState", CountyCode.DEFAULT);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Lookup<CountyCode> newLookup() {
            return ((Lookup<Lookup<CountyCode>>) Lookups.create(CountyCode.codes, new String[] { "state", "county" }))
                    .get(found.getState());
        }
    }

    public static class FromWithDefaultFound extends LookupWithDefaultFoundTest<CountyCode> {
        public FromWithDefaultFound() {
            super(found.getCounty(), CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT), equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT).by(toState).by(toCounty).index()
                    .get(found.getState());
        }
    }

    public static class FromWithDefaultNotFound extends LookupWithDefaultNotFoundTest<CountyCode> {
        public FromWithDefaultNotFound() {
            super("NoState", CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT));
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Lookup<CountyCode> newLookup() {
            return ((Lookup<Lookup<CountyCode>>) Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT)
                    .by("state", "county").index()).get(found.getState());
        }
    }

    public static class FromWithoutDefaultFound extends LookupWithoutDefaultFoundTest<CountyCode> {
        public FromWithoutDefaultFound() {
            super(found.getCounty(), CountyCode.DEFAULT, equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return Lookups.from(CountyCode.codes).by("state").by("county").index().get(found.getState());
        }
    }

    public static class FromWithoutDefaultNotFound extends LookupWithoutDefaultNotFoundTest<CountyCode> {
        public FromWithoutDefaultNotFound() {
            super("NoState", CountyCode.DEFAULT);
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Lookup<CountyCode> newLookup() {
            return ((Lookup<Lookup<CountyCode>>) Lookups.from(CountyCode.codes).by(toState, toCounty).index())
                    .get(found.getState());
        }
    }

}
