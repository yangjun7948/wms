package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-08-21
 * 流程撤回返回状态
 */
public enum ProcessWithdrawResultEnum {
    //撤回失败
    FAIL("fail","流程已被审批，撤回失败！"),
    //撤回成功
    SUCCESS("success","撤回成功"),

    ;

    private String value;
    private String desc;
    ProcessWithdrawResultEnum(String value, String desc) {
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
