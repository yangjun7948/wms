package com.xiaoyai.warehouse.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 入库单据对象 warehouse_in_receipt
 *
 * @author junyang
 * @date 2023-09-09
 */
@ApiModel("入库单")
@Data
public class WarehouseInReceiptVo implements Serializable {

    @ApiModelProperty("入库单ID")
    private Long warehouseInReceiptId;

    /**
     * 单据编号
     */
    @ApiModelProperty("单据编号")
    private String documentNumber;
    /**
     * 入库时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("入库时间")
    private Date inWarehouseTime;
    @ApiModelProperty("入库类型编号")
    private String inWarehouseType;
    @ApiModelProperty("入库类型")
    private String inWarehouseTypeName;
    @ApiModelProperty("入库仓库")
    private String warehouseName;
    @ApiModelProperty("供应商")
    private String supplierName;
    @ApiModelProperty("单据号")
    private String billNo;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("物品列表")
    List<WarehouseGoodsVo> goodsVoList;
}
