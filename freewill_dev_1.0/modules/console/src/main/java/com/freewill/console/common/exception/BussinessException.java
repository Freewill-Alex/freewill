package com.freewill.console.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author GaoJian
 * @description 自定义业务相关异常
 * @email j.gao@ejauto.cn
 * @created 2018/9/25/0025 19:15
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BussinessException extends Exception {

    public BussinessException(String msg, Exception e) {
        super(msg + "\n" + e.getMessage());
    }

    public BussinessException(String msg) {
        super(msg);
    }
}