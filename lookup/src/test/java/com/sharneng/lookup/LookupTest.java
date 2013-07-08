package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import com.sharneng.lookup.testdata.CountyCode;

import org.junit.Test;

public class LookupTest {

    @Test
    public void create_twoLevelLookup_sunnyDay() {
        Lookup<Lookup<CountyCode>> lookup = LookupFactory.create(CountyCode.codes, "state", "county");

        CountyCode p = lookup.get("Mississippi").get("Greene");

        assertThat(p.getCode(), is(28041));
    }
}
