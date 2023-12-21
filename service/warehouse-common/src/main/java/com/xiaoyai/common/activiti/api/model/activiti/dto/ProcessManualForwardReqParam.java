package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.util.List;

/**
 * @author xieyu
 * @date 2021-01-21
 * 流程手动转发参数
 */
public class ProcessManualForwardReqParam {
    /**
     * 被转发人id列表
     * */
    private List<String> forwardEmployeeIds;
    /**
     * 流程类型
     * */
    private List<String> checkedProcess;

    /**
     * 员工id
     * */
    private String employeeId;

    public List<String> getForwardEmployeeIds() {
        return forwardEmployeeIds;
    }

    public void setForwardEmployeeIds(List<String> forwardEmployeeIds) {
        this.forwardEmployeeIds = forwardEmployeeIds;
    }

    public List<String> getCheckedProcess() {
        return checkedProcess;
    }

    public void setCheckedProcess(List<String> checkedProcess) {
        this.checkedProcess = checkedProcess;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
}
