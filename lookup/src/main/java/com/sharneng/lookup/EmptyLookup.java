package com.sharneng.lookup;

import javax.annotation.CheckForNull;

class EmptyLookup<T> implements Lookup<T> {
    @CheckForNull
    private final T defaultValue;

    EmptyLookup(@CheckForNull T defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public boolean has(Object key) {
        return false;
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
        throw (key == null) ? new IllegalArgumentException(Utils.notNull("key")) : Utils.notFoundException(key);
    }
}
