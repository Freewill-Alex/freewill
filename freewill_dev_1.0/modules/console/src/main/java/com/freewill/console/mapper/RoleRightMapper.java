package com.freewill.console.mapper;

import com.freewill.console.system.entity.Right;
import com.freewill.console.system.entity.RoleRight;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleRightMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(RoleRight record);

	int insertSelective(RoleRight record);

	RoleRight selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(RoleRight record);

	int updateByPrimaryKey(RoleRight record);

	void insertBatch(@Param("roleRights") List<RoleRight> roleRights);

	void deleteByRole(@Param("roleId") Integer roleId);

	List<Right> listByRole(@Param("type") Integer type, @Param("roleId") Integer roleId);
}