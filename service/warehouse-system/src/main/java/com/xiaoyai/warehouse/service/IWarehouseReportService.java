package com.xiaoyai.warehouse.service;

import com.github.pagehelper.PageInfo;
import com.xiaoyai.warehouse.domain.dto.WarehouseCodeReportQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockReportQueryDto;

import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2023/9/20
 */
public interface IWarehouseReportService {
    PageInfo<Map> selectStockReportList(WarehouseStockReportQueryDto reportQueryDto);

    /**
     * 获取物品溯源列表
     *
     * @param reportQueryDto
     * @return
     */
    List<WarehouseGoodsTraceDto> selectGoodsTraceDtoList(WarehouseCodeReportQueryDto reportQueryDto);

    /**
     * 物品溯源详情
     * @param snCode
     * @return
     */
    WarehouseGoodsTraceDto selectGoodsTraceDto(String snCode);
}
