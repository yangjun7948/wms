package com.xiaoyai.warehouse.controller;

import com.github.pagehelper.PageInfo;
import com.xiaoyai.common.core.controller.BaseController;
import com.xiaoyai.common.core.domain.AjaxResult;
import com.xiaoyai.common.core.page.TableDataInfo;
import com.xiaoyai.warehouse.domain.WarehouseInReceipt;
import com.xiaoyai.warehouse.domain.dto.WarehouseCodeReportQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockReportQueryDto;
import com.xiaoyai.warehouse.service.IWarehouseReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2023/9/20
 */
@RestController
@RequestMapping("/warehouse/report")
public class WarehouseReportController extends BaseController {

    @Autowired
    IWarehouseReportService iWarehouseReportService;

    @PreAuthorize("@ss.hasPermi('warehouse:report:stock')")
    @PostMapping("/stock")
    public TableDataInfo list(@RequestBody  WarehouseStockReportQueryDto reportQueryDto)
    {
        startPage();
        PageInfo<Map> list = iWarehouseReportService.selectStockReportList(reportQueryDto);
        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('warehouse:report:code')")
    @PostMapping("/code")
    public TableDataInfo list(@RequestBody WarehouseCodeReportQueryDto reportQueryDto)
    {
        startPage();
        List<WarehouseGoodsTraceDto> list = iWarehouseReportService.selectGoodsTraceDtoList(reportQueryDto);
        return getDataTable(list);
    }
    @GetMapping("/code/{snCode}")
    public AjaxResult goodsCodeDetail(@PathVariable("snCode")String snCode)
    {
        return AjaxResult.success(iWarehouseReportService.selectGoodsTraceDto(snCode));
    }

}
