package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2021-01-21
 * 流程转办参数
 */
public class ProcessTransferReqParam {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 被转办人id
     * */
    private String transferEmployeeId;

    /**
     * 被转办人名称
     * */
    private String transferEmployeeName;
    /**
     * 转办意见
     * */
    private String transferComment;

    /**
     * 员工id
     * */
    private String employeeId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTransferEmployeeId() {
        return transferEmployeeId;
    }

    public void setTransferEmployeeId(String transferEmployeeId) {
        this.transferEmployeeId = transferEmployeeId;
    }

    public String getTransferEmployeeName() {
        return transferEmployeeName;
    }

    public void setTransferEmployeeName(String transferEmployeeName) {
        this.transferEmployeeName = transferEmployeeName;
    }

    public String getTransferComment() {
        return transferComment;
    }

    public void setTransferComment(String transferComment) {
        this.transferComment = transferComment;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "ProcessTransferReqParam{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", transferEmployeeId='" + transferEmployeeId + '\'' +
                ", transferEmployeeName='" + transferEmployeeName + '\'' +
                ", transferComment='" + transferComment + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
