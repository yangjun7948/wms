package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2020-11-07
 * 印章制发、废止 、移交申请流程
 */
public class ProcessYzzfParam extends ProcessParam {
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
     **章类型
     */
    private String badgeType;
    /**
     *印章所属公司
     */
    private String printBadgeBelongCompany;
    /**
     *申请章类
     */
    private String applyBadgeType;
    /**
     *印章内容
     */
    private String printBadgeContent;
    /**
     *章次数
     */
    private String badgeTimes;
    /**
     *用章次数(次)
     */
    private String userBadgeTimes;
    /**
     *印章形状
     */
    private String printBadgeShape;
    /**
     *印章字体
     */
    private String printBadgeFont;
    /**
     *印章制造材料
     */
    private String printBadgeMaterials;
    /**
     *印章尺寸
     */
    private String printBadgeSize;
    /**
     *移交部门
     */
    private String transferDepartmentId;
    /**
     *移交部门
     */
    private String transferDepartmentName;
    /**
     *移交人
     */
    private String transferUserId;
    private String transferUserName;
    /**
     *接受部门
     */
    private String receivingDepartmentId;
    private String receivingDepartmentName;
    /**
     *接受人
     */
    private String receivingUserId;
    private String receivingUserName;
    /**
     *申请刻印原因
     */
    private String applyPrintBadgeReason;
    /**
     *文件链接
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

    public String getBadgeType() {
        return badgeType;
    }

    public void setBadgeType(String badgeType) {
        this.badgeType = badgeType;
    }

    public String getPrintBadgeBelongCompany() {
        return printBadgeBelongCompany;
    }

    public void setPrintBadgeBelongCompany(String printBadgeBelongCompany) {
        this.printBadgeBelongCompany = printBadgeBelongCompany;
    }

    public String getApplyBadgeType() {
        return applyBadgeType;
    }

    public void setApplyBadgeType(String applyBadgeType) {
        this.applyBadgeType = applyBadgeType;
    }

    public String getPrintBadgeContent() {
        return printBadgeContent;
    }

    public void setPrintBadgeContent(String printBadgeContent) {
        this.printBadgeContent = printBadgeContent;
    }

    public String getBadgeTimes() {
        return badgeTimes;
    }

    public void setBadgeTimes(String badgeTimes) {
        this.badgeTimes = badgeTimes;
    }

    public String getUserBadgeTimes() {
        return userBadgeTimes;
    }

    public void setUserBadgeTimes(String userBadgeTimes) {
        this.userBadgeTimes = userBadgeTimes;
    }

    public String getPrintBadgeShape() {
        return printBadgeShape;
    }

    public void setPrintBadgeShape(String printBadgeShape) {
        this.printBadgeShape = printBadgeShape;
    }

    public String getPrintBadgeFont() {
        return printBadgeFont;
    }

    public void setPrintBadgeFont(String printBadgeFont) {
        this.printBadgeFont = printBadgeFont;
    }

    public String getPrintBadgeMaterials() {
        return printBadgeMaterials;
    }

    public void setPrintBadgeMaterials(String printBadgeMaterials) {
        this.printBadgeMaterials = printBadgeMaterials;
    }

    public String getPrintBadgeSize() {
        return printBadgeSize;
    }

    public void setPrintBadgeSize(String printBadgeSize) {
        this.printBadgeSize = printBadgeSize;
    }

    public String getTransferDepartmentId() {
        return transferDepartmentId;
    }

    public void setTransferDepartmentId(String transferDepartmentId) {
        this.transferDepartmentId = transferDepartmentId;
    }

    public String getTransferDepartmentName() {
        return transferDepartmentName;
    }

    public void setTransferDepartmentName(String transferDepartmentName) {
        this.transferDepartmentName = transferDepartmentName;
    }

    public String getTransferUserId() {
        return transferUserId;
    }

    public void setTransferUserId(String transferUserId) {
        this.transferUserId = transferUserId;
    }

    public String getTransferUserName() {
        return transferUserName;
    }

    public void setTransferUserName(String transferUserName) {
        this.transferUserName = transferUserName;
    }

    public String getReceivingDepartmentId() {
        return receivingDepartmentId;
    }

    public void setReceivingDepartmentId(String receivingDepartmentId) {
        this.receivingDepartmentId = receivingDepartmentId;
    }

    public String getReceivingDepartmentName() {
        return receivingDepartmentName;
    }

    public void setReceivingDepartmentName(String receivingDepartmentName) {
        this.receivingDepartmentName = receivingDepartmentName;
    }

    public String getReceivingUserId() {
        return receivingUserId;
    }

    public void setReceivingUserId(String receivingUserId) {
        this.receivingUserId = receivingUserId;
    }

    public String getReceivingUserName() {
        return receivingUserName;
    }

    public void setReceivingUserName(String receivingUserName) {
        this.receivingUserName = receivingUserName;
    }

    public String getApplyPrintBadgeReason() {
        return applyPrintBadgeReason;
    }

    public void setApplyPrintBadgeReason(String applyPrintBadgeReason) {
        this.applyPrintBadgeReason = applyPrintBadgeReason;
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
