package com.freewill.console.system.service;

import com.freewill.console.system.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	@Autowired
	private SystemUserService systemUserService;


	/**
	 * 登录服务
	 * 

	 * @return token 登录认证的TOKEN
	 */
	public String doLogin(String userName, String pwd) {
		SystemUser user = systemUserService.getByUserName(userName);

		

			
		return "1";
	}

	/**
	 * 登出服务
	 * @param userId
	 */
	public void logout(Integer userId) {
	}
}
