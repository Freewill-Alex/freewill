package com.freewill.common.exception;

public class IPLimitException extends RuntimeException {

    private static final long serialVersionUID = -674222462340834409L;

    public IPLimitException() {
        super("HTTP请求超出设定的限制");
    }

    public IPLimitException(String message) {
        super(message);
    }
}