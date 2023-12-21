package com.xiaoyai.common.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author junyang
 * @date 2023/11/27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WarehouseScope extends HashMap {
    /**
     * 限制范围的字段名称
     */
    private String scopeName = "warehouse_id";

    /**
     * 具体的数据范围
     */
    private List<String> warehouseList = new ArrayList<>();

}
