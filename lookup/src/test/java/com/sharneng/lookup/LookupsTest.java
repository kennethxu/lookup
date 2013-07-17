package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;

import org.junit.Test;

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
    public void syntax() {
        Lookups.from(CountyCode.codes).select(Integer.class, "code").defaultTo(100).by("state").by("county").index();
        Lookups.from(CountyCode.codes).defaultTo(CountyCode.DEFAULT).by("state", "county").index();
    }
}
