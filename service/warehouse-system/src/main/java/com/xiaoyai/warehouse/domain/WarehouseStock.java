package com.xiaoyai.warehouse.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 库存对象 warehouse_stock
 * 
 * @author junyang
 * @date 2023-09-13
 */
public class WarehouseStock
{
    private static final long serialVersionUID = 1L;

    /** 库存id */
    @TableId(type = IdType.AUTO)
    private Long stockId;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;

    /** 物品id */
    @Excel(name = "物品id")
    private Long goodsId;

    /** 存储物品数量 */
    @Excel(name = "存储物品数量")
    private BigDecimal stockNumber;

    /** 该物品的单价（不是均价） */
    @Excel(name = "该物品的单价", readConverterExp = "不=是均价")
    private BigDecimal goodsPrice;

    /** 存储物品总金额 */
    @Excel(name = "存储物品总金额")
    private BigDecimal stockAmount;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date goodsDeadlineTime;

    public void setStockId(Long stockId) 
    {
        this.stockId = stockId;
    }

    public Long getStockId() 
    {
        return stockId;
    }
    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setStockNumber(BigDecimal stockNumber) 
    {
        this.stockNumber = stockNumber;
    }

    public BigDecimal getStockNumber() 
    {
        return stockNumber;
    }
    public void setGoodsPrice(BigDecimal goodsPrice)
    {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice()
    {
        return goodsPrice;
    }
    public void setStockAmount(BigDecimal stockAmount) 
    {
        this.stockAmount = stockAmount;
    }

    public BigDecimal getStockAmount() 
    {
        return stockAmount;
    }
    public void setGoodsDeadlineTime(Date goodsDeadlineTime) 
    {
        this.goodsDeadlineTime = goodsDeadlineTime;
    }

    public Date getGoodsDeadlineTime() 
    {
        return goodsDeadlineTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("stockId", getStockId())
            .append("warehouseId", getWarehouseId())
            .append("goodsId", getGoodsId())
            .append("stockNumber", getStockNumber())
            .append("goodsPrice", getGoodsPrice())
            .append("stockAmount", getStockAmount())
            .append("goodsDeadlineTime", getGoodsDeadlineTime())
            .toString();
    }
}
