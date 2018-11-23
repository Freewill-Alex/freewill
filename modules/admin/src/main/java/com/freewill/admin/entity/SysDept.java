package com.freewill.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 部门管理
 * </p>
 *
 * @author GaoJian
 * @since 2018-11-18
 */
public class SysDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "dept_id", type = IdType.AUTO)
    private Long deptId;

    /**
     * 上级部门ID，一级部门为0
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 是否删除  -1：已删除  0：正常
     */
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;

    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    @TableField(exist = false)
    private List<?> list;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "SysDept{" +
                "deptId=" + deptId +
                ", parentId=" + parentId +
                ", name=" + name +
                ", orderNum=" + orderNum +
                ", delFlag=" + delFlag +
                "}";
    }
}
