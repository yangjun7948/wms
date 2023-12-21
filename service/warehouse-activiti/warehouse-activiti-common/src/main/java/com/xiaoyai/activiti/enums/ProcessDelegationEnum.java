package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2021-01-22
 * 流程委托处理状态
 */
public enum ProcessDelegationEnum {
    //流程意见征询待处理
    PENDING("PENDING","待处理"),
    //流程意见征询已处理
    RESOLVED("RESOLVED","已处理"),
    //流程转发
    FORWARD("FORWARD","流程转发"),
    //手动转发
    MANUAL_FORWARD("MANUALFORWARD","人工转发"),
    ;
    private String value;
    private String desc;
    ProcessDelegationEnum(String value, String desc) {
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
