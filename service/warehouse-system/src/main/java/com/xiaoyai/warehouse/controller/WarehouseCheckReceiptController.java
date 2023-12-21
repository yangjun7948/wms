package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDto;
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
import com.xiaoyai.warehouse.domain.WarehouseCheckReceipt;
import com.xiaoyai.warehouse.service.IWarehouseCheckReceiptService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 库存盘点Controller
 * 
 * @author junyang
 * @date 2023-10-07
 */
@RestController
@RequestMapping("/warehouse/check")
public class WarehouseCheckReceiptController extends BaseController
{
    @Autowired
    private IWarehouseCheckReceiptService warehouseCheckReceiptService;

    /**
     * 查询库存盘点列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:check:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseCheckReceipt warehouseCheckReceipt)
    {
        startPage();
        List<WarehouseCheckReceiptDto> list = warehouseCheckReceiptService.selectWarehouseCheckReceiptDtoList(warehouseCheckReceipt);
        return getDataTable(list);
    }

    /**
     * 导出库存盘点列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:check:export')")
    @Log(title = "库存盘点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseCheckReceipt warehouseCheckReceipt)
    {
        List<WarehouseCheckReceipt> list = warehouseCheckReceiptService.selectWarehouseCheckReceiptList(warehouseCheckReceipt);
        ExcelUtil<WarehouseCheckReceipt> util = new ExcelUtil<WarehouseCheckReceipt>(WarehouseCheckReceipt.class);
        util.exportExcel(response, list, "库存盘点数据");
    }

    /**
     * 获取库存盘点详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:check:query')")
    @GetMapping(value = "/{checkReceiptId}")
    public AjaxResult getInfo(@PathVariable("checkReceiptId") Long checkReceiptId)
    {
        return AjaxResult.success(warehouseCheckReceiptService.selectWarehouseCheckReceiptByCheckReceiptId(checkReceiptId));
    }

    /**
     * 新增库存盘点
     */
    @PreAuthorize("@ss.hasPermi('warehouse:check:add')")
    @Log(title = "库存盘点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseCheckReceiptDto warehouseCheckReceipt)
    {
        warehouseCheckReceipt.setCheckUserId(this.getUserId());
        warehouseCheckReceipt.setCreateBy(this.getUsername());
        return toAjax(warehouseCheckReceiptService.insertWarehouseCheckReceipt(warehouseCheckReceipt));
    }

    /**
     * 修改库存盘点
     */
    @PreAuthorize("@ss.hasPermi('warehouse:check:edit')")
    @Log(title = "库存盘点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseCheckReceipt warehouseCheckReceipt)
    {
        return toAjax(warehouseCheckReceiptService.updateWarehouseCheckReceipt(warehouseCheckReceipt));
    }

    /**
     * 删除库存盘点
     */
    @PreAuthorize("@ss.hasPermi('warehouse:check:remove')")
    @Log(title = "库存盘点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{checkReceiptIds}")
    public AjaxResult remove(@PathVariable Long[] checkReceiptIds)
    {
        return toAjax(warehouseCheckReceiptService.deleteWarehouseCheckReceiptByCheckReceiptIds(checkReceiptIds));
    }
}
