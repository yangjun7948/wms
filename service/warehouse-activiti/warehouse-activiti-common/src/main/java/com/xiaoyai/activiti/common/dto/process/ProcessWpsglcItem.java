package com.xiaoyai.activiti.common.dto.process;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-07
 * 物品申购明细
 */
public class ProcessWpsglcItem implements Serializable {
    /**
     * 物品类别
     */
    private String goodsCategory;
    /**
     * 物品编号
     */
    private String goodsNumber;
    /**
     * 物品名称
     */
    private String goodsName;
    /**
     * 计量单位
     */
    private String unit;
    /**
     * 规格型号
     */
    private String specification;
    /**
     * 库存数量
     */
    private String stockCount;
    /**
     * 申购数量
     */
    private String applyBuyCount;
    /**
     * 单价
     */
    private String unitPrice;
    /**
     * 小计
     */
    private String total;
    /**
     * 图片
     */
    private List<String> picUrl;
    /**
     * 备注
     */
    private String remark;

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getStockCount() {
        return stockCount;
    }

    public void setStockCount(String stockCount) {
        this.stockCount = stockCount;
    }

    public String getApplyBuyCount() {
        return applyBuyCount;
    }

    public void setApplyBuyCount(String applyBuyCount) {
        this.applyBuyCount = applyBuyCount;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
