package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-12-10
 */
public class ProcessCirculationDto {
    /**
     * 流程节点名称
     * */
    private String nodeName;
    /**
     * 流程节点处理人id
     * */
    private String employeeId;
    /**
     * 流程节点开始时间
     * */
    private String startTime;
    /**
     * 流程节点结束时间
     * */
    private String endTime;
    /**
     * 流程节点消耗时间
     * */
    private String dunration;
    /**
     * 流程节点任务id
     * */
    private String taskId;
    /**
     * 流程节点处理人名称
     * */
    private String employeeName;
    /**
     * 流程节点意见
     * */
    private String processComment;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDunration() {
        return dunration;
    }

    public void setDunration(String dunration) {
        this.dunration = dunration;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getProcessComment() {
        return processComment;
    }

    public void setProcessComment(String processComment) {
        this.processComment = processComment;
    }

    @Override
    public String toString() {
        return "ProcessCirculationDto{" +
                "nodeName='" + nodeName + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", dunration='" + dunration + '\'' +
                ", taskId='" + taskId + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", processComment='" + processComment + '\'' +
                '}';
    }
}
