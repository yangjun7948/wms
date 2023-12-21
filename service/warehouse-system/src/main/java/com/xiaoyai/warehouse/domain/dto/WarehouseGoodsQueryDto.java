package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.warehouse.domain.WarehouseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/21
 */
@Data
@ApiModel("物品查询参数")
public class WarehouseGoodsQueryDto extends WarehouseGoods {
    @ApiModelProperty("物品分类ids")
    private List<Long> categoryIds;
}
