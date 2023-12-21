package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2021-01-12
 * 合同到期提醒流程发起参数
 */
public class ProcessRemindHtdqtxStartParam {
    /**
     * 合同名称
     * */
    private String contractName;
    /**
     * 合同流程num
     * */
    private String procNum;

    /**
     * 合同流程标题
     * */
    private String procName;

    /**
     * 合同流程实例id
     * */
    private String processInstanceId;

    /**
     * 合同到期时间
     * */
    private String remindDate;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    @Override
    public String toString() {
        return "{" +
                "contractName='" + contractName + '\'' +
                ", procNum='" + procNum + '\'' +
                ", procName='" + procName + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", remindDate='" + remindDate + '\'' +
                '}';
    }
}
