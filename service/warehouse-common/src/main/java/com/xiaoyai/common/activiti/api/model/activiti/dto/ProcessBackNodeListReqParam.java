package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2021-01-14
 * 流程可退结点查询参数
 */
public class ProcessBackNodeListReqParam {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }
}
