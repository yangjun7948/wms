package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.warehouse.domain.WarehouseInReceipt;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/10
 */
@Data
@ApiModel("入库单明细")
public class WarehouseInReceiptDto extends WarehouseInReceipt {
    private List<WarehouseGoodsDto> receiptGoodsList;
    @Excel(name = "入库类别")
    @ApiModelProperty("入库类别名称")
    private String inWarehouseTypeName;
}
