package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/11/14
 */
public enum TransferState {
    INIT("1", "草稿"),
    TRANSFER("2", "调拨中"),
    COMPLETE("3", "已完成");
    private final String code;
    private final String info;

    TransferState(String code, String info) {
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
