package com.xiaoyai.activiti.common.dto.process;

/**
 * @author
 * @date
 * 物品领用流程dto
 */
public class RecipientsGoodDto {
    /**
     * ''物品名称''
     */
    private String goodsName;
    /**
     * '' 规格型号''
     */
    private String specificationModel;
    /**
     * ''计量单位''
     */
    private String measurementUnit;
    /**
     * ''数量''
     */
    private String quantity;
    /**
     * ''说明''
     */
    private String description;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getSpecificationModel() {
        return specificationModel;
    }

    public void setSpecificationModel(String specificationModel) {
        this.specificationModel = specificationModel;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
