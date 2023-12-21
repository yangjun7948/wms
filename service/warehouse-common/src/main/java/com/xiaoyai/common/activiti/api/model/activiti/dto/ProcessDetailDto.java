package com.xiaoyai.common.activiti.api.model.activiti.dto;


import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/8
 * 退费申请详情
 */
public class ProcessDetailDto {
    /**
     * 退费类型
     * */
    private String applyType;
    /**
     * 任务紧急程度
     * */
    private String emergencyLevel;
    /**
     * 退费收据号
     * */
    private String receiptNumber;
    /**
     * 退费金额
     * */
    private String refudnMoney;

    /**
     * 退费原因
     * */
    private String refundReason;

    /**
     * 退费详情
     * */
    private List list;

    /**
     * 流程流转意见
     * */
    private List<ProcessCommentCirculate> processCommentList;

    /**
     * 流程各节点意见
     * */
    private List nodeCommentList;

    /**
     * 流程附件资源
     * */
    private String fileAddress;

    /**
     * 流程id
     * */
    private String procId;

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public List getNodeCommentList() {
        return nodeCommentList;
    }

    public void setNodeCommentList(List nodeCommentList) {
        this.nodeCommentList = nodeCommentList;
    }

    public List<ProcessCommentCirculate> getProcessCommentList() {
        return processCommentList;
    }

    public void setProcessCommentList(List<ProcessCommentCirculate> processCommentList) {
        this.processCommentList = processCommentList;
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getRefudnMoney() {
        return refudnMoney;
    }

    public void setRefudnMoney(String refudnMoney) {
        this.refudnMoney = refudnMoney;
    }

    public String getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(String refundReason) {
        this.refundReason = refundReason;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    @Override
    public String toString() {
        return "ProcessDetailDto{" +
                "applyType='" + applyType + '\'' +
                ", emergencyLevel='" + emergencyLevel + '\'' +
                ", receiptNumber='" + receiptNumber + '\'' +
                ", refudnMoney='" + refudnMoney + '\'' +
                ", refundReason='" + refundReason + '\'' +
                ", list=" + list +
                ", processCommentList=" + processCommentList +
                ", nodeCommentList=" + nodeCommentList +
                ", fileAddress='" + fileAddress + '\'' +
                ", procId='" + procId + '\'' +
                '}';
    }
}
