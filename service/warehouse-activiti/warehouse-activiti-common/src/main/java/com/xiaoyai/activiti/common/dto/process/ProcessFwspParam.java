package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2020-11-06
 * 发文审批
 */
public class ProcessFwspParam extends ProcessParam {
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
     * 文件标题
     */
    private String fileTitle;
    /**
     * 发文字号
     */
    private String postTextNumber;

    /**
     * 拟稿部门
     */
    private String draftDeptId;
    private String draftDeptName;
    /**
     * 拟稿人
     */
    private String draftUserId;
    private String draftUserName;
    /**
     * 文件类型
     */
    private String fileType;

    /**
     * 其他类型
     */
    private String otherType;
    /**
     * 拟稿日期
     */
    private String draftDate;
    /**
     * 公文正文 (附件)
     */
    private String officeText;
    /**
     * 主题词
     */
    private String themeWords;
    /**
     * 附件
     */
    private String fileUrls;
    /**
     * 接收单位
     */
    private String receiveDeptId;
    private String receiveDeptName;
    /**
     * 抄送单位
     */
    private String copyDept;
    /**
     * 说明
     */
    private String explain;
    /**
     * 是否需要使用公章
     */
    private String isUseSeal;
    /**
     * 印发日期
     */
    private String printDate;
    /**
     * 用印份数
     */
    private String printNum;

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

    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }

    public String getPostTextNumber() {
        return postTextNumber;
    }

    public void setPostTextNumber(String postTextNumber) {
        this.postTextNumber = postTextNumber;
    }

    public String getDraftDeptId() {
        return draftDeptId;
    }

    public void setDraftDeptId(String draftDeptId) {
        this.draftDeptId = draftDeptId;
    }

    public String getDraftDeptName() {
        return draftDeptName;
    }

    public void setDraftDeptName(String draftDeptName) {
        this.draftDeptName = draftDeptName;
    }

    public String getDraftUserId() {
        return draftUserId;
    }

    public void setDraftUserId(String draftUserId) {
        this.draftUserId = draftUserId;
    }

    public String getDraftUserName() {
        return draftUserName;
    }

    public void setDraftUserName(String draftUserName) {
        this.draftUserName = draftUserName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getOtherType() {
        return otherType;
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType;
    }

    public String getDraftDate() {
        return draftDate;
    }

    public void setDraftDate(String draftDate) {
        this.draftDate = draftDate;
    }

    public String getOfficeText() {
        return officeText;
    }

    public void setOfficeText(String officeText) {
        this.officeText = officeText;
    }

    public String getThemeWords() {
        return themeWords;
    }

    public void setThemeWords(String themeWords) {
        this.themeWords = themeWords;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getReceiveDeptId() {
        return receiveDeptId;
    }

    public void setReceiveDeptId(String receiveDeptId) {
        this.receiveDeptId = receiveDeptId;
    }

    public String getReceiveDeptName() {
        return receiveDeptName;
    }

    public void setReceiveDeptName(String receiveDeptName) {
        this.receiveDeptName = receiveDeptName;
    }

    public String getCopyDept() {
        return copyDept;
    }

    public void setCopyDept(String copyDept) {
        this.copyDept = copyDept;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getIsUseSeal() {
        return isUseSeal;
    }

    public void setIsUseSeal(String isUseSeal) {
        this.isUseSeal = isUseSeal;
    }

    public String getPrintDate() {
        return printDate;
    }

    public void setPrintDate(String printDate) {
        this.printDate = printDate;
    }

    public String getPrintNum() {
        return printNum;
    }

    public void setPrintNum(String printNum) {
        this.printNum = printNum;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
