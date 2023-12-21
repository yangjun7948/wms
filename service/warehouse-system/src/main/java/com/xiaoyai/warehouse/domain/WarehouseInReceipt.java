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
 * 入库单据对象 warehouse_in_receipt
 * 
 * @author junyang
 * @date 2023-09-09
 */
public class WarehouseInReceipt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入库单据id */
    @TableId(type = IdType.AUTO)
    private Long warehouseInReceiptId;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String documentNumber;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date inWarehouseTime;

    /** 入库类别 */
    @Excel(name = "入库类别")
    private String inWarehouseType;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long auditId;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditName;

    /** 状态 0：草稿   1：待审核 2：已审核 */
    @Excel(name = "状态 0：草稿   1：待审核 2：已审核")
    private String state;

    /**
     * 供应商id
     */
    private Long supplierId;
    @Excel(name = "订单号")
    private String billNo;
    public void setWarehouseInReceiptId(Long warehouseInReceiptId) 
    {
        this.warehouseInReceiptId = warehouseInReceiptId;
    }

    public Long getWarehouseInReceiptId() 
    {
        return warehouseInReceiptId;
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
    public void setInWarehouseTime(Date inWarehouseTime) 
    {
        this.inWarehouseTime = inWarehouseTime;
    }

    public Date getInWarehouseTime() 
    {
        return inWarehouseTime;
    }
    public void setInWarehouseType(String inWarehouseType) 
    {
        this.inWarehouseType = inWarehouseType;
    }

    public String getInWarehouseType() 
    {
        return inWarehouseType;
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
            .append("warehouseInReceiptId", getWarehouseInReceiptId())
            .append("warehouseId", getWarehouseId())
            .append("documentNumber", getDocumentNumber())
            .append("inWarehouseTime", getInWarehouseTime())
            .append("inWarehouseType", getInWarehouseType())
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

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
}
