package com.xiaoyai.warehouse.service;

import java.io.IOException;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.dto.WarehouseApplyReceiptDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;

/**
 * 物品Service接口
 * 
 * @author junyang
 * @date 2023-09-07
 */
public interface IWarehouseGoodsService 
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
    public int insertWarehouseGoods(WarehouseGoods warehouseGoods) throws Exception;

    /**
     * 修改物品
     * 
     * @param warehouseGoods 物品
     * @return 结果
     */
    public int updateWarehouseGoods(WarehouseGoods warehouseGoods);

    /**
     * 批量删除物品
     * 
     * @param goodsIds 需要删除的物品主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsByGoodsIds(Long[] goodsIds);

    /**
     * 删除物品信息
     * 
     * @param goodsId 物品主键
     * @return 结果
     */
    public int deleteWarehouseGoodsByGoodsId(Long goodsId);

    /**
     * 获取物品的sn图片zip
     * @param receiptId
     * @param goodsId
     * @return
     */
    byte[] getGoodsSnImage(Long receiptId, Long goodsId) throws IOException;

    /**
     * 获取物品的sn打印文件
     * @param receiptId
     * @param goodsId
     * @return
     */
    byte[] getGoodsSnPdf(Long receiptId, Long goodsId) throws IOException;

    /**
     *  查询仓库物品信息
     * @param warehouseGoods
     * @return
     */
    List<WarehouseStockGoodsDto> selectStockGoodsList(WarehouseStockGoodsDto warehouseGoods);

    /**
     * 获取物品列表
     *
     * @param warehouseGoods
     * @return
     */
    PageInfo<WarehouseGoodsDto> selectWarehouseGoodsVoList(WarehouseGoodsQueryDto warehouseGoods);

    WarehouseGoodsVo getSnCodeDetail(String code);
}
