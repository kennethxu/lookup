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

import com.sharneng.lookup.testdata.CountyCode;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collection;
import java.util.Collections;

@SuppressFBWarnings("NP_NONNULL_PARAM_VIOLATION")
public class LookupBuilderNegativeTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    private LookupBuilder<CountyCode, CountyCode> sut;

    @Before
    public void setup() {
        sut = new LookupBuilder<CountyCode, CountyCode>(CountyCode.codes);
    }

    @Test
    public void select_chokes_onNullClazz() {
        exception.expect(IllegalArgumentException.class);

        new LookupBuilder<String, String>(null).select(null, "state");
    }

    @Test
    public void select_chokes_onNullConverter() {
        exception.expect(IllegalArgumentException.class);

        sut.select(null);
    }

    @Test
    public void select_chokes_onNullExpression() {
        exception.expect(IllegalArgumentException.class);

        sut.select(CountyCode.class, null);
    }

    @Test
    public void by_chokes_onNullExpression() {
        exception.expect(IllegalArgumentException.class);

        sut.by((String) null);
    }

    @Test
    public void by_chokes_onNullExpressions() {
        exception.expect(IllegalArgumentException.class);

        sut.by((String[]) null);
    }

    @Test
    public void by_chokes_onEmptyExpressions() {
        exception.expect(IllegalArgumentException.class);

        sut.by(new String[0]);
    }

    @Test
    public void by_chokes_onNullConverter() {
        exception.expect(IllegalArgumentException.class);

        sut.by((Converter<CountyCode, Object>) null);
    }

    @Test
    public void by_chokes_onNullConverters() {
        exception.expect(IllegalArgumentException.class);

        sut.by((Converter<CountyCode, Object>[]) null);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void by_chokes_onEmptyConverters() {
        exception.expect(IllegalArgumentException.class);

        sut.by((Converter<CountyCode, Object>[]) new Converter<?, ?>[0]);
    }

    @Test
    public void notEmpty_chokes_onNullSource() {
        exception.expect(LookupBuildException.class);

        new LookupBuilder<CountyCode, CountyCode>((Collection<CountyCode>) null).notEmpty();
    }

    @Test
    public void notEmpty_chokes_onEmptySource() {
        exception.expect(LookupBuildException.class);

        new LookupBuilder<CountyCode, CountyCode>(Collections.<CountyCode> emptyList()).notEmpty();
    }

    @Test
    public void index_chokes_onBadExpression() {
        exception.expect(LookupBuildException.class);
        exception.expectMessage("badad");

        sut.by("state", "badad", "county").index();
    }

    @Test
    public void index_chokes_onDuplicateSingleKey() {
        exception.expect(DuplicateKeyException.class);

        sut.by("state").index();
    }

    @Test
    public void index_chokes_onDuplicateMultipleKey() {
        sut = new LookupBuilder<CountyCode, CountyCode>(CountyCode.dupCodes);
        exception.expect(DuplicateKeyException.class);
        exception.expectMessage(CountyCode.code100.toString());
        exception.expectMessage(CountyCode.code200.toString());
        exception.expectMessage("NJ");
        exception.expectMessage("Mercer");

        sut.by("state", "county").index();
    }

}
