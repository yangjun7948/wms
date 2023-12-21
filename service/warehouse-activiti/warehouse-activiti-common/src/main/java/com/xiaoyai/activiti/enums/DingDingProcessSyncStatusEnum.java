package com.xiaoyai.activiti.enums;

/**
 * @author xieyu
 * @date 2020-08-21
 */
public enum DingDingProcessSyncStatusEnum {
    //同步成功
    SYNC_SUCCESS(1,"同步成功"),
    //同步失败
    SYNC_FAIL(-1,"同步失败"),
    //无需同步
    SYNC_NO_NEED(0,"无新流程需同步")
    ;

    private int value;
    private String desc;
    DingDingProcessSyncStatusEnum(int value, String desc) {
        this.value= value;
        this.desc=desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
