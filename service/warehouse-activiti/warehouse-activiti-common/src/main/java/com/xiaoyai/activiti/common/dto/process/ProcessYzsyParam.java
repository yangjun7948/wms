package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-09
 * WY06-印章使用申请流程
 */
public class ProcessYzsyParam extends ProcessParam{
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
     * 用章事由
     */
    private String userBadgeReason;
    /**
     *用章标题
     */
    private String userBadgeTitle;
    /**
     * 相关流程
     */
    private List userBadgeRelatedProcess;
    /**
     * 用章事由说明
     */
    private String userBadgeReasonDescription;
    /**
     * 用章类型
     */
    private String userBadgeType;
    /**
     * 是否外借
     */
    private String borrowFlag;
    /**
     * 印章所属公司
     */
    private String printBadgeBelongCompany;
    /**
     * 印章类型
     */
    private String printBadgeType;
    /**
     * 使用时间
     */
    private String userTime;
    /**
     * 预计归还时间
     */
    private String expectedReturnTime;
    /**
     * 用章范围
     */
    private String userBadgeRange;
    /**
     * 顺序号
     */
    private String sequenceNumber;
    /**
     * 份数(份)
     */
    private String numberOfCopies;
    /**
     * 来文部门单位
     */
    private String communicationDepartmentUnit;
    /**
     * 文件url
     */
    private String fileUrls;
    private String comment;

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

    public String getUserBadgeReason() {
        return userBadgeReason;
    }

    public void setUserBadgeReason(String userBadgeReason) {
        this.userBadgeReason = userBadgeReason;
    }

    public String getUserBadgeTitle() {
        return userBadgeTitle;
    }

    public void setUserBadgeTitle(String userBadgeTitle) {
        this.userBadgeTitle = userBadgeTitle;
    }


    public String getUserBadgeReasonDescription() {
        return userBadgeReasonDescription;
    }

    public void setUserBadgeReasonDescription(String userBadgeReasonDescription) {
        this.userBadgeReasonDescription = userBadgeReasonDescription;
    }

    public String getUserBadgeType() {
        return userBadgeType;
    }

    public void setUserBadgeType(String userBadgeType) {
        this.userBadgeType = userBadgeType;
    }

    public String getBorrowFlag() {
        return borrowFlag;
    }

    public void setBorrowFlag(String borrowFlag) {
        this.borrowFlag = borrowFlag;
    }

    public String getPrintBadgeBelongCompany() {
        return printBadgeBelongCompany;
    }

    public void setPrintBadgeBelongCompany(String printBadgeBelongCompany) {
        this.printBadgeBelongCompany = printBadgeBelongCompany;
    }

    public String getPrintBadgeType() {
        return printBadgeType;
    }

    public void setPrintBadgeType(String printBadgeType) {
        this.printBadgeType = printBadgeType;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String getExpectedReturnTime() {
        return expectedReturnTime;
    }

    public void setExpectedReturnTime(String expectedReturnTime) {
        this.expectedReturnTime = expectedReturnTime;
    }

    public String getUserBadgeRange() {
        return userBadgeRange;
    }

    public void setUserBadgeRange(String userBadgeRange) {
        this.userBadgeRange = userBadgeRange;
    }

    public String getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(String sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(String numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getCommunicationDepartmentUnit() {
        return communicationDepartmentUnit;
    }

    public void setCommunicationDepartmentUnit(String communicationDepartmentUnit) {
        this.communicationDepartmentUnit = communicationDepartmentUnit;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public List getUserBadgeRelatedProcess() {
        return userBadgeRelatedProcess;
    }

    public void setUserBadgeRelatedProcess(List userBadgeRelatedProcess) {
        this.userBadgeRelatedProcess = userBadgeRelatedProcess;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
