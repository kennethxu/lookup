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

/**
 * Indicates an error where same sets of keys resolves to two or more different values when building a lookup.
 * 
 * @author Kenneth Xu
 * 
 */
public class DuplicateKeyException extends LookupBuildException {
    private static final long serialVersionUID = 1L;

    /**
     * Construct a new {@link DuplicateKeyException}.
     * 
     * @param value1
     *            one of the different values resulting to the same set of keys
     * @param value2
     *            another one of the different values resulting to the same set of keys
     * @param keys
     *            the set of keys resolving to tow or more different values
     */
    public DuplicateKeyException(Object value1, Object value2, Object... keys) {
        // TODO fix this
        super(value1.toString());
    }
}
