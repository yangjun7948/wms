package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-06
 * 合同会签审批流程
 */
public class ProcessHthqspParam extends ProcessParam {
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
    /**
     * *合同名称
     */
    private String contractTitle;
    /**
     * 合同类型
     */
    private String contractType;
    /**
     * 此合同为模板合同
     */
    private String contractTemplate;

    /**
     * 支付合同模板
     */
    private String payContractTemplate;

    /**
     * 当前税点
     */
    private String taxPoint;

    /**
     * 是否为历史合同
     */
    private String historicalContractFlag;
    /**
     * 历史合同已支付(元)
     */
    private String historicalContractPaid;

    /**
     * 合同编号
     */
    private String contractNo;
    /**
     * 拟签订日期
     */
    private String proposedSigningDate;

    /**
     * 合同到期类型
     */
    private String contractDateType;

    /**
     * 合同结束日期
     */
    private String contractEndDate;
    /**
     * 合同页码
     */
    private String contractPageNumber;
    /**
     * 我方名称
     */
    private String ourName;
    /**
     * 我方联系人
     */
    private String ourContactUserId;
    private String ourContactUserName;
    /**
     * 我方联系电话
     */
    private String ourContactNumber;
    /**
     * 对方名称
     */
    private String otherName;
    /**
     * 对方联系人
     */
    private String otherContactUserId;
    private String otherContactUserName;
    /**
     * 对方联系电话
     */
    private String otherContactNumber;
    /**
     * 对方地址
     */
    private String otherAddress;
    /**
     * 姓名名称
     */
    private String nameName;
    /**
     * 合同概要
     */
    private String contractSummary;
    /**
     * 合同付款条件及说明
     */
    private String contractPaymentInstructions;
    /**
     * 合同金额(元)
     */
    private String contractAmount;
    /**
     * 已支付金额(元)
     */
    private String paidAmount;
    /**
     * 剩余支付金额(元)
     */
    private String remainingPaymentAmount;
    /**
     * 收款单位
     */
    private String receivingUnit;
    /**
     * 开户行
     */
    private String accountBank;
    /**
     * 账号
     */
    private String account;
    /**
     * 合同正文
     */
    private String mainFileUrls;

    /**
     * 合同附件
     */
    private String fileUrls;
    private String comment;

    /**
     *付款条件
     */
    private List<payDetailDto> payDetailList;
    /**
     *履约保证金
     */
    private List<performanceDetaiDto> performanceDetailList;

    public String getContractTemplate() {
        return contractTemplate;
    }

    public void setContractTemplate(String contractTemplate) {
        this.contractTemplate = contractTemplate;
    }

    public String getPayContractTemplate() {
        return payContractTemplate;
    }

    public void setPayContractTemplate(String payContractTemplate) {
        this.payContractTemplate = payContractTemplate;
    }

    public String getTaxPoint() {
        return taxPoint;
    }

    public void setTaxPoint(String taxPoint) {
        this.taxPoint = taxPoint;
    }

    public List<payDetailDto> getPayDetailList() {
        return payDetailList;
    }

    public void setPayDetailList(List<payDetailDto> payDetailList) {
        this.payDetailList = payDetailList;
    }

    public List<performanceDetaiDto> getPerformanceDetailList() {
        return performanceDetailList;
    }

    public void setPerformanceDetailList(List<performanceDetaiDto> performanceDetailList) {
        this.performanceDetailList = performanceDetailList;
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

    public String getContractTitle() {
        return contractTitle;
    }

    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getHistoricalContractFlag() {
        return historicalContractFlag;
    }

    public void setHistoricalContractFlag(String historicalContractFlag) {
        this.historicalContractFlag = historicalContractFlag;
    }

    public String getHistoricalContractPaid() {
        return historicalContractPaid;
    }

    public void setHistoricalContractPaid(String historicalContractPaid) {
        this.historicalContractPaid = historicalContractPaid;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProposedSigningDate() {
        return proposedSigningDate;
    }

    public void setProposedSigningDate(String proposedSigningDate) {
        this.proposedSigningDate = proposedSigningDate;
    }

    public String getContractDateType() {
        return contractDateType;
    }

    public void setContractDateType(String contractDateType) {
        this.contractDateType = contractDateType;
    }

    public String getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(String contractEndDate) {
        this.contractEndDate = contractEndDate;
    }

    public String getContractPageNumber() {
        return contractPageNumber;
    }

    public void setContractPageNumber(String contractPageNumber) {
        this.contractPageNumber = contractPageNumber;
    }

    public String getOurName() {
        return ourName;
    }

    public void setOurName(String ourName) {
        this.ourName = ourName;
    }

    public String getOurContactUserId() {
        return ourContactUserId;
    }

    public void setOurContactUserId(String ourContactUserId) {
        this.ourContactUserId = ourContactUserId;
    }

    public String getOurContactUserName() {
        return ourContactUserName;
    }

    public void setOurContactUserName(String ourContactUserName) {
        this.ourContactUserName = ourContactUserName;
    }

    public String getOurContactNumber() {
        return ourContactNumber;
    }

    public void setOurContactNumber(String ourContactNumber) {
        this.ourContactNumber = ourContactNumber;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherContactUserId() {
        return otherContactUserId;
    }

    public void setOtherContactUserId(String otherContactUserId) {
        this.otherContactUserId = otherContactUserId;
    }

    public String getOtherContactUserName() {
        return otherContactUserName;
    }

    public void setOtherContactUserName(String otherContactUserName) {
        this.otherContactUserName = otherContactUserName;
    }

    public String getOtherContactNumber() {
        return otherContactNumber;
    }

    public void setOtherContactNumber(String otherContactNumber) {
        this.otherContactNumber = otherContactNumber;
    }

    public String getOtherAddress() {
        return otherAddress;
    }

    public void setOtherAddress(String otherAddress) {
        this.otherAddress = otherAddress;
    }

    public String getNameName() {
        return nameName;
    }

    public void setNameName(String nameName) {
        this.nameName = nameName;
    }

    public String getContractSummary() {
        return contractSummary;
    }

    public void setContractSummary(String contractSummary) {
        this.contractSummary = contractSummary;
    }

    public String getContractPaymentInstructions() {
        return contractPaymentInstructions;
    }

    public void setContractPaymentInstructions(String contractPaymentInstructions) {
        this.contractPaymentInstructions = contractPaymentInstructions;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getRemainingPaymentAmount() {
        return remainingPaymentAmount;
    }

    public void setRemainingPaymentAmount(String remainingPaymentAmount) {
        this.remainingPaymentAmount = remainingPaymentAmount;
    }

    public String getReceivingUnit() {
        return receivingUnit;
    }

    public void setReceivingUnit(String receivingUnit) {
        this.receivingUnit = receivingUnit;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getMainFileUrls() {
        return mainFileUrls;
    }

    public void setMainFileUrls(String mainFileUrls) {
        this.mainFileUrls = mainFileUrls;
    }

}
