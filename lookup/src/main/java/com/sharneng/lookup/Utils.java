package com.sharneng.lookup;

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
}