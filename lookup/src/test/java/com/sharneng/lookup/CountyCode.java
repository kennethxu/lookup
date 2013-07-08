package com.sharneng.lookup;

/**
 * A test class. See http://www.itl.nist.gov/fipspubs/co-codes/states.htm
 * 
 * @author Kenneth Xu
 * 
 */
public class CountyCode {

    private final int code;
    private final String state;
    private final String county;

    public CountyCode(int code, String state, String county) {
        this.code = code;
        this.state = state;
        this.county = county;
    }

    public int getCode() {
        return code;
    }

    public String getState() {
        return state;
    }

    public String getCounty() {
        return county;
    }
}
