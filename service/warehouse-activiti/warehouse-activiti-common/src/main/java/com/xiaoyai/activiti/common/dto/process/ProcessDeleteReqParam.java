package com.xiaoyai.activiti.common.dto.process;

import java.io.Serializable;

/**
 * @author xieyu
 * @date 2020-10-22
 * 流程撤回请求参数
 */
public class ProcessDeleteReqParam implements Serializable {
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

    @Override
    public String toString() {
        return "ProcessDeleteReqParam{" +
                "processInstanceId='" + processInstanceId + '\'' +
                '}';
    }
}
