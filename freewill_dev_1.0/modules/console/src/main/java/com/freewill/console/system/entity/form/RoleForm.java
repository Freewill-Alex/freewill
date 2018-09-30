/**
 * 
 */
package com.freewill.console.system.entity.form;


import com.freewill.console.common.validation.AddGroup;
import com.freewill.console.common.validation.EditGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author fyden
 *
 */
public class RoleForm {

	@NotNull(groups = { EditGroup.class }, message = "角色ID不能为空")
	private Integer id;
	
	@NotNull(groups = { AddGroup.class }, message = "类型不能为空")
	private Integer type;

	@NotEmpty(groups = { AddGroup.class, EditGroup.class }, message = "角色名不能为空")
	private String roleName;

	private String roleCode;

	private String remark;

	@NotNull(groups = { AddGroup.class, EditGroup.class }, message = "权限集不能为空")
	private String rightIds;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName
	 *            the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode
	 *            the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the rightIds
	 */
	public String getRightIds() {
		return rightIds;
	}

	/**
	 * @param rightIds
	 *            the rightIds to set
	 */
	public void setRightIds(String rightIds) {
		this.rightIds = rightIds;
	}

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
}
