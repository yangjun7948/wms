package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2020-11-05
 */
public class ProcessZgbjbParam extends ProcessParam {
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
     * 采购标准
     */
    private String purchasingStandard;

    /**
     * 计划购买品牌型号
     */

    private String buyBrandModel;

    /**
     * 实际购买价格
     */

    private String actualPurchasePrice;

    /**
     * 采购事由
     */

    private String purchasingReasons;

    /**
     * 附件名称
     */
    private String fileUrls;

    /**
     * 发票
     */
    private String billUrls;
    private String comment;

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

    public String getPurchasingStandard() {
        return purchasingStandard;
    }

    public void setPurchasingStandard(String purchasingStandard) {
        this.purchasingStandard = purchasingStandard;
    }

    public String getBuyBrandModel() {
        return buyBrandModel;
    }

    public void setBuyBrandModel(String buyBrandModel) {
        this.buyBrandModel = buyBrandModel;
    }

    public String getActualPurchasePrice() {
        return actualPurchasePrice;
    }

    public void setActualPurchasePrice(String actualPurchasePrice) {
        this.actualPurchasePrice = actualPurchasePrice;
    }

    public String getPurchasingReasons() {
        return purchasingReasons;
    }

    public void setPurchasingReasons(String purchasingReasons) {
        this.purchasingReasons = purchasingReasons;
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

    public String getBillUrls() {
        return billUrls;
    }

    public void setBillUrls(String billUrls) {
        this.billUrls = billUrls;
    }
}
