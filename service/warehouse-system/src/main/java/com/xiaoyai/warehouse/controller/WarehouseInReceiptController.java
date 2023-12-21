package com.xiaoyai.warehouse.controller;

import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.constant.WareHouseConstants;
import com.xiaoyai.common.enums.warehouse.ReceiptState;
import com.xiaoyai.common.enums.warehouse.ReceiptType;
import com.xiaoyai.warehouse.domain.WarehouseApplyReceipt;
import com.xiaoyai.warehouse.domain.WarehouseOutReceipt;
import com.xiaoyai.warehouse.domain.WarehouseTransferReceipt;
import com.xiaoyai.warehouse.domain.dto.WarehouseInReceiptDto;
import com.xiaoyai.warehouse.service.*;
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
import com.xiaoyai.warehouse.domain.WarehouseInReceipt;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 入库单据Controller
 *
 * @author junyang
 * @date 2023-09-09
 */
@RestController
@RequestMapping("/warehouse/inreceipt")
public class WarehouseInReceiptController extends BaseController {
    @Autowired
    private IWarehouseInReceiptService warehouseInReceiptService;
    @Autowired
    private IWarehouseOutReceiptService warehouseOutReceiptService;
    @Autowired
    private IWarehouseTransferReceiptService warehouseTransferReceiptService;
    @Autowired
    private IWarehouseApplyReceiptService warehouseApplyReceiptService;

    /**
     * 查询入库单据列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseInReceipt warehouseInReceipt) {
        startPage();
        List<WarehouseInReceipt> list = warehouseInReceiptService.selectWarehouseInReceiptList(warehouseInReceipt);
        return getDataTable(list);
    }

    /**
     * 导出入库单据列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:export')")
    @Log(title = "入库单据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseInReceipt warehouseInReceipt) {
        List<WarehouseInReceipt> list = warehouseInReceiptService.selectWarehouseInReceiptList(warehouseInReceipt);
        ExcelUtil<WarehouseInReceipt> util = new ExcelUtil<WarehouseInReceipt>(WarehouseInReceipt.class);
        util.exportExcel(response, list, "入库单据数据");
    }

    /**
     * 获取入库单据详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:query')")
    @GetMapping(value = "/{warehouseInReceiptId}")
    public AjaxResult getInfo(@PathVariable("warehouseInReceiptId") Long warehouseInReceiptId) {
        return AjaxResult.success(warehouseInReceiptService.selectWarehouseInReceiptByWarehouseInReceiptId(warehouseInReceiptId));
    }

    /**
     * 新增入库单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:add')")
    @Log(title = "入库单据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseInReceiptDto warehouseInReceipt) {
        warehouseInReceipt.setCreateBy(this.getUsername());
        return toAjax(warehouseInReceiptService.insertWarehouseInReceipt(warehouseInReceipt));
    }

    /**
     * 修改入库单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:edit')")
    @Log(title = "入库单据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseInReceiptDto warehouseInReceipt) {
        warehouseInReceipt.setUpdateBy(this.getUsername());
        return toAjax(warehouseInReceiptService.updateWarehouseInReceipt(warehouseInReceipt));
    }

    /**
     * 入库单审核
     *
     * @param warehouseInReceipt
     * @return
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:approve')")
    @Log(title = "入库单据审批", businessType = BusinessType.UPDATE)
    @PostMapping("/approve")
    public AjaxResult approve(@RequestBody WarehouseInReceiptDto warehouseInReceipt) throws Exception {
        warehouseInReceipt.setAuditId(getUserId());
        warehouseInReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseInReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseInReceiptService.approveWarehouseInReceipt(warehouseInReceipt));
    }

    /**
     * 取消入库单审核
     *
     * @param warehouseInReceipt
     * @return
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:approve')")
    @Log(title = "入库单据", businessType = BusinessType.UPDATE)
    @PostMapping("/unApprove")
    public AjaxResult unApprove(@RequestBody WarehouseInReceiptDto warehouseInReceipt) throws Exception {
        warehouseInReceipt.setAuditId(getUserId());
        warehouseInReceipt.setAuditName(getLoginUser().getUser().getNickName());
        warehouseInReceipt.setAuditTime(DateTime.now());
        return toAjax(warehouseInReceiptService.approveWarehouseInReceipt(warehouseInReceipt));
    }

    /**
     * 删除入库单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:remove')")
    @Log(title = "入库单据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{warehouseInReceiptId}")
    public AjaxResult remove(@PathVariable Long warehouseInReceiptId) {
        return toAjax(warehouseInReceiptService.deleteWarehouseInReceiptByWarehouseInReceiptId(warehouseInReceiptId));
    }

    /**
     * 打印入库单据
     */

    @GetMapping("/pdf/{warehouseInReceiptId}")
    public ResponseEntity print(@PathVariable Long warehouseInReceiptId) {
        byte[] zipBytes = warehouseInReceiptService.inReceiptPdf(warehouseInReceiptId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf")); // 设置响应内容类型
        headers.setContentDispositionFormData("attachment", UUID.randomUUID() + ".pdf"); // 设置文件名
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }

    /**
     * 获取待处理单据
     */
    @PreAuthorize("@ss.hasPermi('warehouse:inreceipt:approve')")
    @GetMapping("/listTodoReceipt/{type}")
    public TableDataInfo listToReceiptList(@PathVariable String type) {
        if (ReceiptType.InReceipt.getCode().equals(type)) {
            List<WarehouseInReceipt> list = warehouseInReceiptService.list(Wrappers.<WarehouseInReceipt>lambdaQuery().eq(WarehouseInReceipt::getState,
                            ReceiptState.APPROVE.getCode())
                    .orderByDesc(WarehouseInReceipt::getUpdateTime).last(" limit 10"));
            return getDataTable(list);
        } else if (ReceiptType.OutReceipt.getCode().equals(type)) {
            List<WarehouseOutReceipt> list = warehouseOutReceiptService.list(Wrappers.<WarehouseOutReceipt>lambdaQuery().eq(WarehouseOutReceipt::getState,
                            ReceiptState.APPROVE.getCode())
                    .orderByDesc(WarehouseOutReceipt::getUpdateTime)
                    .last(" limit 10"));
            return getDataTable(list);
        } else if (ReceiptType.TransferReceipt.getCode().equals(type)) {
            List<WarehouseTransferReceipt> list = warehouseTransferReceiptService.list(Wrappers.<WarehouseTransferReceipt>lambdaQuery().eq(WarehouseTransferReceipt::getState,
                            ReceiptState.APPROVE.getCode())
                    .orderByDesc(WarehouseTransferReceipt::getUpdateTime)
                    .last(" limit 10"));
            return getDataTable(list);
        } else if (ReceiptType.ApplyReceipt.getCode().equals(type)) {
            List<WarehouseApplyReceipt> list = warehouseApplyReceiptService.list(Wrappers.<WarehouseApplyReceipt>lambdaQuery().eq(WarehouseApplyReceipt::getState,
                            ReceiptState.APPROVE.getCode())
                    .orderByDesc(WarehouseApplyReceipt::getUpdateTime)
                    .last(" limit 10"));
            return getDataTable(list);
        } else {
            return null;
        }
    }

}
