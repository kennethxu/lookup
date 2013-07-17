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

    static String notNullIndexed(final String argumentName, final int index) {
        String suffix;
        final int ten = 10;
        final int first = 1, secound = 2, third = 3;
        switch (index % ten) {
        case first:
            suffix = "st";
            break;
        case secound:
            suffix = "nd";
            break;
        case third:
            suffix = "rd";
            break;
        default:
            suffix = "th";
            break;
        }
        return "Argument of " + index + suffix + " " + argumentName + " parameter must not be null.";
    }

    static LookupException notFoundException(final Object key) {
        return new LookupException("Value not found for given key " + key);
    }

    @SuppressWarnings("unchecked")
    static <T> Converter<T, T> toSelf() {
        return (Converter<T, T>) TO_SELF;
    }

}
