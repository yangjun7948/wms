package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-07
 */
public class ProcessWpsgParam extends ProcessParam {
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
     * 备注
     */
    private String purchasingReasons;
    /**
     * 采购金额判断
     */
    private String purchaseAmountJudgment;
    /**
     * 期望交付日期
     */
    private String expectedDeliveryDate;

    /**
     * 明细列表
     */
    private List<ProcessWpsgItem> wpsgProcessItemList;

    /**
     * 合计
     */
    private String total;

    private String comment;
    private String fileUrls;

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getPurchasingReasons() {
        return purchasingReasons;
    }

    public void setPurchasingReasons(String purchasingReasons) {
        this.purchasingReasons = purchasingReasons;
    }

    public String getPurchaseAmountJudgment() {
        return purchaseAmountJudgment;
    }

    public void setPurchaseAmountJudgment(String purchaseAmountJudgment) {
        this.purchaseAmountJudgment = purchaseAmountJudgment;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public List<ProcessWpsgItem> getWpsgProcessItemList() {
        return wpsgProcessItemList;
    }

    public void setWpsgProcessItemList(List<ProcessWpsgItem> wpsgProcessItemList) {
        this.wpsgProcessItemList = wpsgProcessItemList;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
