package com.freewill.console.common.service;

import com.freewill.common.sms.SmsUtil;
import com.aliyun.mns.model.BatchSmsAttributes.SmsReceiverParams;
import org.springframework.stereotype.Component;

/**
 * 短信验证码平台
 *
 * @author sanma
 */
@Component
public class SmsService {

    /**
     * 司机注册的验证码
     *
     * @param phone
     * @param code
     */
    public void sendDriver(String phone, String code) {
        SmsReceiverParams params = new SmsReceiverParams();
        params.setParam("code", code);
        SmsUtil.sendSMS(phone, "SMS_146802357", params);
    }

}
