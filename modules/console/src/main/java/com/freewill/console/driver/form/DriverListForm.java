package com.freewill.console.driver.form;


import com.freewill.console.common.page.BasePageForm;

/**
 * @Description 应聘司机表单
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-29 19:25
 */
public class DriverListForm extends BasePageForm {
    private Integer id;

    private String name;

    private String phone;

    private Integer type;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
