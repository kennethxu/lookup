package com.sharneng.lookup;

import com.sharneng.lookup.testdata.CountyCode;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;

public class LookupBuilderNegativeTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    @SuppressWarnings("NP_NONNULL_PARAM_VIOLATION")
    public void constructor_chokes_onNullClazz() {
        exception.expect(IllegalArgumentException.class);

        new LookupBuilder<CountyCode>(CountyCode.DEFAULT, null, "state", "county");
    }

    @Test
    public void constructor_chokes_onNullProperties() {
        exception.expect(IllegalArgumentException.class);

        new LookupBuilder<CountyCode>(CountyCode.DEFAULT, CountyCode.class, (String[]) null);
    }

    @Test
    public void constructor_chokes_onEmptyProperties() {
        exception.expect(IllegalArgumentException.class);

        new LookupBuilder<CountyCode>(CountyCode.DEFAULT, CountyCode.class, new String[0]);
    }

    @Test
    public void constructor_chokes_onNullProperty() {
        exception.expect(IllegalArgumentException.class);

        new LookupBuilder<CountyCode>(CountyCode.DEFAULT, CountyCode.class, "state", null);
    }

    @Test
    public void constructor_chokes_onNonExistProperty() {
        exception.expect(LookupBuildException.class);

        new LookupBuilder<CountyCode>(null, CountyCode.class, "state", "bad", "county");
    }

    @Test
    public void build_chokes_onDuplicateSingleKey() {
        exception.expect(DuplicateKeyException.class);

        new LookupBuilder<CountyCode>(null, CountyCode.class, "state").build(CountyCode.codes);
    }

    @Test
    public void build_chokes_onDuplicateMultipleKey() {
        final CountyCode code1 = new CountyCode(100, "NJ", "Mercer");
        final CountyCode code2 = new CountyCode(200, "NJ", "Mercer");
        List<CountyCode> codes = Arrays.asList(new CountyCode[] { code1, code2 });
        exception.expect(DuplicateKeyException.class);
        exception.expectMessage(code1.toString());
        exception.expectMessage(code2.toString());
        exception.expectMessage("NJ");
        exception.expectMessage("Mercer");

        new LookupBuilder<CountyCode>(null, CountyCode.class, "state", "county").build(codes);
    }

}
