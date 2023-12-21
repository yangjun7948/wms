package com.xiaoyai.common.enums.warehouse;

import lombok.Data;

/**
 * @author junyang
 * @date 2023/9/10
 */
public enum ReceiptType {
    InReceipt("1", "入库单"),
    OutReceipt("2", "出库单"),
    TransferReceipt("3", "调拨单"),
    ApplyReceipt("4", "申请单");
    private final String code;
    private final String info;

    ReceiptType(String code, String info) {
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
