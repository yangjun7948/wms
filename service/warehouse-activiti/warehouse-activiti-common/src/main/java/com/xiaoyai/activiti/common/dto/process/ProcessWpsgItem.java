package com.xiaoyai.activiti.common.dto.process;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-07
 * 物品申购明细
 */
public class ProcessWpsgItem implements Serializable {
    private String index;
    /**
     * *产品名称
     */
    private String productName;
    /**
     * 规格
     */
    private String specification;
    /**
     * 单位
     */
    private String unit;
    /**
     * 库存
     */
    private String stock;
    /**
     * 本月申购数量
     */
    private String purchaseQuantityThisMonth;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 总计
     */
    private String subtotal;
    /**
     * 备注
     */
    private String remark;
    /**
     * 图片地址
     */
    private List<String> picUrl;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPurchaseQuantityThisMonth() {
        return purchaseQuantityThisMonth;
    }

    public void setPurchaseQuantityThisMonth(String purchaseQuantityThisMonth) {
        this.purchaseQuantityThisMonth = purchaseQuantityThisMonth;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }
}
