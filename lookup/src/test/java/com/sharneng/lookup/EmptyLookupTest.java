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

import static org.hamcrest.Matchers.*;

public class EmptyLookupTest {
    private static final String ARGUMENT_DEFAULT = "defaultParam";
    private static final String INSTANCE_DEFAULT = "default";
    private static final Object KEY = "key";

    public static class WithDefault extends LookupWithDefaultNotFoundTest<String> {
        public WithDefault() {
            super(KEY, ARGUMENT_DEFAULT, equalTo(INSTANCE_DEFAULT));
        }

        @Override
        protected Lookup<String> newLookup() {
            return new EmptyLookup<String>(INSTANCE_DEFAULT);
        }
    }

    public static class WithoutDefault extends LookupWithoutDefaultNotFoundTest<String> {
        public WithoutDefault() {
            super(KEY, ARGUMENT_DEFAULT);
        }

        @Override
        protected Lookup<String> newLookup() {
            return new EmptyLookup<String>(null);
        }
    }

}
