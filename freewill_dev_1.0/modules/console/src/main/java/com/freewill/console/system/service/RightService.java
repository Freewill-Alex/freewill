package com.freewill.console.system.service;

import com.freewill.console.mapper.RightMapper;
import com.freewill.console.mapper.RoleRightMapper;
import com.freewill.console.mapper.SystemUserMapper;
import com.freewill.console.system.entity.Right;
import com.freewill.console.system.entity.SystemUser;
import com.freewill.console.system.entity.dto.RightInfo;
import com.freewill.console.system.entity.em.RightEnum;
import com.freewill.console.system.entity.form.RoleRightListForm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RightService {

	@Resource
	private RoleRightMapper roleRightMapper;

	@Resource
	private SystemUserMapper systemUserMapper;
	
	@Resource
	private RightMapper rightMapper;
	
	/**
	 * 获取用户权限列表
	 * <系统用户权限>
	 * @param userId
	 * @return
	 */
	public List<Right> listBySystemUser(Integer userId) {
		SystemUser user = systemUserMapper.selectByPrimaryKey(userId);
		return roleRightMapper.listByRole(RightEnum.TypeEnum.CONSOLE.getKey(),user.getRoleId());
	}
	
	/**
	 * 获取角色的权 限树
	 * 
	 * @return
	 */
	public List<RightInfo> list(RoleRightListForm form) {
		List<Right> allRights = rightMapper.listAll(form.getType(), true);
		List<Right> userRights = roleRightMapper.listByRole(form.getType(), form.getRoleId());
		Set<Integer> rightIdSet = new HashSet<>();
		
		for(Right ur : userRights){
			rightIdSet.add(ur.getId());
		}

		List<RightInfo> infoList = new ArrayList<>();

		for (Right right : allRights) {
			// 首层权限
			if (right.getParentId() == null) {
				RightInfo firstLevel = buildRightInfo(right, rightIdSet);
				firstLevel.setChildren(buildRightTree(firstLevel, allRights, rightIdSet));
				infoList.add(firstLevel);
			}
		}

		return infoList;
	}

	/**
	 * 创建权限树
	 * 
	 * @param parent
	 *            父节点
	 * @param allRights
	 *            所有的权限集合
	 * @param rightIdSet
	 *            当前角色具有的权限ID集合，用于标记选中状态
	 * @return
	 */
	private List<RightInfo> buildRightTree(RightInfo parent, List<Right> allRights, Set<Integer> rightIdSet) {
		List<RightInfo> infoList = new ArrayList<>();

		for (Right right : allRights) {
			if (parent.getId().equals(right.getParentId())) {
				RightInfo rightInfo = buildRightInfo(right, rightIdSet);
				rightInfo.setChildren(buildRightTree(rightInfo, allRights, rightIdSet));
				infoList.add(rightInfo);
			}
		}

		return infoList;
	}

	/**
	 * 创建一个RightInfo对象
	 * 
	 * @param right
	 *            权限对象
	 * @param rightIdSet
	 *            当前角色具有的权限ID集合，用于标记选中状态
	 * @return
	 */
	private RightInfo buildRightInfo(Right right, Set<Integer> rightIdSet) {
		RightInfo rightInfo = new RightInfo();
		rightInfo.setId(right.getId());
		rightInfo.setRightCode(right.getRightCode());
		rightInfo.setRightName(right.getRightName());
		if (rightIdSet.contains(right.getId())) {
			rightInfo.setIsSelected(RightEnum.SelectEnum.SELECT1.getKey());
		} else {
			rightInfo.setIsSelected(RightEnum.SelectEnum.SELECT0.getKey());
		}

		return rightInfo;
	}

}
