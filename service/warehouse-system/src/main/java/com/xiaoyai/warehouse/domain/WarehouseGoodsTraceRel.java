package com.xiaoyai.warehouse.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 物品流转记录对象 warehouse_goods_trace_rel
 * 
 * @author junyang
 * @date 2023-09-10
 */
public class WarehouseGoodsTraceRel
{
    private static final long serialVersionUID = 1L;

    /** 溯源记录id */
    private Long traceRecordId;

    /** 物品溯源id */
    @Excel(name = "物品溯源id")
    private Long traceId;

    /** 入库单id，出库单id */
    @Excel(name = "入库单id，出库单id")
    private Long relId;

    /** 物品ID */
    @Excel(name = "物品ID")
    private Long goodsId;
    /** 记录类型 */
    @Excel(name = "记录类型")
    private String recordType;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date recordTime;


    public void setTraceRecordId(Long traceRecordId) 
    {
        this.traceRecordId = traceRecordId;
    }

    public Long getTraceRecordId() 
    {
        return traceRecordId;
    }
    public void setTraceId(Long traceId) 
    {
        this.traceId = traceId;
    }

    public Long getTraceId() 
    {
        return traceId;
    }
    public void setRelId(Long relId) 
    {
        this.relId = relId;
    }

    public Long getRelId() 
    {
        return relId;
    }
    public void setRecordType(String recordType) 
    {
        this.recordType = recordType;
    }

    public String getRecordType() 
    {
        return recordType;
    }
    public void setRecordTime(Date recordTime) 
    {
        this.recordTime = recordTime;
    }

    public Date getRecordTime() 
    {
        return recordTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("traceRecordId", getTraceRecordId())
            .append("traceId", getTraceId())
            .append("relId", getRelId())
            .append("recordType", getRecordType())
            .append("recordTime", getRecordTime())
            .toString();
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
