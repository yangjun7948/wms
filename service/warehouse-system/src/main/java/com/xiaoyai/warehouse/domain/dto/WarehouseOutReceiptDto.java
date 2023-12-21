package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.warehouse.domain.WarehouseOutReceipt;
import com.xiaoyai.warehouse.domain.WarehouseOutReceiptDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/13
 */
@Data
@ApiModel("出库单明细")
public class WarehouseOutReceiptDto extends WarehouseOutReceipt {
    private List<WarehouseGoodsDto> receiptGoodsList;
    /** 出库类别 */
    @Excel(name = "出库类别名称")
    @ApiModelProperty("出库类别名称")
    private String outWarehouseTypeName;
}
