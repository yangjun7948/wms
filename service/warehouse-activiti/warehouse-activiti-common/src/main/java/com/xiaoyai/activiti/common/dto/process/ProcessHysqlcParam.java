package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-06
 * 制度修订申请流程
 */
public class ProcessHysqlcParam extends ProcessParam {
    /**
     * 部门id
     */
    private String departmentId;
    /**
     * 部门名称
     */
    private String departmentName;
    /**
     * 申请人id
     */
    private String applyUserId;
    /**
     * 申请人名称
     */
    private String applyUserName;


    private String hostUserName;
    private String hostUserId;
    private String meetingContactUserName;
    private String meetingContactUserId;
    private String meetingRecorderUserName;
    private String meetingRecorderUserId;
    private String meetingName;
    private String meetingType;
    private String meetingTitle;
    private String meetingContent;
    private String attendeesUserName;
    private List<String> attendeesUserId;
    private String otherUserName;
    private String otherUserId;
    private String meetingAddress;
    private String externalMeetingPlace;
    private String  meetingStartTime;
    private String meetingEndTime;
    private String timeLength;

    private List<ServicesProcessDto> services;
    private String comment;

    /**
     * 附件
     */
    private String fileUrls;


    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
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

    public String getHostUserName() {
        return hostUserName;
    }

    public void setHostUserName(String hostUserName) {
        this.hostUserName = hostUserName;
    }

    public String getHostUserId() {
        return hostUserId;
    }

    public void setHostUserId(String hostUserId) {
        this.hostUserId = hostUserId;
    }

    public String getMeetingContactUserName() {
        return meetingContactUserName;
    }

    public void setMeetingContactUserName(String meetingContactUserName) {
        this.meetingContactUserName = meetingContactUserName;
    }

    public String getMeetingContactUserId() {
        return meetingContactUserId;
    }

    public void setMeetingContactUserId(String meetingContactUserId) {
        this.meetingContactUserId = meetingContactUserId;
    }

    public String getMeetingRecorderUserName() {
        return meetingRecorderUserName;
    }

    public void setMeetingRecorderUserName(String meetingRecorderUserName) {
        this.meetingRecorderUserName = meetingRecorderUserName;
    }

    public String getMeetingRecorderUserId() {
        return meetingRecorderUserId;
    }

    public void setMeetingRecorderUserId(String meetingRecorderUserId) {
        this.meetingRecorderUserId = meetingRecorderUserId;
    }

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getMeetingType() {
        return meetingType;
    }

    public void setMeetingType(String meetingType) {
        this.meetingType = meetingType;
    }

    public String getMeetingTitle() {
        return meetingTitle;
    }

    public void setMeetingTitle(String meetingTitle) {
        this.meetingTitle = meetingTitle;
    }

    public String getMeetingContent() {
        return meetingContent;
    }

    public void setMeetingContent(String meetingContent) {
        this.meetingContent = meetingContent;
    }

    public String getAttendeesUserName() {
        return attendeesUserName;
    }

    public void setAttendeesUserName(String attendeesUserName) {
        this.attendeesUserName = attendeesUserName;
    }

    public List<String> getAttendeesUserId() {
        return attendeesUserId;
    }

    public void setAttendeesUserId(List<String> attendeesUserId) {
        this.attendeesUserId = attendeesUserId;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public void setOtherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
    }

    public String getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getMeetingAddress() {
        return meetingAddress;
    }

    public void setMeetingAddress(String meetingAddress) {
        this.meetingAddress = meetingAddress;
    }

    public String getExternalMeetingPlace() {
        return externalMeetingPlace;
    }

    public void setExternalMeetingPlace(String externalMeetingPlace) {
        this.externalMeetingPlace = externalMeetingPlace;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(String meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public String getTimeLength() {
        return timeLength;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }

    public List<ServicesProcessDto> getServices() {
        return services;
    }

    public void setServices(List<ServicesProcessDto> services) {
        this.services = services;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
