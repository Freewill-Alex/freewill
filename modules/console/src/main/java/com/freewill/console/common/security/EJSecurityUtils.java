package com.freewill.console.common.security;


/**
 * 安全工具类
 *
 * @author dengfuyuan
 */
public class EJSecurityUtils {

    // 摘要混淆码
    public static final String HASH_SALT = "ejauto.ministore.34df232";

    public static final String HASH_ALGORITHM = "SHA1";

    public static final int HASH_INTERATIONS = 256;

    private EJSecurityUtils() {

    }

    /**
     * 加密
     *
     * @param text 原文
     * @return 密文
     */
//    public static String encryptPwd(String text) {
//        SimpleHash sh = new SimpleHash(HASH_ALGORITHM, text, HASH_SALT,
//                HASH_INTERATIONS);
//        return sh.toHex();
//    }
//
//    public static void main(String[] a) {
//        System.out.println(encryptPwd("UX8x13vqvqij"));
//
////		System.out.println(UUID.randomUUID().toString().replaceAll("-", ""));
//    }

//    /**
//     * 获取当前登陆用户的ID
//     *
//     * @return
//     */
//    public static Integer getUserId() {
//        Subject s = SecurityUtils.getSubject();
//        if (s != null && (s.isRemembered() || s.isAuthenticated())) {
//            return ((ShiroUser) s.getPrincipal()).getUserId();
//        }
//
//        throw new UnauthenticatedException();
//    }
//
//    /**
//     * 获取当前登录的用户的认证对象
//     *
//     * @return ShiroUser 如果已经登录，否则
//     */
//    public static ShiroUser getShiroUser() {
//        Subject s = SecurityUtils.getSubject();
//        if (s != null && (s.isRemembered() || s.isAuthenticated())) {
//            return ((ShiroUser) s.getPrincipal());
//        }
//
//        throw new UnauthenticatedException();
//    }
//
//    /**
//     * 判断是否已登录
//     *
//     * @return
//     */
//    public static boolean isLogin() {
//        Subject s = SecurityUtils.getSubject();
//        return s != null && (s.isRemembered() || s.isAuthenticated());
//
//    }
//
//    /**
//     * 检查权限
//     * <p>
//     * 失败是会抛出UnauthorizedException异常
//     *
//     * @param right 权限名
//     */
//    public static void checkPermission(String right) {
//        Subject s = SecurityUtils.getSubject();
//        s.checkPermission(right);
//    }
//
//    /**
//     * 判断是否有此权限
//     *
//     * @param right
//     * @return
//     */
//    public static boolean isPermitted(String right) {
//        Subject s = SecurityUtils.getSubject();
//        if (null != s) {
//            return s.isPermitted(right);
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 判断是否有此角色
//     *
//     * @param role
//     * @return
//     */
//    public static boolean hasRole(String role) {
//        Subject s = SecurityUtils.getSubject();
//        if (null != s) {
//            return s.hasRole(role);
//        } else {
//            return false;
//        }
//    }
}
