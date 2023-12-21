package com.xiaoyai.warehouse.domain.dto;

import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/23
 */
@Data
public class WarehouseCodeReportQueryDto {
    private String goodsCode;
    private String goodsName;
    private String snCode;
    private List<Long> categoryIds;
}
