package com.freewill.console.mapper;


import com.freewill.console.domain.DriverVisitRecord;

public interface DriverVisitRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverVisitRecord record);

    int insertSelective(DriverVisitRecord record);

    DriverVisitRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DriverVisitRecord record);

    int updateByPrimaryKey(DriverVisitRecord record);
}