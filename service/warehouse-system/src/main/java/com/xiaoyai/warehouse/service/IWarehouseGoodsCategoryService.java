package com.xiaoyai.warehouse.service;

import java.util.List;

import com.xiaoyai.common.core.domain.TreeSelect;
import com.xiaoyai.warehouse.domain.WarehouseGoodsCategory;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 物料分类Service接口
 * 
 * @author junyang
 * @date 2023-09-08
 */
public interface IWarehouseGoodsCategoryService extends IService<WarehouseGoodsCategory>
{
    /**
     * 查询物料分类
     * 
     * @param categoryId 物料分类主键
     * @return 物料分类
     */
    public WarehouseGoodsCategory selectWarehouseGoodsCategoryByCategoryId(Long categoryId);

    /**
     * 查询物料分类列表
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 物料分类集合
     */
    public List<WarehouseGoodsCategory> selectWarehouseGoodsCategoryList(WarehouseGoodsCategory warehouseGoodsCategory);

    /**
     * 新增物料分类
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 结果
     */
    public int insertWarehouseGoodsCategory(WarehouseGoodsCategory warehouseGoodsCategory) throws Exception;

    /**
     * 修改物料分类
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 结果
     */
    public int updateWarehouseGoodsCategory(WarehouseGoodsCategory warehouseGoodsCategory);

    /**
     * 批量删除物料分类
     * 
     * @param categoryIds 需要删除的物料分类主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsCategoryByCategoryIds(Long[] categoryIds);

    /**
     * 删除物料分类信息
     * 
     * @param categoryId 物料分类主键
     * @return 结果
     */
    public int deleteWarehouseGoodsCategoryByCategoryId(Long categoryId);

    List<TreeSelect> selectWarehouseGoodsCategoryTree(WarehouseGoodsCategory warehouseGoodsCategory);
}
