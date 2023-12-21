package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.core.domain.TreeSelect;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.common.utils.TreeUtils;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseGoodsCategoryMapper;
import com.xiaoyai.warehouse.domain.WarehouseGoodsCategory;
import com.xiaoyai.warehouse.service.IWarehouseGoodsCategoryService;

import javax.swing.tree.TreeNode;

/**
 * 物料分类Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-08
 */
@Service
public class WarehouseGoodsCategoryServiceImpl extends ServiceImpl<WarehouseGoodsCategoryMapper, WarehouseGoodsCategory> implements IWarehouseGoodsCategoryService
{
    @Autowired
    private WarehouseGoodsCategoryMapper warehouseGoodsCategoryMapper;

    /**
     * 查询物料分类
     * 
     * @param categoryId 物料分类主键
     * @return 物料分类
     */
    @Override
    public WarehouseGoodsCategory selectWarehouseGoodsCategoryByCategoryId(Long categoryId)
    {
        return warehouseGoodsCategoryMapper.selectWarehouseGoodsCategoryByCategoryId(categoryId);
    }

    /**
     * 查询物料分类列表
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 物料分类
     */
    @Override
    public List<WarehouseGoodsCategory> selectWarehouseGoodsCategoryList(WarehouseGoodsCategory warehouseGoodsCategory)
    {
        return warehouseGoodsCategoryMapper.selectWarehouseGoodsCategoryList(warehouseGoodsCategory);
    }

    /**
     * 新增物料分类
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 结果
     */
    @Override
    public int insertWarehouseGoodsCategory(WarehouseGoodsCategory warehouseGoodsCategory) throws Exception {
        WarehouseGoodsCategory query= new WarehouseGoodsCategory();
        query.setCategoryCode(warehouseGoodsCategory.getCategoryCode());
        List<WarehouseGoodsCategory> categoryList=  warehouseGoodsCategoryMapper.selectWarehouseGoodsCategoryList(query);
        if(categoryList.size()>0){
            throw new Exception("物品编号重复");
        }
        warehouseGoodsCategory.setCreateTime(DateUtils.getNowDate());
        return warehouseGoodsCategoryMapper.insertWarehouseGoodsCategory(warehouseGoodsCategory);
    }

    /**
     * 修改物料分类
     * 
     * @param warehouseGoodsCategory 物料分类
     * @return 结果
     */
    @Override
    public int updateWarehouseGoodsCategory(WarehouseGoodsCategory warehouseGoodsCategory)
    {
        warehouseGoodsCategory.setUpdateTime(DateUtils.getNowDate());
        return warehouseGoodsCategoryMapper.updateWarehouseGoodsCategory(warehouseGoodsCategory);
    }

    /**
     * 批量删除物料分类
     * 
     * @param categoryIds 需要删除的物料分类主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsCategoryByCategoryIds(Long[] categoryIds)
    {
        return warehouseGoodsCategoryMapper.deleteWarehouseGoodsCategoryByCategoryIds(categoryIds);
    }

    /**
     * 删除物料分类信息
     * 
     * @param categoryId 物料分类主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsCategoryByCategoryId(Long categoryId)
    {
        return warehouseGoodsCategoryMapper.deleteWarehouseGoodsCategoryByCategoryId(categoryId);
    }

    @Override
    public List<TreeSelect> selectWarehouseGoodsCategoryTree(WarehouseGoodsCategory warehouseGoodsCategory) {
        List<WarehouseGoodsCategory> goodsCategoryList= warehouseGoodsCategoryMapper.selectWarehouseGoodsCategoryList(warehouseGoodsCategory);
        goodsCategoryList.forEach(t->t.setTreeId(t.getCategoryId()));
        List<WarehouseGoodsCategory>  categoryTree=  TreeUtils.build(goodsCategoryList,0);
        return categoryTree.stream().map(t-> getTreeSelect(t)).collect(Collectors.toList());
    }
    private TreeSelect getTreeSelect(WarehouseGoodsCategory goodsCategory){
        TreeSelect treeSelect =new TreeSelect();
        treeSelect.setId(goodsCategory.getCategoryId());
        treeSelect.setLabel(goodsCategory.getCategoryName());
        treeSelect.setChildren(goodsCategory.getChildren().stream().map(t-> getTreeSelect((WarehouseGoodsCategory)t)).collect(Collectors.toList()));
        return treeSelect;
    }
}
