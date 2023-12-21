package com.xiaoyai.warehouse.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.xiaoyai.common.core.domain.TreeSelect;
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
import com.xiaoyai.warehouse.domain.WarehouseGoodsCategory;
import com.xiaoyai.warehouse.service.IWarehouseGoodsCategoryService;
import com.xiaoyai.common.utils.poi.ExcelUtil;

/**
 * 物料分类Controller
 * 
 * @author junyang
 * @date 2023-09-08
 */
@RestController
@RequestMapping("/warehouse/goodscategory")
public class WarehouseGoodsCategoryController extends BaseController
{
    @Autowired
    private IWarehouseGoodsCategoryService warehouseGoodsCategoryService;

    /**
     * 查询物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:list')")
    @GetMapping("/list")
    public AjaxResult list(WarehouseGoodsCategory warehouseGoodsCategory)
    {
        List<WarehouseGoodsCategory> list = warehouseGoodsCategoryService.selectWarehouseGoodsCategoryList(warehouseGoodsCategory);
        return AjaxResult.success(list);
    }
    /**
     * 查询物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:list')")
    @GetMapping("/tree")
    public AjaxResult tree(WarehouseGoodsCategory warehouseGoodsCategory)
    {
        List<TreeSelect> list = warehouseGoodsCategoryService.selectWarehouseGoodsCategoryTree(warehouseGoodsCategory);
        return AjaxResult.success(list);
    }
    /**
     * 导出物料分类列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:export')")
    @Log(title = "物料分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WarehouseGoodsCategory warehouseGoodsCategory)
    {
        List<WarehouseGoodsCategory> list = warehouseGoodsCategoryService.selectWarehouseGoodsCategoryList(warehouseGoodsCategory);
        ExcelUtil<WarehouseGoodsCategory> util = new ExcelUtil<WarehouseGoodsCategory>(WarehouseGoodsCategory.class);
        util.exportExcel(response, list, "物料分类数据");
    }

    /**
     * 获取物料分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:query')")
    @GetMapping(value = "/{categoryId}")
    public AjaxResult getInfo(@PathVariable("categoryId") Long categoryId)
    {
        return AjaxResult.success(warehouseGoodsCategoryService.selectWarehouseGoodsCategoryByCategoryId(categoryId));
    }

    /**
     * 新增物料分类
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:add')")
    @Log(title = "物料分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WarehouseGoodsCategory warehouseGoodsCategory)
    {
        try {
            return toAjax(warehouseGoodsCategoryService.insertWarehouseGoodsCategory(warehouseGoodsCategory));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改物料分类
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:edit')")
    @Log(title = "物料分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WarehouseGoodsCategory warehouseGoodsCategory)
    {
        return toAjax(warehouseGoodsCategoryService.updateWarehouseGoodsCategory(warehouseGoodsCategory));
    }

    /**
     * 删除物料分类
     */
    @PreAuthorize("@ss.hasPermi('warehouse:goodscategory:remove')")
    @Log(title = "物料分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{categoryIds}")
    public AjaxResult remove(@PathVariable Long[] categoryIds)
    {
        return toAjax(warehouseGoodsCategoryService.deleteWarehouseGoodsCategoryByCategoryIds(categoryIds));
    }
}
