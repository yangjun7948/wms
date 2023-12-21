package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyai.common.annotation.Anonymous;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.WarehouseGoodsCategory;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
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
import com.xiaoyai.warehouse.domain.WarehouseCode;
import com.xiaoyai.warehouse.service.IWarehouseCodeService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 *  仓库编号Controller
 * 
 * @author junyang
 * @date 2023-09-08
 */
@RestController
@RequestMapping("/warehouse/warehousecode")
public class WarehouseCodeController extends BaseController
{
    @Autowired
    private IWarehouseCodeService warehouseCodeService;

    /**
     *  生成入库单据号
     * @param
     * @return
     */
    @GetMapping("/inReceiptCode")
    public AjaxResult InReceiptCode()
    {
        return AjaxResult.success(warehouseCodeService.getInReceiptCode());
    }

    /**
     *  生成出库单据号
     * @param
     * @return
     */
    @GetMapping("/outReceiptCode")
    public AjaxResult OutReceiptCode()
    {
        return AjaxResult.success(warehouseCodeService.getOutReceiptCode());
    }
    /**
     *  生成调拨单单据号
     * @param
     * @return
     */
    @GetMapping("/transferCode")
    public AjaxResult transferCode()
    {
        return AjaxResult.success(warehouseCodeService.getTransferCode());
    }

    @GetMapping("/applyCode")
    public AjaxResult applyCode()
    {
        return AjaxResult.success(warehouseCodeService.getApplyCode());
    }

    @GetMapping("/checkCode")
    public AjaxResult checkCode()
    {
        return AjaxResult.success(warehouseCodeService.getCheckCode());
    }
    /**
     * 生成物品SN溯源码
     * @return
     */
    @GetMapping("/goodsSNCode")
    public AjaxResult GoodsSNCode(WarehouseGoodsDto goods){
        return AjaxResult.success(warehouseCodeService.getGoodSnCodes(goods));
    }
//    @Anonymous()
//    @GetMapping("/test")
//    public String test() throws Exception {
//
//        return warehouseCodeService.getInReceiptCode();
//    }
}
