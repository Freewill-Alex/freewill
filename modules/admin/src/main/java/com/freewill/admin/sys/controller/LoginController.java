package com.freewill.admin.sys.controller;

import com.freewill.admin.sys.form.LoginForm;
import com.freewill.common.group.SelectGroup;
import com.freewill.common.web.annotation.ResponseResult;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户登录接口
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-11-24 12:30
 */
@RestController
@RequestMapping("/")
@Log4j2
@ResponseResult
public class LoginController {

    /**
     * 登录操作
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, @Validated(SelectGroup.class) LoginForm form) {
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        log.debug("Login exception ---->>{}",   exception);
        String msg = "api";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                log.debug("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                log.debug("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                log.debug("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                log.debug("else -- >" + exception);
            }
        }
        return msg;
    }


    /**
     * 注销操作
     */
    @RequestMapping("/logout")
    public String logout() {
        //注销
        SecurityUtils.getSubject().logout();
        return "注销";
    }

}
