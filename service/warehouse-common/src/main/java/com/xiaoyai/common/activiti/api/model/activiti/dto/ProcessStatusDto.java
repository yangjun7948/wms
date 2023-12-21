package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020/5/13
 * 流程状态封装
 */
public class ProcessStatusDto {
    /**
     * 操作人
     * */
    private String operateUserName;

    /**
     * 操作状态
     * */
    private String operateStatus;

    /**
     * 接收时间
     * */
    private String startTime;

    /**
     * 操作时间
     * */
    private String endTime;

    /**
     * 耗时
     * */
    private String duration;

    public String getOperateUserName() {
        return operateUserName;
    }

    public void setOperateUserName(String operateUserName) {
        this.operateUserName = operateUserName;
    }

    public String getOperateStatus() {
        return operateStatus;
    }

    public void setOperateStatus(String operateStatus) {
        this.operateStatus = operateStatus;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
