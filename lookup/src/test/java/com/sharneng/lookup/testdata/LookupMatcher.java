// SUPPRESS CHECKSTYLE FOR TEST CODE
package com.sharneng.lookup.testdata;

import com.sharneng.lookup.Lookup;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class LookupMatcher extends BaseMatcher<Lookup<CountyCode>> {
    private final Object key;
    private final CountyCode value;
    private final String description;

    public LookupMatcher(Object key, CountyCode value, String description) {
        this.key = key;
        this.value = value;
        this.description = description;
    }

    @Override
    public boolean matches(Object item) {
        if (!(item instanceof Lookup<?>)) return false;
        @SuppressWarnings("unchecked")
        Lookup<CountyCode> lookup = (Lookup<CountyCode>) item;
        return (lookup.get(key).equals(value));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(this.description);
    }

}
