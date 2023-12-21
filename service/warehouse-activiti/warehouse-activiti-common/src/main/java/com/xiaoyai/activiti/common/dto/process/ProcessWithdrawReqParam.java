package com.xiaoyai.activiti.common.dto.process;

import java.io.Serializable;

/**
 * @author xieyu
 * @date 2020-10-22
 * 流程撤回请求参数
 */
public class ProcessWithdrawReqParam implements Serializable {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 流程code
     * */
    private String procCode;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    @Override
    public String toString() {
        return "ProcessWithdrawReqParam{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", procCode='" + procCode + '\'' +
                '}';
    }
}
