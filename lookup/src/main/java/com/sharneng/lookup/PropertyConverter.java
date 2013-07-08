/*
 * Copyright (c) 2011 Original Authors
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

import static java.util.Locale.ENGLISH;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PropertyConverter<TFrom> implements Converter<TFrom, Object> {
    private static final Class<?>[] empty = new Class[0];
    private final Method getter;

    public PropertyConverter(final Class<TFrom> clazz, final String property) {
        Method getter;
        try {
            final String capitalize = property.substring(0, 1).toUpperCase(ENGLISH) + property.substring(1);
            try {
                getter = clazz.getMethod("get" + capitalize, empty);
            } catch (NoSuchMethodException e) {
                try {
                    getter = clazz.getMethod("is" + capitalize, empty);
                } catch (NoSuchMethodException e2) {
                    throw e;
                }
            }
        } catch (NoSuchMethodException e) {
            throw new LookupException("Unable to find getter for property " + property, e);
        }
        this.getter = getter;
    }

    public Object convert(final TFrom source) {
        try {
            return source == null ? null : getter.invoke(source, (Object[]) null);
        } catch (IllegalAccessException e) {
            throw new LookupException(e);
        } catch (InvocationTargetException e) {
            throw new LookupException(e);
        }
    }

}
