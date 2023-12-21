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
 * 库存预警对象 warehouse_stock_warn
 * 
 * @author junyang
 * @date 2023-10-08
 */
public class WarehouseStockWarn
{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long warnId;

    /** $column.columnComment */

    private Long goodsId;

    /** $column.columnComment */
    private String goodsName;

    /** $column.columnComment */
    private BigDecimal stockNumber;

    /** $column.columnComment */
    private Long warehouseId;

    private String warehouseName;
    /** $column.columnComment */
    private BigDecimal maxStock;

    /** $column.columnComment */
    private BigDecimal minStock;

    /** $column.columnComment */
    private Date warnTime;

    public void setWarnId(Long warnId) 
    {
        this.warnId = warnId;
    }

    public Long getWarnId() 
    {
        return warnId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsName(String goodsName) 
    {
        this.goodsName = goodsName;
    }

    public String getGoodsName() 
    {
        return goodsName;
    }
    public void setStockNumber(BigDecimal stockNumber)
    {
        this.stockNumber = stockNumber;
    }

    public BigDecimal getStockNumber()
    {
        return stockNumber;
    }
    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }
    public void setMaxStock(BigDecimal maxStock)
    {
        this.maxStock = maxStock;
    }

    public BigDecimal getMaxStock()
    {
        return maxStock;
    }
    public void setMinStock(BigDecimal minStock)
    {
        this.minStock = minStock;
    }

    public BigDecimal getMinStock()
    {
        return minStock;
    }
    public void setWarnTime(Date warnTime) 
    {
        this.warnTime = warnTime;
    }

    public Date getWarnTime() 
    {
        return warnTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("warnId", getWarnId())
            .append("goodsId", getGoodsId())
            .append("goodsName", getGoodsName())
            .append("stockNumber", getStockNumber())
            .append("warehouseId", getWarehouseId())
            .append("maxStock", getMaxStock())
            .append("minStock", getMinStock())
            .append("warnTime", getWarnTime())
            .toString();
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }
}
