package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/9/11
 */
public enum ReceiptState {
    INIT("1", "草稿"),
    SCAN("2", "待扫码"),
    APPROVE("3", "待审核"),
    COMPLETE("4", "已完成");
    private final String code;
    private final String info;

    ReceiptState(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
