package com.freewill.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.freewill.common.group.AddGroup;
import com.freewill.common.group.EditGroup;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 系统用户
 * </p>
 *
 * @author GaoJian
 * @since 2018-11-18
 */
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 用户名
     */
    @TableField("username")
    @NotBlank(message = "用户名不能为空", groups = {AddGroup.class, EditGroup.class})
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * 盐
     */
    @TableField("salt")
    private String salt;

    /**
     * 邮箱
     */
    @TableField("email")
    @NotBlank(message = "邮箱不能为空", groups = {AddGroup.class, EditGroup.class})
    @Email(message = "邮箱格式不正确", groups = {AddGroup.class, EditGroup.class})
    private String email;

    /**
     * 手机号
     */
    @TableField("mobile")
    private String mobile;

    /**
     * 角色ID列表
     */
    @TableField(exist = false)
    private List<Long> roleIdList;

    /**
     * 状态  0：禁用   1：正常
     */
    @TableField("status")
    private Integer status;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    @NotNull(message = "部门不能为空", groups = {AddGroup.class, EditGroup.class})
    private Long deptId;

    /**
     * 部门名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String credentialsSalt() {
        return username + salt;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", username=" + username +
                ", password=" + password +
                ", salt=" + salt +
                ", email=" + email +
                ", mobile=" + mobile +
                ", status=" + status +
                ", deptId=" + deptId +
                ", createTime=" + createTime +
                "}";
    }
}
