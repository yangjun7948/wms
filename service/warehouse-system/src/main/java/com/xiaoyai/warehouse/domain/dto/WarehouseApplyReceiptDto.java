package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.warehouse.domain.WarehouseApplyReceipt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/19
 */
@Data
@ApiModel("要货申请")
public class WarehouseApplyReceiptDto extends WarehouseApplyReceipt {
    private String applyUserName;
    private String applyWarehouseName;
    private String requestedUserName;
    private Long outWarehouseId;
    @ApiModelProperty("要货物品明细")
    private List<WarehouseGoodsDto> receiptGoodsList;
}
