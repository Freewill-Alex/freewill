//package com.freewill.console.user.controller;
//
//import com.freewill.cms.common.security.EJSecurityUtils;
//import com.freewill.cms.common.validgroup.SelectGroup;
//import com.freewill.cms.user.dto.UserInfo;
//import com.freewill.cms.user.form.LoginForm;
//import com.freewill.cms.user.service.LoginService;
//import com.freewill.cms.user.service.UserService;
//import org.apache.struts.util.RequestUtils;
//import org.apache.struts.util.ResponseUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validgroup.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 登录管理Controller
// *
// * @author fyden
// */
//@Controller
//public class LoginController {
//
//    @Autowired
//    private LoginService loginService;
//
//    @Autowired
//    private UserService userService;
//
//
//    /**
//     * 登录操作
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/login")
//    public void login(HttpServletRequest request, HttpServletResponse response, @Validated(SelectGroup.class) LoginForm form) {
//
//        String ipAddress = RequestUtils.getIpAddr(request);
//        String browser = RequestUtils.getBrowser(request);
//        String token = loginService.doLogin(form, ipAddress, browser);
//
//        RespObj obj = RespObj.successObj();
//        obj.setData(token);
//        ResponseUtils.renderJson(response, obj);
//    }
//
//    /**
//     * 获取登陆用户信息
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/userInfo")
//    public void getLoginUser(HttpServletRequest request, HttpServletResponse response) {
////        Integer userId = EJSecurityUtils.getUserId();
////
////        UserInfo user = userService.getUserInfoById(userId);
////
////
////        RespObj rsp = RespObj.successObj();
////        rsp.setData(user);
////        ResponseUtils.renderJson(response, rsp);
//    }
//
//    /**
//     * 登出操作
//     *
//     * @param request
//     * @param response
//     */
//    @RequestMapping("/logout")
//    public void logout(HttpServletRequest request, HttpServletResponse response) {
//        Integer userId = EJSecurityUtils.getUserId();
//        loginService.logout(userId);
//        ResponseUtils.renderJson(response, RespObj.successObj());
//    }
//
//
//}
