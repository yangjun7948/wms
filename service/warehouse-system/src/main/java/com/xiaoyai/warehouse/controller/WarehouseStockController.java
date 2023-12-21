package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyai.warehouse.domain.WarehouseStockWarn;
import com.xiaoyai.warehouse.service.IWarehouseStockWarnService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.xiaoyai.common.annotation.Log;
import com.xiaoyai.common.core.controller.BaseController;
import com.xiaoyai.common.core.domain.AjaxResult;
import com.xiaoyai.common.enums.BusinessType;
import com.xiaoyai.warehouse.domain.WarehouseStock;
import com.xiaoyai.warehouse.service.IWarehouseStockService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 库存Controller
 * 
 * @author junyang
 * @date 2023-09-13
 */
@RestController
@RequestMapping("/warehouse/warehouseStock")
public class WarehouseStockController extends BaseController
{
    @Autowired
    private IWarehouseStockService warehouseStockService;
    @Autowired
    private IWarehouseStockWarnService warehouseStockWarnService;
    @GetMapping("/warn")
    private TableDataInfo stockWarn(){
        startPage();
        List<WarehouseStockWarn>list= warehouseStockWarnService.stockWarnList();
        return getDataTable(list);
    }
}
