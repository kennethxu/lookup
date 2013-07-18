/*
 * Copyright (c) 2013 Original Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sharneng.lookup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import com.sharneng.lookup.testdata.Model.Base;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OgnlConverterTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private Base base = new Base();

    @Test
    public void canFindAndAccessPropertyWithGetMethod() {
        OgnlConverter<Base, String> sut = new OgnlConverter<Base, String>(String.class, "property1");

        assertThat(sut.convert(base), equalTo((Object) base.getProperty1()));
        assertThat(sut.convert(null), nullValue());
    }

    @Test
    public void canFindAndAccessPropertyWithIsMethod() {
        OgnlConverter<Base, Boolean> sut = new OgnlConverter<Base, Boolean>(Boolean.class, "propertyTwo");

        assertThat(sut.convert(base), equalTo((Object) base.isPropertyTwo()));
        assertThat(sut.convert(null), nullValue());
    }

    @Test
    public void constructor_chokes_onBadExpression() {
        exception.expect(LookupBuildException.class);

        new OgnlConverter<Base, Object>(Object.class, "non ! Exist");
    }

    @Test
    public void convert_chokes_onNonExistProperty() {
        OgnlConverter<Base, Object> sut = new OgnlConverter<Base, Object>(Object.class, "nonExist");
        exception.expect(LookupBuildException.class);

        sut.convert(base);
    }

    @Test
    public void convert_chokes_onNonPublicMethod() throws Exception {
        OgnlConverter<Base, String> sut = new OgnlConverter<Base, String>(String.class, "nonPublic");
        exception.expect(LookupBuildException.class);

        sut.convert(base);
    }

    @Test
    public void convert_chokes_whenExceptionThrownFromGetter() throws Exception {
        OgnlConverter<Base, String> sut = new OgnlConverter<Base, String>(String.class, "exception");
        exception.expect(LookupBuildException.class);

        sut.convert(base);
    }

}
