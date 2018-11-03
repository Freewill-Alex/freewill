package com.freewill.console.driver.controller;

import com.freewill.common.utils.StringUtils;
import com.freewill.common.web.annotation.ResponseResult;
import com.freewill.console.common.exception.BussinessException;
import com.freewill.console.common.page.BasePage;
import com.freewill.console.common.service.SmsService;
import com.freewill.console.domain.DriverApplication;
import com.freewill.console.driver.form.DriverListForm;
import com.freewill.console.driver.service.DriverInviteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

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

    private static final String KEY_CHECKCODE = "DRIVER:CHECKCODE";
    private static final int SEND_INTERVAL = 60000;
    @Autowired
    private DriverInviteService driverService;
    @Autowired
    private SmsService smsService;
    private Random randomSmsCode = new Random();

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
    public BasePage<DriverApplication> detail(HttpServletRequest request, HttpServletResponse response, DriverListForm form) {
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

    /**
     * 注册报名司机
     *
     * @param response
     * @param form
     */
//    @RequestMapping("/add")
//    public void applyDriver(HttpServletResponse response, @Validated(AddGroup.class) DriverForm form) {
//        //校验验证码
//        if (form.getName() != null) {
//            RespObj obj = verifyCode(form.getSmsToken(), form.getSmsCode());
//            if (null != obj) {
//                ResponseUtils.renderJson(response, obj);
//                return;
//            }
//        }
//
//        DriverApplication da = driverService.apply(form);
//        RespObj rsp;
//        if ((da != null)) {
//            rsp = RespObj.successObj();
//            da.setPosterUrl(OSSUtils.addPrefix(da.getPosterUrl()));
//            // 注册成功后返回原对象给前台
//            rsp.setData(da);
//        } else {
//            rsp = RespObj.failedObj("注册失败。");
//        }
//        ResponseUtils.renderJson(response, rsp);
//    }

    /**
     * 发送验证码
     *
     * @param request
     * @param response
     */
//    @RequestMapping("/sendsms")
//    public void sendSms(HttpServletRequest request, HttpServletResponse response) {
//        String phone = request.getParameter("phone");
//        String tokenParam = request.getParameter("token");
//        if (StringUtils.isEmpty(phone)) {
//            ResponseUtils.renderJson(response, RespObj.failedObj("电话号码不能为空"));
//            return;
//        }
////        DriverApplication hasUser = driverService.selectByPhone(phone);
////       if (hasUser != null) {
////           ResponseUtils.renderJson(response, RespObj.failedObj("用户手机号码已被注册"));
////           return; }
//
//        int code = 1000 + randomSmsCode.nextInt(8999);
//        if (CommonConfig.isSmsOff()) {
//            // 调试模式模式111111,并且不发送短信
//            code = 111111;
//        }
//        // 验证手机号格式是否正确
//        // 判断60秒内已经发送过
//        RMap<String, CheckCode> codeMap = RedisUtils.getRedisson().getMap(KEY_CHECKCODE);
//        if (!StringUtils.isEmpty(tokenParam)) {
//            if (codeMap.isExists()) {
//                CheckCode checkcode = codeMap.get(tokenParam);
//                if (checkcode != null && (System.currentTimeMillis() - checkcode.getSendTime()) < SEND_INTERVAL) {
//                    ResponseUtils.renderJson(response, RespObj.failedObj("请稍后再获取验证码"));
//                    return;
//                }
//            }
//        }
//        //短信验证校验
//        smsService.sendDriver(phone, String.valueOf(code));
//
//        String token = UUID.randomUUID().toString();
//        codeMap.put(token, new CheckCode(phone, CheckCode.TYPE_SUPPLY, String.valueOf(code), System.currentTimeMillis()));
//
//        Map<String, String> data = new HashMap<>(16);
//        data.put("token", token);
//
//        RespObj obj = RespObj.successObj();
//        obj.setData(data);
//        ResponseUtils.renderJson(response, obj);
//    }


//    /**
//     * 验证码校验
//     *
//     * @param token
//     * @param code
//     * @return
//     */
//    private RespObj verifyCode(String token, String code) {
//        RMap<String, CheckCode> codeMap = RedisUtils.getRedisson().getMap(KEY_CHECKCODE);
//
//        if (!codeMap.isExists()) {
//            return RespObj.failedObj("请获取验证码");
//        }
//        CheckCode checkcode = codeMap.get(token);
//        if (checkcode == null) {
//            return RespObj.failedObj("请获取验证码");
//        }
//
//        // 判断验证是否过期
//        if (!checkcode.isValid()) {
//            codeMap.remove(token);
//            return RespObj.failedObj("验证码已失效,请重新获取");
//        }
//
//        if (!code.equals(checkcode.getCode())) {
//            checkcode.tryVerify();
//            codeMap.put(token, checkcode);
//            return RespObj.failedObj("验证码不正确");
//        }
//
//        // 设置验证码验证通过
//        checkcode.setPassed(true);
//        codeMap.put(token, checkcode);
//
//        // 检查是否验证通过
//        if (!checkcode.isValid() || !checkcode.isPassed() || !CheckCode.TYPE_SUPPLY.equals(checkcode.getType())) {
//            return RespObj.failedObj("验证码未验证通过,请重新获取");
//        }
//        return null;
//    }

}