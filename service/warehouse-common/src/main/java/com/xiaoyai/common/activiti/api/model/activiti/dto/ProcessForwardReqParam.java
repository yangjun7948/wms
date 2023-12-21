package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2021-01-21
 * 流程转发参数
 */
public class ProcessForwardReqParam {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 被转发人id
     * */
    private String forwardEmployeeId;

    /**
     * 被转发人名称
     * */
    private String forwardEmployeeName;
    /**
     * 转发意见
     * */
    private String forwardComment;

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

    public String getForwardEmployeeId() {
        return forwardEmployeeId;
    }

    public void setForwardEmployeeId(String forwardEmployeeId) {
        this.forwardEmployeeId = forwardEmployeeId;
    }

    public String getForwardEmployeeName() {
        return forwardEmployeeName;
    }

    public void setForwardEmployeeName(String forwardEmployeeName) {
        this.forwardEmployeeName = forwardEmployeeName;
    }

    public String getForwardComment() {
        return forwardComment;
    }

    public void setForwardComment(String forwardComment) {
        this.forwardComment = forwardComment;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "ProcessForwardReqParam{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", forwardEmployeeId='" + forwardEmployeeId + '\'' +
                ", forwardEmployeeName='" + forwardEmployeeName + '\'' +
                ", forwardComment='" + forwardComment + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
