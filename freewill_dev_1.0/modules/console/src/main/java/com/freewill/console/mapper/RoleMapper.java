package com.freewill.console.mapper;

import com.freewill.console.system.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);
    
    /**
     * 插入并获取id
     * @param record
     * @return
     */
    int insertAndGetId(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    /**
     * 根据条件查找角色列表
     * @param param
     * @return
     */
    List<Role> list(Map<String, Object> param);
    
    Role getByCode(String code);
}