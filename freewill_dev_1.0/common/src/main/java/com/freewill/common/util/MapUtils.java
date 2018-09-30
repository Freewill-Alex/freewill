//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.freewill.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapUtils {
    public MapUtils() {
    }

    public static String transMap2Str(Map<?, ?> map) {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Entry<?, ?> entry = (Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" : entry.getValue().toString()).append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }

    public static Map<String, Object> transBean2Map(Object obj) {
        if (obj == null) {
            return null;
        } else {
            HashMap map = new HashMap();

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if (!key.equals("class")) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(obj);
                        if (value != null) {
                            map.put(key, value);
                        }
                    }
                }
            } catch (Exception var11) {
                System.out.println("transBean2Map Error " + var11);
            }

            return map;
        }
    }

    public static Map<String, Object> trans2Map(Object obj) {
        if (obj == null) {
            return null;
        } else {
            HashMap map = new HashMap();

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                PropertyDescriptor[] var4 = propertyDescriptors;
                int var5 = propertyDescriptors.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    PropertyDescriptor property = var4[var6];
                    String key = property.getName();
                    if (!key.equals("class")) {
                        Method getter = property.getReadMethod();
                        Object value = getter.invoke(obj);
                        if (value != null) {
                            map.put(key, value);
                        } else {
                            map.put(key, "-");
                        }
                    }
                }
            } catch (Exception var11) {
                System.out.println("transBean2Map Error " + var11);
            }

            return map;
        }
    }

    public static void transMap2Bean(Map<String, Object> map, Object obj) {
        if (map != null && obj != null) {
            try {
//                BeanUtils.populate(obj, map);
            } catch (Exception var3) {
                System.out.println("transMap2Bean2 Error " + var3);
            }

        }
    }
}
