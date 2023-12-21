package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @author xieyu
 * @date 2020-11-08
 * 垃圾清运请求参数
 */
public class ProcessLjqyStartParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;
    private String applyDate;
    private List selectGarbagePhotoList;
    private String totalAmount;
    private String projectAddress;
    private String paymentUnitName;
    private String opposingUnit;
    private String contractPaymentMethod;
    private String reasonExplanation;
    private String contractTotalAmount;
    private String cumulativeSpent;
    private String cumulativeInvoiceAmountReceived;
    private String unpaidAmount;
    private String payThisApplication;
    private String actualPayment;
    private String invoiceReceived;
    private String paymentMethod;
    private String beneficiaryAccountName;
    private String otherAccountBank;
    private String otherAccount;
    private String fileUrls;
    private String relatedContractId;
    private String relatedContractName;
    private List relatedContractList;
    private String comment;

    public List getRelatedContractList() {
        return relatedContractList;
    }

    public void setRelatedContractList(List relatedContractList) {
        this.relatedContractList = relatedContractList;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public List getSelectGarbagePhotoList() {
        return selectGarbagePhotoList;
    }

    public void setSelectGarbagePhotoList(List selectGarbagePhotoList) {
        this.selectGarbagePhotoList = selectGarbagePhotoList;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getPaymentUnitName() {
        return paymentUnitName;
    }

    public void setPaymentUnitName(String paymentUnitName) {
        this.paymentUnitName = paymentUnitName;
    }

    public String getOpposingUnit() {
        return opposingUnit;
    }

    public void setOpposingUnit(String opposingUnit) {
        this.opposingUnit = opposingUnit;
    }

    public String getContractPaymentMethod() {
        return contractPaymentMethod;
    }

    public void setContractPaymentMethod(String contractPaymentMethod) {
        this.contractPaymentMethod = contractPaymentMethod;
    }

    public String getReasonExplanation() {
        return reasonExplanation;
    }

    public void setReasonExplanation(String reasonExplanation) {
        this.reasonExplanation = reasonExplanation;
    }

    public String getContractTotalAmount() {
        return contractTotalAmount;
    }

    public void setContractTotalAmount(String contractTotalAmount) {
        this.contractTotalAmount = contractTotalAmount;
    }

    public String getCumulativeSpent() {
        return cumulativeSpent;
    }

    public void setCumulativeSpent(String cumulativeSpent) {
        this.cumulativeSpent = cumulativeSpent;
    }

    public String getCumulativeInvoiceAmountReceived() {
        return cumulativeInvoiceAmountReceived;
    }

    public void setCumulativeInvoiceAmountReceived(String cumulativeInvoiceAmountReceived) {
        this.cumulativeInvoiceAmountReceived = cumulativeInvoiceAmountReceived;
    }

    public String getUnpaidAmount() {
        return unpaidAmount;
    }

    public void setUnpaidAmount(String unpaidAmount) {
        this.unpaidAmount = unpaidAmount;
    }

    public String getPayThisApplication() {
        return payThisApplication;
    }

    public void setPayThisApplication(String payThisApplication) {
        this.payThisApplication = payThisApplication;
    }

    public String getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(String actualPayment) {
        this.actualPayment = actualPayment;
    }

    public String getInvoiceReceived() {
        return invoiceReceived;
    }

    public void setInvoiceReceived(String invoiceReceived) {
        this.invoiceReceived = invoiceReceived;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBeneficiaryAccountName() {
        return beneficiaryAccountName;
    }

    public void setBeneficiaryAccountName(String beneficiaryAccountName) {
        this.beneficiaryAccountName = beneficiaryAccountName;
    }

    public String getOtherAccountBank() {
        return otherAccountBank;
    }

    public void setOtherAccountBank(String otherAccountBank) {
        this.otherAccountBank = otherAccountBank;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getRelatedContractId() {
        return relatedContractId;
    }

    public void setRelatedContractId(String relatedContractId) {
        this.relatedContractId = relatedContractId;
    }

    public String getRelatedContractName() {
        return relatedContractName;
    }

    public void setRelatedContractName(String relatedContractName) {
        this.relatedContractName = relatedContractName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
