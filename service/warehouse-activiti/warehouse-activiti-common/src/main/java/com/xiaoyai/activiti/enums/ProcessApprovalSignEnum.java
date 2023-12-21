package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2021-01-26
 * 流程审批标志
 */
public enum ProcessApprovalSignEnum {
    SUBMIT(1,"提交"),
    APPROVE(2,"批准"),
    CONSULTATION(3,"意见征询"),
    CONSULTATION_REPLY(4,"意见征询回复"),
    FORWARD(5,"转发"),
    FORWARD_REPLY(6,"转发批注"),
    TRANSFER(7,"转办"),
    SAND_BACK(8,"退回"),
    APPROVAL_BACK(8,"审批撤回"),
    ;
    private int code;
    private String value;
    ProcessApprovalSignEnum(int code, String value) {
        this.code= code;
        this.value=value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
