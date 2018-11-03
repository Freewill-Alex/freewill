package com.freewill.console.mapper;

import com.freewill.console.system.entity.SystemUser;
import com.freewill.console.system.entity.dto.SystemUserInfo;
import com.freewill.console.system.entity.dto.SystemUserListInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SystemUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SystemUser record);

    int insertSelective(SystemUser record);

    SystemUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SystemUser record);

    int updateByPrimaryKey(SystemUser record);
    
    
    SystemUser getByUserName(@Param("userName") String userName, @Param("status") Integer status);

    SystemUserInfo getUserInfoById(@Param("id") Integer id, @Param("status") Integer status);
    
    SystemUserListInfo listBy(Map<String, Object> param);
    
    List<SystemUserListInfo> list(Map<String, Object> param);
}