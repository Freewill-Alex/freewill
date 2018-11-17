package com.freewill.cms.user.form;


import com.freewill.cms.common.validgroup.AddGroup;
import com.freewill.cms.common.validgroup.EditGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 添加修改用户表单
 */
public class UserEditForm  {

    @NotNull(message = "用户ID不能为空", groups = {EditGroup.class})
    private Integer id;

    @NotEmpty(message = "姓名不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(max = 20, message = "姓名超过最大长度(20)", groups = {AddGroup.class, EditGroup.class})
    private String name;

    @NotEmpty(message = "手机号码不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(max = 20, message = "手机号码超过最大长度(20)", groups = {AddGroup.class, EditGroup.class})
    private String phone;

    @NotEmpty(message = "角色不能为空", groups = {AddGroup.class, EditGroup.class})
    private String roleIds;

    @Size(max = 200, message = "备注超过最大长度(200)", groups = {AddGroup.class, EditGroup.class})
    private String job;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }


}
