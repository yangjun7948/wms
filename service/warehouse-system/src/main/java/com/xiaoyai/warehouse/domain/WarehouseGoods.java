package com.xiaoyai.warehouse.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 物品对象 warehouse_goods
 * 
 * @author junyang
 * @date 2023-09-07
 */
public class WarehouseGoods extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 物品id */
    @TableId(type = IdType.AUTO)
    private Long goodsId;

    /** 类别ID */
    private Long categoryId;

    @Excel(name = "物品类别")
    @ApiModelProperty("物品类别")
    @TableField(exist = false)
    private String categoryName;
    /** 类别编号 */
    private String categoryCode;

    /** 物品编号 */
    @Excel(name = "物品编号")
    private String goodsCode;

    /** 物品名称 */
    @Excel(name = "物品名称")
    @ApiModelProperty("物品名称")
    private String goodsName;

    /** 仓位 */
    private String area;

    /** 规格型号 */
    @Excel(name = "规格型号",readConverterDict="goods_specification")
    private Integer specificationType;

    /** 计量单位 */
    @Excel(name = "计量单位",readConverterDict="goods_unit")
    private Integer unitType;

    /** 物品颜色 */
    @Excel(name = "物品颜色",readConverterDict="goods_color")
    private Integer colorType;

    /** 最小库存 */
    @Excel(name = "最小库存")
    private BigDecimal minStock;

    /** 最大库存 */
    @Excel(name = "最大库存")
    private BigDecimal maxStock;

    /** 物品图片 */
    @Excel(name = "物品图片")
    private String goodsPicture;
    /** 有效期 */
    @Excel(name = "有效期")
    private Integer expirationDate;
    /** 类型 */
    @Excel(name = "类型",readConverterDict="sys_date_type")
    private Integer expirationDateType;

    /** 质保时间 */
    @Excel(name = "质保时间")
    private Integer warrantyDate;
    /** 类型 */
    @Excel(name = "类型",readConverterDict="sys_date_type")
    private Integer warrantyDateType;

    /**  序列号验证 */
    @Excel(name = "重点物资")
    private String snValidate;

    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }
    public void setGoodsCode(String goodsCode) 
    {
        this.goodsCode = goodsCode;
    }

    public String getGoodsCode() 
    {
        return goodsCode;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setSpecificationType(Integer specificationType) 
    {
        this.specificationType = specificationType;
    }

    public Integer getSpecificationType() 
    {
        return specificationType;
    }
    public void setUnitType(Integer unitType) 
    {
        this.unitType = unitType;
    }

    public Integer getUnitType() 
    {
        return unitType;
    }
    public void setColorType(Integer colorType) 
    {
        this.colorType = colorType;
    }

    public Integer getColorType() 
    {
        return colorType;
    }
    public void setMinStock(BigDecimal minStock) 
    {
        this.minStock = minStock;
    }

    public BigDecimal getMinStock() 
    {
        return minStock;
    }
    public void setMaxStock(BigDecimal maxStock) 
    {
        this.maxStock = maxStock;
    }

    public BigDecimal getMaxStock() 
    {
        return maxStock;
    }
    public void setGoodsPicture(String goodsPicture) 
    {
        this.goodsPicture = goodsPicture;
    }

    public String getGoodsPicture() 
    {
        return goodsPicture;
    }
    public void setExpirationDateType(Integer expirationDateType)
    {
        this.expirationDateType = expirationDateType;
    }

    public Integer getExpirationDateType()
    {
        return expirationDateType;
    }
    public void setExpirationDate(Integer expirationDate) 
    {
        this.expirationDate = expirationDate;
    }

    public Integer getExpirationDate() 
    {
        return expirationDate;
    }
    public void setWarrantyDateType(Integer warrantyDateType)
    {
        this.warrantyDateType = warrantyDateType;
    }

    public Integer getWarrantyDateType()
    {
        return warrantyDateType;
    }
    public void setwarrantyDate(Integer warrantyDate)
    {
        this.warrantyDate = warrantyDate;
    }

    public Integer getwarrantyDate()
    {
        return warrantyDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("goodsId", getGoodsId())
            .append("categoryId", getCategoryId())
            .append("categoryCode", getCategoryCode())
            .append("goodsCode", getGoodsCode())
            .append("goodsName", getGoodsName())
            .append("area", getArea())
            .append("specificationType", getSpecificationType())
            .append("unitType", getUnitType())
            .append("colorType", getColorType())
            .append("minStock", getMinStock())
            .append("maxStock", getMaxStock())
            .append("goodsPicture", getGoodsPicture())
            .append("expirationDateType", getExpirationDateType())
            .append("expirationDate", getExpirationDate())
            .append("warrantyDateType", getWarrantyDateType())
            .append("warrantyDate", getwarrantyDate())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSnValidate() {
        return snValidate;
    }

    public void setSnValidate(String snValidate) {
        this.snValidate = snValidate;
    }
}
