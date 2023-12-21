package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2021-07-16
 */
public class ProcessStartStatisticDto {
    private String taskApplyUserId;
    private String taskApplyUserName;
    private String count;

    public String getTaskApplyUserId() {
        return taskApplyUserId;
    }

    public void setTaskApplyUserId(String taskApplyUserId) {
        this.taskApplyUserId = taskApplyUserId;
    }

    public String getTaskApplyUserName() {
        return taskApplyUserName;
    }

    public void setTaskApplyUserName(String taskApplyUserName) {
        this.taskApplyUserName = taskApplyUserName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
