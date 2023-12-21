package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-11-08
 * 社保公积金请求参数
 */
public class ProcessSbGjjStartParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;
    private String decreaseFlag;
    private String laborRelations;
    private String signingLaborAgreementDate;
    private String applyReason;
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

    public String getDecreaseFlag() {
        return decreaseFlag;
    }

    public void setDecreaseFlag(String decreaseFlag) {
        this.decreaseFlag = decreaseFlag;
    }

    public String getLaborRelations() {
        return laborRelations;
    }

    public void setLaborRelations(String laborRelations) {
        this.laborRelations = laborRelations;
    }

    public String getSigningLaborAgreementDate() {
        return signingLaborAgreementDate;
    }

    public void setSigningLaborAgreementDate(String signingLaborAgreementDate) {
        this.signingLaborAgreementDate = signingLaborAgreementDate;
    }

    public String getApplyReason() {
        return applyReason;
    }

    public void setApplyReason(String applyReason) {
        this.applyReason = applyReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
