package com.xiaoyai.activiti.common.dto.process;

import java.util.List;
import java.util.Map;

/**
 * @author yaowentao
 * @date 2021-12-20
 * RS05人力资源编制审批流程请求参数
 */
public class ProcessRlzybzsqlcStartParam {
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
     * 新建部门
     */
    private String newDepartment;

    /**
     * 原组织架构图
     */
    private String fileUrls;

    /**
     * 新组织架构图
     */
    private String newFileUrls;

    /**
     * 新建部门名称
     */
    private String newDepartmentName;

    /**
     * 编制部门
     */
    private String  preparationDepartmentId;

    /**
     * 编制部门名称
     */
    private String  preparationDepartmentName;

    // 原有人员编制
    private List<OriginalStaffingDto> originalStaffings;

    // 新增人员编制
    private List<NewStaffingDto> newStaffings;

    // 审批意见
    private String comment;

    // 原组织架构图
    private List<Map> fileUrlList;

    // 新组织架构图
    private List<Map> newFileUrlList;

    private String orgId;

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

    public String getNewDepartment() {
        return newDepartment;
    }

    public void setNewDepartment(String newDepartment) {
        this.newDepartment = newDepartment;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getNewFileUrls() {
        return newFileUrls;
    }

    public void setNewFileUrls(String newFileUrls) {
        this.newFileUrls = newFileUrls;
    }

    public List<OriginalStaffingDto> getOriginalStaffings() {
        return originalStaffings;
    }

    public void setOriginalStaffings(List<OriginalStaffingDto> originalStaffings) {
        this.originalStaffings = originalStaffings;
    }

    public List<NewStaffingDto> getNewStaffings() {
        return newStaffings;
    }

    public void setNewStaffings(List<NewStaffingDto> newStaffings) {
        this.newStaffings = newStaffings;
    }

    public String getNewDepartmentName() {
        return newDepartmentName;
    }

    public void setNewDepartmentName(String newDepartmentName) {
        this.newDepartmentName = newDepartmentName;
    }

    public String getPreparationDepartmentId() {
        return preparationDepartmentId;
    }

    public void setPreparationDepartmentId(String preparationDepartmentId) {
        this.preparationDepartmentId = preparationDepartmentId;
    }

    public String getPreparationDepartmentName() {
        return preparationDepartmentName;
    }

    public void setPreparationDepartmentName(String preparationDepartmentName) {
        this.preparationDepartmentName = preparationDepartmentName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<Map> getFileUrlList() {
        return fileUrlList;
    }

    public void setFileUrlList(List<Map> fileUrlList) {
        this.fileUrlList = fileUrlList;
    }

    public List<Map> getNewFileUrlList() {
        return newFileUrlList;
    }

    public void setNewFileUrlList(List<Map> newFileUrlList) {
        this.newFileUrlList = newFileUrlList;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
