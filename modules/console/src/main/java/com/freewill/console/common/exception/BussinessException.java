package com.freewill.console.common.exception;

/**
 * @author GaoJian
 * @description 自定义业务相关异常
 * @email j.gao@ejauto.cn
 * @created 2018/9/25/0025 19:15
 */
public class BussinessException extends RuntimeException {

    public BussinessException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public BussinessException(String msg) {
        super(msg);
    }
}