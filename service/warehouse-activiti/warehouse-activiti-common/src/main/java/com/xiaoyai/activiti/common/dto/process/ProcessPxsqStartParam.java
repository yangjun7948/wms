package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @author xieyu
 * @date 2020-11-08
 * 培训申请请求参数
 */
public class ProcessPxsqStartParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;
    private List participantIds;
    private String participantName;
    private String trainingFeedbackPersonId;
    private String trainingFeedbackPersonName;
    private String trainingLocation;
    private String trainingUnit;
    private String trainingStartTime;
    private String trainingEndTime;
    private String applicationCourseName;
    private String applicationCourseContent;
    private String trainingNotes;
    private String trainingSummary;
    private String fileUrls;
    private String comment;

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

    public List getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List participantIds) {
        this.participantIds = participantIds;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getTrainingFeedbackPersonId() {
        return trainingFeedbackPersonId;
    }

    public void setTrainingFeedbackPersonId(String trainingFeedbackPersonId) {
        this.trainingFeedbackPersonId = trainingFeedbackPersonId;
    }

    public String getTrainingFeedbackPersonName() {
        return trainingFeedbackPersonName;
    }

    public void setTrainingFeedbackPersonName(String trainingFeedbackPersonName) {
        this.trainingFeedbackPersonName = trainingFeedbackPersonName;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public String getTrainingUnit() {
        return trainingUnit;
    }

    public void setTrainingUnit(String trainingUnit) {
        this.trainingUnit = trainingUnit;
    }

    public String getTrainingStartTime() {
        return trainingStartTime;
    }

    public void setTrainingStartTime(String trainingStartTime) {
        this.trainingStartTime = trainingStartTime;
    }

    public String getTrainingEndTime() {
        return trainingEndTime;
    }

    public void setTrainingEndTime(String trainingEndTime) {
        this.trainingEndTime = trainingEndTime;
    }

    public String getApplicationCourseName() {
        return applicationCourseName;
    }

    public void setApplicationCourseName(String applicationCourseName) {
        this.applicationCourseName = applicationCourseName;
    }

    public String getApplicationCourseContent() {
        return applicationCourseContent;
    }

    public void setApplicationCourseContent(String applicationCourseContent) {
        this.applicationCourseContent = applicationCourseContent;
    }

    public String getTrainingNotes() {
        return trainingNotes;
    }

    public void setTrainingNotes(String trainingNotes) {
        this.trainingNotes = trainingNotes;
    }

    public String getTrainingSummary() {
        return trainingSummary;
    }

    public void setTrainingSummary(String trainingSummary) {
        this.trainingSummary = trainingSummary;
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
