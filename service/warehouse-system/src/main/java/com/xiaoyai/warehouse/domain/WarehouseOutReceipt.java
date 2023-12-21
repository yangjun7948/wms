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
 * 出库单据对象 warehouse_out_receipt
 * 
 * @author junyang
 * @date 2023-09-13
 */
public class WarehouseOutReceipt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 出库单据id */
    @TableId(type = IdType.AUTO)
    private Long warehouseOutReceiptId;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String documentNumber;

    /** 出库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "出库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outWarehouseTime;

    /** 出库类别 */
    @Excel(name = "出库类别")
    private String outWarehouseType;

    /** 领料日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领料日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    /** 领料人id */
    @Excel(name = "领料人id")
    private Long receiveId;

    /** 领料人 */
    @Excel(name = "领料人")
    private String receiveName;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long auditId;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditName;

    /** 单据状态  0：保存  1：审核通过 */
    @Excel(name = "单据状态  0：保存  1：审核通过")
    private String state;

    public void setWarehouseOutReceiptId(Long warehouseOutReceiptId) 
    {
        this.warehouseOutReceiptId = warehouseOutReceiptId;
    }

    public Long getWarehouseOutReceiptId() 
    {
        return warehouseOutReceiptId;
    }
    public void setWarehouseId(Long warehouseId) 
    {
        this.warehouseId = warehouseId;
    }

    public Long getWarehouseId() 
    {
        return warehouseId;
    }
    public void setDocumentNumber(String documentNumber) 
    {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() 
    {
        return documentNumber;
    }
    public void setOutWarehouseTime(Date outWarehouseTime) 
    {
        this.outWarehouseTime = outWarehouseTime;
    }

    public Date getOutWarehouseTime() 
    {
        return outWarehouseTime;
    }
    public void setOutWarehouseType(String outWarehouseType) 
    {
        this.outWarehouseType = outWarehouseType;
    }

    public String getOutWarehouseType() 
    {
        return outWarehouseType;
    }
    public void setReceiveTime(Date receiveTime) 
    {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveTime() 
    {
        return receiveTime;
    }
    public void setReceiveId(Long receiveId)
    {
        this.receiveId = receiveId;
    }

    public Long getReceiveId()
    {
        return receiveId;
    }
    public void setReceiveName(String receiveName)
    {
        this.receiveName = receiveName;
    }

    public String getReceiveName() 
    {
        return receiveName;
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
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("warehouseOutReceiptId", getWarehouseOutReceiptId())
            .append("warehouseId", getWarehouseId())
            .append("documentNumber", getDocumentNumber())
            .append("outWarehouseTime", getOutWarehouseTime())
            .append("outWarehouseType", getOutWarehouseType())
            .append("receiveTime", getReceiveTime())
            .append("receiveId", getReceiveId())
            .append("receiveName", getReceiveName())
            .append("auditTime", getAuditTime())
            .append("auditId", getAuditId())
            .append("auditName", getAuditName())
            .append("state", getState())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
