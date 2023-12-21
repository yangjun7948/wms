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
 * 库存盘点对象 warehouse_check_receipt
 * 
 * @author junyang
 * @date 2023-10-07
 */
public class WarehouseCheckReceipt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 盘点ID */
    @TableId(type = IdType.AUTO)
    private Long checkReceiptId;

    /** 盘点人ID */
    @Excel(name = "盘点人ID")
    private Long checkUserId;

    /** 仓库id */
    @Excel(name = "仓库id")
    private Long warehouseId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String documentNumber;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date checkTime;

    private String state;
    public void setCheckReceiptId(Long checkReceiptId) 
    {
        this.checkReceiptId = checkReceiptId;
    }

    public Long getCheckReceiptId() 
    {
        return checkReceiptId;
    }
    public void setCheckUserId(Long checkUserId) 
    {
        this.checkUserId = checkUserId;
    }

    public Long getCheckUserId() 
    {
        return checkUserId;
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
    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("checkReceiptId", getCheckReceiptId())
            .append("checkUserId", getCheckUserId())
            .append("warehouseId", getWarehouseId())
            .append("documentNumber", getDocumentNumber())
            .append("checkTime", getCheckTime())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
