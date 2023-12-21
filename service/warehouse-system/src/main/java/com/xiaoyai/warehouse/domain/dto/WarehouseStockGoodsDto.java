package com.xiaoyai.warehouse.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *  库存物品
 * @author junyang
 * @date 2023/9/13
 */
@Data
public class WarehouseStockGoodsDto extends WarehouseGoods {

    private Long warehouseId;
    /**
     *  物品单价
     */
    private BigDecimal goodsPrice;


    /**
     * 物品有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goodsDeadlineTime;

    /**
     * 库存数量
     */
    private BigDecimal stockNumber;

}
