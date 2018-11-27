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
    @NotEmpty(groups = {SelectGroup.class}, message = "账号[username]不能为空")
    private String username;
    @NotEmpty(groups = {SelectGroup.class}, message = "密码[password]不能为空")
    private String password;
    //    @NotEmpty(groups = {SelectGroup.class}, message = "验证码[captcha]不能为空")
    private String captcha;
    /**
     * 是否记住我
     */
    private Boolean remenberMe;

}
