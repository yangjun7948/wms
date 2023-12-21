package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-12-21
 * 流程发起保存类型
 */
public enum ProcessStartOrSaveEnum {
    //流程保存
    PROCESS_SAVE("1","流程保存"),
    //流程发起
    PROCESS_START("2","流程发起"),

    ;

    private String value;
    private String desc;
    ProcessStartOrSaveEnum(String value, String desc) {
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
