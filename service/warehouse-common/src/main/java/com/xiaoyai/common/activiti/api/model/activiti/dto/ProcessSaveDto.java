package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020/5/8
 * 保存流程实体类
 */
public class ProcessSaveDto {

    /**
     * 任务描述id
     * */
    private String taskDescId;
    /**
     * 流程标题描述
     * */
    private String taskDescription;
    /**
     * 流程发起人id
     * */
    private String taskApplyUserId;

    /**
     * 发起人姓名
     * */
    private String userName;
    /**
     * 具体业务id
     * */
    private String businessId;
    /**
     * 流程id
     * */
    private String procId;
    /**
     * 任务紧急程度
     * */
    private String emergencyLevel;
    /**
     * 任务id
     * */
    private String taskId;
    /**
     * 任务执行id
     * */
    private String executionId;
    /**
     * 任务实例id
     * */
    private String procInstId;
    /**
     * 任务定义id
     * */
    private String procDefId;
    /**
     * 任务状态类型 流程状态0：流程已保存 1：流程已提交
     * */
    private String statusType;
    /**
     * 任务创建时间
     * */
    private String createTime;

    public String getTaskDescId() {
        return taskDescId;
    }

    public void setTaskDescId(String taskDescId) {
        this.taskDescId = taskDescId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskApplyUserId() {
        return taskApplyUserId;
    }

    public void setTaskApplyUserId(String taskApplyUserId) {
        this.taskApplyUserId = taskApplyUserId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(String procInstId) {
        this.procInstId = procInstId;
    }

    public String getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(String procDefId) {
        this.procDefId = procDefId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ProcessSaveDto{" +
                "taskDescId='" + taskDescId + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskApplyUserId='" + taskApplyUserId + '\'' +
                ", userName='" + userName + '\'' +
                ", businessId='" + businessId + '\'' +
                ", procId='" + procId + '\'' +
                ", emergencyLevel='" + emergencyLevel + '\'' +
                ", taskId='" + taskId + '\'' +
                ", executionId='" + executionId + '\'' +
                ", procInstId='" + procInstId + '\'' +
                ", procDefId='" + procDefId + '\'' +
                ", statusType='" + statusType + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
