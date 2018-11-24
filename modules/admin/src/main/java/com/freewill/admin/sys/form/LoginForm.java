package com.freewill.admin.sys.form;

import com.freewill.common.group.SelectGroup;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Description 登录表单
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-11-24 12:39
 */
@Data
public class LoginForm {
    @NotEmpty(groups = { SelectGroup.class}, message = "用户名username不能为空")
    private String username;//帐号
    @NotEmpty(groups = { SelectGroup.class}, message = "用户密码pwd不能为空")
    private String password; //密码;

    private Boolean remenberMe;//是否记住我

}
