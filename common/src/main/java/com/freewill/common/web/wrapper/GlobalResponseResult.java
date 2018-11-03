package com.freewill.common.web.wrapper;

import com.freewill.common.utils.JsonUtil;
import lombok.Getter;

/**
 * @Description 全局统一响应结果封装类
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-25 16:31
 */

@Getter
public class GlobalResponseResult implements Result {
    private boolean isSuccess;
    private String path;
    private String errorMsg="";
    private Object data;

    public GlobalResponseResult setPath(String path) {
        this.path = path;
        return this;
    }

    public GlobalResponseResult isSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
        return this;
    }

    public GlobalResponseResult setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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
