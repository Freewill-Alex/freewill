package com.freewill.common.web.wrapper;

import com.baomidou.mybatisplus.extension.api.R;
import com.freewill.common.utils.JsonUtil;
import lombok.Getter;

/**
 * @Description 全局统一响应结果封装类
 * @Author GaoJian
 * @Email j.gao@ejauto.cn
 * @Created 2018-09-25 16:31
 */
@Getter
public class Result extends R<Object> {

    private String path;

    public Result setPath(String path) {
        this.path = path;
        return this;
    }


    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}
