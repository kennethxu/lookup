package com.sharneng.lookup;

import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;

public class LookupBuilderSingleKeyTest {
    private static final CountyCode found = new CountyCode(1081, "Alabama", "Lee");

    @SuppressWarnings("unchecked")
    private static Lookup<CountyCode> createLookup(CountyCode instanceDefault) {
        LookupBuilder<CountyCode, CountyCode> builder = new LookupBuilder<CountyCode, CountyCode>(CountyCode.codes);
        builder.defaultTo(instanceDefault);
        builder.by("code");
        return (Lookup<CountyCode>) builder.index();
    }

    public static class WithDefaultFound extends LookupWithDefaultFoundTest<CountyCode> {
        public WithDefaultFound() {
            super(found.getCode(), CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT), equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return createLookup(CountyCode.DEFAULT);
        }
    }

    public static class WithDefaultNotFound extends LookupWithDefaultNotFoundTest<CountyCode> {
        public WithDefaultNotFound() {
            super(-1, CountyCode.DEFAULT, equalTo(CountyCode.DEFAULT));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return createLookup(CountyCode.DEFAULT);
        }
    }

    public static class WithoutDefaultFound extends LookupWithoutDefaultFoundTest<CountyCode> {
        public WithoutDefaultFound() {
            super(found.getCode(), CountyCode.DEFAULT, equalTo(found));
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return createLookup(null);
        }
    }

    public static class WithoutDefaultNotFound extends LookupWithoutDefaultNotFoundTest<CountyCode> {
        public WithoutDefaultNotFound() {
            super(-1, CountyCode.DEFAULT);
        }

        @Override
        protected Lookup<CountyCode> newLookup() {
            return createLookup(null);
        }
    }
}
