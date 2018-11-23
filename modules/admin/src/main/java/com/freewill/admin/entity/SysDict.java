package com.freewill.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 数据字典表
 * </p>
 *
 * @author GaoJian
 * @since 2018-11-18
 */
public class SysDict implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 字典名称
     */
    @TableField("name")
    @NotBlank(message = "字典名称不能为空")
    private String name;

    /**
     * 字典类型
     */
    @TableField("type")
    @NotBlank(message = "字典类型不能为空")
    private String type;

    /**
     * 字典码
     */
    @TableField("code")
    @NotBlank(message = "字典值不能为空")
    private String code;

    /**
     * 字典值
     */
    @TableField("value")
    private String value;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 删除标记  -1：已删除  0：正常
     */
    @TableLogic
    @TableField("del_flag")
    private Integer delFlag;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "SysDict{" +
                "id=" + id +
                ", name=" + name +
                ", type=" + type +
                ", code=" + code +
                ", value=" + value +
                ", orderNum=" + orderNum +
                ", remark=" + remark +
                ", delFlag=" + delFlag +
                "}";
    }
}
