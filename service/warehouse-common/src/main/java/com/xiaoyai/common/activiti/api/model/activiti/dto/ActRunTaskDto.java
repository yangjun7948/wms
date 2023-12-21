package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2021-01-23
 */
public class ActRunTaskDto {

    private String id;
    private String processInstanceId;
    private String name;
    private String taskDefinitionKey;
    private String assignee;
    private String delegation;
    private int priority;
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    @Override
    public String toString() {
        return "ActRunTaskDto{" +
                "id='" + id + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", name='" + name + '\'' +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", assignee='" + assignee + '\'' +
                ", delegation='" + delegation + '\'' +
                ", priority=" + priority +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
