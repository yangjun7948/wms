package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020/5/22
 * 任务处理人
 */
public class ProcessAssigneeDto {
    /**
     * 用户id
     * */
    private String userId;

    /**
     * 用户名
     * */
    private String userName;

    /**
     * 用户职位id
     * */
    private String jobTitleId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJobTitleId() {
        return jobTitleId;
    }

    public void setJobTitleId(String jobTitleId) {
        this.jobTitleId = jobTitleId;
    }
}
