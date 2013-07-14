package com.sharneng.lookup;

import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class LookupBuilderMultiKeyTest {

    private static class LookupMatcher extends BaseMatcher<Lookup<CountyCode>> {
        private final Object key;
        private final CountyCode value;
        private final String description;

        public LookupMatcher(Object key, CountyCode value, String description) {
            this.key = key;
            this.value = value;
            this.description = description;
        }

        @Override
        public boolean matches(Object item) {
            if (!(item instanceof Lookup<?>)) return false;
            @SuppressWarnings("unchecked")
            Lookup<CountyCode> lookup = (Lookup<CountyCode>) item;
            return (lookup.get(key).equals(value));
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(this.description);
        }

    }

    private static final Lookup<CountyCode> argumentDefault = new EmptyLookup<CountyCode>(CountyCode.DEFAULT);
    private static final CountyCode found = new CountyCode(1081, "Alabama", "Lee");
    private static final Matcher<Lookup<CountyCode>> foundMatcher = new LookupMatcher(found.getCounty(), found,
            "a lookup for state Alabama that can find county Lee");
    private static final Matcher<Lookup<CountyCode>> defaultMatcher = new LookupMatcher("Greene", CountyCode.DEFAULT,
            "an empty lookup");

    private static Lookup<Lookup<CountyCode>> newDoubleKeyLookup(CountyCode instanceDefault) {
        return Utils.toLookup2(new LookupBuilder<CountyCode>(instanceDefault, CountyCode.class, "state", "county")
                .build(CountyCode.codes));
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
