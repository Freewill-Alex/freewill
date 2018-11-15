package com.freewill.cms.mapper;

import com.freewill.cms.domain.User;
import com.freewill.cms.user.dto.UserInfo;
import com.freewill.cms.user.dto.UserListInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    UserInfo getByUserName(Map<String, Object> param);

    UserInfo getUserInfoById(@Param("id") Integer id, @Param("status") Integer status);

    List<UserListInfo> listBy(Map<String, Object> param);


    UserInfo getByOpenId(Map<String, Object> param);

    List<UserInfo> listByRole(Map<String, Object> param);
}