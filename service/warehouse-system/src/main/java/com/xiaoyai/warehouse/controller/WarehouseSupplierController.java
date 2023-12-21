package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.xiaoyai.warehouse.domain.WarehouseSupplier;
import com.xiaoyai.warehouse.service.IWarehouseSupplierService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 供应商库Controller
 * 
 * @author junyang
 * @date 2023-09-07
 */
@RestController
@RequestMapping("/warehouse/supplier")
public class WarehouseSupplierController extends BaseController
{
    @Autowired
    private IWarehouseSupplierService warehouseSupplierService;

    /**
     * 查询供应商库列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseSupplier warehouseSupplier)
    {
        startPage();
        List<WarehouseSupplier> list = warehouseSupplierService.selectWarehouseSupplierList(warehouseSupplier);
        return getDataTable(list);
    }

    /**
     * 导出供应商库列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:supplier:export')")
    @Log(title = "供应商库", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseSupplier warehouseSupplier)
    {
        List<WarehouseSupplier> list = warehouseSupplierService.selectWarehouseSupplierList(warehouseSupplier);
        ExcelUtil<WarehouseSupplier> util = new ExcelUtil<WarehouseSupplier>(WarehouseSupplier.class);
        util.exportExcel(response, list, "供应商库数据");
    }

    /**
     * 获取供应商库详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:supplier:query')")
    @GetMapping(value = "/{supplierId}")
    public AjaxResult getInfo(@PathVariable("supplierId") Long supplierId)
    {
        return AjaxResult.success(warehouseSupplierService.selectWarehouseSupplierBySupplierId(supplierId));
    }

    /**
     * 新增供应商库
     */
    @PreAuthorize("@ss.hasPermi('warehouse:supplier:add')")
    @Log(title = "供应商库", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseSupplier warehouseSupplier)
    {
        return toAjax(warehouseSupplierService.insertWarehouseSupplier(warehouseSupplier));
    }

    /**
     * 修改供应商库
     */
    @PreAuthorize("@ss.hasPermi('warehouse:supplier:edit')")
    @Log(title = "供应商库", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseSupplier warehouseSupplier)
    {
        return toAjax(warehouseSupplierService.updateWarehouseSupplier(warehouseSupplier));
    }

    /**
     * 删除供应商库
     */
    @PreAuthorize("@ss.hasPermi('warehouse:supplier:remove')")
    @Log(title = "供应商库", businessType = BusinessType.DELETE)
	@DeleteMapping("/{supplierIds}")
    public AjaxResult remove(@PathVariable Long[] supplierIds)
    {
        return toAjax(warehouseSupplierService.deleteWarehouseSupplierBySupplierIds(supplierIds));
    }
}
