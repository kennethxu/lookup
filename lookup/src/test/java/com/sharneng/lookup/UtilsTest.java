package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class UtilsTest {

    @Test
    public void countingSuffix() {
        assertThat(Utils.countingSuffix(1), is("st"));
        assertThat(Utils.countingSuffix(31), is("st"));
        assertThat(Utils.countingSuffix(2), is("nd"));
        assertThat(Utils.countingSuffix(122), is("nd"));
        assertThat(Utils.countingSuffix(3), is("rd"));
        assertThat(Utils.countingSuffix(83), is("rd"));
        assertThat(Utils.countingSuffix(4), is("th"));
        assertThat(Utils.countingSuffix(14), is("th"));
        assertThat(Utils.countingSuffix(5), is("th"));
        assertThat(Utils.countingSuffix(65), is("th"));
        assertThat(Utils.countingSuffix(6), is("th"));
        assertThat(Utils.countingSuffix(996), is("th"));
        assertThat(Utils.countingSuffix(7), is("th"));
        assertThat(Utils.countingSuffix(27), is("th"));
        assertThat(Utils.countingSuffix(8), is("th"));
        assertThat(Utils.countingSuffix(888), is("th"));
        assertThat(Utils.countingSuffix(9), is("th"));
        assertThat(Utils.countingSuffix(39), is("th"));
        assertThat(Utils.countingSuffix(10), is("th"));
    }
}
