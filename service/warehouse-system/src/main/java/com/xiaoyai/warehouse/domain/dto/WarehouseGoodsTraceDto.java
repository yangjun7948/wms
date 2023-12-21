package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTrace;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/11
 */
@Data
public class WarehouseGoodsTraceDto extends WarehouseGoodsTrace {
    private String goodsName;
    private String goodsCode;
    private String categoryName;
    /** 规格型号 */
    @Excel(name = "规格型号",readConverterDict="goods_specification")
    private Integer specificationType;

    /** 计量单位 */
    @Excel(name = "计量单位",readConverterDict="goods_unit")
    private Integer unitType;

    /** 物品颜色 */
    @Excel(name = "物品颜色",readConverterDict="goods_color")
    private Integer colorType;

    private List<WarehouseGoodsTraceRelDto> traceRelDtoList;
}
