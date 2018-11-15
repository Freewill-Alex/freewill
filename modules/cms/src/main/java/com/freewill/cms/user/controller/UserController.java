//package com.freewill.console.user.controller;
//
//import com.freewill.cms.common.validation.AddGroup;
//import com.freewill.cms.common.validation.EditGroup;
//import com.freewill.cms.common.validation.SelectGroup;
//import com.freewill.cms.user.dto.UserInfo;
//import com.freewill.cms.user.dto.UserListInfo;
//import com.freewill.cms.user.form.SalfEditForm;
//import com.freewill.cms.user.form.UserEditForm;
//import com.freewill.cms.user.form.UserListForm;
//import com.freewill.cms.user.service.LoginService;
//import com.freewill.cms.user.service.UserService;
//import com.github.pagehelper.PageInfo;
//import org.apache.struts.util.ResponseUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//@RequestMapping("/user")
//public class UserController {
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private LoginService loginService;
//
//
//    @RequestMapping("/list")
//    public void list(HttpServletRequest request, HttpServletResponse response, @Validated(SelectGroup.class) UserListForm form) {
//        PageInfo<UserListInfo> page = userService.list(form);
//
//        RespObj rs = RespObj.successObj();
//        rs.setData(page);
//        ResponseUtils.renderJson(response, rs);
//    }
//
//
//    @RequestMapping("/detail")
//    @RequiresPermissions("user:user:detail")
//    public void detail(HttpServletRequest request, HttpServletResponse response, @Validated(SelectGroup.class) IdForm form) {
//        UserInfo user = userService.getUserInfoById(form.getId());
//
//
//        RespObj rs = RespObj.successObj();
//        rs.setData(user);
//        ResponseUtils.renderJson(response, rs);
//    }
//
//    @RequestMapping("/add")
//    @RequiresPermissions("user:user:add")
//    public void add(HttpServletRequest request, HttpServletResponse response, @Validated(AddGroup.class) UserEditForm form) {
//        userService.add(form);
//        ResponseUtils.renderJson(response, RespObj.successObj());
//    }
//
//    @RequestMapping("/modify")
//    @RequiresPermissions("user:user:edit")
//    public void modify(HttpServletRequest request, HttpServletResponse response, @Validated(EditGroup.class) UserEditForm form) {
//        userService.modify(form);
//        ResponseUtils.renderJson(response, RespObj.successObj());
//    }
//
//    @RequestMapping("/edit")
//    public void edit(HttpServletRequest request, HttpServletResponse response, @Validated(EditGroup.class) SalfEditForm form) {
//        userService.edit(form);
//        ResponseUtils.renderJson(response, RespObj.successObj());
//    }
//
//    @RequestMapping("/changePwd")
//    public void changePwd(HttpServletRequest request, HttpServletResponse response, String oldPwd, String newPwd) {
//
//        if (StringUtils.isEmpty(oldPwd) || StringUtils.isEmpty(newPwd)) {
//            ResponseUtils.renderJson(response, RespObj.failedObj("必填参数为空"));
//            return;
//        }
//        loginService.changePwd(oldPwd, newPwd);
//        ResponseUtils.renderJson(response, RespObj.successObj());
//    }
//
//
//    @RequestMapping("/delete")
//    @RequiresPermissions("user:user:delete")
//    public void delete(HttpServletRequest request, HttpServletResponse response, @Validated(EditGroup.class) IdForm form) {
//        userService.delete(form);
//        ResponseUtils.renderJson(response, RespObj.successObj());
//    }
//
//
////    @RequestMapping("/buildBusinessCard")
////    public void buildBusinessCard(HttpServletRequest request, HttpServletResponse response, @Validated(EditGroup.class) MerchantsCodeForm form) {
////        String url = userService.build(form);
////        RespObj rs = RespObj.successObj();
////        rs.setData(url);
////        ResponseUtils.renderJson(response, rs);
////    }
//
//
//}
