package com.xiaoyai.common.enums.warehouse;

/**
 * @author junyang
 * @date 2023/11/26
 */
public enum ApplyType {
    PERSON("2", "个人"),
    COMPANY("1", "公司");
    private final String code;
    private final String info;

    ApplyType(String code, String info) {
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
