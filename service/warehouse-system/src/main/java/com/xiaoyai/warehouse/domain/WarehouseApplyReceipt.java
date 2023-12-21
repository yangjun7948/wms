package com.xiaoyai.warehouse.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 要货申请对象 warehouse_apply_receipt
 * 
 * @author junyang
 * @date 2023-09-18
 */
public class WarehouseApplyReceipt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 要货申请 */
    @TableId(type = IdType.AUTO)
    private Long applyReceiptId;

    /** 要货人ID */
    @Excel(name = "要货人ID")
    @ApiModelProperty("要货人ID")
    private Long applyUserId;

    @Excel(name = "要货仓库")
    @ApiModelProperty("要货仓库")
    private Long applyWarehouseId;

    /** 单据编号 */
    @Excel(name = "单据编号")
    @ApiModelProperty("要货单号")
    private String documentNumber;

    /** 要货类型 1向公司要货，2向个人要货 */
    @Excel(name = "要货类型")
    @ApiModelProperty("要货类型,1向公司要货，2向个人要货")
    private String applyType;

    @Excel(name = "被要货人Id")
    @ApiModelProperty("被要货人Id，类型是个人时需要传")
    private Long requestedUserId;
    /** 出库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "要货时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("要货时间")
    private Date applyTime;

    /** 单据状态  0：草稿 1：待审核  2：待发货 3：已出库 */
    @ApiModelProperty("要货状态， 0：草稿 1：待审核  2：待发货 3：已出库")
    private String state;
    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    /** 审核人 */
    @Excel(name = "审核人")
    private String auditName;
    /** 审核人id */
    @Excel(name = "审核人id")
    private Long auditId;


    private Long outReceiptId;
    private String outDocumentNumber;
    private Long inReceiptId;
    private String inDocumentNumber;

    public void setApplyReceiptId(Long applyReceiptId) 
    {
        this.applyReceiptId = applyReceiptId;
    }

    public Long getApplyReceiptId() 
    {
        return applyReceiptId;
    }
    public void setApplyUserId(Long applyUserId) 
    {
        this.applyUserId = applyUserId;
    }

    public Long getApplyUserId() 
    {
        return applyUserId;
    }
    public void setDocumentNumber(String documentNumber) 
    {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() 
    {
        return documentNumber;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
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
            .append("applyReceiptId", getApplyReceiptId())
            .append("applyUserId", getApplyUserId())
            .append("documentNumber", getDocumentNumber())
            .append("applyTime", getApplyTime())
            .append("state", getState())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

    public String getApplyType() {
        return applyType;
    }

    public void setApplyType(String applyType) {
        this.applyType = applyType;
    }

    public Long getRequestedUserId() {
        return requestedUserId;
    }

    public void setRequestedUserId(Long requestedUserId) {
        this.requestedUserId = requestedUserId;
    }

    public Long getApplyWarehouseId() {
        return applyWarehouseId;
    }

    public void setApplyWarehouseId(Long applyWarehouseId) {
        this.applyWarehouseId = applyWarehouseId;
    }

    public Long getOutReceiptId() {
        return outReceiptId;
    }

    public void setOutReceiptId(Long outReceiptId) {
        this.outReceiptId = outReceiptId;
    }

    public String getOutDocumentNumber() {
        return outDocumentNumber;
    }

    public void setOutDocumentNumber(String outDocumentNumber) {
        this.outDocumentNumber = outDocumentNumber;
    }

    public Long getInReceiptId() {
        return inReceiptId;
    }

    public void setInReceiptId(Long inReceiptId) {
        this.inReceiptId = inReceiptId;
    }

    public String getInDocumentNumber() {
        return inDocumentNumber;
    }

    public void setInDocumentNumber(String inDocumentNumber) {
        this.inDocumentNumber = inDocumentNumber;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public String getAuditName() {
        return auditName;
    }

    public void setAuditName(String auditName) {
        this.auditName = auditName;
    }
}
