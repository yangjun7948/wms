package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2021-01-21
 * 流程意见征询参数
 */
public class ProcessOpinionSeekReqParam {
    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 被征询人id
     * */
    private String opinionSeekerId;

    /**
     * 被征询人名称
     * */
    private String opinionSeekerName;
    /**
     * 征询意见
     * */
    private String opinionSeekComment;

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

    public String getOpinionSeekerId() {
        return opinionSeekerId;
    }

    public void setOpinionSeekerId(String opinionSeekerId) {
        this.opinionSeekerId = opinionSeekerId;
    }

    public String getOpinionSeekerName() {
        return opinionSeekerName;
    }

    public void setOpinionSeekerName(String opinionSeekerName) {
        this.opinionSeekerName = opinionSeekerName;
    }

    public String getOpinionSeekComment() {
        return opinionSeekComment;
    }

    public void setOpinionSeekComment(String opinionSeekComment) {
        this.opinionSeekComment = opinionSeekComment;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "ProcessOpinionSeekReqParam{" +
                "processInstanceId='" + processInstanceId + '\'' +
                ", opinionSeekerId='" + opinionSeekerId + '\'' +
                ", opinionSeekerName='" + opinionSeekerName + '\'' +
                ", opinionSeekComment='" + opinionSeekComment + '\'' +
                ", employeeId='" + employeeId + '\'' +
                '}';
    }
}
