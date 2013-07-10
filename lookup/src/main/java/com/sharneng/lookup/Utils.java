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
}
