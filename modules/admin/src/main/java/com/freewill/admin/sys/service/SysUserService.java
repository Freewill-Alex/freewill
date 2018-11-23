package com.freewill.admin.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.freewill.admin.entity.SysUser;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author GaoJian
 * @since 2018-11-18
 */
public interface SysUserService extends IService<SysUser> {


      SysUser getByUsername(String name);
}
