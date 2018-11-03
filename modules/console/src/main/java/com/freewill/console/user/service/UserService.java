package com.freewill.console.user.service;

import com.aliyun.oss.internal.OSSUtils;
import com.freewill.console.common.exception.BussinessException;
import com.freewill.console.common.service.SmsService;
import com.freewill.console.domain.User;
import com.freewill.console.mapper.UserMapper;
import com.freewill.console.user.dto.UserInfo;
import com.freewill.console.user.em.UserEnum;
import com.freewill.console.user.form.SalfEditForm;
import com.freewill.console.user.form.UserEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import com.freewill.console.common.utils.OSSUtils;

@Service
public class UserService {

    @Autowired
    private SmsService smsService;

    @Autowired
    private UserMapper userMapper;


    public static void main(String[] args) {


        System.out.println(Integer.valueOf(""));
    }

    public UserInfo getByUserName(String userName) {
        Map<String, Object> param = new HashMap<>();
        param.put("userName", userName);
        param.put("status", UserEnum.Status.E1_ON_JOB.getKey());
        //param.put("storeId", storeId);
        return userMapper.getByUserName(param);
    }

    public UserInfo getUserInfoById(Integer id) {
        return userMapper.getUserInfoById(id, UserEnum.Status.E1_ON_JOB.getKey());
    }

    /**
     * 分页列表
     *
     * @return 用户列表
     */
//    public PageInfo<UserListInfo> list(UserListForm form) {
//        Map<String, Object> paramMap = MapUtils.transBean2Map(form);
//        paramMap.put("status", UserEnum.Status.E1_ON_JOB.getKey());
//        Page<UserListInfo> page = PageHelper.startPage(form.getPageNo(), form.getPageSize());
//        userMapper.listBy(paramMap);
//
//        return new PageInfo<>(page);
//    }

    /**
     * 添加员工
     *
     * @param form
     */
    @Transactional
    public void add(UserEditForm form) {
//        UserInfo hasUser = getByUserName(form.getPhone(), form.getMerchantsCode(), form.getStoreId());
//        if (hasUser != null) {
//            throw new BussinessException("该用户已存在");
//        }
//        User user = new User();
//        user.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
//        user.setJob(form.getJob());
//        user.setName(form.getName());
//        user.setPhone(form.getPhone());
//        user.setUserName(form.getPhone());
//        user.setPassword(EJSecurityUtils.encryptPwd(form.getPhone().substring(5)));
//        user.setStatus(UserEnum.Status.E1_ON_JOB.getKey());
//        user.setStoreId(form.getStoreId());
//        Date date = new Date();
//        user.setCreateTime(date);
//        user.setUpdateTime(date);
//        userMapper.insert(user);

    }

    /**
     * 修改员工信息
     *
     * @param form
     */
    @Transactional
    public void modify(UserEditForm form) {

//        if (EJSecurityUtils.getUserId().equals(form.getId())) {
//            throw new BussinessException("不允许操作");
//        }

        User user = userMapper.selectByPrimaryKey(form.getId());
        if (user == null) {
            throw new BussinessException("用户不存在");
        }

        UserInfo hasUser = getByUserName(form.getPhone());
        if (hasUser != null && !hasUser.getId().equals(form.getId())) {
            throw new BussinessException("登录账号已存在");
        }
        user.setJob(form.getJob());
        user.setName(form.getName());
        user.setPhone(form.getPhone());
        user.setUserName(form.getPhone());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);


    }

    @SuppressWarnings("serial")
    @Transactional
    public void edit(SalfEditForm form) {

        User user = userMapper.selectByPrimaryKey(form.getUserId());
        if (user == null) {
            throw new BussinessException("用户不存在");
        }
//        user.setHeadImage(OSSUtils.removePrefix(form.getHeadImage()));
        user.setSex(form.getSex());
        user.setIntroduce(form.getIntroduce());
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKey(user);

    }

//    public void delete(IdForm form) {
//        User user = userMapper.selectByPrimaryKey(form.getId());
//        if (user == null) {
//            throw new BussinessException("用户不存在");
//        }
//        user.setStatus(UserEnum.Status.E0_DIMISSION.getKey());
//        user.setUpdateTime(new Date());
//        userMapper.updateByPrimaryKey(user);
//    }

    /**
     //     * 生成我的名片
     //     *
     //     * @param form
     //     * @return
     //     */
//    public String build(MerchantsCodeForm form) {
//        UserInfo user = getUserInfoById(EJSecurityUtils.getUserId());
//        String qrContent = ApiConfig.DOMAIN + "/ministore/" + form.getMerchantsCode() + "/poster/" + user.getUuid();
//
//        InputStream inputStream = PosterUtil.buildToInputStreamBusinessCard(ApiConfig.BUSINESSCARD, qrContent,
//                OSSUtils.addPrefix(user.getLogo()) + "?x-oss-process=image/resize,h_80", user.getName(), user.getJob(), user.getUserName(), user.getMerchantsName(), user.getAddress());
//
//        String url = OSSUtils.savePublishFile(inputStream, "userBusinessCard", ".jpg");
//
//        return OSSUtils.addPrefix(url);
//    }


}
