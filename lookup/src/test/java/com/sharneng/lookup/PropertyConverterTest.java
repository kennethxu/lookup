package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.sharneng.lookup.testdata.Model.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PropertyConverterTest {
    private static final Class<?>[] NO_PARAM = null;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Base base = new Base();

    @Test
    public void canFindAndAccessPropertyWithGetMethod() {
        PropertyConverter<Base> sut = new PropertyConverter<Base>(Base.class, "property1");

        assertThat(sut.convert(base), equalTo((Object) base.getProperty1()));
        assertThat(sut.convert(null), nullValue());
    }

    @Test
    public void canFindAndAccessPropertyWithIsMethod() {
        PropertyConverter<Base> sut = new PropertyConverter<Base>(Base.class, "propertyTwo");

        assertThat(sut.convert(base), equalTo((Object) base.isPropertyTwo()));
        assertThat(sut.convert(null), nullValue());
    }

    @Test
    public void constructor_chokes_onNonExistProperty() {
        exception.expect(LookupBuildException.class);

        new PropertyConverter<Base>(Base.class, "nonExist");
    }

    @Test
    public void convert_chokes_onNonPublicMethod() throws Exception {
        exception.expect(LookupBuildException.class);
        PropertyConverter<Base> sut = new PropertyConverter<Base>(
                Base.class.getDeclaredMethod("getNonPublic", NO_PARAM));
        sut.convert(base);
    }

    @Test
    public void convert_chokes_whenExceptionThrownFromGetter() throws Exception {
        exception.expect(LookupBuildException.class);
        PropertyConverter<Base> sut = new PropertyConverter<Base>(
                Base.class.getDeclaredMethod("getException", NO_PARAM));
        sut.convert(base);
    }

}
