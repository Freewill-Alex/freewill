package com.freewill.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class StringUtils {
	
	public static boolean isEmpty(Object obj){
        if (null == obj) {
			return true;
        }
        return "".equals(obj.toString());
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T format(String str, T defaultValue){
		if(null==str || "".equals(str)){
			return defaultValue;
		}
		
		if(defaultValue instanceof String){
			return (T) str;
		}else if(defaultValue instanceof Integer){
			return (T) Integer.valueOf(str);
		}else if(defaultValue instanceof Float){
			return (T) Float.valueOf(str);
		}else if(defaultValue instanceof Long){
			return (T) Long.valueOf(str);
		}else if(defaultValue instanceof Double){
			return (T) Double.valueOf(str);
		}else if(defaultValue instanceof Boolean){
			return (T) Boolean.valueOf(str);
		}else if(defaultValue instanceof byte[]){
			return (T) str.getBytes();
		}else{
			return (T) str;
		}
	}
	
	/**
	 * 获取截断省略字符串
	 * 
	 * @param text
	 * @param maxTextLen
	 * @return
	 */
	public static String getTextBrief(String text, int maxTextLen) {
		String brief = "";
		if (text.length() > maxTextLen) {
			brief = text.substring(0, maxTextLen) + "...";
		} else {
			brief = text;
		}
		return brief;
	}
	
	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID(){
		String uuid = UUID.randomUUID().toString();
		return uuid.replace("-", "");
	}
	
	/**
	 * 生成No
	 * 
	 * 格式：前缀 + yyyyMMddHHmm + 四位随机数字
	 * 
	 * @return
	 */
	public static String generateNo(String prefix) {
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Random random = new Random();
		return prefix + format.format(calender.getTime()) + random.nextInt(1000);
	}
	
	/**
	 * 随机生成指定长度的数字
	 * @param length
	 * @return
	 */
	public static String randomNum(int length){
		
		if(length<=1)return null;
		
		Integer lengthNumber = (int) Math.pow(10, length-1);
		
		Integer x = (int)((Math.random()*9+1) * lengthNumber);
		
		return String.valueOf(x);
	}
}
