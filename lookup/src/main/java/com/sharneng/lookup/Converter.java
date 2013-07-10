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

import javax.annotation.CheckForNull;

/**
 * Interface to convert from one object to another.
 * 
 * @author Kenneth Xu
 * 
 * @param <TFrom>
 *            type of the object to convert from
 * @param <TTo>
 *            type of the object to convert to
 */
interface Converter<TFrom, TTo> {

    /**
     * Convert from one object to another.
     * 
     * @param source
     *            the object to convert from
     * @return the converted object or null if source is null
     */
    @CheckForNull
    TTo convert(@CheckForNull TFrom source);
}
