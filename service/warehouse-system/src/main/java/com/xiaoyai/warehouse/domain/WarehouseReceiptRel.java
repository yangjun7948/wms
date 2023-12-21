package com.xiaoyai.warehouse.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 调拨关系对象 warehouse_receipt_rel
 * 
 * @author junyang
 * @date 2023-09-16
 */
public class WarehouseReceiptRel
{
    private static final long serialVersionUID = 1L;

    /** 关系id */
    @TableId(type = IdType.AUTO)
    private Long relId;

    /** 调拨单id */
    @Excel(name = "调拨单id")
    private Long transferId;

    /** 调拨单编号 */
    @Excel(name = "调拨单编号")
    private String transferDocumentNumber;

    /** 入库单id */
    @Excel(name = "入库单id")
    private Long inId;

    /** 入库单编号 */
    @Excel(name = "入库单编号")
    private String inDocumentNumber;

    /** 出库id */
    @Excel(name = "出库id")
    private Long outId;

    /** 出库编号 */
    @Excel(name = "出库编号")
    private String outDocumentNumber;

    public void setRelId(Long relId) 
    {
        this.relId = relId;
    }

    public Long getRelId() 
    {
        return relId;
    }
    public void setTransferId(Long transferId) 
    {
        this.transferId = transferId;
    }

    public Long getTransferId() 
    {
        return transferId;
    }
    public void setTransferDocumentNumber(String transferDocumentNumber) 
    {
        this.transferDocumentNumber = transferDocumentNumber;
    }

    public String getTransferDocumentNumber() 
    {
        return transferDocumentNumber;
    }
    public void setInId(Long inId) 
    {
        this.inId = inId;
    }

    public Long getInId() 
    {
        return inId;
    }
    public void setInDocumentNumber(String inDocumentNumber) 
    {
        this.inDocumentNumber = inDocumentNumber;
    }

    public String getInDocumentNumber() 
    {
        return inDocumentNumber;
    }
    public void setOutId(Long outId) 
    {
        this.outId = outId;
    }

    public Long getOutId() 
    {
        return outId;
    }
    public void setOutDocumentNumber(String outDocumentNumber) 
    {
        this.outDocumentNumber = outDocumentNumber;
    }

    public String getOutDocumentNumber() 
    {
        return outDocumentNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("relId", getRelId())
            .append("transferId", getTransferId())
            .append("transferDocumentNumber", getTransferDocumentNumber())
            .append("inId", getInId())
            .append("inDocumentNumber", getInDocumentNumber())
            .append("outId", getOutId())
            .append("outDocumentNumber", getOutDocumentNumber())
            .toString();
    }
}
