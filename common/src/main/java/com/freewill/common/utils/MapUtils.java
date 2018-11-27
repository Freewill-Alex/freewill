package com.freewill.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author sanma
 */
public class MapUtils {
    public MapUtils() {
    }

    public static String transMap2Str(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
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
            HashMap map = new HashMap(16);

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                int var5 = propertyDescriptors.length;

                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    if (!"class".equals(key)) {
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

    public static HashMap trans2Map(Object obj) {
        if (obj == null) {
            return null;
        } else {
            HashMap map = new HashMap(16);

            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                int var5 = propertyDescriptors.length;

                for (PropertyDescriptor property : propertyDescriptors) {
                    String key = property.getName();
                    if (!"class".equals(key)) {
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

}
