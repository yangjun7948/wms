package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/9/23
 */
public enum DictCodes {
    OUT_RECEIPT_TYPE("outreceipt_type", "出库类型"),
    IN_RECEIPT_TYPE("inreceipt_type", "入库类型"),
    GOODS_SPECIFICATION("goods_specification", "物品规格"),
    GOODS_UNIT("goods_unit", "物品单位"),
    GOODS_COLOR("goods_color", "物品颜色");
    private final String code;
    private final String info;

    DictCodes(String code, String info) {
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
