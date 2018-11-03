package com.freewill.common.web.wrapper;

import com.freewill.common.util.JsonUtil;
import lombok.Getter;

/**
 * @Description 全局统一响应结果封装类
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-25 16:31
 */

@Getter
public class GlobalResponseResult implements Result {
    private String status;
    private String path;
    private String message;
    private Object data;

    public GlobalResponseResult setPath(String path) {
        this.path = path;
        return this;
    }

    public GlobalResponseResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public GlobalResponseResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public GlobalResponseResult setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}
