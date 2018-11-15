
package com.freewill.cms.common.dto;

import java.io.Serializable;

/**
 * 校验码类
 *
 * @author dengfuyuan
 */
public class CheckCode implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 423425018923619891L;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 生成校验码
     */
    private String code;
    /**
     * 发送时间
     */
    private long sendTime;
    /**
     * 是否已经验证通过
     */
    private boolean isPassed;

    /**
     * 输入次数，超过一定次数则验证码失效
     */
    private int tryTimes;


    public CheckCode() {
    }

    /**
     * 构造方法
     *
     * @param phone    电话号码
     * @param code     校验码
     * @param sendTime 发送时间
     */
    public CheckCode(String phone, String code, long sendTime) {
        this.phone = phone;
        this.code = code;
        this.sendTime = sendTime;
    }

    /**
     * 是否过期
     *
     * @return true 未过期
     */
    public boolean isValid() {
        /*
      校验码有效期时间
     */
        int verifyInterval = 6000 * 10;
        return (System.currentTimeMillis() - sendTime) < verifyInterval && tryTimes <= 3;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the sendTime
     */
    public long getSendTime() {
        return sendTime;
    }

    /**
     * @return the isPassed
     */
    public boolean isPassed() {
        return isPassed;
    }

    /**
     * @param isPassed the isPassed to set
     */
    public void setPassed(boolean isPassed) {
        this.isPassed = isPassed;
    }

    /**
     * 尝试验证次数累加
     */
    public void tryVerify() {
        tryTimes++;
    }
}
