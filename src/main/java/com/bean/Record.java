package com.bean;

public class Record {
    private int id;//告诫信息编号;
    private int appId;//告诫信息所属的应用
    private int ruleId;//告诫信息所属规则
    private int isEmail;//告诫信息是否通过邮件告警;
    private int isPhone;//告警信息是否通过短信告警
    private int isColse;//告警信息是否处理完毕
    private String line;//原始日志信息



    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", appId=" + appId +
                ", ruleId=" + ruleId +
                ", isEmail=" + isEmail +
                ", isPhone=" + isPhone +
                ", isColse=" + isColse +
                ", line='" + line + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public int getRuleId() {
        return ruleId;
    }

    public void setRuleId(int ruleId) {
        this.ruleId = ruleId;
    }

    public int getIsEmail() {
        return isEmail;
    }

    public void setIsEmail(int isEmail) {
        this.isEmail = isEmail;
    }

    public int getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(int isPhone) {
        this.isPhone = isPhone;
    }

    public int getIsColse() {
        return isColse;
    }

    public void setIsColse(int isColse) {
        this.isColse = isColse;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
