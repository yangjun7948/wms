package com.xiaoyai.warehouse.domain.dto;

import lombok.Data;

/**
 * @author junyang
 * @date 2023/9/12
 */
@Data
public class ReceiptDetailFields {
    private String goodsCode;
    private String goodsName;
    private String specification;
    private String unitMeasurementName;
    private String unitPrice;
    private String goodsCount;
    private String goodsAmount;
    private String goodsRemark;
    private String snCode;
}
