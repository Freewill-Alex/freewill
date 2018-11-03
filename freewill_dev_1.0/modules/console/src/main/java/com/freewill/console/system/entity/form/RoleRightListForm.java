package com.freewill.console.system.entity.form;

import com.freewill.console.common.validation.SelectGroup;

import javax.validation.constraints.NotNull;

public class RoleRightListForm {

	@NotNull(groups = { SelectGroup.class }, message = "type不能为空")
	private Integer type;

	@NotNull(groups = { SelectGroup.class }, message = "roleId不能为空")
	private Integer roleId;

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the roleId
	 */
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId the roleId to set
	 */
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
}
