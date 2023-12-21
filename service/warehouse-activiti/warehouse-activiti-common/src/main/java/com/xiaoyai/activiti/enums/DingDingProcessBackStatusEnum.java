package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-08-21
 * 流程审批后返回状态
 */
public enum DingDingProcessBackStatusEnum {
    //新创建
    NEW("NEW","新创建"),
    //运行中
    RUNNING("RUNNING","运行中"),
    //被终止
    TERMINATED("TERMINATED","被终止"),
    //取消
    CANCELED("CANCELED","取消"),
    //取消
    REFUSE("refuse","取消"),
    //完成
    COMPLETED("COMPLETED","完成"),

    ;

    private String value;
    private String desc;
    DingDingProcessBackStatusEnum(String value, String desc) {
        this.value= value;
        this.desc=desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
