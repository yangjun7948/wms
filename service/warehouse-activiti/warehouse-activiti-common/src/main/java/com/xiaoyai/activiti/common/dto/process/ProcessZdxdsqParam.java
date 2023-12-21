package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2020-11-06
 * 制度修订申请流程
 */
public class ProcessZdxdsqParam extends ProcessParam {
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
     * 制度名称
     */
    private String systemName;

    private String systemControlDepartmentId;
    /**
     * 制度主控部门
     */
    private String systemControlDepartmentName;
    /**
     * 修订人id
     */
    private String revisedUserId;
    /**
     * 修订人名称
     */
    private String revisedUserName;
    /**
     * 修订原因
     */
    private String revisedReason;
    /**
     * 修改/新建制度
     */
    private String modifyOrNewSystem;
    /**
     * 新系统名称
     */
    private String newSystemName;
    /**
     *原制度
     */
    private String oldSystem;
    /**
     * 修订内容
     */
    private String revisedContent;
    /**
     * 附件
     */
    private String fileUrls;
    private String comment;

    public String getSystemControlDepartmentId() {
        return systemControlDepartmentId;
    }

    public void setSystemControlDepartmentId(String systemControlDepartmentId) {
        this.systemControlDepartmentId = systemControlDepartmentId;
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

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemControlDepartmentName() {
        return systemControlDepartmentName;
    }

    public void setSystemControlDepartmentName(String systemControlDepartmentName) {
        this.systemControlDepartmentName = systemControlDepartmentName;
    }

    public String getRevisedUserId() {
        return revisedUserId;
    }

    public void setRevisedUserId(String revisedUserId) {
        this.revisedUserId = revisedUserId;
    }

    public String getRevisedUserName() {
        return revisedUserName;
    }

    public void setRevisedUserName(String revisedUserName) {
        this.revisedUserName = revisedUserName;
    }

    public String getRevisedReason() {
        return revisedReason;
    }

    public void setRevisedReason(String revisedReason) {
        this.revisedReason = revisedReason;
    }

    public String getModifyOrNewSystem() {
        return modifyOrNewSystem;
    }

    public void setModifyOrNewSystem(String modifyOrNewSystem) {
        this.modifyOrNewSystem = modifyOrNewSystem;
    }

    public String getNewSystemName() {
        return newSystemName;
    }

    public void setNewSystemName(String newSystemName) {
        this.newSystemName = newSystemName;
    }

    public String getOldSystem() {
        return oldSystem;
    }

    public void setOldSystem(String oldSystem) {
        this.oldSystem = oldSystem;
    }

    public String getRevisedContent() {
        return revisedContent;
    }

    public void setRevisedContent(String revisedContent) {
        this.revisedContent = revisedContent;
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
