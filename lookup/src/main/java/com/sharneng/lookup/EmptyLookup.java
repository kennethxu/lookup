package com.sharneng.lookup;

import javax.annotation.CheckForNull;

class EmptyLookup<T> implements Lookup<T> {
    private static final Lookup<?>[] CHAIN = new Lookup<?>[Lookups.LEVEL_LIMIT];
    @CheckForNull
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

    EmptyLookup(@CheckForNull T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public T find(Object key) {
        return defaultValue;
    }

    @Override
    public T find(@CheckForNull Object key, @CheckForNull T defaultValue) {
        return defaultValue;
    }

    @Override
    public T get(Object key) {
        if (defaultValue != null) return defaultValue;
        throw Utils.notFoundException(key);
    }

    @Override
    public T get(@CheckForNull Object key, T defaultValue) {
        if (defaultValue == null) throw new IllegalArgumentException(Utils.notNull("defaultValue"));
        return defaultValue;
    }

    @Override
    public T hunt(Object key) {
        key.toString();
        throw Utils.notFoundException(key);
    }
}
