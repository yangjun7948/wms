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
import com.xiaoyai.warehouse.domain.WarehouseStore;
import com.xiaoyai.warehouse.service.IWarehouseStoreService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 门店信息Controller
 * 
 * @author junyang
 * @date 2023-09-13
 */
@RestController
@RequestMapping("/warehouse/store")
public class WarehouseStoreController extends BaseController
{
    @Autowired
    private IWarehouseStoreService warehouseStoreService;

    /**
     * 查询门店信息列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:store:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseStore warehouseStore)
    {
        startPage();
        List<WarehouseStore> list = warehouseStoreService.selectWarehouseStoreList(warehouseStore);
        return getDataTable(list);
    }

    /**
     * 导出门店信息列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:store:export')")
    @Log(title = "门店信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseStore warehouseStore)
    {
        List<WarehouseStore> list = warehouseStoreService.selectWarehouseStoreList(warehouseStore);
        ExcelUtil<WarehouseStore> util = new ExcelUtil<WarehouseStore>(WarehouseStore.class);
        util.exportExcel(response, list, "门店信息数据");
    }

    /**
     * 获取门店信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:store:query')")
    @GetMapping(value = "/{storeId}")
    public AjaxResult getInfo(@PathVariable("storeId") Long storeId)
    {
        return AjaxResult.success(warehouseStoreService.selectWarehouseStoreByStoreId(storeId));
    }

    /**
     * 新增门店信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:store:add')")
    @Log(title = "门店信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseStore warehouseStore)
    {
        return toAjax(warehouseStoreService.insertWarehouseStore(warehouseStore));
    }

    /**
     * 修改门店信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:store:edit')")
    @Log(title = "门店信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseStore warehouseStore)
    {
        return toAjax(warehouseStoreService.updateWarehouseStore(warehouseStore));
    }

    /**
     * 删除门店信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:store:remove')")
    @Log(title = "门店信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{storeIds}")
    public AjaxResult remove(@PathVariable Long[] storeIds)
    {
        return toAjax(warehouseStoreService.deleteWarehouseStoreByStoreIds(storeIds));
    }
}
