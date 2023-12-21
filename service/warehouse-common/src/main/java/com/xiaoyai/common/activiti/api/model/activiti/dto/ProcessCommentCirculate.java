package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/9
 * 流程流转意见
 */
public class ProcessCommentCirculate {
    /**
     * 用户姓名
     * */
    private String userName;
    /**
     * 用户所属部门
     * */
    private String department;
    /**
     * 流程审批意见
     * */
    private String comment;
    /**
     * 流程审批意见文件
     * */
    private List<ProcessCommentFileDto> commentFileList;
    /**
     * 流程下一个接收人
     * */
    private String receiver;
    /**
     * 用户审批时间
     * */
    private String approvalTime;
    /**
     * 流程节点名称
     * */
    private String processNodeName;
    /**
     * 节点结果
     * */
    private String approvalResult;

    /**
     * 节点id
     * */
    private String processNodeId;

    public String getProcessNodeId() {
        return processNodeId;
    }

    public void setProcessNodeId(String processNodeId) {
        this.processNodeId = processNodeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalTime(String approvalTime) {
        this.approvalTime = approvalTime;
    }

    public String getProcessNodeName() {
        return processNodeName;
    }

    public void setProcessNodeName(String processNodeName) {
        this.processNodeName = processNodeName;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public List<ProcessCommentFileDto> getCommentFileList() {
        return commentFileList;
    }

    public void setCommentFileList(List<ProcessCommentFileDto> commentFileList) {
        this.commentFileList = commentFileList;
    }
}
