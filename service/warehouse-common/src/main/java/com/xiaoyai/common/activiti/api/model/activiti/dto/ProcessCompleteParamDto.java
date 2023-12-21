package com.xiaoyai.common.activiti.api.model.activiti.dto;


import com.alibaba.fastjson2.JSONObject;

import java.util.Map;

/**
 * @author xieyu
 * @date 2020/5/25
 * 流程审批请求参数
 */
public class ProcessCompleteParamDto {
    /**
     * 用户id
     * */
    private String userId;

    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 任务id
     * */
    private String taskId;

    /**
     * 审批意见
     * */
    private String comment;

    /**
     * 审批意见附件
     * */
    private String commentFile;

    /**
     * 用户安全级别
     * */
    private int securityLevel;

    /**
     * 流程流向条件
     * */
    private Map conditionParam;

    /**
     * 表单数据
     * */
    private JSONObject formDate;

    /**
     *相关参数
     * */
    private Map relatedParameterData;

    /**
     * 流程owner
     * */
    private String owner;

    /**
     * 流程是否是退回到发起人状态
     * */
    private String withdrawEditStatus;

    /**
     * 委派状态
     * */
    private String delegation;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    private String employeeId;

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public Map getConditionParam() {
        return conditionParam;
    }

    public void setConditionParam(Map conditionParam) {
        this.conditionParam = conditionParam;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public JSONObject getFormDate() {
        return formDate;
    }

    public void setFormDate(JSONObject formDate) {
        this.formDate = formDate;
    }

    public Map getRelatedParameterData() {
        return relatedParameterData;
    }

    public void setRelatedParameterData(Map relatedParameterData) {
        this.relatedParameterData = relatedParameterData;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getWithdrawEditStatus() {
        return withdrawEditStatus;
    }

    public void setWithdrawEditStatus(String withdrawEditStatus) {
        this.withdrawEditStatus = withdrawEditStatus;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public String getCommentFile() {
        return commentFile;
    }

    public void setCommentFile(String commentFile) {
        this.commentFile = commentFile;
    }

    @Override
    public String toString() {
        return "ProcessCompleteParamDto{" +
                "userId='" + userId + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", comment='" + comment + '\'' +
                ", commentFile='" + commentFile + '\'' +
                ", securityLevel=" + securityLevel +
                ", conditionParam=" + conditionParam +
                ", formDate=" + formDate +
                ", relatedParameterData=" + relatedParameterData +
                ", owner='" + owner + '\'' +
                ", withdrawEditStatus='" + withdrawEditStatus + '\'' +
                ", delegation='" + delegation + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
