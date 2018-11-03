package com.freewill.console.driver.service;

import com.freewill.console.common.exception.BussinessException;
import com.freewill.console.common.page.BasePage;
import com.freewill.console.domain.DriverApplication;
import com.freewill.console.driver.em.DriverEnum;
import com.freewill.console.driver.form.DriverForm;
import com.freewill.console.driver.form.DriverListForm;
import com.freewill.console.mapper.DriverApplicationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;


/**
 * @Description 司机邀请活动分享服务
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-29 14:07
 */
@Service
public class DriverInviteService {
    @Resource
    DriverApplicationMapper driverMapper;

    /**
     * 添加报名的司机或者普通推荐人
     *
     * @param form 表单数据
     * @return DriverApplication
     */
    @Transactional(rollbackFor = Exception.class)
    public DriverApplication apply(DriverForm form) {
        String name = form.getName();
        String code = form.getSmsCode();
        String phone = form.getPhone();
        String uuid = form.getUuid();

        //校验报名的是普通用户和司机的逻辑
        boolean flag = (null == name && null == code);
        //先通过报名人手机号查询是否有此人
        DriverApplication hasDriver = selectByPhone(phone);

        DriverApplication newDriver = new DriverApplication();

        //通过推荐人注册时校验推荐人的有效性
        if (null != uuid) {
            DriverApplication pda = driverMapper.selectByUUID(uuid);
            if (null != pda) {
                newDriver.setPid(pda.getId());
                if (hasDriver != null) {
                    hasDriver.setPid(pda.getId());
                }
            } else {
                throw new BussinessException("无法查证您的推荐人");
            }
        }


        int conmonUserEnumKey = DriverEnum.Type.COMMON_USER.getKey();
        int driverEnumKey = DriverEnum.Type.DRIVER.getKey();
        //如果数据库存有司机或推荐人则进入逻辑
        if (hasDriver != null) {
            //已注册的普通用户可自动转为司机
            if (!flag && hasDriver.getType() == conmonUserEnumKey) {
                Integer pid = hasDriver.getPid();
                driverMapper.changeDriverByPhone(name, pid, phone);
                hasDriver.setName(name);
                hasDriver.setType(driverEnumKey);
                return hasDriver;
            }
            //有存在的用户如果是司机时注册则提示
            if (flag || hasDriver.getType() == driverEnumKey) {
                hasDriver.setRemark("您已登记报名活动。");
                return hasDriver;
            }
        }

        //未报名过的继续完成报名
        uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String qrContent = ApiConfig.DOMAIN + "/driver/#/selectAction?uuid=" + uuid;
//        InputStream inputStream = PosterUtil.buildToInputStream(POST_TEMPLATE_URL, qrContent);
//        String url = OSSUtils.savePublishFile(inputStream, "driver", POST_TEMPLATE_URL.substring(POST_TEMPLATE_URL.lastIndexOf(".")));
        newDriver.setUuid(uuid);
        newDriver.setType(flag ? conmonUserEnumKey : driverEnumKey);
        newDriver.setName(name);
        newDriver.setPhone(phone);
        newDriver.setStatus(DriverEnum.Status.TO_COMMUNICATE.getKey());
        Date date = new Date();
        newDriver.setCreateTime(date);
        newDriver.setUpdateTime(date);
//        newDriver.setUrl(qrContent);
//        newDriver.setPosterUrl(url);
        return driverMapper.insert(newDriver) > 0 ? newDriver : null;

    }


    /**
     * 查询注册司机报名列表接口（司机）
     *
     * @param form 表单数据
     * @return PageInfo<DriverApplication>
     */
    public BasePage<DriverApplication> getAllDriver(DriverListForm form) {
        BasePage<DriverApplication> page = new BasePage<>(form.getCurrent(),form.getSize());
        return  driverMapper.selectAllDriver(page,form);
    }

    /**
     * 查询注册推荐人报名列表接口（司机和普通用户）
     *
     * @param form 表单数据
     * @return PageInfo<DriverApplication>
     */
    public BasePage<DriverApplication> getAllList(DriverListForm form) {
        BasePage<DriverApplication> page =new BasePage<>(form.getCurrent(),form.getSize());
        return  driverMapper.selectAll(page,form);
    }

    /**
     * 查询被推荐人信息列表接口（司机和普通用户）
     *
     * @param form 推荐人ID
     * @return List<DriverApplication>
     */
    public BasePage<DriverApplication> getDetailList(DriverListForm form) {
        BasePage<DriverApplication> page =new BasePage<>(form.getCurrent(),form.getSize());
        return driverMapper.selectChildByPid(page,form.getId());
    }

    /**
     * 可批量修改（司机和普通用户）状态
     *
     * @param ids    修改（司机和普通用户）ID串
     * @param status 沟通状态
     * @return int
     */
    public int batchUpdateStatus(String ids, Integer status) {
        return driverMapper.updateStatusByPrimaryKey(ids, status);
    }


    /**
     * 通过手机号查询司机用户是否存在
     *
     * @param phone 手机号
     * @return DriverApplication
     */
    private DriverApplication selectByPhone(String phone) {
        return driverMapper.selectByPhone(phone);
    }
}
