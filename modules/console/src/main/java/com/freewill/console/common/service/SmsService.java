package com.freewill.console.common.service;

import com.aliyun.mns.model.BatchSmsAttributes.SmsReceiverParams;
import com.freewill.common.config.CommonConfig;
import com.freewill.common.redis.RedisUtils;
import com.freewill.common.sms.SmsUtil;
import com.freewill.common.utils.StringUtils;
import com.freewill.console.common.dto.CheckCode;
import com.freewill.console.common.exception.BussinessException;
import org.redisson.api.RMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 短信验证码平台
 *
 * @author sanma
 */
@Component
public class SmsService {

    /**
     * smsTemplate 短信模板
     */
    private static final String SMS_TMEPLATE = "SMS_146802357";
    /**
     * smsKey 存储验证码信息的键
     */
    private static final String SMS_KEY = "SDP:SMS:CHECKCODE";

    private Random randomSmsCode = new Random();

    /**
     * 验证码发送服务
     *
     * @param phone    手机号
     * @param oldToken 原有的短信令牌
     * @return Map(String, String) 新的短信令牌
     */
    public Map<String, String> smsSend(String phone, String oldToken) {
        if (StringUtils.isEmpty(phone)) {
            throw new NullPointerException("phone不能为空");
        }
        return sendCheckCode(phone, oldToken);
    }


    /**
     * 获取redis存储的短信验证码RMap信息
     *
     * @return RMap（String, CheckCode）
     */
    public RMap<String, CheckCode> getCodeMap() {
        RMap<String, CheckCode> codeMap = RedisUtils.getRedisson().getMap(SMS_KEY);
        if (!codeMap.isExists()) {
            throw new BussinessException("请获取验证码");
        }
        return codeMap;
    }



    /**
     * 短信验证码发送逻辑
     *
     * @param phone    手机号
     * @param oldToken 原有的短信令牌
     * @return Map 新的短信令牌
     */
    private Map<String, String> sendCheckCode(String phone, String oldToken) {
        String code = String.valueOf(1000 + randomSmsCode.nextInt(8999));
        //通过settings.properties中的smsoff开关设置
        if (CommonConfig.isSmsOff()) {
            // 调试模式模式111111,并且不发送短信
            code = "111111";
        }
        // 验证手机号格式是否正确
        // 判断60秒内已经发送过
        RMap<String, CheckCode> codeMap = getCodeMap();
        if (!StringUtils.isEmpty(oldToken)) {
                CheckCode checkcode = codeMap.get(oldToken);
            //校验验证码存在并是否超过有效期
            if (checkcode != null && checkcode.isValid()) {
                    throw new BussinessException("请稍后再获取验证码");
                }
        }
        //短信验证码发送
        SmsReceiverParams params = new SmsReceiverParams();
        params.setParam("code", code);
        SmsUtil.sendSMS(phone, SMS_TMEPLATE, params);
        //生成新的smsToken
        String newToken = UUID.randomUUID().toString();
        //存入redis
        codeMap.put(newToken, new CheckCode(phone, code, System.currentTimeMillis()));

        Map<String, String> data = new HashMap<>(16);
        data.put("smsToken", newToken);
        return data;


    }

}
