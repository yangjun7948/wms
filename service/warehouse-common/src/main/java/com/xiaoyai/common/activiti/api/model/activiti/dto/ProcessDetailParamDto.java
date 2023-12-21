package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020/5/22
 * 查看流程详情参数
 */
public class ProcessDetailParamDto {
    /**
     * 流程实例id
     * */
    private String processInstanceId;
    /**
     * 流程id
     * */
    private String procId;

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
