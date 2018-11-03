package com.freewill.console.system.controller;//package com.freewill.console.common.controller;
//
//import com.freewill.console.common.validation.AddGroup;
//import com.freewill.console.common.validation.SelectGroup;
//import com.freewill.console.system.entity.Role;
//import com.freewill.console.system.entity.wrapper.RoleForm;
//import com.freewill.console.system.entity.wrapper.RoleListForm;
//import com.freewill.console.service.RightService;
//import com.freewill.console.service.RoleService;
//import com.github.pagehelper.PageInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * @author lyz
// *
// * 2018年6月23日
// */
//@Controller
//@RequestMapping("/role")
//public class RoleController {
//
//	@Autowired
//	private RoleService roleService;
//	@Autowired
//	private RightService rightService;
//
//	/**
//	 * 获取角色列表
//	 * @param request
//	 * @param response
//	 */
//	@RequestMapping("/list")
//	public void list(HttpServletRequest request, HttpServletResponse response,
//                     @Validated(SelectGroup.class)RoleListForm wrapper) {
//		PageInfo<Role> list = roleService.list(wrapper);
//
//		RespObj obj = RespObj.successObj();
//		obj.setData(list);
//		ResponseUtils.renderJsonp(request, response, obj);
//	}
//
//	/**
//	 * 新增角色
//	 * @param request
//	 * @param response
//	 * @param wrapper
//	 */
//	@RequestMapping("/add")
//	public void add(HttpServletRequest request, HttpServletResponse response,
//                    @Validated(AddGroup.class)RoleForm wrapper) {
//		roleService.add(wrapper);
//
//		ResponseUtils.renderJson(response, RespObj.successObj());
//	}
//
//	/**
//	 * 修改角色跟对应权限
//	 * @param request
//	 * @param response
//	 * @param wrapper
//	 */
//	@RequestMapping("/modify")
//	public void modify(HttpServletRequest request, HttpServletResponse response,
//                       @Validated(EditGroup.class)RoleForm wrapper) {
//		roleService.modify(wrapper);
//
//		ResponseUtils.renderJson(response, RespObj.successObj());
//	}
//
//	/**
//	 * 根据id获取role详情
//	 * @param request
//	 * @param reponse
//	 * @param roleId
//	 */
//	@RequestMapping("/detail")
//	public void detail(HttpServletRequest request, HttpServletResponse response, String roleId) {
//		if (roleId == null) {
//			ResponseUtils.renderJsonp(request, response, RespObj.failedObj("角色ID不能为空"));
//			return;
//		}
//		Role role = roleService.getByRoleId(Integer.parseInt(roleId));
//
//		RespObj obj = RespObj.successObj();
//		obj.setData(role);
//		ResponseUtils.renderJsonp(request, response, obj);
//	}
//}
//
