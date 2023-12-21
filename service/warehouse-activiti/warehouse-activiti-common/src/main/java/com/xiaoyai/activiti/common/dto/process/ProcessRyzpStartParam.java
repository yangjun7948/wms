package com.xiaoyai.activiti.common.dto.process;

import java.util.List;
import java.util.Map;

/**
 * @author xieyu
 * @date 2020-11-04
 * 钉钉修改流程请求参数
 */
public class ProcessRyzpStartParam {
    /**
     * 流程名称
     */
    private String procName;
    /**
     * 流程编号
     */
    private String procNum;
    /**
     * 流程code
     */
    private String procCode;
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

    /**
     * 招聘岗位
     */
    private String recruitmentPosition;

    /**
     * 招聘人数
     */
    private String recruitmentCount;

    /**
     * 希望到岗日期
     */
    private String hopeArriveTime;

    /**
     * 紧急程度
     */
    private String emergencyLevel;

    /**
     * 特急情况说明
     */
    private String situationDescription;

    /**
     * 部门招聘对接人
     */
    private String recruitmentContactId;

    /**
     * 部门招聘对接人名称
     */
    private String recruitmentContactName;

    /**
     * 招聘原因
     */
    private String recruitmentReason;

    /**
     * 拟替换人员
     */
    private String replacementId;

    /**
     * 拟替换人员名称
     */
    private String replacementName;

    /**
     * 相关附件
     */
    private String attachments;

    /**
     * 其他要求
     */
    private String otherRequirement;

    /**
     * 基本要求
     */
    List baseRequireList;
    private List<Map> fileUrlList;
    private String comment;

    /**
     * 原因说明
     */
    private String reasonExplain;

    /**
     * 行政级别
     */
    private String userLevel;

    public String getReasonExplain() {
        return reasonExplain;
    }

    public void setReasonExplain(String reasonExplain) {
        this.reasonExplain = reasonExplain;
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

    public String getRecruitmentPosition() {
        return recruitmentPosition;
    }

    public void setRecruitmentPosition(String recruitmentPosition) {
        this.recruitmentPosition = recruitmentPosition;
    }

    public String getRecruitmentCount() {
        return recruitmentCount;
    }

    public void setRecruitmentCount(String recruitmentCount) {
        this.recruitmentCount = recruitmentCount;
    }

    public String getHopeArriveTime() {
        return hopeArriveTime;
    }

    public void setHopeArriveTime(String hopeArriveTime) {
        this.hopeArriveTime = hopeArriveTime;
    }

    public String getEmergencyLevel() {
        return emergencyLevel;
    }

    public void setEmergencyLevel(String emergencyLevel) {
        this.emergencyLevel = emergencyLevel;
    }

    public String getSituationDescription() {
        return situationDescription;
    }

    public void setSituationDescription(String situationDescription) {
        this.situationDescription = situationDescription;
    }

    public String getRecruitmentContactId() {
        return recruitmentContactId;
    }

    public void setRecruitmentContactId(String recruitmentContactId) {
        this.recruitmentContactId = recruitmentContactId;
    }

    public String getRecruitmentContactName() {
        return recruitmentContactName;
    }

    public void setRecruitmentContactName(String recruitmentContactName) {
        this.recruitmentContactName = recruitmentContactName;
    }

    public String getRecruitmentReason() {
        return recruitmentReason;
    }

    public void setRecruitmentReason(String recruitmentReason) {
        this.recruitmentReason = recruitmentReason;
    }

    public String getReplacementId() {
        return replacementId;
    }

    public void setReplacementId(String replacementId) {
        this.replacementId = replacementId;
    }

    public String getReplacementName() {
        return replacementName;
    }

    public void setReplacementName(String replacementName) {
        this.replacementName = replacementName;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }

    public String getOtherRequirement() {
        return otherRequirement;
    }

    public void setOtherRequirement(String otherRequirement) {
        this.otherRequirement = otherRequirement;
    }

    public List getBaseRequireList() {
        return baseRequireList;
    }

    public void setBaseRequireList(List baseRequireList) {
        this.baseRequireList = baseRequireList;
    }

    public List<Map> getFileUrlList() {
        return fileUrlList;
    }

    public void setFileUrlList(List<Map> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
