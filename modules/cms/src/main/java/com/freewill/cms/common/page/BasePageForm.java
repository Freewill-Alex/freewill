package com.freewill.cms.common.page;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author sanma
 */
@Slf4j
@Data
public class BasePageForm {
    //默认页数
    private long current = 1;
    //默认每页数量
    private long size = 20;
    //时间戳
    private long timestamp=0L;
}