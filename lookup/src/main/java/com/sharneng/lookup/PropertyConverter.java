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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class PropertyConverter<TFrom> implements Converter<TFrom, Object> {
    private final Method getter;

    public PropertyConverter(final Class<? extends TFrom> clazz, final String property) {
        this(toMethod(clazz, property));
    }

    public PropertyConverter(Method getter) {
        this.getter = getter;
    }

    private static Method toMethod(final Class<?> clazz, final String property) {
        Method getter = new PropertyGetterFinder().findGetter(clazz, property);
        if (getter == null) throw new LookupBuildException("Unable to find getter for property " + property
                + " on class " + clazz);
        return getter;
    }

    public Object convert(final TFrom source) {
        try {
            return source == null ? null : getter.invoke(source, (Object[]) null);
        } catch (IllegalAccessException e) {
            throw new LookupBuildException(e);
        } catch (InvocationTargetException e) {
            throw new LookupBuildException(e);
        }
    }

}
