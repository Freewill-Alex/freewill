package com.freewill.console.system.controller;

import com.freewill.console.common.validation.EditGroup;
import com.freewill.console.common.validation.SelectGroup;
import com.freewill.console.system.entity.dto.SystemUserInfo;
import com.freewill.console.system.entity.dto.SystemUserListInfo;
import com.freewill.console.system.entity.form.SystemUserForm;
import com.freewill.console.system.entity.form.SystemUserListForm;
import com.freewill.console.system.service.SystemUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author lyz
 *
 * 2018年7月2日
 */
@Controller
@RequestMapping("/systemUser")
public class SystemUserController {
	
	@Autowired
	private SystemUserService systemUserService;
	
	/**
	 * 系统用户列表
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping("/list")
	public PageInfo<SystemUserListInfo> list(HttpServletRequest request, HttpServletResponse response,
											 @Validated(SelectGroup.class)SystemUserListForm form) {
		  systemUserService.pageList(form);
		
	return systemUserService.pageList(form);
	}
	
	/**
	 * 新增系统用户
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping("/add")
	public String add(HttpServletRequest request, HttpServletResponse response,
                    @Validated(SelectGroup.class)SystemUserForm form) {

		
		   systemUserService.add(form);
		   return "新增系统用户";
	}
	
	/**
	 * 获取系统用户详情
	 * @param request
	 * @param response
	 */
	@RequestMapping("/detail")
	public SystemUserInfo detail(HttpServletRequest request, HttpServletResponse response,
								 String id) {



		return systemUserService.getUserInfoById(Integer.parseInt(id));
	}
	
	/**
	 * 修改系统用户
	 * @param request
	 * @param response
	 * @param form
	 */
	@RequestMapping("/modify")
	public String modify(HttpServletRequest request, HttpServletResponse response,
                       @Validated(EditGroup.class)SystemUserForm form) {
		 systemUserService.modify(form);
		return "修改系统用户";
	}
	
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/resetPassword")
	public String resetPassword(HttpServletRequest request, HttpServletResponse response,
                              @Validated(EditGroup.class)SystemUserForm form) {
//		wrapper.setPassword(EJSecurityUtils.encryptPwd(wrapper.getPassword()));
		  systemUserService.modify(form);
//		ResponseUtils.renderJson(response, RespObj.successObj());
		return "重置密码";
	}
}

