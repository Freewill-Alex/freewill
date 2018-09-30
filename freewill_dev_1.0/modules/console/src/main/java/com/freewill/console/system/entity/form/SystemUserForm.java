package com.freewill.console.system.entity.form;

import com.freewill.console.common.validation.AddGroup;
import com.freewill.console.common.validation.EditGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author lyz
 *
 * 2018年7月2日
 */
public class SystemUserForm {

	@NotNull(groups = { EditGroup.class}, message = "id不能为空")
	private Integer id;
	
	@NotEmpty(groups = { AddGroup.class, EditGroup.class}, message = "userName不能为空")
    private String userName;
	
	@NotEmpty(groups = { AddGroup.class}, message = "password不能为空")
	private String password;
	
	@NotEmpty(groups = { AddGroup.class, EditGroup.class}, message = "name不能为空")
    private String name;
	
	private Integer sex;
	
	private String phone;
	
	@NotNull(groups = { AddGroup.class, EditGroup.class}, message = "roleId不能为空")
	private Integer roleId;
	
	@NotNull(groups = { AddGroup.class, EditGroup.class}, message = "status不能为空")
	private Integer status;
	
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}

