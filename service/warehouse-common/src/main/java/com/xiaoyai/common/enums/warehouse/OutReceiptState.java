package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/9/13
 */
public enum OutReceiptState {
    CAOGAO("1", "草稿"),
    DAICHUKU("2", "待出库"),
    APPROVE("3", "待审核"),
    COMPLETE("4", "已完成");
    private final String code;
    private final String info;

    OutReceiptState(String code, String info) {
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
