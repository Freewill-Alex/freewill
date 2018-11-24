package com.freewill.admin.common.security;

import com.freewill.admin.entity.SysRole;
import com.freewill.admin.entity.SysUser;
import com.freewill.admin.sys.service.SysRoleService;
import com.freewill.admin.sys.service.SysUserService;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author Administrator
 * @date 2017/12/11
 * 自定义权限匹配和账号密码匹配
 */
@Log4j2
public class MyShiroDbRealm extends AuthorizingRealm {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("权限配置-->MyShiroDbRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        SysUser userInfo = (SysUser) principals.getPrimaryPrincipal();
        List<Long> roleIds=userInfo.getRoleIdList();
        Collection<SysRole> roleList =sysRoleService.listByIds(roleIds);
        for (SysRole role :  roleList) {
            authorizationInfo.addRole(role.getRoleName());
            //权限统计
//            for (SysMenu p : role.getPermissions()) {
//                authorizationInfo.addStringPermission(p.getPerms());
//            }
        }
        return authorizationInfo;
    }

    /*主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确。*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        //获取用户的输入的账号.
        String username = (String) token.getPrincipal();
        log.info("Token---credentials:{}",token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser userInfo = sysUserService.getByUsername(username);
        log.debug("userInfo----->>:{}",userInfo);
        if (userInfo == null) {
            return null;
        }
        //账户冻结
        if (userInfo.getStatus() == 1) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(
                userInfo, //用户名
                userInfo.getPassword(), //密码
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
    }

}
