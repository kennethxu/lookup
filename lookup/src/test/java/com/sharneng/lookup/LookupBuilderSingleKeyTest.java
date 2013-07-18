package com.sharneng.lookup;

import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;

public class LookupBuilderSingleKeyTest {
    private static final String INSTANCE_DEFAULT = "instanceDefault";
    private static final CountyCode found = new CountyCode(1081, "Alabama", "Lee");

    private static Lookup<CountyCode> createLookup(CountyCode instanceDefault) {
        return newBuilder().defaultTo(instanceDefault).by("code").index();
    }

    private static LookupBuilder<CountyCode, CountyCode> newBuilder() {
        return new LookupBuilder<CountyCode, CountyCode>(CountyCode.codes);
    }

    private static Lookup<String> createSelectLookup(String instanceDefault) {
        return newBuilder().select(String.class, "county").defaultTo(instanceDefault).by("code").index();
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

    public static class SelectWithDefaultFound extends LookupWithDefaultFoundTest<String> {
        public SelectWithDefaultFound() {
            super(found.getCode(), INSTANCE_DEFAULT, equalTo(INSTANCE_DEFAULT), equalTo(found.getCounty()));
        }

        @Override
        protected Lookup<String> newLookup() {
            return createSelectLookup(INSTANCE_DEFAULT);
        }
    }

    public static class SelectWithDefaultNotFound extends LookupWithDefaultNotFoundTest<String> {
        public SelectWithDefaultNotFound() {
            super(-1, INSTANCE_DEFAULT, equalTo(INSTANCE_DEFAULT));
        }

        @Override
        protected Lookup<String> newLookup() {
            return createSelectLookup(INSTANCE_DEFAULT);
        }
    }

    public static class SelectWithoutDefaultFound extends LookupWithoutDefaultFoundTest<String> {
        public SelectWithoutDefaultFound() {
            super(found.getCode(), INSTANCE_DEFAULT, equalTo(found.getCounty()));
        }

        @Override
        protected Lookup<String> newLookup() {
            return createSelectLookup(null);
        }
    }

    public static class SelectWithoutDefaultNotFound extends LookupWithoutDefaultNotFoundTest<String> {
        public SelectWithoutDefaultNotFound() {
            super(-1, INSTANCE_DEFAULT);
        }

        @Override
        protected Lookup<String> newLookup() {
            return createSelectLookup(null);
        }
    }

}
