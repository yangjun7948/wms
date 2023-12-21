package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2021-01-14
 * 流程可退结点查询参数
 */
public class ProcessApprovalWithdrawReqParam {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "ProcessApprovalWithdrawReqParam{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
