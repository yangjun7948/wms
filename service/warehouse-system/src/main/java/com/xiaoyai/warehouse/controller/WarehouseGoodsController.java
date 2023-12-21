package com.xiaoyai.warehouse.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyai.common.annotation.Anonymous;
import com.xiaoyai.warehouse.domain.WarehouseGoodsCategory;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockGoodsDto;
import com.xiaoyai.warehouse.service.IWarehouseGoodsCategoryService;
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
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.service.IWarehouseGoodsService;
import com.xiaoyai.common.utils.poi.ExcelUtil;
import com.xiaoyai.common.core.page.TableDataInfo;

/**
 * 物品Controller
 *
 * @author junyang
 * @date 2023-09-07
 */
@RestController
@RequestMapping("/warehouse/goods")
public class WarehouseGoodsController extends BaseController {
    @Autowired
    private IWarehouseGoodsService warehouseGoodsService;
    @Autowired
    private IWarehouseGoodsCategoryService warehouseGoodsCategoryService;

    /**
     * 查询物品列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goods:list')")
    @GetMapping("/list")
    public TableDataInfo list(WarehouseGoodsQueryDto warehouseGoods) {
        startPage();
        List<WarehouseGoods> list = warehouseGoodsService.selectWarehouseGoodsList(warehouseGoods);
        return getDataTable(list);
    }

    /**
     * 导出物品列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goods:export')")
    @Log(title = "物品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseGoodsQueryDto warehouseGoods) {
        List<WarehouseGoods> list = warehouseGoodsService.selectWarehouseGoodsList(warehouseGoods);
        ExcelUtil<WarehouseGoods> util = new ExcelUtil<WarehouseGoods>(WarehouseGoods.class);
        util.exportExcel(response, list, "物品数据");
    }

    /**
     * 获取物品详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goods:query')")
    @GetMapping(value = "/{goodsId}")
    public AjaxResult getInfo(@PathVariable("goodsId") Long goodsId) {
        return AjaxResult.success(warehouseGoodsService.selectWarehouseGoodsByGoodsId(goodsId));
    }

    /**
     * 新增物品
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goods:add')")
    @Log(title = "物品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseGoods warehouseGoods) throws Exception {
        warehouseGoods.setCreateBy(this.getUsername());
        return toAjax(warehouseGoodsService.insertWarehouseGoods(warehouseGoods));
    }

    /**
     * 修改物品
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goods:edit')")
    @Log(title = "物品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseGoods warehouseGoods) {
        warehouseGoods.setUpdateBy(this.getUsername());
        return toAjax(warehouseGoodsService.updateWarehouseGoods(warehouseGoods));
    }

    /**
     * 删除物品
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goods:remove')")
    @Log(title = "物品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{goodsIds}")
    public AjaxResult remove(@PathVariable Long[] goodsIds) {
        return toAjax(warehouseGoodsService.deleteWarehouseGoodsByGoodsIds(goodsIds));
    }

    /**
     * 库存物品列表
     * @param warehouseGoods
     * @return
     */

    @GetMapping("/listStockGoods")
    public TableDataInfo listStockGoods(WarehouseStockGoodsDto warehouseGoods) {
        startPage();
        List<WarehouseStockGoodsDto> list = warehouseGoodsService.selectStockGoodsList(warehouseGoods);
        return getDataTable(list);
    }


    /**
     * 溯源码图片预览
     * @param receiptId
     * @param goodsId
     * @return
     * @throws IOException
     */
    @GetMapping("/snPreview/{receiptId}/{goodsId}")
    public ResponseEntity<byte[]> snPreview(@PathVariable("receiptId") Long receiptId, @PathVariable("goodsId") Long goodsId) throws IOException {
        byte[] zipBytes = warehouseGoodsService.getGoodsSnImage(receiptId, goodsId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 设置响应内容类型
        headers.setContentDispositionFormData("attachment", UUID.randomUUID() + ".jpg"); // 设置文件名
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }

    /**
     *  溯源码打印pdf
     * @param receiptId
     * @param goodsId
     * @return
     * @throws IOException
     */
    @GetMapping("/snCodePdf/{receiptId}/{goodsId}")
    public ResponseEntity<byte[]> snCodePdf(@PathVariable("receiptId") Long receiptId, @PathVariable("goodsId") Long goodsId) throws IOException {
        byte[] zipBytes = warehouseGoodsService.getGoodsSnPdf(receiptId, goodsId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf")); // 设置响应内容类型
        headers.setContentDispositionFormData("attachment", UUID.randomUUID() + ".pdf"); // 设置文件名
        return new ResponseEntity<>(zipBytes, headers, HttpStatus.OK);
    }
}
