package com.freewill.security.user.form;


import com.freewill.common.group.SelectGroup;

import javax.validation.constraints.Size;

public class UserListForm  {

    @Size(groups = {SelectGroup.class}, max = 50, message = "关键字超过最大长度(50)")
    private String keyword;

    private Integer status;

    private Integer roleId;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
