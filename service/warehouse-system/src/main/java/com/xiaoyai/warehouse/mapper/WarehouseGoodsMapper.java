package com.xiaoyai.warehouse.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseCustomer;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockGoodsDto;

/**
 * 物品Mapper接口
 * 
 * @author junyang
 * @date 2023-09-07
 */
public interface WarehouseGoodsMapper  extends BaseMapper<WarehouseGoods>
{
    /**
     * 查询物品
     * 
     * @param goodsId 物品主键
     * @return 物品
     */
    public WarehouseGoods selectWarehouseGoodsByGoodsId(Long goodsId);

    /**
     * 查询物品列表
     * 
     * @param warehouseGoods 物品
     * @return 物品集合
     */
    public List<WarehouseGoods> selectWarehouseGoodsList(WarehouseGoodsQueryDto warehouseGoods);

    /**
     * 新增物品
     * 
     * @param warehouseGoods 物品
     * @return 结果
     */
    public int insertWarehouseGoods(WarehouseGoods warehouseGoods);

    /**
     * 修改物品
     * 
     * @param warehouseGoods 物品
     * @return 结果
     */
    public int updateWarehouseGoods(WarehouseGoods warehouseGoods);

    /**
     * 删除物品
     * 
     * @param goodsId 物品主键
     * @return 结果
     */
    public int deleteWarehouseGoodsByGoodsId(Long goodsId);

    /**
     * 批量删除物品
     * 
     * @param goodsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsByGoodsIds(Long[] goodsIds);

    /**
     * 查询仓库物品信息
     * @param warehouseGoods
     * @return
     */
    List<WarehouseStockGoodsDto> selectStockGoodsList(WarehouseStockGoodsDto warehouseGoods);
}
