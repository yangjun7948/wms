package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateTime;
import com.xiaoyai.common.enums.warehouse.ApplyState;
import com.xiaoyai.warehouse.domain.dto.WarehouseApplyReceiptDto;
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
import com.xiaoyai.warehouse.domain.WarehouseApplyReceipt;
import com.xiaoyai.warehouse.service.IWarehouseApplyReceiptService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 要货申请Controller
 * 
 * @author junyang
 * @date 2023-09-18
 */
@RestController
@RequestMapping("/warehouse/apply")
public class WarehouseApplyReceiptController extends BaseController
{
    @Autowired
    private IWarehouseApplyReceiptService warehouseApplyReceiptService;

    /**
     * 查询要货申请列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseApplyReceiptDto warehouseApplyReceiptDto)
    {
        startPage();
        List<WarehouseApplyReceiptDto> list = warehouseApplyReceiptService.selectWarehouseApplyReceiptList(warehouseApplyReceiptDto);
        return getDataTable(list);
    }

    /**
     * 导出要货申请列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:export')")
    @Log(title = "要货申请导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseApplyReceiptDto warehouseApplyReceipt)
    {
        List<WarehouseApplyReceiptDto> list = warehouseApplyReceiptService.selectWarehouseApplyReceiptList(warehouseApplyReceipt);
        ExcelUtil<WarehouseApplyReceiptDto> util = new ExcelUtil<WarehouseApplyReceiptDto>(WarehouseApplyReceiptDto.class);
        util.exportExcel(response, list, "要货申请数据");
    }

    /**
     * 获取要货申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:query')")
    @GetMapping(value = "/{applyReceiptId}")
    public AjaxResult getInfo(@PathVariable("applyReceiptId") Long applyReceiptId)
    {
        return AjaxResult.success(warehouseApplyReceiptService.selectWarehouseApplyReceiptByApplyReceiptId(applyReceiptId));
    }

    /**
     * 新增要货申请
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:add')")
    @Log(title = "要货申请新增", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseApplyReceipt warehouseApplyReceipt)
    {
        return toAjax(warehouseApplyReceiptService.insertWarehouseApplyReceipt(warehouseApplyReceipt));
    }

    /**
     * 修改要货申请
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:edit')")
    @Log(title = "要货申请编辑", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception {
        return toAjax(warehouseApplyReceiptService.updateWarehouseApplyReceipt(warehouseApplyReceipt));
    }
    /**
     *  同意要货申请
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:edit')")
    @Log(title = "要货申请审批", businessType = BusinessType.UPDATE)
    @PutMapping("/agree")
    public AjaxResult agree(@RequestBody WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception {
        warehouseApplyReceipt.setState(ApplyState.OUTING.getCode());
        warehouseApplyReceipt.setAuditId(getUserId());
        warehouseApplyReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseApplyReceiptService.updateWarehouseApplyReceipt(warehouseApplyReceipt));
    }
    /**
     * 修改要货申请
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:edit')")
    @Log(title = "要货申请拒绝", businessType = BusinessType.UPDATE)
    @PutMapping("reject")
    public AjaxResult reject(@RequestBody WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception {
        warehouseApplyReceipt.setState(ApplyState.INIT.getCode());
        return toAjax(warehouseApplyReceiptService.updateWarehouseApplyReceipt(warehouseApplyReceipt));
    }
    /**
     * 删除要货申请
     */
    @PreAuthorize("@ss.hasPermi('warehouse:apply:remove')")
    @Log(title = "要货申请删除", businessType = BusinessType.DELETE)
	@DeleteMapping("/{applyReceiptId}")
    public AjaxResult remove(@PathVariable Long applyReceiptId)
    {
        return toAjax(warehouseApplyReceiptService.deleteWarehouseApplyReceiptByApplyReceiptId(applyReceiptId));
    }
}
