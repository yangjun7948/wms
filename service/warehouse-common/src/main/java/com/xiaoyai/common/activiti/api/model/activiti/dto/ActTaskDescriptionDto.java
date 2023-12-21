package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xieyu
 * @date 2020/8/13
 * 流程标题描述信息
 */
public class ActTaskDescriptionDto implements Serializable {
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
    private String executionId;
    private String procInstId;
    private String procDefId;
    /**
     * 流程状态0：流程已保存 1：流程已提交
     * */
    private String statusType;
    /**
     * 创建时间
     * */
    private String createTime;
    /**
     * 文件传输路径
     * */
    private String fileAddress;
    /**
     * 用户所在小区id
     * */
    private String communityId;

    private String procNum;

    private String taskApplyUserName;

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

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    public String getTaskApplyUserName() {
        return taskApplyUserName;
    }

    public void setTaskApplyUserName(String taskApplyUserName) {
        this.taskApplyUserName = taskApplyUserName;
    }
}
