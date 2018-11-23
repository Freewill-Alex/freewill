package com.freewill.admin.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.freewill.admin.entity.SysUser;
import com.freewill.admin.mapper.SysUserMapper;
import com.freewill.admin.sys.service.SysUserService;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author GaoJian
 * @since 2018-11-18
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public SysUser getByUsername(String username) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public boolean saveBatch(Collection<SysUser> entityList) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<SysUser> entityList) {
        return false;
    }

    @Override
    public SysUser getOne(Wrapper<SysUser> queryWrapper) {
        return null;
    }
}
