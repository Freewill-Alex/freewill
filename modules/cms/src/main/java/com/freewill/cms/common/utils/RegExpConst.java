package com.freewill.cms.common.utils;

/**
 * @Description 正则规则表达式集合
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-11-02 9:42
 */
public class RegExpConst {
    /**
     * 经纪人密码校验规则：字母+数字的组合,且长度不能小于6,不能大于16
     */
    public static final String BROKER_PWD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    /**
     * 手机号校验规则：前三位格式1[3.4.5.6.7.8.][0-9] 且长度为11位
     */
    public static final String MOBILE_PHONE = "^[1][3-8][0-9]{9}$";
    /**
     * 银行卡号校验规则：纯数字首位不为0 且长度为15-19位
     */
    public static final String BANK_CARD_NO = "^[1-9][0-9]{14,18}$";
}
