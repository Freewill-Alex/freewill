package com.freewill.security.user.em;

public class UserEnum {

    public enum Status {

        E1_ON_JOB(1, "在职"),
        E0_DIMISSION(0, "离职");

        private int key;
        private String value;

        Status(int key, String value) {
            this.key = key;
            this.value = value;
        }

        public static String getValue(int key) {
            for (Status t : Status.values()) {
                if (t.key == key) {
                    return t.value;
                }
            }
            return null;
        }

        /**
         * @return the key
         */
        public int getKey() {
            return key;
        }

        /**
         * @return the value
         */
        public String getValue() {
            return value;
        }
    }
}
