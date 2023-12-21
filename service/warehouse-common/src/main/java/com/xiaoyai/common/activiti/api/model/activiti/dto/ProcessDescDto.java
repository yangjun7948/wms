package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020/5/13
 * 流程业务数据封装
 */
public class ProcessDescDto {
    /**
     * 流程资源id
     */
    private String taskDescId;
    /**
     * 流程描述名称
     */
    private String taskDescription;
    /**
     * 流程发起人
     */
    private String taskApplyUserId;

    private String taskApplyUserName;
    /**
     * 流程业务id
     */
    private String businessId;
    /**
     * 流程id
     */
    private String procId;
    /**
     * 流程紧急程度
     */
    private String emergencyLevel;
    /**
     * 流程实例id
     */
    private String processInstanceId;
    /**
     * 流程状态
     */
    private String statusType;
    /**
     * 流程申请时间
     */
    private String createTime;
    /**
     * 流程附件地址
     */
    private String fileAddress;

    /**
     * 流程名称
     * */
    private String procName;

    /**
     * 流程code
     * */
    private String processCode;

    /**
     * 表单数据
     */
    private String formValue;

    /**
     * 流程描述补充 task_description_supplement
     */
    private String taskDescriptionSupplement;

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    private String procNum;

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getTaskDescId() {
        return taskDescId;
    }

    public void setTaskDescId(String taskDescId) {
        this.taskDescId = taskDescId;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskApplyUserId() {
        return taskApplyUserId;
    }

    public void setTaskApplyUserId(String taskApplyUserId) {
        this.taskApplyUserId = taskApplyUserId;
    }

    public String getTaskApplyUserName() {
        return taskApplyUserName;
    }

    public void setTaskApplyUserName(String taskApplyUserName) {
        this.taskApplyUserName = taskApplyUserName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }


    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getFormValue() {
        return formValue;
    }

    public void setFormValue(String formValue) {
        this.formValue = formValue;
    }

    public String getTaskDescriptionSupplement() {
        return taskDescriptionSupplement;
    }

    public void setTaskDescriptionSupplement(String taskDescriptionSupplement) {
        this.taskDescriptionSupplement = taskDescriptionSupplement;
    }
}
