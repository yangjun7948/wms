package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @author xieyu
 * @date 2020-11-04
 * 钉钉修改流程请求参数
 */
public class ProcessRcfybxsqlcStartParam {
    /**
     * 流程名称
     */
    private String procName;
    /**
     * 流程编号
     */
    private String procNum;
    /**
     * 流程code
     */
    private String procCode;
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 申请人id
     */
    private String applyUserId;
    /**
     * 申请人名称
     */
    private String applyUserName;

    private String hasReference;
    /**
     * 费用支付主题
     */
    private String paymentSubjectName;
    private String paymentSubjectId;
    private List<ReimbursementDetailDto> reimbursements;

    private String expenseType;
    private String referencesName;
    private String referencesId;
    private String expenseRemark;
    private String bearDepartmentName;
    private String expenseAmount;
    private String capitalAmount;
    private String owedAmount;
    private String writeOffAmount;
    private String handleAmount;
    private String accountName;
    private String accountBank;
    private String accountNum;

    private String invoiceDate;

    private String lastExpenseDate;

    private String occurDate;

    private String paymentMethod;

    private String fileUrls;
    private String comment;

    public String getPaymentSubjectName() {
        return paymentSubjectName;
    }

    public void setPaymentSubjectName(String paymentSubjectName) {
        this.paymentSubjectName = paymentSubjectName;
    }

    public String getPaymentSubjectId() {
        return paymentSubjectId;
    }

    public void setPaymentSubjectId(String paymentSubjectId) {
        this.paymentSubjectId = paymentSubjectId;
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

    public String getHasReference() {
        return hasReference;
    }

    public void setHasReference(String hasReference) {
        this.hasReference = hasReference;
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public List<ReimbursementDetailDto> getReimbursements() {
        return reimbursements;
    }

    public void setReimbursements(List<ReimbursementDetailDto> reimbursements) {
        this.reimbursements = reimbursements;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getReferencesName() {
        return referencesName;
    }

    public void setReferencesName(String referencesName) {
        this.referencesName = referencesName;
    }

    public String getReferencesId() {
        return referencesId;
    }

    public void setReferencesId(String referencesId) {
        this.referencesId = referencesId;
    }

    public String getExpenseRemark() {
        return expenseRemark;
    }

    public void setExpenseRemark(String expenseRemark) {
        this.expenseRemark = expenseRemark;
    }

    public String getBearDepartmentName() {
        return bearDepartmentName;
    }

    public void setBearDepartmentName(String bearDepartmentName) {
        this.bearDepartmentName = bearDepartmentName;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getCapitalAmount() {
        return capitalAmount;
    }

    public void setCapitalAmount(String capitalAmount) {
        this.capitalAmount = capitalAmount;
    }

    public String getOwedAmount() {
        return owedAmount;
    }

    public void setOwedAmount(String owedAmount) {
        this.owedAmount = owedAmount;
    }

    public String getWriteOffAmount() {
        return writeOffAmount;
    }

    public void setWriteOffAmount(String writeOffAmount) {
        this.writeOffAmount = writeOffAmount;
    }

    public String getHandleAmount() {
        return handleAmount;
    }

    public void setHandleAmount(String handleAmount) {
        this.handleAmount = handleAmount;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getLastExpenseDate() {
        return lastExpenseDate;
    }

    public void setLastExpenseDate(String lastExpenseDate) {
        this.lastExpenseDate = lastExpenseDate;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
