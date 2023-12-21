package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import lombok.Data;

/**
 * @author junyang
 * @date 2023/9/24
 */
@Data
public class WarehouseGoodsTraceRelDto extends WarehouseGoodsTraceRel {

    private String documentNumber;
    private int warehouseType;
    private String createBy;
    private String warehouseName;
}
