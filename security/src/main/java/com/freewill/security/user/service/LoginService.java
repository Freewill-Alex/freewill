//package com.freewill.console.user.service;
//
//import com.freewill.common.utils.StringUtils;
//import com.freewill.common.exception.BussinessException;
//import com.freewill.common.security.EJSecurityUtils;
//import com.freewill.admin.domain.User;
//import com.freewill.admin.mapper.UserMapper;
//import com.freewill.security.user.dto.UserInfo;
//import com.freewill.security.user.em.UserEnum;
//import com.freewill.security.user.form.LoginForm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class LoginService {
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private UserMapper userMapper;
//
//
//
//
//    /**
//     * 登录服务
//     *
//     * @param ipAddress
//     * @param browser
//     * @return token 登录认证的TOKEN
//     */
//    public String doLogin(LoginForm form, String ipAddress, String browser) {
//        UserInfo userInfo = userService.getByUserName(form.getUsername());
//        if (userInfo == null) {
//            throw new BussinessException("账号不存在");
//        }
//
//
//        boolean success;
//        String logMsg;
//
//        // 用户已离职
//        if (userInfo.getStatus() == UserEnum.Status.E1_ON_JOB.getKey()) {
//            if (userInfo.getPassword().equals(EJSecurityUtils.encryptPwd(form.getPwd()))) {
//                logMsg = "登录成功";
//                success = true;
//            } else {
//                logMsg = "登录失败，原因：密码错误";
//                success = false;
//            }
//
//        } else {
//            logMsg = "登录失败，原因：用户被禁用";
//            success = false;
//        }
//
//
//        if (success) {
//
//            //清除当前用户之前的登录用户信息
////            tokenManager.logout(userInfo.getId());
//
//            String token = StringUtils.getUUID();
////            tokenManager.setShiroUser(token, new ShiroUser(userInfo.getId(), userInfo.getStatus()));
//            return token;
//        } else {
//            throw new BussinessException(logMsg);
//        }
//    }
//
//
//    /**
//     * 登出服务
//     *
//     * @param userId
//     */
//    public void logout(Integer userId) {
//        User user = userMapper.selectByPrimaryKey(userId);
//        user.setOpenid(null);
//        userMapper.updateByPrimaryKey(user);
////        tokenManager.logout(userId);
////        SecurityUtils.getSubject().logout();
//    }
//
////    public void changePwd(String oldPwd, String newPwd) {
////
////        User user = userMapper.selectByPrimaryKey(EJSecurityUtils.getUserId());
////        if (user == null) {
////            throw new BussinessException("用户不存在");
////        }
////        if (user.getPassword().equals(EJSecurityUtils.encryptPwd(oldPwd))) {
////            user.setPassword(EJSecurityUtils.encryptPwd(newPwd));
////        } else {
////            throw new BussinessException("原始密码错误");
////        }
////        userMapper.updateByPrimaryKey(user);
////
////        this.logout(user.getId());
////    }
//}
