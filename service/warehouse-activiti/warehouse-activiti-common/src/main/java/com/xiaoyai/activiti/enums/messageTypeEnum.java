package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2021-01-26
 * 消息类型
 */
public enum messageTypeEnum {
    XZ("XZCW", "1001"),
    WX("维修", "1002"),
    RL("RLZY", "1003"),
    PZJC("品质检查", "1005"),
    ;
    private String code;
    private String value;

    messageTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
