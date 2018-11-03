package com.freewill.console.system.entity.dto;

import java.util.List;

public class RightInfo {

	/**
	 * 权限id
	 */
	private Integer id;

	/**
	 * 权限名称
	 */
	private String rightName;

	/**
	 * 权限编码
	 */
	private String rightCode;

	/**
	 * 是否选中(1选中 0 否)
	 */
	private Integer isSelected;

	/**
	 * 子权限集
	 */
	private List<RightInfo> children;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	public Integer getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(Integer isSelected) {
		this.isSelected = isSelected;
	}

	public List<RightInfo> getChildren() {
		return children;
	}

	public void setChildren(List<RightInfo> children) {
		this.children = children;
	}
}
