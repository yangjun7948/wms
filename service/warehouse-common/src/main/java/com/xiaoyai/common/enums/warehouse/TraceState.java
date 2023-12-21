package com.xiaoyai.common.enums.warehouse;

/**
 * 物品溯源状态
 * @author junyang
 * @date 2023/9/15
 */
public enum TraceState {
    NEED_SCAN("0", "待扫码"),
    IN_WAREHOUSE_APPROVE("1", "入库待审核"),
    IN_WAREHOUSE("2", "已入库"),
    OUT_WAREHOUSE_APPROVE("3", "出库待审核"),
    OUT_WAREHOUSE("4", "已出库"),
    USED("5", "已使用");
    private final String code;
    private final String info;

    TraceState(String code, String info) {
        this.code = code;
        this.info = info;
    }
    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
