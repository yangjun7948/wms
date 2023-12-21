package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xieyu
 * @date 2020/5/21
 * 流程发起请求参数
 */
public class ProcessStartParamDto implements Serializable {
    /**
     * 流程id
     * */
    private String procId;

    /**
     * 流程参数
     * */
    private Map paramMap;

    /**
     * 用户id
     * */
    private String userId;

    /**
     * 用户名称
     * */
    private String userName;

    /**
     * 流程名称
     * */
    private String procName;

    /**
     * 紧急程度
     * */
    private String emergencyLevel;

    /**
     * 文件存放地址
     * */
    private String fileAddress;

    /**
     * 流程状态 0保存 1提交
     * */
    private String statusType = "1";

    /**
     * 用户安全级别
     * */
    private int securityLevel;

    /**
     * 用户直接领导
     * */
    private String directLeader;

    /**
     * 用户职务
     * */
    private String jobTitleId;

    /**
     * 流程流向条件
     * */
    private Map conditionParam;

    /**
     * 提价类型 0：保存 1：提交
     * */
    private String submitType;

    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     *流程业务id
     * */
    private String businessKey;

    /**
     * 部门id
     * */
    private String orgId;

    /**
     * 用户小区id
     * */
    private String communityId;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getSubmitType() {
        return submitType;
    }

    public void setSubmitType(String submitType) {
        this.submitType = submitType;
    }

    public Map getConditionParam() {
        return conditionParam;
    }

    public void setConditionParam(Map conditionParam) {
        this.conditionParam = conditionParam;
    }

    public String getDirectLeader() {
        return directLeader;
    }

    public void setDirectLeader(String directLeader) {
        this.directLeader = directLeader;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }

    public int getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(int securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public Map getParamMap() {
        return paramMap;
    }

    public void setParamMap(Map paramMap) {
        this.paramMap = paramMap;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

}
