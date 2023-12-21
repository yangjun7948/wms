package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-08-21
 * 流程节点名称
 */
public enum ProcessMyRequestTypeEnum {
    //全部
    ALL("-1", "全部"),
    RUNNING("0", "未结束"),
    END("1", "已结束"),

    ;

    private String value;
    private String desc;

    ProcessMyRequestTypeEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
