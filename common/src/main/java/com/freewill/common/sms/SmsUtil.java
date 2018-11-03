package com.freewill.common.sms;

import com.aliyun.mns.model.BatchSmsAttributes.SmsReceiverParams;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.freewill.common.config.CommonConfig;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);
	
	// 产品名称:云通信短信API产品,开发者无需替换
	static final String PRODUCT = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String DOMAIN = "dysmsapi.aliyuncs.com";
	
	private SmsUtil(){}
	
	public static void sendSMS(String phone, String template,  SmsReceiverParams params){
		// 调试模式不发送短信
		if (CommonConfig.isSmsOff()) {
			return;
		}
		// 可自助调整超时时间
		// System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		// System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		try {
			// 初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", CommonConfig.getAliyunRamAccesskey(),
					CommonConfig.getAliyunRamAccesssecret());
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			// 组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			// 必填:待发送手机号
			request.setPhoneNumbers(phone);
			// 必填:短信签名-可在短信控制台中找到
			request.setSignName(CommonConfig.getAliyunSmsSign());
			// 必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(template);
			// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			Gson gson = new Gson();
			request.setTemplateParam(gson.toJson(params.getParams()));

			// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			// request.setOutId("yourOutId");

			// hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			if (!"OK".equals(sendSmsResponse.getCode())) {
				logger.info("send msg not ok" + sendSmsResponse.getMessage());
			}
		} catch (Exception ex) {
			logger.error("send msg failed", ex);
		}
	}
	
}
