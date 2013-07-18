package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;

import org.junit.Test;

import java.util.Collection;

public class LookupsTest {

    @Test
    public void createTwoLevelLookup_canGet_whenFound() {
        Lookup<Lookup<CountyCode>> lookup = Lookups.create(CountyCode.codes, "state", "county");

        CountyCode p = lookup.hunt("Mississippi").hunt("Greene");

        assertThat(p.getCode(), is(28041));
    }

    @Test
    public void createTwoLevelLookup_canSafeGet_whenFound() {
        Lookup<Lookup<CountyCode>> lookup = Lookups.create(CountyCode.codes, "state", "county");

        CountyCode p = lookup.get("Mississippi").get("Greene");

        assertThat(p.getCode(), is(28041));
    }

    @Test
    public void createTwoLevelLookup_canSafeGet_whenNotFoundLastLevel() {
        Lookup<Lookup<CountyCode>> lookup = Lookups.create(CountyCode.codes, "state", "county");

        CountyCode p = lookup.get("Mississippi").find("No County");

        assertThat(p, nullValue());
    }

    @Test
    public void createTwoLevelLookup_canSafeGet_whenNotFoundFirstLevel() {
        Lookup<Lookup<CountyCode>> lookup = Lookups.create(CountyCode.codes, "state", "county");

        CountyCode p = lookup.get("No State").find("Greene");

        assertThat(p, nullValue());
    }

    @Test
    public void fluent_takesFirst_whenUseFirstOnDuplicate() {
        Lookup<Integer> lookup = Lookups.from(CountyCode.dupCodes).select(Integer.class, "code").useFirstOnDuplicate()
                .by("state").index();

        assertThat(lookup.find(CountyCode.code100.getState()), is(CountyCode.code100.getCode()));
    }

    @Test
    public void fluent_takesLast_whenUseLastOnDuplicate() {
        Lookup<Integer> lookup = Lookups.from(CountyCode.dupCodes).select(Integer.class, "code").useLastOnDuplicate()
                .by("state").index();

        assertThat(lookup.find(CountyCode.code200.getState()), is(CountyCode.code200.getCode()));
    }

    @Test
    public void fluent_canBuildFromEmpty() {
        Lookup<CountyCode> lookup = Lookups.from((Collection<CountyCode>) null).defaultTo(CountyCode.DEFAULT)
                .by("code").index();

        assertThat(lookup.get("anything"), is(CountyCode.DEFAULT));
    }

    @SuppressWarnings("unused")
    public void syntaxCheck(boolean select, boolean hasDefault) {
        if (!select && !hasDefault) {

            Lookup<CountyCode> l1 = Lookups.from(CountyCode.codes).notEmpty().useFirstOnDuplicate().by("code").index();
            Lookup<Lookup<CountyCode>> l2 = Lookups.from(CountyCode.codes).useLastOnDuplicate().notEmpty().by("state")
                    .by("county").index();
            Lookup<Lookup<Lookup<CountyCode>>> l3 = Lookups.from(CountyCode.codes).useLastOnDuplicate().notEmpty()
                    .by("state").by("county").by("something").index();
            @SuppressWarnings("unchecked")
            Lookup<Lookup<CountyCode>> l4 = (Lookup<Lookup<CountyCode>>) Lookups.from(CountyCode.codes)
                    .useLastOnDuplicate().notEmpty().by("state", "county").index();
            @SuppressWarnings("unchecked")
            Lookup<Lookup<Lookup<CountyCode>>> l5 = (Lookup<Lookup<Lookup<CountyCode>>>) Lookups.from(CountyCode.codes)
                    .useLastOnDuplicate().notEmpty().by("state").by("county", "something").index();

        } else if (!select && hasDefault) {

            Lookup<CountyCode> l1 = Lookups.from(CountyCode.codes).notEmpty().useFirstOnDuplicate()
                    .defaultTo(CountyCode.DEFAULT).by("code").index();
            Lookup<Lookup<CountyCode>> l2 = Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT)
                    .useLastOnDuplicate().notEmpty().by("state").by("county").index();
            Lookup<Lookup<Lookup<CountyCode>>> l3 = Lookups.from(CountyCode.codes).useLastOnDuplicate()
                    .defaultTo(CountyCode.DEFAULT).notEmpty().by("state").by("county").by("something").index();
            @SuppressWarnings("unchecked")
            Lookup<Lookup<CountyCode>> l4 = (Lookup<Lookup<CountyCode>>) Lookups.from(CountyCode.codes)
                    .useLastOnDuplicate().notEmpty().defaultTo(CountyCode.DEFAULT).by("state", "county").index();
            @SuppressWarnings("unchecked")
            Lookup<Lookup<Lookup<CountyCode>>> l5 = (Lookup<Lookup<Lookup<CountyCode>>>) Lookups.from(CountyCode.codes)
                    .defaultTo(CountyCode.DEFAULT).useLastOnDuplicate().notEmpty().by("state")
                    .by("county", "something").index();

        } else if (select && !hasDefault) {

            Lookup<Integer> l1 = Lookups.from(CountyCode.codes).select(Integer.class, "code").notEmpty()
                    .useFirstOnDuplicate().by("code").index();
            Lookup<Lookup<Integer>> l2 = Lookups.from(CountyCode.codes).useLastOnDuplicate()
                    .select(Integer.class, "code").notEmpty().by("state").by("county").index();
            Lookup<Lookup<Lookup<Integer>>> l3 = Lookups.from(CountyCode.codes).useLastOnDuplicate().notEmpty()
                    .select(Integer.class, "code").by("state").by("county").by("something").index();

        } else if (select && hasDefault) {

            Lookup<Integer> l1 = Lookups.from(CountyCode.codes).select(Integer.class, "code").notEmpty().defaultTo(100)
                    .useFirstOnDuplicate().by("code").index();
            Lookup<Lookup<Integer>> l2 = Lookups.from(CountyCode.codes).useLastOnDuplicate()
                    .select(Integer.class, "code").notEmpty().defaultTo(100).by("state").by("county").index();
            Lookup<Lookup<Lookup<Integer>>> l3 = Lookups.from(CountyCode.codes).useLastOnDuplicate().notEmpty()
                    .select(Integer.class, "code").defaultTo(100).by("state").by("county").by("something").index();

        }
    }
}
