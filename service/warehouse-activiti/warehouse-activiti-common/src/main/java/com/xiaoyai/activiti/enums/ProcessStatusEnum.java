package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-12-18
 */
public enum ProcessStatusEnum {
    //保存
    SAVE(1,"SAVE"),
    //运行中
    RUNNING(1,"RUNNING"),
    //退回
    WITHDRAW(2,"WITHDRAW"),
    ;

    private int code;
    private String value;

    ProcessStatusEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
