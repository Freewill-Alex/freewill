package com.freewill.console.mapper;

import com.freewill.console.system.entity.Right;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RightMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Right record);

    int insertSelective(Right record);

    Right selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);
    
    /**
	 * 查询所有权限
	 * @param includeGroupRight 是否包含特殊权限
	 * @return
	 */
	List<Right> listAll(@Param("type") Integer type, @Param("includeGroupRight") Boolean includeGroupRight);
}