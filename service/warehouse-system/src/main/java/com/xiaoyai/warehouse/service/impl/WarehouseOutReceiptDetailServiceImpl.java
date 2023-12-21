package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseOutReceiptDetailMapper;
import com.xiaoyai.warehouse.domain.WarehouseOutReceiptDetail;
import com.xiaoyai.warehouse.service.IWarehouseOutReceiptDetailService;

/**
 *  物品出库明细Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-13
 */
@Service
public class WarehouseOutReceiptDetailServiceImpl extends ServiceImpl<WarehouseOutReceiptDetailMapper, WarehouseOutReceiptDetail> implements IWarehouseOutReceiptDetailService
{
    @Autowired
    private WarehouseOutReceiptDetailMapper warehouseOutReceiptDetailMapper;

    /**
     * 查询 物品出库明细
     * 
     * @param outReceiptDetailId  物品出库明细主键
     * @return  物品出库明细
     */
    @Override
    public WarehouseOutReceiptDetail selectWarehouseOutReceiptDetailByOutReceiptDetailId(Long outReceiptDetailId)
    {
        return warehouseOutReceiptDetailMapper.selectWarehouseOutReceiptDetailByOutReceiptDetailId(outReceiptDetailId);
    }

    /**
     * 查询 物品出库明细列表
     * 
     * @param warehouseOutReceiptDetail  物品出库明细
     * @return  物品出库明细
     */
    @Override
    public List<WarehouseOutReceiptDetail> selectWarehouseOutReceiptDetailList(WarehouseOutReceiptDetail warehouseOutReceiptDetail)
    {
        return warehouseOutReceiptDetailMapper.selectWarehouseOutReceiptDetailList(warehouseOutReceiptDetail);
    }

    /**
     * 新增 物品出库明细
     * 
     * @param warehouseOutReceiptDetail  物品出库明细
     * @return 结果
     */
    @Override
    public int insertWarehouseOutReceiptDetail(WarehouseOutReceiptDetail warehouseOutReceiptDetail)
    {
        return warehouseOutReceiptDetailMapper.insertWarehouseOutReceiptDetail(warehouseOutReceiptDetail);
    }

    /**
     * 修改 物品出库明细
     * 
     * @param warehouseOutReceiptDetail  物品出库明细
     * @return 结果
     */
    @Override
    public int updateWarehouseOutReceiptDetail(WarehouseOutReceiptDetail warehouseOutReceiptDetail)
    {
        return warehouseOutReceiptDetailMapper.updateWarehouseOutReceiptDetail(warehouseOutReceiptDetail);
    }

    /**
     * 批量删除 物品出库明细
     * 
     * @param outReceiptDetailIds 需要删除的 物品出库明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseOutReceiptDetailByOutReceiptDetailIds(Long[] outReceiptDetailIds)
    {
        return warehouseOutReceiptDetailMapper.deleteWarehouseOutReceiptDetailByOutReceiptDetailIds(outReceiptDetailIds);
    }

    /**
     * 删除 物品出库明细信息
     * 
     * @param outReceiptDetailId  物品出库明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseOutReceiptDetailByOutReceiptDetailId(Long outReceiptDetailId)
    {
        return warehouseOutReceiptDetailMapper.deleteWarehouseOutReceiptDetailByOutReceiptDetailId(outReceiptDetailId);
    }

    @Override
    public List<WarehouseGoodsDto> getOutReceiptGoodsList(Long warehouseOutReceiptId) {
        return warehouseOutReceiptDetailMapper.getOutReceiptGoodsList(warehouseOutReceiptId);
    }

    @Override
    public List<WarehouseGoodsDto> getOutReceiptGoodsWithSnCodeList(Long warehouseOutReceiptId) {
        return warehouseOutReceiptDetailMapper.getOutReceiptGoodsWithSnCodeList(warehouseOutReceiptId);
    }
}
