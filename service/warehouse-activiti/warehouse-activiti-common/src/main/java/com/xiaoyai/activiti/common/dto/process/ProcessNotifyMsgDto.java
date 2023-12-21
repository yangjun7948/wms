package com.xiaoyai.activiti.common.dto.process;

import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2020-12-10
 */
public class ProcessNotifyMsgDto {
    private String processInstanceId;
    /**
     * 流程模板code
     */
    private String processTemplateCode;
    /**
     * 流程名称
     */
    private String processName;
    /**
     * 申请人名称
     */
    private String applyUser;
    /**
     * 申请时间
     */
    private String applyDate;

    /**
     * 接收人的微信openId
     */
    private String receiveOpenId;

    private List<String> appCids;

    private List<String> employeeIdList;

    public Map getNoticeInfo() {
        return noticeInfo;
    }

    public void setNoticeInfo(Map noticeInfo) {
        this.noticeInfo = noticeInfo;
    }

    public List<String> getAppCids() {
        return appCids;
    }

    public void setAppCids(List<String> appCids) {
        this.appCids = appCids;
    }

    /**
     * 通知内容
     */
    private Map noticeInfo;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * 当前节点名称
     */
    private String nodeName;
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    private String taskId;

    public String getReceiveOpenId() {
        return receiveOpenId;
    }

    public void setReceiveOpenId(String receiveOpenId) {
        this.receiveOpenId = receiveOpenId;
    }


    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }
    public String getProcessTemplateCode() {
        return processTemplateCode;
    }

    public void setProcessTemplateCode(String processTemplateCode) {
        this.processTemplateCode = processTemplateCode;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Override
    public String toString() {
        return "ProcessNotifyMsgDto{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", processTemplateCode='" + processTemplateCode + '\'' +
                ", processName='" + processName + '\'' +
                ", applyUser='" + applyUser + '\'' +
                ", applyDate='" + applyDate + '\'' +
                ", receiveOpenId='" + receiveOpenId + '\'' +
                ", noticeInfo=" + noticeInfo +
                ", nodeName='" + nodeName + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }

    public List<String> getEmployeeIdList() {
        return employeeIdList;
    }

    public void setEmployeeIdList(List<String> employeeIdList) {
        this.employeeIdList = employeeIdList;
    }
}

