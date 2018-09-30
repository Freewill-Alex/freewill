package com.freewill.console.system.entity.em;

/**
 * 
 * @author JimBo-java
 *
 */
public class RightEnum {
	
	
	public enum SelectEnum {
	
		SELECT1(1, "选中"), SELECT0(0, "未选中");
	
		private int key;
		private String value;
	
		private SelectEnum(int key, String value) {
			this.key = key;
			this.value = value;
		}
	
		public int getKey() {
			return key;
		}
	
		public void setKey(int key) {
			this.key = key;
		}
	
		public String getValue() {
			return value;
		}
	
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public enum TypeEnum {
		
		//权限类型  1后台权限 2小程序端权限
		CONSOLE(1, "后台权限"), API(2, "小程序端权限");
	
		private int key;
		private String value;
	
		private TypeEnum(int key, String value) {
			this.key = key;
			this.value = value;
		}
	
		public int getKey() {
			return key;
		}
	
		public void setKey(int key) {
			this.key = key;
		}
	
		public String getValue() {
			return value;
		}
	
		public void setValue(String value) {
			this.value = value;
		}
	}
}