package com.sharneng.lookup;

import java.util.Map;

class MultiLevelLookup<T extends Lookup<?>> extends MapBasedLookup<T> {
    private final T defaultValue;

    @SuppressWarnings("unchecked")
    MultiLevelLookup(Map<? extends Object, ? extends T> map, int level) {
        super(map);
        defaultValue = (T) EmptyLookup.getChainAt(level);
    }

    @Override
    public T safeGet(Object key) {
        final T result = super.safeGet(key);
        return result == null ? (T) defaultValue : result;
    }
}
