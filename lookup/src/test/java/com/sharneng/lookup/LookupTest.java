package com.sharneng.lookup;

public class LookupTest {

    public void x() {
        Lookup<Lookup<PersonMock>> lookup = LookupFactory.create(null, "s", "s");
        // Collection<Person>
        PersonMock p = lookup.get("name").get(1);
    }
}
