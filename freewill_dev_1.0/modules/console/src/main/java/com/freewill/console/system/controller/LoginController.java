package com.freewill.console.system.controller;//package com.freewill.console.common.controller;
//
//import com.alibaba.druid.util.StringUtils;
//import com.ejauto.common.RespObj;
//import com.ejauto.common.security.EJSecurityUtils;
//import com.freewill.console.system.entity.Right;
//import com.freewill.console.system.entity.dto.SystemUserInfo;
//import com.ejauto.utils.servlet.ResponseUtils;
//import com.freewill.console.service.LoginService;
//import com.freewill.console.service.RightService;
//import com.freewill.console.service.SystemUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// * 登录管理Controller
// *
// * @author fyden
// *
// */
//@Controller
//public class LoginController {
//
//	@Autowired
//	private LoginService loginService;
//
//	@Autowired
//	private SystemUserService systemUserService;
//
//	@Autowired
//	private RightService rightService;
//
//	/**
//	 * 登录操作
//	 *
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/login")
//	public void login(HttpServletRequest request, HttpServletResponse response) {
//		String username = request.getParameter("username");
//		String pwd = request.getParameter("pwd");
//
//		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pwd)) {
//			ResponseUtils.renderJson(response, RespObj.failedObj("账号或密码为空"));
//			return;
//		}
//		String token = loginService.doLogin(username, pwd);
//		RespObj obj = RespObj.successObj();
//		obj.setData(token);
//		ResponseUtils.renderJson(response, obj);
//	}
//
//
//	/**
//	 * 获取登陆用户信息
//	 *
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/userInfo")
//	public void getLoginUser(HttpServletRequest request, HttpServletResponse response) {
//		Integer userId = EJSecurityUtils.getUserId();
//
//		SystemUserInfo user = systemUserService.getUserInfoById(userId);
//		// 查询用户权限
//		List<Right> rightList = rightService.listBySystemUser(userId);
//		user.setRightList(rightList);
//
//		RespObj rsp = RespObj.successObj();
//		rsp.setData(user);
//		ResponseUtils.renderJson(response, rsp);
//	}
//
//	/**
//	 * 登出操作
//	 *
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/logout")
//	public void logout(HttpServletRequest request, HttpServletResponse response) {
//		Integer userId = EJSecurityUtils.getUserId();
//		loginService.logout(userId);
//		ResponseUtils.renderJson(response, RespObj.successObj());
//	}
//}
