package com.xiaoyai.warehouse.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.poi.hpsf.Decimal;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author junyang
 * @date 2023/9/10
 */
@Data
@ApiModel("物品信息")
public class WarehouseGoodsDto extends WarehouseGoods {
    /**
     * 物品数量
     */
    @ApiModelProperty("物品数量")
    private Integer goodsNumber;
    /**
     * 物品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 物品总金额
     */
    private BigDecimal goodsAmount;

    /**
     * 物品有效期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date goodsDeadlineTime;

    /**
     * 是否验证了验证码
     */
    private Boolean snCodeValidate;

    /**
     * 物品溯源码
     */
    private String snCode;
    /**
     * 规格
     */
    private String categoryName;
    @ApiModelProperty("规格")
    private String specificationName;
    @ApiModelProperty("单位")
    private String unitName;
    @ApiModelProperty("颜色")
    private String colorName;
}
