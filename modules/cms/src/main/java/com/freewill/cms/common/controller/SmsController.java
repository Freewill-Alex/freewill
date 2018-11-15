package com.freewill.cms.common.controller;

import com.freewill.common.utils.StringUtils;
import com.freewill.common.web.annotation.IPLimit;
import com.freewill.cms.common.dto.CheckCode;
import com.freewill.cms.common.exception.BussinessException;
import com.freewill.cms.common.service.SmsService;
import org.redisson.api.RMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Description 短信验证码接口
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-10-13 14:15
 */
@Controller
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;


    /**
     * 发送验证码
     *
     * @param request
     * @param response
     */
    @IPLimit(count = 3)
    @RequestMapping("/send")
    public Map<String, String> sendSms(HttpServletRequest request, HttpServletResponse response) {
        String phone = request.getParameter("phone");
        String token = request.getParameter("smsToken");

        if (StringUtils.isEmpty(phone)) {
            throw new BussinessException("手机号码不能为空");
        } else if (11 != phone.length()) {
            throw new BussinessException("手机号码长度错误");
        }
        //短信验证校验
        return smsService.smsSend(phone, token);
    }

    /**
     * 验证码校验
     *
     * @param request
     * @param response
     * @param smsCode
     * @param smsToken
     */
    @RequestMapping("/valid")
    public void verifyCode(HttpServletRequest request, HttpServletResponse response, String smsCode, String smsToken) {
        if (StringUtils.isEmpty(smsCode) || StringUtils.isEmpty(smsToken)) {
            throw new BussinessException("请先获取验证码");
        } else {
            RMap<String, CheckCode> codeMap = smsService.getCodeMap();
            if (codeMap.isExists()) {
                CheckCode checkcode = codeMap.get(smsToken);
                if (checkcode == null) {
                    throw new BussinessException("请获取验证码");
                } else if (!checkcode.isValid()) {
                    codeMap.remove(smsToken);
                     throw new BussinessException("验证码已失效,请重新获取");
                } else if (!smsCode.equals(checkcode.getCode())) {
                    checkcode.tryVerify();
                    codeMap.put(smsToken, checkcode);
                    throw new BussinessException("验证码不正确");
                } else {
                    // 设置验证码验证通过
                    checkcode.setPassed(true);
                    codeMap.put(smsToken, checkcode);
                    // 检查是否验证通过
                    if (!checkcode.isValid() || !checkcode.isPassed()) {
                        throw new BussinessException("验证码未验证通过,请重新获取");
                    }
                }
            } else {
                throw new BussinessException("请获取验证码");
            }
        }
    }
}
