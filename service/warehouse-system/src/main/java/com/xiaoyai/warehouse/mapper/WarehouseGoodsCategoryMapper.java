package com.xiaoyai.warehouse.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseGoodsCategory;

/**
 * 物料分类Mapper接口
 * 
 * @author junyang
 * @date 2023-09-08
 */
public interface WarehouseGoodsCategoryMapper extends BaseMapper<WarehouseGoodsCategory>
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
    public int insertWarehouseGoodsCategory(WarehouseGoodsCategory warehouseGoodsCategory);

    /**
     * 修改物料分类
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 结果
     */
    public int updateWarehouseGoodsCategory(WarehouseGoodsCategory warehouseGoodsCategory);

    /**
     * 删除物料分类
     * 
     * @param categoryId 物料分类主键
     * @return 结果
     */
    public int deleteWarehouseGoodsCategoryByCategoryId(Long categoryId);

    /**
     * 批量删除物料分类
     * 
     * @param categoryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsCategoryByCategoryIds(Long[] categoryIds);
}
