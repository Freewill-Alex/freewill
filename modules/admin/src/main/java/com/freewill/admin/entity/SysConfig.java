package com.freewill.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * <p>
 * 系统配置信息表
 * </p>
 *
 * @author GaoJian
 * @since 2018-11-18
 */
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * key
     */
    @TableField("param_key")
    @NotBlank(message = "参数名不能为空")
    private String paramKey;

    /**
     * value
     */
    @TableField("param_value")
    @NotBlank(message = "参数值不能为空")
    private String paramValue;

    /**
     * 状态   0：隐藏   1：显示
     */
    @TableField("status")
    private Integer status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysConfig{" +
                "id=" + id +
                ", paramKey=" + paramKey +
                ", paramValue=" + paramValue +
                ", status=" + status +
                ", remark=" + remark +
                "}";
    }
}
