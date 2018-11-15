package com.freewill.cms.driver.form;


import com.freewill.cms.common.validation.AddGroup;
import com.freewill.cms.common.validation.EditGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * @Description 注册司机表单
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-29 14:13
 */
public class DriverForm {

    private String uuid;

    private String smsToken;

    @Size(max = 20, message = "姓名超过最大长度(20)", groups = {AddGroup.class, EditGroup.class})
    private String name;

    @NotEmpty(message = "手机号码不能为空", groups = {AddGroup.class, EditGroup.class})
    @Size(max = 11, message = "手机号码超过最大长度(11)", groups = {AddGroup.class, EditGroup.class})
    private String phone;

    @Size(max = 6, message = "验证码超过最大长度(6)", groups = {AddGroup.class, EditGroup.class})
    private String smsCode;


    public String getSmsToken() {
        return smsToken;
    }

    public void setSmsToken(String smsToken) {
        this.smsToken = smsToken;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
