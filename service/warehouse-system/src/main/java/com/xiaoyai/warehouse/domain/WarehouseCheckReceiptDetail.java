package com.xiaoyai.warehouse.domain;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 盘点单据明细对象 warehouse_check_receipt_detail
 * 
 * @author junyang
 * @date 2023-10-07
 */
public class WarehouseCheckReceiptDetail
{
    private static final long serialVersionUID = 1L;

    /** 入库明细ID */
    @TableId(type = IdType.AUTO)
    private Long checkReceiptDetailId;

    /** 入库单据 */
    @Excel(name = "入库单据")
    private Long checkReceiptId;

    /** 物品ID */
    @Excel(name = "物品ID")
    private Long goodsId;

    /** 盘点变化数量 */
    @Excel(name = "盘点变化数量")
    private BigDecimal goodsChangeNumber;

    /** 物品原数量 */
    @Excel(name = "物品原数量")
    private BigDecimal goodsOldNumber;

    /** 物品盘点后数量 */
    @Excel(name = "物品盘点后数量")
    private BigDecimal goodsNewNumber;
    /** 备注 */
    private String remark;

    public void setCheckReceiptDetailId(Long checkReceiptDetailId) 
    {
        this.checkReceiptDetailId = checkReceiptDetailId;
    }

    public Long getCheckReceiptDetailId() 
    {
        return checkReceiptDetailId;
    }
    public void setCheckReceiptId(Long checkReceiptId) 
    {
        this.checkReceiptId = checkReceiptId;
    }

    public Long getCheckReceiptId() 
    {
        return checkReceiptId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setGoodsChangeNumber(BigDecimal goodsChangeNumber) 
    {
        this.goodsChangeNumber = goodsChangeNumber;
    }

    public BigDecimal getGoodsChangeNumber() 
    {
        return goodsChangeNumber;
    }
    public void setGoodsOldNumber(BigDecimal goodsOldNumber) 
    {
        this.goodsOldNumber = goodsOldNumber;
    }

    public BigDecimal getGoodsOldNumber() 
    {
        return goodsOldNumber;
    }
    public void setGoodsNewNumber(BigDecimal goodsNewNumber) 
    {
        this.goodsNewNumber = goodsNewNumber;
    }

    public BigDecimal getGoodsNewNumber() 
    {
        return goodsNewNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkReceiptDetailId", getCheckReceiptDetailId())
            .append("checkReceiptId", getCheckReceiptId())
            .append("goodsId", getGoodsId())
            .append("goodsChangeNumber", getGoodsChangeNumber())
            .append("goodsOldNumber", getGoodsOldNumber())
            .append("goodsNewNumber", getGoodsNewNumber())
            .append("remark", getRemark())
            .toString();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
