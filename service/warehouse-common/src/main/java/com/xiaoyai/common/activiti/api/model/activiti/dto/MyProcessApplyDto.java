package com.xiaoyai.common.activiti.api.model.activiti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author xieyu
 * @date 2020/5/8
 * 流程详情列表
 */
public class MyProcessApplyDto {
    /**
     * 任务描述id
     */
    private String taskDescId;
    /**
     * 任务id
     */
    private String taskId;
    /**
     * 任务标题描述
     */
    private String taskDescription;
    /**
     * 任务创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 创建人姓名
     */
    private String userName;
    /**
     * 任务实例id
     */
    private String processInstanceId;

    /**
     * 任务状态详情见枚举ProcessStatusEnum
     */
    private String statusType;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 附件资源地址
     */
    private String fileAddress;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 流程名称
     */
    private String procName;

    /**
     * 流程名称(小程序)
     */
    private String name;

    /**
     * 流程id
     */
    private String procId;

    /**
     * 流程所属类别名称
     */
    private String procTypeName;
    /**
     * 流程所属类别
     */
    private String procType;

    /**
     * 页面跳转路由
     */
    private String pageRouting;

    /**
     * 流程num
     */
    private String procNum;

    /**
     * 是否展示撤回按钮
     */
    private boolean showWithdrawFlag;

    /**
     * 流程code
     * */
    private String processCode;

    /**
     * 流程是不是测试类别
     * */
    private boolean processIsTest;

    /**
     * 任务拥有者
     * */
    private String owner;

    /**
     * 委托状态 详见 ProcessDelegationEnum 枚举
     * */
    private String delegation;

    private String suspensionState;

    private String taskDescriptionSupplement;
    /**
     * 任务当前处理人 我的请求用到 若此值为空 则用户对此流程无提交权限
     */
    private String curTaskHandler;
    private String curTaskId;
    private String applyUserName;

    private String belongProjectName;

    public String getCurTaskId() {
        return curTaskId;
    }

    public void setCurTaskId(String curTaskId) {
        this.curTaskId = curTaskId;
    }

    public String getCurTaskHandler() {
        return curTaskHandler;
    }

    public void setCurTaskHandler(String curTaskHandler) {
        this.curTaskHandler = curTaskHandler;
    }

    public String getUntreatedUser() {
        return untreatedUser;
    }

    public void setUntreatedUser(String untreatedUser) {
        this.untreatedUser = untreatedUser;
    }

    private String untreatedUser;

    public String getPageRouting() {
        return pageRouting;
    }

    public void setPageRouting(String pageRouting) {
        this.pageRouting = pageRouting;
    }

    public String getProcType() {
        return procType;
    }

    public void setProcType(String procType) {
        this.procType = procType;
    }

    public String getProcTypeName() {
        return procTypeName;
    }

    public void setProcTypeName(String procTypeName) {
        this.procTypeName = procTypeName;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getTaskDescId() {
        return taskDescId;
    }

    public void setTaskDescId(String taskDescId) {
        this.taskDescId = taskDescId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    public boolean isShowWithdrawFlag() {
        return showWithdrawFlag;
    }

    public void setShowWithdrawFlag(boolean showWithdrawFlag) {
        this.showWithdrawFlag = showWithdrawFlag;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isProcessIsTest() {
        return processIsTest;
    }

    public void setProcessIsTest(boolean processIsTest) {
        this.processIsTest = processIsTest;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public String getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(String suspensionState) {
        this.suspensionState = suspensionState;
    }

    public String getTaskDescriptionSupplement() {
        return taskDescriptionSupplement;
    }

    public void setTaskDescriptionSupplement(String taskDescriptionSupplement) {
        this.taskDescriptionSupplement = taskDescriptionSupplement;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getBelongProjectName() {
        return belongProjectName;
    }

    public void setBelongProjectName(String belongProjectName) {
        this.belongProjectName = belongProjectName;
    }

    @Override
    public String toString() {
        return "MyProcessApplyDto{" +
                "taskDescId='" + taskDescId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", createTime='" + createTime + '\'' +
                ", userName='" + userName + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", statusType='" + statusType + '\'' +
                ", nodeName='" + nodeName + '\'' +
                ", fileAddress='" + fileAddress + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", procName='" + procName + '\'' +
                ", name='" + name + '\'' +
                ", procId='" + procId + '\'' +
                ", procTypeName='" + procTypeName + '\'' +
                ", procType='" + procType + '\'' +
                ", pageRouting='" + pageRouting + '\'' +
                ", procNum='" + procNum + '\'' +
                ", showWithdrawFlag=" + showWithdrawFlag +
                ", processCode='" + processCode + '\'' +
                ", processIsTest=" + processIsTest +
                ", owner='" + owner + '\'' +
                ", delegation='" + delegation + '\'' +
                ", suspensionState='" + suspensionState + '\'' +
                ", taskDescriptionSupplement='" + taskDescriptionSupplement + '\'' +
                ", curTaskHandler='" + curTaskHandler + '\'' +
                ", curTaskId='" + curTaskId + '\'' +
                ", applyUserName='" + applyUserName + '\'' +
                ", belongProjectName='" + belongProjectName + '\'' +
                ", untreatedUser='" + untreatedUser + '\'' +
                '}';
    }
}
