package com.xiaoyai.warehouse.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 物品溯源对象 warehouse_goods_trace
 * 
 * @author junyang
 * @date 2023-09-10
 */
public class WarehouseGoodsTrace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 溯源ID */
    @TableId(type = IdType.AUTO)
    private Long traceId;

    /** 物品ID */
    @Excel(name = "物品ID")
    private Long goodsId;

    /** 溯源码 */
    @Excel(name = "溯源码")
    private String snCode;

    /**
     * 物品状态
     */
    private String traceState;
    /**
     * 入库单号
     */
    private Long inReceiptId;

    /**
     * 当前所在仓库id
     */
    private Long warehouseId;
    public void setTraceId(Long traceId) 
    {
        this.traceId = traceId;
    }

    public Long getTraceId() 
    {
        return traceId;
    }
    public void setGoodsId(Long goodsId) 
    {
        this.goodsId = goodsId;
    }

    public Long getGoodsId() 
    {
        return goodsId;
    }
    public void setSnCode(String snCode) 
    {
        this.snCode = snCode;
    }

    public String getSnCode() 
    {
        return snCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("traceId", getTraceId())
            .append("goodsId", getGoodsId())
            .append("snCode", getSnCode())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }

    public Long getInReceiptId() {
        return inReceiptId;
    }

    public void setInReceiptId(Long inReceiptId) {
        this.inReceiptId = inReceiptId;
    }

    public String getTraceState() {
        return traceState;
    }

    public void setTraceState(String traceState) {
        this.traceState = traceState;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long wareHouseId) {
        this.warehouseId = wareHouseId;
    }
}
