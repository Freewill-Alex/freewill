package com.freewill.cms.user.form;


import com.freewill.cms.common.validation.SelectGroup;

import javax.validation.constraints.NotEmpty;

public class LoginForm {

    @NotEmpty(groups = {SelectGroup.class}, message = "用户名username不能为空")
    private String username;

    @NotEmpty(groups = {SelectGroup.class}, message = "用户密码pwd不能为空")
    private String pwd;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }


}
