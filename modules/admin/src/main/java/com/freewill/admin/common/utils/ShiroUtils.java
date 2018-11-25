/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.freewill.admin.common.utils;

import com.freewill.admin.entity.SysUser;
import com.freewill.common.exception.BussinessException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * 
 * @author freewill
 * @email gaojian920813@sina.com
 * @date 2018年11月12日 上午9:49:19
 */
public class ShiroUtils {

	/**  加密算法 */
	public final static String HASH_ALGORITHM_NAME = "SHA-256";
	/**  循环次数 */
	public final static int HASH_ITERATIONS = 16;

	/**
	 * 加密
	 * @param password 密码
	 * @param credentialsSalt  盐(username+salt)
	 * @return 密文
	 */
	public static String sha256(String password, String credentialsSalt) {
		return new SimpleHash(HASH_ALGORITHM_NAME, password, credentialsSalt, HASH_ITERATIONS).toString();
	}

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static SysUser getUserEntity() {
		return (SysUser)SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	
	public static String getKaptcha(String key) {
		Object kaptcha = getSessionAttribute(key);
		if(kaptcha == null){
			throw new BussinessException("验证码已失效");
		}
		getSession().removeAttribute(key);
		return kaptcha.toString();
	}

    /**
     * 检查权限
     * <p>
     * 失败是会抛出UnauthorizedException异常
     *
     * @param right 权限名
     */
    public static void checkPermission(String right) {
        Subject s = SecurityUtils.getSubject();
        s.checkPermission(right);
    }

    /**
     * 判断是否有此权限
     *
     * @param right
     * @return
     */
    public static boolean isPermitted(String right) {
		Subject s = SecurityUtils.getSubject();
		return null != s && s.isPermitted(right);
	}

    /**
     * 判断是否有此角色
     *
     * @param role
     * @return
     */
    public static boolean hasRole(String role) {
		Subject s = SecurityUtils.getSubject();
		return null != s && s.hasRole(role);
	}
}
