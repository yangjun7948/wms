package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/9/13
 */
public enum ApplyState {
    INIT("0", "草稿"),
    APPROVE("1", "待审核"),
    OUTING("2", "待出库"),
    INING("3", "待入库"),
    COMPLETE("4", "已完成");
    private final String code;
    private final String info;

    ApplyState(String code, String info) {
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
