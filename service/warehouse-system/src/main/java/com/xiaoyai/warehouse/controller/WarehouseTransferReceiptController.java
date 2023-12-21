package com.xiaoyai.warehouse.controller;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateTime;
import com.xiaoyai.warehouse.domain.dto.WarehouseOutReceiptDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseTransferReceiptDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.xiaoyai.warehouse.domain.WarehouseTransferReceipt;
import com.xiaoyai.warehouse.service.IWarehouseTransferReceiptService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 调拨单据Controller
 * 
 * @author junyang
 * @date 2023-09-16
 */
@RestController
@RequestMapping("/warehouse/transfer")
public class WarehouseTransferReceiptController extends BaseController
{
    @Autowired
    private IWarehouseTransferReceiptService warehouseTransferReceiptService;

    /**
     * 查询调拨单据列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseTransferReceipt warehouseTransferReceipt)
    {
        startPage();
        List<WarehouseTransferReceiptDto> list = warehouseTransferReceiptService.selectWarehouseTransferReceiptList(warehouseTransferReceipt);
        return getDataTable(list);
    }

    /**
     * 导出调拨单据列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:export')")
    @Log(title = "调拨单据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseTransferReceipt warehouseTransferReceipt)
    {
        List<WarehouseTransferReceiptDto> list = warehouseTransferReceiptService.selectWarehouseTransferReceiptList(warehouseTransferReceipt);
        ExcelUtil<WarehouseTransferReceiptDto> util = new ExcelUtil<WarehouseTransferReceiptDto>(WarehouseTransferReceiptDto.class);
        util.exportExcel(response, list, "调拨单据数据");
    }

    /**
     * 获取调拨单据详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long warehouseTransferReceiptId)
    {
        return AjaxResult.success(warehouseTransferReceiptService.selectWarehouseTransferReceiptById(warehouseTransferReceiptId));
    }

    /**
     * 新增调拨单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:add')")
    @Log(title = "调拨单据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseTransferReceiptDto warehouseTransferReceiptDto)
    {
        warehouseTransferReceiptDto.setCreateBy(getUsername());
        return toAjax(warehouseTransferReceiptService.insertWarehouseTransferReceipt(warehouseTransferReceiptDto));
    }

    /**
     * 修改调拨单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:edit')")
    @Log(title = "调拨单据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseTransferReceiptDto warehouseTransferReceiptDto)
    {
        return toAjax(warehouseTransferReceiptService.updateWarehouseTransferReceipt(warehouseTransferReceiptDto));
    }

    /**
     * 删除调拨单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:remove')")
    @Log(title = "调拨单据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{warehouseTransferReceiptIds}")
    public AjaxResult remove(@PathVariable Long[] warehouseTransferReceiptIds)
    {
        return toAjax(warehouseTransferReceiptService.deleteWarehouseTransferReceiptByWarehouseTransferReceiptIds(warehouseTransferReceiptIds));
    }

    @PreAuthorize("@ss.hasPermi('warehouse:transfer:approve')")
    @Log(title = "调拨单据", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody WarehouseTransferReceiptDto warehouseTransferReceipt) throws Exception {
        warehouseTransferReceipt.setAuditId(getUserId());
        warehouseTransferReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseTransferReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseTransferReceiptService.approveWarehouseTransferReceipt(warehouseTransferReceipt));
    }

    /**
     * 取消审核
     * @param warehouseTransferReceipt
     * @return
     * @throws Exception
     */
    @PreAuthorize("@ss.hasPermi('warehouse:transfer:approve')")
    @Log(title = "调拨单据", businessType = BusinessType.UPDATE)
    @PostMapping("/unApprove")
    public AjaxResult unApprove(@RequestBody WarehouseTransferReceiptDto warehouseTransferReceipt) throws Exception {
        warehouseTransferReceipt.setAuditId(getUserId());
        warehouseTransferReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseTransferReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseTransferReceiptService.unApproveWarehouseOutReceipt(warehouseTransferReceipt));
    }
    /**
     *  打印入库单据
     */

    @GetMapping("/pdf/{warehouseTransferReceiptId}")
    public ResponseEntity print(@PathVariable Long warehouseTransferReceiptId)
    {
        byte[] zipBytes = warehouseTransferReceiptService.transferReceiptPdf(warehouseTransferReceiptId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf")); // 设置响应内容类型
        headers.setContentDispositionFormData("attachment", UUID.randomUUID() + ".pdf"); // 设置文件名
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }
}
