package com.freewill.console.system.controller;

import com.freewill.common.web.annotation.ResponseResult;
import com.freewill.console.common.validation.SelectGroup;
import com.freewill.console.system.entity.dto.RightInfo;
import com.freewill.console.system.entity.form.RoleRightListForm;
import com.freewill.console.system.service.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@ResponseResult
@RestController
@RequestMapping("/right")
public class RightController {

	@Autowired
	private RightService rightService;

	/**
	 * 获取指定角色的权限列表
	 * 兼容角色为空的情况
	 * <API角色权限>
	 * @param request
	 * @param response
	 */
	@RequestMapping("/list")
	public List<RightInfo> list(HttpServletRequest request, HttpServletResponse response,
								@Validated(SelectGroup.class)RoleRightListForm form) {
		List<RightInfo> rightInfoList = rightService.list(form);
	 return rightInfoList;
	}

}
