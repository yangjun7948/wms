package com.xiaoyai.warehouse.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 入库单据对象 warehouse_in_receipt
 * 
 * @author junyang
 * @date 2023-09-09
 */
@ApiModel("出库单")
@Data
public class WarehouseOutReceiptVo
{

    @ApiModelProperty("出库单ID")
    private Long warehouseOutReceiptId;

    /** 单据编号 */
    @ApiModelProperty("单据编号")
    private String documentNumber;
    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("出库时间")
    private Date outWarehouseTime;
    @ApiModelProperty("出库类型编号")
    private String outWarehouseType;
    @ApiModelProperty("出库类型")
    private String outWarehouseTypeName;
    @ApiModelProperty("出库仓库")
    private String warehouseName;
    @ApiModelProperty("接收门店/仓库")
    private String receiveName;
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("物品列表")
    List<WarehouseGoodsVo> goodsVoList;
}
