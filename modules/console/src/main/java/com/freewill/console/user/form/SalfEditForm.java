package com.freewill.console.user.form;


public class SalfEditForm  {

    private Integer userId;

    //头像
    private String headImage;

    //个性介绍
    private String introduce;

    //0男 1女
    private Integer sex;

    //标签的json串
    private String labelJson;

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLabelJson() {
        return labelJson;
    }

    public void setLabelJson(String labelJson) {
        this.labelJson = labelJson;
    }

}
