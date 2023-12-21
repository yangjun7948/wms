package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-07
 */
public class ProcessWpsglcParam extends ProcessParam {
    /**
     * 申请部门id
     */
    private String applyDepartmentId;
    /**
     * 申请部门名称
     */
    private String applyDepartmentName;
    /**
     * 申请人id
     */
    private String applyUserId;
    /**
     * 申请人名称
     */
    private String applyUserName;
    /**
     * 申购日期
     */
    private String applyBuyDate;
    /**
     * 申购说明
     */
    private String purchasingReasons;

    /**
     * 明细
     */
    private List<ProcessWpsglcItem> wpsgProcessItemList;

    /**
     * 申购部门id
     */
    private String applyBuyDepartmentId;
    /**
     * 申购部门名称
     */
    private String applyBuyDepartmentName;


    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
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

    public String getApplyBuyDate() {
        return applyBuyDate;
    }

    public void setApplyBuyDate(String applyBuyDate) {
        this.applyBuyDate = applyBuyDate;
    }

    public String getPurchasingReasons() {
        return purchasingReasons;
    }

    public void setPurchasingReasons(String purchasingReasons) {
        this.purchasingReasons = purchasingReasons;
    }

    public List<ProcessWpsglcItem> getWpsgProcessItemList() {
        return wpsgProcessItemList;
    }

    public void setWpsgProcessItemList(List<ProcessWpsglcItem> wpsgProcessItemList) {
        this.wpsgProcessItemList = wpsgProcessItemList;
    }

    public String getApplyBuyDepartmentId() {
        return applyBuyDepartmentId;
    }

    public void setApplyBuyDepartmentId(String applyBuyDepartmentId) {
        this.applyBuyDepartmentId = applyBuyDepartmentId;
    }

    public String getApplyBuyDepartmentName() {
        return applyBuyDepartmentName;
    }

    public void setApplyBuyDepartmentName(String applyBuyDepartmentName) {
        this.applyBuyDepartmentName = applyBuyDepartmentName;
    }
}
