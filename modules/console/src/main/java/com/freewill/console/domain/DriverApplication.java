package com.freewill.console.domain;

import java.util.Date;

/**
 * @author sanma
 */
public class DriverApplication {
    private Integer id;

    private Integer pid;

    private String uuid;

    private String name;

    private String phone;

    private Integer type;

    private Integer status;

    private String url;

    private String posterUrl;

    private String extCol1;

    private String extCol2;

    private String extCol3;

    private String remark;

    private Integer countNum;

    private Integer referrerId;
    private String referrerName;
    private String referrerPhone;
    private Date createTime;

    private Date updateTime;

    public Integer getCountNum() {
        return countNum;
    }

    public void setCountNum(Integer countNum) {
        this.countNum = countNum;
    }

    public Integer getReferrerId() {
        return referrerId;
    }

    public void setReferrerId(Integer referrerId) {
        this.referrerId = referrerId;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerPhone() {
        return referrerPhone;
    }

    public void setReferrerPhone(String referrerPhone) {
        this.referrerPhone = referrerPhone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getExtCol1() {
        return extCol1;
    }

    public void setExtCol1(String extCol1) {
        this.extCol1 = extCol1;
    }

    public String getExtCol2() {
        return extCol2;
    }

    public void setExtCol2(String extCol2) {
        this.extCol2 = extCol2;
    }

    public String getExtCol3() {
        return extCol3;
    }

    public void setExtCol3(String extCol3) {
        this.extCol3 = extCol3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}