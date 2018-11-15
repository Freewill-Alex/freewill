package com.freewill.cms.mapper;

import com.freewill.cms.common.page.BasePage;
import com.freewill.cms.domain.DriverApplication;
import com.freewill.cms.driver.form.DriverListForm;
import org.apache.ibatis.annotations.Param;

/**
 * @author sanma
 */
public interface DriverApplicationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DriverApplication record);

    int insertSelective(DriverApplication record);

    DriverApplication selectByPrimaryKey(Integer id);

    DriverApplication selectByPhone(String phone);

    DriverApplication selectByUUID(String uuid);

    int changeDriverByPhone(@Param("name") String name, @Param("pid") Integer pid, @Param("phone") String phone);

    int updateByPrimaryKeySelective(DriverApplication record);

    int updateByPrimaryKey(DriverApplication record);

    BasePage<DriverApplication> selectAllDriver( BasePage<DriverApplication> base, @Param("form")DriverListForm form);

    BasePage<DriverApplication> selectAll( BasePage<DriverApplication> base, @Param("form")DriverListForm form);

    BasePage<DriverApplication> selectChildByPid( BasePage<DriverApplication> base, @Param("id")Integer id);

    int updateStatusByPrimaryKey(@Param("ids") String ids, @Param("status") Integer status);
}