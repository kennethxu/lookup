package com.sharneng.lookup;

import java.util.Map;

class MultiLevelLookup<T extends Lookup<?>> extends MapBasedLookup<T> {

    @SuppressWarnings("unchecked")
    MultiLevelLookup(Map<? extends Object, ? extends T> map, int level) {
        super(map, (T) EmptyLookup.getChainAt(level));
    }
}
