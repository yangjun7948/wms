package com.xiaoyai.warehouse.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author junyang
 * @date 2023/9/10
 */
@Data
@ApiModel("入库物品明细")
public class WarehouseGoodsVo {

    @ApiModelProperty("物品ID")
    private Long goodsId;

    /**
     * 是否开启验证（Y-是，N-否）
     */
    @ApiModelProperty("是否开启验证")
    private String snValidate;

    /**
     * 是否扫码完成
     */
    @ApiModelProperty("是否扫码成功，扫码成功需要修改此字段")
    private Boolean scanSuccess;
    /**
     * 物品溯源码
     */
    @ApiModelProperty("溯源码")
    private String snCode;
    /**
     *  物品类型
     */
    @ApiModelProperty("物品类型")
    private String categoryName;
    /**
     *  物品类型
     */
    @ApiModelProperty("物品类型编码")
    private String categoryCode;
    /**
     *  物品类型
     */
    @ApiModelProperty("物品名称")
    private String goodsName;
}
