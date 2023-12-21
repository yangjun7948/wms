package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-10-29
 * 流程发起基类
 */
public class ProcessReqBaseParam {
    /**
     * 申请部门名称
     */
    private String applyDepartmentName;
    /**
     * 申请部门id
     */
    private String applyDepartmentId;
    /**
     * 申请人id
     */
    private String applyUserId;
    /**
     * 申请人名称
     */
    private String applyUserName;

    /**
     * 流程code
     * */
    private String procCode;

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

    public String getProcCode() {
        return procCode;
    }

    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }
}
