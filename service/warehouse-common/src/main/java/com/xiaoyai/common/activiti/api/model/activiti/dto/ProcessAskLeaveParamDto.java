package com.xiaoyai.common.activiti.api.model.activiti.dto;


/**
 * @author xieyu
 * @date 2020/5/25
 * 请假调休流程发起请求参数
 */
public class ProcessAskLeaveParamDto extends ProcessStartParamDto {
    /**
     * 请假时长
     * */
    private double leaveTime;

    public double getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(double leaveTime) {
        this.leaveTime = leaveTime;
    }
}
