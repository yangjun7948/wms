package com.xiaoyai.warehouse.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 调拨单据对象 warehouse_transfer_receipt
 * 
 * @author junyang
 * @date 2023-09-16
 */
public class WarehouseTransferReceipt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 调拨单据id */
    @TableId(type = IdType.AUTO)
    private Long warehouseTransferReceiptId;

    /** 调出仓库id */

    private Long transferOutWarehouseId;

    /** 调入仓库id */
    private Long transferInWarehouseId;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String documentNumber;

    /** 调拨时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "调拨时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date transferWarehouseTime;

    /** 调拨类别 */
    @Excel(name = "调拨类别")
    private String transferType;

    /** 单据状态 */
    @Excel(name = "单据状态")
    private String state;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long auditId;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditName;

    public void setWarehouseTransferReceiptId(Long warehouseTransferReceiptId) 
    {
        this.warehouseTransferReceiptId = warehouseTransferReceiptId;
    }

    public Long getWarehouseTransferReceiptId() 
    {
        return warehouseTransferReceiptId;
    }
    public void setTransferOutWarehouseId(Long transferOutWarehouseId) 
    {
        this.transferOutWarehouseId = transferOutWarehouseId;
    }

    public Long getTransferOutWarehouseId() 
    {
        return transferOutWarehouseId;
    }
    public void setTransferInWarehouseId(Long transferInWarehouseId) 
    {
        this.transferInWarehouseId = transferInWarehouseId;
    }

    public Long getTransferInWarehouseId() 
    {
        return transferInWarehouseId;
    }
    public void setDocumentNumber(String documentNumber) 
    {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() 
    {
        return documentNumber;
    }
    public void setTransferWarehouseTime(Date transferWarehouseTime) 
    {
        this.transferWarehouseTime = transferWarehouseTime;
    }

    public Date getTransferWarehouseTime() 
    {
        return transferWarehouseTime;
    }
    public void setTransferType(String transferType) 
    {
        this.transferType = transferType;
    }

    public String getTransferType() 
    {
        return transferType;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setAuditTime(Date auditTime) 
    {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() 
    {
        return auditTime;
    }
    public void setAuditId(Long auditId)
    {
        this.auditId = auditId;
    }

    public Long getAuditId()
    {
        return auditId;
    }
    public void setAuditName(String auditName) 
    {
        this.auditName = auditName;
    }

    public String getAuditName() 
    {
        return auditName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("warehouseTransferReceiptId", getWarehouseTransferReceiptId())
            .append("transferOutWarehouseId", getTransferOutWarehouseId())
            .append("transferInWarehouseId", getTransferInWarehouseId())
            .append("documentNumber", getDocumentNumber())
            .append("transferWarehouseTime", getTransferWarehouseTime())
            .append("transferType", getTransferType())
            .append("state", getState())
            .append("auditTime", getAuditTime())
            .append("auditId", getAuditId())
            .append("auditName", getAuditName())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
