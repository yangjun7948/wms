package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020/5/25
 * 流程回退请求参数
 */
public class ProcessBackFlowParamDto {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 回退原因
     * */
    private String comment;

    /**
     * 流程状态
     * */
    private String statusType;

    /**
     * 流程id
     * */
    private String procId;

    /**
     * 退回至任务结点id
     * */
    private String taskDefinitionId;
    /**
     * 退回至任务结点id
     * */
    private String taskDefinitionName;

    /**
     * 任务id
     */
    private String taskId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTaskDefinitionId() {
        return taskDefinitionId;
    }

    public void setTaskDefinitionId(String taskDefinitionId) {
        this.taskDefinitionId = taskDefinitionId;
    }

    public String getTaskDefinitionName() {
        return taskDefinitionName;
    }

    public void setTaskDefinitionName(String taskDefinitionName) {
        this.taskDefinitionName = taskDefinitionName;
    }

    @Override
    public String toString() {
        return "ProcessBackFlowParamDto{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", userId='" + userId + '\'' +
                ", comment='" + comment + '\'' +
                ", statusType='" + statusType + '\'' +
                ", procId='" + procId + '\'' +
                ", taskDefinitionId='" + taskDefinitionId + '\'' +
                ", taskDefinitionName='" + taskDefinitionName + '\'' +
                '}';
    }
}
