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

import ognl.Ognl;
import ognl.OgnlException;

class OgnlConverter<TFrom, TTo> implements Converter<TFrom, TTo> {
    private final Object expression;
    private final Class<? extends TTo> resultClass;

    public OgnlConverter(final Class<? extends TTo> clazz, final String expression) {
        this.resultClass = clazz;
        try {
            this.expression = Ognl.parseExpression(expression);
        } catch (OgnlException e) {
            throw new LookupBuildException(e);
        }
    }

    public TTo convert(final TFrom source) {
        try {
            Object result = source == null ? null : Ognl.getValue(expression, source);
            return resultClass.cast(result);
        } catch (OgnlException e) {
            throw new LookupBuildException(e);
        }
    }

}
