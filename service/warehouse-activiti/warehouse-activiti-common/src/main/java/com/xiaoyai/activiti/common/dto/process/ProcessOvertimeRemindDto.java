package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2021-08-11
 */
public class ProcessOvertimeRemindDto {
    private String instanceId;
    private String assignee;
    private String createTime;

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
