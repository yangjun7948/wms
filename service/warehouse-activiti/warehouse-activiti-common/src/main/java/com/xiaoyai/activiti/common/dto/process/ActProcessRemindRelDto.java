package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2021-01-12
 */
public class ActProcessRemindRelDto {
    /**
     * 流程提醒id
     */
    private String actProcessRemindRelId;
    /**
     * '流程code'
     */
    private String processCode;
    /**
     * '提醒流程code'
     */
    private String remindProcessCode;

    /**
     * 流程num
     * */
    private String procNum;

    /**
     * 提醒时间字段
     * */
    private String remindDateField;

    public String getActProcessRemindRelId() {
        return actProcessRemindRelId;
    }

    public void setActProcessRemindRelId(String actProcessRemindRelId) {
        this.actProcessRemindRelId = actProcessRemindRelId;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getRemindProcessCode() {
        return remindProcessCode;
    }

    public void setRemindProcessCode(String remindProcessCode) {
        this.remindProcessCode = remindProcessCode;
    }

    public String getRemindDateField() {
        return remindDateField;
    }

    public void setRemindDateField(String remindDateField) {
        this.remindDateField = remindDateField;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }
}
