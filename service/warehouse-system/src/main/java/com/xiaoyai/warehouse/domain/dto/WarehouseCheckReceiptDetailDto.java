package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.warehouse.domain.WarehouseCheckReceiptDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author junyang
 * @date 2023/10/8
 */
@Data
public class WarehouseCheckReceiptDetailDto extends WarehouseCheckReceiptDetail {
    private String colorType;
    private String unitType;
    private String specificationType;
    private String goodsPicture;
    private String goodsCode;
    private String goodsName;
}
