package com.freewill.common.exception;

/**
 * @author GaoJian
 * @description 自定义系统500相关异常
 * @email j.gao@ejauto.cn
 * @created 2018/9/25/0025 19:15
 */
public class SystemException extends RuntimeException {

    public SystemException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public SystemException(String msg) {
        super(msg);
    }
}