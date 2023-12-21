package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author junyang
 * @date 2021-01-23
 */
public class ProcessDeployReqParam {
    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    private String procId;

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    private String procNum;
}
