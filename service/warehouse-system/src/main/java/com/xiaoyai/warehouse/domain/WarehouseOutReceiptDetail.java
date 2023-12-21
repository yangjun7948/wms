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
 *  物品出库明细对象 warehouse_out_receipt_detail
 * 
 * @author junyang
 * @date 2023-09-13
 */
public class WarehouseOutReceiptDetail
{
    private static final long serialVersionUID = 1L;

    /** 入库明细ID */
    @TableId(type = IdType.AUTO)
    private Long outReceiptDetailId;

    /** 入库单据 */
    @Excel(name = "入库单据")
    private Long outReceiptId;

    /** 物品ID */
    @Excel(name = "物品ID")
    private Long goodsId;

    /** 物品单价 */
    @Excel(name = "物品单价")
    private BigDecimal goodsPrice;

    /** 物品数量 */
    @Excel(name = "物品数量")
    private BigDecimal goodsNumber;

    /** 物品金额 */
    @Excel(name = "物品金额")
    private BigDecimal goodsAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "物品有效期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date goodsDeadlineTime;

    public void setOutReceiptDetailId(Long outReceiptDetailId) 
    {
        this.outReceiptDetailId = outReceiptDetailId;
    }

    public Long getOutReceiptDetailId() 
    {
        return outReceiptDetailId;
    }
    public void setOutReceiptId(Long outReceiptId) 
    {
        this.outReceiptId = outReceiptId;
    }

    public Long getOutReceiptId() 
    {
        return outReceiptId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsPrice(BigDecimal goodsPrice) 
    {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice() 
    {
        return goodsPrice;
    }
    public void setGoodsNumber(BigDecimal goodsNumber) 
    {
        this.goodsNumber = goodsNumber;
    }

    public BigDecimal getGoodsNumber() 
    {
        return goodsNumber;
    }
    public void setGoodsAmount(BigDecimal goodsAmount) 
    {
        this.goodsAmount = goodsAmount;
    }

    public BigDecimal getGoodsAmount() 
    {
        return goodsAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("outReceiptDetailId", getOutReceiptDetailId())
            .append("outReceiptId", getOutReceiptId())
            .append("goodsId", getGoodsId())
            .append("goodsPrice", getGoodsPrice())
            .append("goodsNumber", getGoodsNumber())
            .append("goodsAmount", getGoodsAmount())
            .toString();
    }

    public Date getGoodsDeadlineTime() {
        return goodsDeadlineTime;
    }

    public void setGoodsDeadlineTime(Date goodsDeadlineTime) {
        this.goodsDeadlineTime = goodsDeadlineTime;
    }
}
