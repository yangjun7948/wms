package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author junyang
 * @date 2023/9/20
 */
@Data
@ApiModel("库存查询参数")
public class WarehouseStockReportQueryDto {
    /**
     *  仓库ids
     */
    @ApiModelProperty("仓库ids")
    private List<Long> warehouseIds;
    /**
     *  是否区分单价
     */
    @ApiModelProperty("是否区分单价")
    private boolean showPrice;
    /**
     * 是否区分有效期
     */
    @ApiModelProperty("是否区分有效期")
    private boolean showDeadLineTime;
    /**
     * 物品IDS
     */
    @ApiModelProperty("物品IDS")
    private List<Long> goodsIds;
    /**
     * 物品分类ids
     */
    @ApiModelProperty("物品分类ids")
    private List<Long> categoryIds;

    /** 物品编号 */
    @ApiModelProperty("物品编号")
    private String goodsCode;

    /** 物品名称 */
    @ApiModelProperty("物品名称")
    private String goodsName;
}
