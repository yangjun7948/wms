package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/11/13
 */
public enum WarehouseType {
    FEIXIU("1", "飞修公司仓库"),
    FEIXIUSHIFU("2", "飞修师傅个人仓库");
    private final String code;
    private final String info;

    WarehouseType(String code, String info) {
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
