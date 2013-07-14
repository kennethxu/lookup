// SUPPRESS CHECKSTYLE FOR TEST CODE
package com.sharneng.lookup.testdata;

public interface Model {
    public interface A {
        String getPropertyA();

        String getCommon();
    }

    public interface B {
        String getPropertyB();

        String getCommon();
    }

    public interface C extends A {

    }

    public interface D extends A, B {

    }

    public static class Base implements D {
        public String getProperty1() {
            return "Base";
        }

        String getNonPublic() {
            return null;
        }

        public String getException() throws Exception {
            throw new Exception();
        }

        public boolean isPropertyTwo() {
            return false;
        }

        @Override
        public String getPropertyA() {
            return "Base.A";
        }

        @Override
        public String getCommon() {
            return "Base";
        }

        @Override
        public String getPropertyB() {
            return "Base.B";
        }

        public String isStringIs() {
            return null;
        }

        public String getURL() {
            return null;
        }

        public boolean getBooleanGet() {
            return false;
        }

    }

    public static class Sub extends Base implements B, C {
        @Override
        public String getProperty1() {
            return "Sub";
        }

        public static String getStaticMethod() {
            return null;
        }

        @Override
        public String getCommon() {
            return "SubCommon";
        }

        @Override
        public String getPropertyA() {
            return "Sub.A";
        }

        public Boolean isBooleanIs() {
            return true;
        }

        public void getVoidReturn() {

        }

        public String getWithParameter(String p) {
            return null;
        }

        public String toString() {
            return "Sub";
        }
    }

}
