package com.freewill.console.system.service;

import com.freewill.common.util.MapUtils;
import com.freewill.console.mapper.SystemUserMapper;
import com.freewill.console.system.entity.SystemUser;
import com.freewill.console.system.entity.dto.SystemUserInfo;
import com.freewill.console.system.entity.dto.SystemUserListInfo;
import com.freewill.console.system.entity.em.SystemUserEnum;
import com.freewill.console.system.entity.form.SystemUserForm;
import com.freewill.console.system.entity.form.SystemUserListForm;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

@Service
public class SystemUserService {

	@Resource
	private SystemUserMapper systemUserMapper;
	
	public SystemUser getByUserName(String userName) {
		return systemUserMapper.getByUserName(userName, SystemUserEnum.Status.E1_ON_JOB.getKey());
	}

	public SystemUserInfo getUserInfoById(Integer id) {
		return systemUserMapper.getUserInfoById(id,SystemUserEnum.Status.E1_ON_JOB.getKey());
	}
	
	/**
	 * 分页获取系统用户列表接口
	 * @param form
	 * @return
	 */
	public PageInfo<SystemUserListInfo> pageList(SystemUserListForm form){
		Page<SystemUserListInfo> page = PageHelper.startPage(form.getPageNo(), form.getPageSize());
		
		Map<String, Object> map = MapUtils.transBean2Map(form);
		
		systemUserMapper.list(map);
		return new PageInfo<>(page);
	}
	
	/**
	 * 新增系统用户接口
	 * @param form
	 */
	public void add(SystemUserForm form) {
		SystemUser su = new SystemUser();
		
		su.setUserName(form.getUserName());
//		su.setPassword(EJSecurityUtils.encryptPwd(wrapper.getPassword()));
		su.setName(form.getName());
		su.setPhone(form.getPhone());
		su.setRoleId(form.getRoleId());
		su.setSex(form.getSex());
		su.setStatus(form.getStatus());
		su.setRemark(form.getRemark());
		
		Date date = new Date();
		su.setCreateTime(date);
		su.setUpdateTime(date);
		
		systemUserMapper.insert(su);
	}
	
	/**
	 * 修改系统用户接口
	 * @param form
	 */
	public void modify(SystemUserForm form) {
		SystemUser su = new SystemUser();
		
		BeanUtils.copyProperties(form, su);
		
//		if(StringUtils.isNotBlank(wrapper.getPassword())) {   //修改密码
//			su.setPassword(EJSecurityUtils.encryptPwd(wrapper.getPassword()));
//		}
		
		Date date = new Date();
		su.setUpdateTime(date);
		
		systemUserMapper.updateByPrimaryKeySelective(su);	
	}
}
