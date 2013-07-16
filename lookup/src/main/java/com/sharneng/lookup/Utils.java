package com.sharneng.lookup;

import java.lang.reflect.Method;

final class Utils {
    private Utils() {
    }

    static String notNull(final String argumentName) {
        return "Argument " + argumentName + " must not be null.";
    }

    static LookupException notFoundException(final Object key) {
        return new LookupException("Value not found for given key " + key);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    static <T> Converter<T, Object>[] toGeneric(final Converter... converters) {
        return (Converter<T, Object>[]) converters;
    }

    @SuppressWarnings("unchecked")
    static <T> Lookup<Lookup<T>> toLookup2(final Lookup<?> create) {
        return (Lookup<Lookup<T>>) create;
    }

    @SuppressWarnings("unchecked")
    static <T> Class<T> getClass(T value) {
        return (Class<T>) value.getClass();
    }

    static <T> Converter<T, Object>[] toConverters(final Class<? extends T> clazz, final String... properties) {
        if (clazz == null) throw new IllegalArgumentException(notNull("clazz"));
        if (properties == null) throw new IllegalArgumentException(notNull("properties"));
        if (properties.length == 0) throw new IllegalArgumentException("At least one property must be supplied");
        final Converter<T, Object>[] converters = toGeneric(new Converter[properties.length]);
        for (int i = 0; i < properties.length; i++) {
            if (properties[i] == null) throw new IllegalArgumentException(notNull("property" + (i + 1)));
        }
        Method[] getters = new PropertyGetterFinder().findGetters(clazz, properties);
        for (int i = 0; i < getters.length; i++) {
            if (getters[i] == null) throw new LookupBuildException("Unable to find getter for property "
                    + properties[i] + " on class " + clazz);
            converters[i] = new PropertyConverter<T>(getters[i]);
        }
        return converters;
    }
}
