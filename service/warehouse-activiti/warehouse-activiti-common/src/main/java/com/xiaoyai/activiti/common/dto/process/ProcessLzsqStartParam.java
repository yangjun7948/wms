package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-11-08
 * 离职申请请求参数
 */
public class ProcessLzsqStartParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;
    private String leavingUserName;
    private String leavingUserId;
    private String level;
    private String belongDepartmentId;
    private String belongDepartmentName;
    private String belongPosition;
    private String departmentHeaderId;
    private String departmentHeaderName;
    private String resignationLeaveTime;
    private String entryTime;
    private String estimatedTimeToLeave;
    private String leavingReason;
    private String leavingReasonRemark;
    private String comment;

    public String getLeavingUserName() {
        return leavingUserName;
    }

    public void setLeavingUserName(String leavingUserName) {
        this.leavingUserName = leavingUserName;
    }

    public String getLeavingUserId() {
        return leavingUserId;
    }

    public void setLeavingUserId(String leavingUserId) {
        this.leavingUserId = leavingUserId;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBelongDepartmentId() {
        return belongDepartmentId;
    }

    public void setBelongDepartmentId(String belongDepartmentId) {
        this.belongDepartmentId = belongDepartmentId;
    }

    public String getBelongDepartmentName() {
        return belongDepartmentName;
    }

    public void setBelongDepartmentName(String belongDepartmentName) {
        this.belongDepartmentName = belongDepartmentName;
    }

    public String getBelongPosition() {
        return belongPosition;
    }

    public void setBelongPosition(String belongPosition) {
        this.belongPosition = belongPosition;
    }

    public String getDepartmentHeaderId() {
        return departmentHeaderId;
    }

    public void setDepartmentHeaderId(String departmentHeaderId) {
        this.departmentHeaderId = departmentHeaderId;
    }

    public String getDepartmentHeaderName() {
        return departmentHeaderName;
    }

    public void setDepartmentHeaderName(String departmentHeaderName) {
        this.departmentHeaderName = departmentHeaderName;
    }

    public String getResignationLeaveTime() {
        return resignationLeaveTime;
    }

    public void setResignationLeaveTime(String resignationLeaveTime) {
        this.resignationLeaveTime = resignationLeaveTime;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getEstimatedTimeToLeave() {
        return estimatedTimeToLeave;
    }

    public void setEstimatedTimeToLeave(String estimatedTimeToLeave) {
        this.estimatedTimeToLeave = estimatedTimeToLeave;
    }

    public String getLeavingReason() {
        return leavingReason;
    }

    public void setLeavingReason(String leavingReason) {
        this.leavingReason = leavingReason;
    }

    public String getLeavingReasonRemark() {
        return leavingReasonRemark;
    }

    public void setLeavingReasonRemark(String leavingReasonRemark) {
        this.leavingReasonRemark = leavingReasonRemark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
