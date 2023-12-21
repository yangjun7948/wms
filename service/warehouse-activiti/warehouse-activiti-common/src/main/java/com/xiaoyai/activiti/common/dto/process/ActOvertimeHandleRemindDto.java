package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2021-08-11
 */
public class ActOvertimeHandleRemindDto {
    /**
     * '主键id'
     */
    private String remindId;
    /**
     * '主键id'
     */
    private String processInstanceId;
    /**
     * '主键id'
     */
    private String statusCd;
    /**
     * '主键id'
     */
    private String createTime;

    public String getRemindId() {
        return remindId;
    }

    public void setRemindId(String remindId) {
        this.remindId = remindId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
