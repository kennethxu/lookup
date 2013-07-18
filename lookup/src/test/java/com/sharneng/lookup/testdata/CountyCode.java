/*
 * Copyright (c) 2013 Original Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// SUPPRESS CHECKSTYLE FOR TEST CODE
package com.sharneng.lookup.testdata;

import java.util.Arrays;
import java.util.List;

/**
 * A test class. See http://www.itl.nist.gov/fipspubs/co-codes/states.htm
 * 
 * @author Kenneth Xu
 * 
 */
public class CountyCode {
    public static final CountyCode DEFAULT = new CountyCode(0, null, null);
    public static final CountyCode code100 = new CountyCode(100, "NJ", "Mercer");
    public static final CountyCode code200 = new CountyCode(200, "NJ", "Mercer");
    public static final List<CountyCode> dupCodes = Arrays.asList(new CountyCode[] { code100, code200 });

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + code;
        result = prime * result + ((county == null) ? 0 : county.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        CountyCode other = (CountyCode) obj;
        if (code != other.code) return false;
        if (county == null) {
            if (other.county != null) return false;
        } else if (!county.equals(other.county)) return false;
        if (state == null) {
            if (other.state != null) return false;
        } else if (!state.equals(other.state)) return false;
        return true;
    }

    public static final List<CountyCode> codes;

    static {
        //@formatter:off
        CountyCode[] a = new CountyCode[]{
                new CountyCode(1001,    "Alabama",  "Autauga"),
                new CountyCode(1003,    "Alabama",  "Baldwin"),
                new CountyCode(1005,    "Alabama",  "Barbour"),
                new CountyCode(1007,    "Alabama",  "Bibb"),
                new CountyCode(1009,    "Alabama",  "Blount"),
                new CountyCode(1011,    "Alabama",  "Bullock"),
                new CountyCode(1013,    "Alabama",  "Butler"),
                new CountyCode(1015,    "Alabama",  "Calhoun"),
                new CountyCode(1017,    "Alabama",  "Chambers"),
                new CountyCode(1019,    "Alabama",  "Cherokee"),
                new CountyCode(1021,    "Alabama",  "Chilton"),
                new CountyCode(1023,    "Alabama",  "Choctaw"),
                new CountyCode(1025,    "Alabama",  "Clarke"),
                new CountyCode(1027,    "Alabama",  "Clay"),
                new CountyCode(1029,    "Alabama",  "Cleburne"),
                new CountyCode(1031,    "Alabama",  "Coffee"),
                new CountyCode(1033,    "Alabama",  "Colbert"),
                new CountyCode(1035,    "Alabama",  "Conecuh"),
                new CountyCode(1037,    "Alabama",  "Coosa"),
                new CountyCode(1039,    "Alabama",  "Covington"),
                new CountyCode(1041,    "Alabama",  "Crenshaw"),
                new CountyCode(1043,    "Alabama",  "Cullman"),
                new CountyCode(1045,    "Alabama",  "Dale"),
                new CountyCode(1047,    "Alabama",  "Dallas"),
                new CountyCode(1049,    "Alabama",  "DeKalb*"),
                new CountyCode(1051,    "Alabama",  "Elmore"),
                new CountyCode(1053,    "Alabama",  "Escambia"),
                new CountyCode(1055,    "Alabama",  "Etowah"),
                new CountyCode(1057,    "Alabama",  "Fayette"),
                new CountyCode(1059,    "Alabama",  "Franklin"),
                new CountyCode(1061,    "Alabama",  "Geneva"),
                new CountyCode(1063,    "Alabama",  "Greene"),
                new CountyCode(1065,    "Alabama",  "Hale"),
                new CountyCode(1067,    "Alabama",  "Henry"),
                new CountyCode(1069,    "Alabama",  "Houston"),
                new CountyCode(1071,    "Alabama",  "Jackson"),
                new CountyCode(1073,    "Alabama",  "Jefferson"),
                new CountyCode(1075,    "Alabama",  "Lamar"),
                new CountyCode(1077,    "Alabama",  "Lauderdale"),
                new CountyCode(1079,    "Alabama",  "Lawrence"),
                new CountyCode(1081,    "Alabama",  "Lee"),
                new CountyCode(1083,    "Alabama",  "Limestone"),
                new CountyCode(1085,    "Alabama",  "Lowndes"),
                new CountyCode(1087,    "Alabama",  "Macon"),
                new CountyCode(1089,    "Alabama",  "Madison"),
                new CountyCode(1091,    "Alabama",  "Marengo"),
                new CountyCode(1093,    "Alabama",  "Marion"),
                new CountyCode(1095,    "Alabama",  "Marshall"),
                new CountyCode(1097,    "Alabama",  "Mobile"),
                new CountyCode(1099,    "Alabama",  "Monroe"),
                new CountyCode(1101,    "Alabama",  "Montgomery"),
                new CountyCode(1103,    "Alabama",  "Morgan"),
                new CountyCode(1105,    "Alabama",  "Perry"),
                new CountyCode(1107,    "Alabama",  "Pickens"),
                new CountyCode(1109,    "Alabama",  "Pike"),
                new CountyCode(1111,    "Alabama",  "Randolph"),
                new CountyCode(1113,    "Alabama",  "Russell"),
                new CountyCode(1115,    "Alabama",  "St. Clair"),
                new CountyCode(1117,    "Alabama",  "Shelby"),
                new CountyCode(1119,    "Alabama",  "Sumter"),
                new CountyCode(1121,    "Alabama",  "Talladega"),
                new CountyCode(1123,    "Alabama",  "Tallapoosa"),
                new CountyCode(1125,    "Alabama",  "Tuscaloosa"),
                new CountyCode(1127,    "Alabama",  "Walker"),
                new CountyCode(1129,    "Alabama",  "Washington"),
                new CountyCode(1131,    "Alabama",  "Wilcox"),
                new CountyCode(1133,    "Alabama",  "Winston"),
                new CountyCode(28001,   "Mississippi",  "Adams"),
                new CountyCode(28003,   "Mississippi",  "Alcorn"),
                new CountyCode(28005,   "Mississippi",  "Amite"),
                new CountyCode(28007,   "Mississippi",  "Attala"),
                new CountyCode(28009,   "Mississippi",  "Benton"),
                new CountyCode(28011,   "Mississippi",  "Bolivar"),
                new CountyCode(28013,   "Mississippi",  "Calhoun"),
                new CountyCode(28015,   "Mississippi",  "Carroll"),
                new CountyCode(28017,   "Mississippi",  "Chickasaw"),
                new CountyCode(28019,   "Mississippi",  "Choctaw"),
                new CountyCode(28021,   "Mississippi",  "Claiborne"),
                new CountyCode(28023,   "Mississippi",  "Clarke"),
                new CountyCode(28025,   "Mississippi",  "Clay"),
                new CountyCode(28027,   "Mississippi",  "Coahoma"),
                new CountyCode(28029,   "Mississippi",  "Copiah"),
                new CountyCode(28031,   "Mississippi",  "Covington"),
                new CountyCode(28033,   "Mississippi",  "DeSoto"),
                new CountyCode(28035,   "Mississippi",  "Forrest"),
                new CountyCode(28037,   "Mississippi",  "Franklin"),
                new CountyCode(28039,   "Mississippi",  "George"),
                new CountyCode(28041,   "Mississippi",  "Greene"),
                new CountyCode(28043,   "Mississippi",  "Grenada"),
                new CountyCode(28045,   "Mississippi",  "Hancock"),
                new CountyCode(28047,   "Mississippi",  "Harrison"),
                new CountyCode(28049,   "Mississippi",  "Hinds"),
                new CountyCode(28051,   "Mississippi",  "Holmes"),
                new CountyCode(28053,   "Mississippi",  "Humphreys"),
                new CountyCode(28055,   "Mississippi",  "Issaquena"),
                new CountyCode(28057,   "Mississippi",  "Itawamba"),
                new CountyCode(28059,   "Mississippi",  "Jackson"),
                new CountyCode(28061,   "Mississippi",  "Jasper"),
                new CountyCode(28063,   "Mississippi",  "Jefferson"),
                new CountyCode(28065,   "Mississippi",  "Jefferson Davis"),
                new CountyCode(28067,   "Mississippi",  "Jones"),
                new CountyCode(28069,   "Mississippi",  "Kemper"),
                new CountyCode(28071,   "Mississippi",  "Lafayette"),
                new CountyCode(28073,   "Mississippi",  "Lamar"),
                new CountyCode(28075,   "Mississippi",  "Lauderdale"),
                new CountyCode(28077,   "Mississippi",  "Lawrence"),
                new CountyCode(28079,   "Mississippi",  "Leake"),
                new CountyCode(28081,   "Mississippi",  "Lee"),
                new CountyCode(28083,   "Mississippi",  "Leflore"),
                new CountyCode(28085,   "Mississippi",  "Lincoln"),
                new CountyCode(28087,   "Mississippi",  "Lowndes"),
                new CountyCode(28089,   "Mississippi",  "Madison"),
                new CountyCode(28091,   "Mississippi",  "Marion"),
                new CountyCode(28093,   "Mississippi",  "Marshall"),
                new CountyCode(28095,   "Mississippi",  "Monroe"),
                new CountyCode(28097,   "Mississippi",  "Montgomery"),
                new CountyCode(28099,   "Mississippi",  "Neshoba"),
                new CountyCode(28101,   "Mississippi",  "Newton"),
                new CountyCode(28103,   "Mississippi",  "Noxubee"),
                new CountyCode(28105,   "Mississippi",  "Oktibbeha"),
                new CountyCode(28107,   "Mississippi",  "Panola"),
                new CountyCode(28109,   "Mississippi",  "Pearl River"),
                new CountyCode(28111,   "Mississippi",  "Perry"),
                new CountyCode(28113,   "Mississippi",  "Pike"),
                new CountyCode(28115,   "Mississippi",  "Pontotoc"),
                new CountyCode(28117,   "Mississippi",  "Prentiss"),
                new CountyCode(28119,   "Mississippi",  "Quitman"),
                new CountyCode(28121,   "Mississippi",  "Rankin"),
                new CountyCode(28123,   "Mississippi",  "Scott"),
                new CountyCode(28125,   "Mississippi",  "Sharkey"),
                new CountyCode(28127,   "Mississippi",  "Simpson"),
                new CountyCode(28129,   "Mississippi",  "Smith"),
                new CountyCode(28131,   "Mississippi",  "Stone"),
                new CountyCode(28133,   "Mississippi",  "Sunflower"),
                new CountyCode(28135,   "Mississippi",  "Tallahatchie"),
                new CountyCode(28137,   "Mississippi",  "Tate"),
                new CountyCode(28139,   "Mississippi",  "Tippah"),
                new CountyCode(28141,   "Mississippi",  "Tishomingo"),
                new CountyCode(28143,   "Mississippi",  "Tunica"),
                new CountyCode(28145,   "Mississippi",  "Union"),
                new CountyCode(28147,   "Mississippi",  "Walthall"),
                new CountyCode(28149,   "Mississippi",  "Warren"),
                new CountyCode(28151,   "Mississippi",  "Washington"),
                new CountyCode(28153,   "Mississippi",  "Wayne"),
                new CountyCode(28155,   "Mississippi",  "Webster"),
                new CountyCode(28157,   "Mississippi",  "Wilkinson"),
                new CountyCode(28159,   "Mississippi",  "Winston"),
                new CountyCode(28161,   "Mississippi",  "Yalobusha"),
                new CountyCode(28163,   "Mississippi",  "Yazoo"),
        };
        //@formatter:on
        codes = Arrays.asList(a);
    }
}
