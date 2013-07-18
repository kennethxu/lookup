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
// SUPPRESS CHECKSTYLE FOR TEST CODE
package com.sharneng.lookup.testdata;

import com.sharneng.lookup.Lookup;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class LookupMatcher extends BaseMatcher<Lookup<CountyCode>> {
    private final Object key;
    private final CountyCode value;
    private final String description;

    public LookupMatcher(Object key, CountyCode value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    @Override
    public boolean matches(Object item) {
        if (!(item instanceof Lookup<?>)) return false;
        @SuppressWarnings("unchecked")
        Lookup<CountyCode> lookup = (Lookup<CountyCode>) item;
        return (lookup.get(key).equals(value));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.description);
    }

}
