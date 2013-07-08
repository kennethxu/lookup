package com.sharneng.lookup;

class EmptyLookup<T> implements Lookup<T> {
    private static final Lookup<?>[] CHAIN = new Lookup<?>[Lookups.LEVEL_LIMIT];
    private final T defaultValue;

    static {
        Lookup<Object> lookup = null;
        for (int i = 0; i < Lookups.LEVEL_LIMIT; i++) {
            lookup = new EmptyLookup<Object>(lookup);
            CHAIN[i] = lookup;
        }
    }

    static Lookup<?> getChainAt(int level) {
        return CHAIN[level];
    }

    EmptyLookup(T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T get(Object key) {
        throw new LookupException("Value not found for given key " + key);
    }

    @Override
    public T safeGet(Object key) {
        return defaultValue;
    }
}
