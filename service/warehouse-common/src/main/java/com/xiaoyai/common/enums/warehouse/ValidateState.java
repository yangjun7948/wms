package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/9/23
 */
public enum ValidateState {
    YES("Y", "待扫码"),
    NO("N", "已入库");
    private final String code;
    private final String info;

    ValidateState(String code, String info) {
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
