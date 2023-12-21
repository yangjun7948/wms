package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2020-11-05
 */
public class ProcessQspflcParam extends ProcessParam{
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
     * 请示报告名称
     */
    private String requestReportName;
    /**
     * 请示报告内容
     */
    private String requestReportContent;

    /**
     * 附件名称
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

    public String getRequestReportName() {
        return requestReportName;
    }

    public void setRequestReportName(String requestReportName) {
        this.requestReportName = requestReportName;
    }

    public String getRequestReportContent() {
        return requestReportContent;
    }

    public void setRequestReportContent(String requestReportContent) {
        this.requestReportContent = requestReportContent;
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
