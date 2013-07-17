package com.sharneng.lookup;

final class Utils {
    private static final Converter<?, ?> TO_SELF = new Converter<Object, Object>() {
        public Object convert(Object source) {
            return source;
        }
    };

    private Utils() {
    }

    static String notNull(final String argumentName) {
        return "Argument " + argumentName + " must not be null.";
    }

    static LookupException notFoundException(final Object key) {
        return new LookupException("Value not found for given key " + key);
    }

    @SuppressWarnings("unchecked")
    static <T> Converter<T, T> toSelf() {
        return (Converter<T, T>) TO_SELF;
    }

}
