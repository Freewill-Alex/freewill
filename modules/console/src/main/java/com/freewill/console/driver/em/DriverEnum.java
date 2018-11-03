package com.freewill.console.driver.em;

/**
 * @Description 司机应聘相关枚举信息
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-29 14:28
 */
public class DriverEnum {

    public enum Status {

        TO_COMMUNICATE(1, "沟通中"),
        COMMISSION_HAS(0, "已提车");

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

    public enum Type {

        COMMON_USER(1, "普通推荐人"),
        DRIVER(0, "司机");

        private int key;
        private String value;

        Type(int key, String value) {
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
