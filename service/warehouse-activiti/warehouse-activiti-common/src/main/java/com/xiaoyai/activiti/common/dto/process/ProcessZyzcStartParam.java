package com.xiaoyai.activiti.common.dto.process;

import java.util.List;
import java.util.Map;

/**
 * @author xieyu
 * @date 2020-11-04
 * 职称、职业资格培训考试审批流程
 */
public class ProcessZyzcStartParam {
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

    private String post;
    private String declarationMethod;
    private String companyRegisteredFlag;
    private String startTime;
    private String endTime;
    private String trainingLocation;
    private String examName;
    private String cost;
    private String continuingEducation;

    /**
     * 附件
     */
    private String fileUrls;

    /**
     * 图片数组
     */
    private List<Map> fileList;

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

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDeclarationMethod() {
        return declarationMethod;
    }

    public void setDeclarationMethod(String declarationMethod) {
        this.declarationMethod = declarationMethod;
    }

    public String getCompanyRegisteredFlag() {
        return companyRegisteredFlag;
    }

    public void setCompanyRegisteredFlag(String companyRegisteredFlag) {
        this.companyRegisteredFlag = companyRegisteredFlag;
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

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getContinuingEducation() {
        return continuingEducation;
    }

    public void setContinuingEducation(String continuingEducation) {
        this.continuingEducation = continuingEducation;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public List<Map> getFileList() {
        return fileList;
    }

    public void setFileList(List<Map> fileList) {
        this.fileList = fileList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
