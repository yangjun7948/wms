package com.xiaoyai.warehouse.controller;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateTime;
import com.xiaoyai.warehouse.domain.dto.WarehouseInReceiptDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseOutReceiptDto;
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
import com.xiaoyai.warehouse.domain.WarehouseOutReceipt;
import com.xiaoyai.warehouse.service.IWarehouseOutReceiptService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 出库单据Controller
 * 
 * @author junyang
 * @date 2023-09-13
 */
@RestController
@RequestMapping("/warehouse/outreceipt")
public class WarehouseOutReceiptController extends BaseController
{
    @Autowired
    private IWarehouseOutReceiptService warehouseOutReceiptService;

    /**
     * 查询出库单据列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseOutReceipt warehouseOutReceipt)
    {
        startPage();
        List<WarehouseOutReceipt> list = warehouseOutReceiptService.selectWarehouseOutReceiptList(warehouseOutReceipt);
        return getDataTable(list);
    }

    /**
     * 导出出库单据列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:export')")
    @Log(title = "出库单据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseOutReceipt warehouseOutReceipt)
    {
        List<WarehouseOutReceipt> list = warehouseOutReceiptService.selectWarehouseOutReceiptList(warehouseOutReceipt);
        ExcelUtil<WarehouseOutReceipt> util = new ExcelUtil<WarehouseOutReceipt>(WarehouseOutReceipt.class);
        util.exportExcel(response, list, "出库单据数据");
    }

    /**
     * 获取出库单据详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:query')")
    @GetMapping(value = "/{warehouseOutReceiptId}")
    public AjaxResult getInfo(@PathVariable("warehouseOutReceiptId") Long warehouseOutReceiptId)
    {
        return AjaxResult.success(warehouseOutReceiptService.selectWarehouseOutReceiptByWarehouseOutReceiptId(warehouseOutReceiptId));
    }

    /**
     * 新增出库单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:add')")
    @Log(title = "出库单据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseOutReceiptDto warehouseOutReceiptDto)
    {
        warehouseOutReceiptDto.setCreateBy(this.getUsername());
        return toAjax(warehouseOutReceiptService.insertWarehouseOutReceipt(warehouseOutReceiptDto));
    }

    /**
     * 修改出库单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:edit')")
    @Log(title = "出库单据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseOutReceiptDto warehouseOutReceipt)
    {
        warehouseOutReceipt.setUpdateBy(this.getUsername());
        return toAjax(warehouseOutReceiptService.updateWarehouseOutReceipt(warehouseOutReceipt));
    }

    /**
     * 删除出库单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:remove')")
    @Log(title = "出库单据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{warehouseOutReceiptIds}")
    public AjaxResult remove(@PathVariable Long[] warehouseOutReceiptIds)
    {
        return toAjax(warehouseOutReceiptService.deleteWarehouseOutReceiptByWarehouseOutReceiptIds(warehouseOutReceiptIds));
    }

    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:approve')")
    @Log(title = "入库单据审批", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody WarehouseOutReceiptDto warehouseOutReceipt) throws Exception {
        warehouseOutReceipt.setAuditId(getUserId());
        warehouseOutReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseOutReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseOutReceiptService.approveWarehouseOutReceipt(warehouseOutReceipt));
    }

    /**
     * 取消审核
     * @param warehouseOutReceipt
     * @return
     * @throws Exception
     */
    @PreAuthorize("@ss.hasPermi('warehouse:outreceipt:approve')")
    @Log(title = "入库单据", businessType = BusinessType.UPDATE)
    @PostMapping("/unApprove")
    public AjaxResult unApprove(@RequestBody WarehouseOutReceiptDto warehouseOutReceipt) throws Exception {
        warehouseOutReceipt.setAuditId(getUserId());
        warehouseOutReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseOutReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseOutReceiptService.unApproveWarehouseOutReceipt(warehouseOutReceipt));
    }
    /**
     *  打印入库单据
     */

    @GetMapping("/pdf/{warehouseOutReceiptId}")
    public ResponseEntity print(@PathVariable Long warehouseOutReceiptId)
    {
        byte[] zipBytes = warehouseOutReceiptService.outReceiptPdf(warehouseOutReceiptId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf")); // 设置响应内容类型
        headers.setContentDispositionFormData("attachment", UUID.randomUUID() + ".pdf"); // 设置文件名
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }
}
