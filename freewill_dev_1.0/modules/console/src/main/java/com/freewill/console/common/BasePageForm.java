package com.freewill.console.common;

/**
 * 分页查询Form
 *
 * @author pengqi
 * @date 2017年8月16日 下午3:37:23
 */
public class BasePageForm {

    private Integer pageNo = 1; //默认页数

    private Integer pageSize = 20; //默认每页数量

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}