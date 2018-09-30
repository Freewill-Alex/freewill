package com.freewill.console.system.entity;

public class Right {
    private Integer id;

    private String rightName;

    private String rightCode;

    private String remark;

    private Integer parentId;

    private Integer type;

    private Integer groupRight;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightCode() {
        return rightCode;
    }

    public void setRightCode(String rightCode) {
        this.rightCode = rightCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getGroupRight() {
        return groupRight;
    }

    public void setGroupRight(Integer groupRight) {
        this.groupRight = groupRight;
    }
}