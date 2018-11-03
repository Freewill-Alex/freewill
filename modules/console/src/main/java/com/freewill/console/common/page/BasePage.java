package com.freewill.console.common.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 分页查询Form
 *
 * @author gaojian
 * @date 2018年10月16日 下午3:37:23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class BasePage<T> extends Page<T> {
    private static final long serialVersionUID = 5194933845448697148L;

    private Integer selectInt;
    private String selectStr;

    public BasePage(long current, long size) {
        super(current, size);
    }
}