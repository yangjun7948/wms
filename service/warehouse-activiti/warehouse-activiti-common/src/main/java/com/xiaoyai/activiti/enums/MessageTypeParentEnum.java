package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2021-01-26
 * 消息类型
 */
public enum MessageTypeParentEnum {
    LCTX("XZCW", "1"),
    WX("维修", "3"),
    WBTX("RLZY", "4"),
    PZJC("品质检查", "2"),
    ;
    private String code;
    private String value;

    MessageTypeParentEnum(String code, String value) {
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
