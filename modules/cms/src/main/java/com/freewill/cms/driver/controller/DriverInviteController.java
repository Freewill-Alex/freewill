package com.freewill.cms.driver.controller;

import com.freewill.common.utils.StringUtils;
import com.freewill.common.web.annotation.ResponseResult;
import com.freewill.cms.common.exception.BussinessException;
import com.freewill.cms.common.page.BasePage;
import com.freewill.cms.common.validation.SelectGroup;
import com.freewill.cms.domain.DriverApplication;
import com.freewill.cms.driver.form.DriverListForm;
import com.freewill.cms.driver.service.DriverInviteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 司机邀请活动API
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-29 14:08
 */
@ResponseResult
@RestController
@RequestMapping("/driver")
@Slf4j
public class DriverInviteController {

    @Autowired
    private DriverInviteService driverService;

    /**
     * 批量修改（司机和普通推荐人）的状态
     *
     * @param response
     */
    @RequestMapping("/update_status")
    public String updateStatus(HttpServletResponse response, String ids, Integer status) {
        if (StringUtils.isEmpty(ids)) {
            throw  new BussinessException("被修改人不能为空");
        }
        if (StringUtils.isEmpty(status)) {
            throw  new BussinessException("状态不能为空");
        }
        if (driverService.batchUpdateStatus(ids, status) <= 0) {
            throw  new BussinessException("修改失败");
        }
        return "已完成修改";
    }

    /**
     * 查询被推荐人的列表详情
     *
     * @param response
     */
    @RequestMapping("/list_child")
    public BasePage<DriverApplication> detail(HttpServletRequest request, HttpServletResponse response, @Validated(SelectGroup.class) DriverListForm form) {
        if (form.getId() == null) {
          throw  new BussinessException("推荐人ID不能为空");
        }
       return driverService.getDetailList(form);
    }

    /**
     * 查询司机报名列表
     *
     * @param response
     */
    @RequestMapping("/list_driver")
    public BasePage<DriverApplication> ListDriver(HttpServletResponse response, DriverListForm form) {
        return driverService.getAllDriver(form);
    }

    /**
     * 查询所有司机和普通推荐人报名列表
     *
     * @param response
     */
    @RequestMapping("/list_all")
    public BasePage<DriverApplication> ListAll(HttpServletResponse response, DriverListForm form) {
        return driverService.getAllList(form);
    }


}