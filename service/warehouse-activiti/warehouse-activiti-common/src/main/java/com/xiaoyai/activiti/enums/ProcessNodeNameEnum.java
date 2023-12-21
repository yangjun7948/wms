package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-08-21
 * 流程节点名称
 */
public enum ProcessNodeNameEnum {
    //发起人
    STARTER("发起人","提交申请"),

    ;

    private String value;
    private String desc;
    ProcessNodeNameEnum(String value, String desc) {
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
