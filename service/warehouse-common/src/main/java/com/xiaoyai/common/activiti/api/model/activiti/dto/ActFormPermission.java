package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020-12-17
 * 流程表单编辑dto
 */
public class ActFormPermission {
    /**
     * 表单id
     */
    private String formId;
    /**
     * '流程task节点的id'
     */
    private String taskdefKey;
    /**
     * '表单明细ID'
     */
    private String detailId;
    /**
     * '流程key'
     */
    private String procKey;
    /**
     * '只读状态 0:只读，1:可编辑'
     */
    private String readOnly;

    /**
     * 展示权限
     * */
    private String viewPermission;

    /**
     * 当前流程操作节点标记
     * */
    private String approvalSign;

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getTaskdefKey() {
        return taskdefKey;
    }

    public void setTaskdefKey(String taskdefKey) {
        this.taskdefKey = taskdefKey;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getProcKey() {
        return procKey;
    }

    public void setProcKey(String procKey) {
        this.procKey = procKey;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getViewPermission() {
        return viewPermission;
    }

    public void setViewPermission(String viewPermission) {
        this.viewPermission = viewPermission;
    }

    public String getApprovalSign() {
        return approvalSign;
    }

    public void setApprovalSign(String approvalSign) {
        this.approvalSign = approvalSign;
    }
}
