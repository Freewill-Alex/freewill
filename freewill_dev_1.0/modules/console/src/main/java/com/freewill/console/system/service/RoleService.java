package com.freewill.console.system.service;


import com.freewill.common.util.MapUtils;
import com.freewill.console.mapper.RoleMapper;
import com.freewill.console.mapper.RoleRightMapper;
import com.freewill.console.mapper.SystemUserMapper;
import com.freewill.console.system.entity.Role;
import com.freewill.console.system.entity.RoleRight;
import com.freewill.console.system.entity.SystemUser;
import com.freewill.console.system.entity.form.RoleForm;
import com.freewill.console.system.entity.form.RoleListForm;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author JimBo-java
 *
 */
@Slf4j
@Service
public class RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private SystemUserMapper systemUserMapper;

	@Resource
	private RoleRightMapper roleRightMapper;
	

	/**
	 * 查询用户所属角色
	 * @param userId
	 * @return
	 */
	public Role getBySysUser(Integer userId) {
		SystemUser user = systemUserMapper.selectByPrimaryKey(userId);
		return roleMapper.selectByPrimaryKey(user.getRoleId());
	}

	/**
	 * 查询角色列表
	 * 
	 * @param form
	 * @return
	 */

	public PageInfo<Role> list(RoleListForm form) {
		com.github.pagehelper.Page<Object> page = PageHelper.startPage((Integer) form.getPageNo(), (Integer) form.getPageSize());
		
		roleMapper.list(MapUtils.transBean2Map(form));
		return new PageInfo (page);
	}
	
	/**
	 * 新增角色并添加权限
	 * @param form
	 */
	public void add(RoleForm form) {
		Role role = new Role();
		BeanUtils.copyProperties(form, role);
		
		roleMapper.insertAndGetId(role);
		int roleId = role.getId();
		
		String[] rightIds = form.getRightIds().split(",");
		List<RoleRight> roleRightList = new ArrayList<RoleRight> ();
		for(String rightId : rightIds) {
			RoleRight roleRight = new RoleRight();
			roleRight.setRoleId(roleId);
			roleRight.setRightId(Integer.parseInt(rightId));
			
			roleRightList.add(roleRight);
		}
		if(roleRightList!=null && roleRightList.size()>0) {
			roleRightMapper.insertBatch(roleRightList);
		}
	}
	
	/**
	 * 根据roleId查询角色详情
	 * @param roleId
	 * @return
	 */
	public Role getByRoleId(Integer roleId) {
		return roleMapper.selectByPrimaryKey(roleId);
	}
	
	/**
	 * 根据类型获取角色
	 * @param type
	 * @return
	 */
	public List<Role> getRoleList(Integer type) {
		Map<String, Object> paramMap = new HashMap<String, Object> ();
		if(type!=null && type!=0) {
			paramMap.put("type", type);
		}
		List<Role> roleList = roleMapper.list(paramMap);
		return roleList;
	}
	
	/**
	 * 修改角色
	 * @param form
	 */
	public void modify(RoleForm form) {
		Role role = new Role();
		BeanUtils.copyProperties(form, role);
		
		roleMapper.updateByPrimaryKeySelective(role);
		

		String[] rightIds = form.getRightIds().split(",");
		if(rightIds.length < 1) {
			log.info("勾选的权限为空");
		} else {
			//删除旧的权限勾选
			roleRightMapper.deleteByRole(role.getId());
			
			List<RoleRight> roleRightList = new ArrayList<RoleRight> ();
			for(String rightId : rightIds) {
				RoleRight roleRight = new RoleRight();
				roleRight.setRoleId(role.getId());
				roleRight.setRightId(Integer.parseInt(rightId));
				
				roleRightList.add(roleRight);
			}
			if(roleRightList!=null && roleRightList.size()>0) {
				roleRightMapper.insertBatch(roleRightList);
				log.info("修改权限成功！");
			}
		}
	
	}
}
