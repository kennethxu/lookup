package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static com.sharneng.lookup.testdata.Model.*;

import org.junit.Test;

import java.lang.reflect.Method;

public class PropertyGetterFinderTest {

    private static final Class<?>[] NO_PARAM = null;
    private static final Object[] NO_ARG = null;

    private Base base = new Base();
    private Sub sub = new Sub();

    @Test
    public void canFindSimplePropertyMethod() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Base.class, "property1");
        assertThat(m, equalTo(Base.class.getMethod("getProperty1", NO_PARAM)));
    }

    @Test
    public void canFindPrimitiveBooleanPropertyMethod() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Base.class, "propertyTwo");
        assertThat(m, equalTo(Base.class.getMethod("isPropertyTwo", NO_PARAM)));
    }

    @Test
    public void canFindBooleanPropertyMethod() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "booleanIs");
        assertThat(m, equalTo(Sub.class.getMethod("isBooleanIs", NO_PARAM)));
    }

    @Test
    public void shouldFindParentProperty() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "property1");
        assertThat(m, equalTo(Base.class.getMethod("getProperty1", NO_PARAM)));
    }

    @Test
    public void shouldFindInterfaceProperty() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "propertyB");
        assertThat(m, equalTo(B.class.getMethod("getPropertyB", NO_PARAM)));
    }

    @Test
    public void doesNotFailOnAmbiguousProperty() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "common");
        assertThat(m, not(nullValue()));
        assertThat(m.invoke(sub, NO_ARG), equalTo((Object) sub.getCommon()));
        assertThat(m.invoke(base, NO_ARG), equalTo((Object) base.getCommon()));
    }

    @Test
    public void isPrefixOnlyApplyToBooleanTypes() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "stringIs");
        assertThat(m, nullValue());
    }

    @Test
    public void canFindUpperCaseProperty() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "URL");
        assertThat(m, equalTo(Base.class.getMethod("getURL", NO_PARAM)));
    }

    @Test
    public void returnsNullWhenPropertyDoesNotExist() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "nonExist");
        assertThat(m, nullValue());
    }

    @Test
    public void doesNotReturnNonPublicMethod() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "nonPublic");
        assertThat(m, nullValue());
    }

    @Test
    public void doesNotReturnStaticMethod() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "staticMethod");
        assertThat(m, nullValue());
    }

    @Test
    public void doesNotReturnVoidMethod() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "voidReturn");
        assertThat(m, nullValue());
    }

    @Test
    public void doesNotReturnMethodWithParameter() throws Exception {
        Method m = new PropertyGetterFinder().findGetter(Sub.class, "withParameter");
        assertThat(m, nullValue());
    }

    @Test
    public void canFindMultipleMethods() throws Exception {
        Method[] m = new PropertyGetterFinder().findGetters(Sub.class, "propertyTwo", "nonExist", "booleanIs",
                "property1", "propertyB", "common");
        assertThat(m[0], equalTo(Base.class.getMethod("isPropertyTwo", NO_PARAM)));
        assertThat(m[1], nullValue());
        assertThat(m[2], equalTo(Sub.class.getMethod("isBooleanIs", NO_PARAM)));
        assertThat(m[3], equalTo(Base.class.getMethod("getProperty1", NO_PARAM)));
        assertThat(m[4], equalTo(B.class.getMethod("getPropertyB", NO_PARAM)));
        assertThat(m[5], not(nullValue()));
        assertThat(m[5].invoke(sub, NO_ARG), equalTo((Object) sub.getCommon()));
        assertThat(m[5].invoke(base, NO_ARG), equalTo((Object) base.getCommon()));
    }

}
