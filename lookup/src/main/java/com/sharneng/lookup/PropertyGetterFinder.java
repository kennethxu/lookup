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

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

class PropertyGetterFinder {
    private static final Prefix GET = Prefix.GET;
    private static final Prefix IS = Prefix.IS;

    private static enum Prefix {
        GET("get"),
        IS("is");
        private final int start;
        private final String prefix;

        private Prefix(String prefix) {
            this.prefix = prefix;
            this.start = prefix.length();
        }
    }

    private String[] properties;
    private Method[] methods;
    private Set<Class<?>> processedInterfaces;

    public Method findGetter(Class<?> clazz, String property) {
        return findGetters(clazz, property)[0];
    }

    public Method[] findGetters(Class<?> clazz, String... properties) {
        this.properties = properties;
        methods = new Method[properties.length];
        processedInterfaces = new HashSet<Class<?>>();
        findGetters(clazz);
        return methods;
    }

    private void findGetters(Class<?> clazz) {
        Class<?> base = clazz.getSuperclass();
        if (base != null && !base.equals(Object.class)) findGetters(base);
        for (Class<?> i : clazz.getInterfaces()) {
            if (processedInterfaces.contains(i)) continue;
            findGetters(i);
            processedInterfaces.add(i);
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (!isRightSignature(m)) continue; // it isn't a public instance method with no parameter
            Prefix prefix = getPrefix(m);
            if (prefix == null) continue; // if name neither starts with "get", nor "is" for boolean, it is not a getter
            processGetter(m, prefix);
        }
    }

    private void processGetter(Method m, Prefix prefix) {
        String name = m.getName();
        String property = Character.isLowerCase(name.charAt(prefix.start + 1)) ? Character.toLowerCase(name
                .charAt(prefix.start)) + name.substring(prefix.start + 1) : name.substring(prefix.start);
        for (int i = 0; i < properties.length; i++) {
            if (methods[i] == null && property.equals(properties[i])) {
                methods[i] = m;
                break;
            }
        }
    }

    private static Prefix getPrefix(Method m) {
        String name = m.getName();
        Class<?> returnType = m.getReturnType();
        return name.startsWith(GET.prefix) ? GET : name.startsWith(IS.prefix) && isBooleanType(returnType) ? IS : null;
    }

    private static boolean isBooleanType(Class<?> type) {
        return type == Boolean.TYPE || type == Boolean.class;
    }

    /**
     * a getter method must be public instance method with no parameter and non void return type.
     */
    private static boolean isRightSignature(Method m) {
        final int modifiers = m.getModifiers();

        return (modifiers & Modifier.PUBLIC) != 0 && (modifiers & Modifier.STATIC) == 0
                && m.getParameterTypes().length == 0 && !m.getReturnType().equals(Void.TYPE);
    }

}
