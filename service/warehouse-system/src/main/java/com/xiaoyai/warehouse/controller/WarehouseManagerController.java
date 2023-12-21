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
import com.xiaoyai.warehouse.domain.WarehouseManager;
import com.xiaoyai.warehouse.service.IWarehouseManagerService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 仓库权限Controller
 * 
 * @author junyang
 * @date 2023-11-13
 */
@RestController
@RequestMapping("/warehouse/manager")
public class WarehouseManagerController extends BaseController
{
    @Autowired
    private IWarehouseManagerService warehouseManagerService;

    /**
     * 查询仓库权限列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:manager:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseManager warehouseManager)
    {
        startPage();
        List<WarehouseManager> list = warehouseManagerService.selectWarehouseManagerList(warehouseManager);
        return getDataTable(list);
    }

    /**
     * 导出仓库权限列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:manager:export')")
    @Log(title = "仓库权限", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseManager warehouseManager)
    {
        List<WarehouseManager> list = warehouseManagerService.selectWarehouseManagerList(warehouseManager);
        ExcelUtil<WarehouseManager> util = new ExcelUtil<WarehouseManager>(WarehouseManager.class);
        util.exportExcel(response, list, "仓库权限数据");
    }

    /**
     * 获取仓库权限详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:manager:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(warehouseManagerService.selectWarehouseManagerByUserId(userId));
    }

    /**
     * 新增仓库权限
     */
    @PreAuthorize("@ss.hasPermi('warehouse:manager:add')")
    @Log(title = "仓库权限", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseManager warehouseManager)
    {
        return toAjax(warehouseManagerService.insertWarehouseManager(warehouseManager));
    }

    /**
     * 修改仓库权限
     */
    @PreAuthorize("@ss.hasPermi('warehouse:manager:edit')")
    @Log(title = "仓库权限", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseManager warehouseManager)
    {
        return toAjax(warehouseManagerService.updateWarehouseManager(warehouseManager));
    }

    /**
     * 删除仓库权限
     */
    @PreAuthorize("@ss.hasPermi('warehouse:manager:remove')")
    @Log(title = "仓库权限", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(warehouseManagerService.deleteWarehouseManagerByUserIds(userIds));
    }
}
