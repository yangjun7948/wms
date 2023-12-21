package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseTransferReceiptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseTransferReceiptDetailMapper;
import com.xiaoyai.warehouse.domain.WarehouseTransferReceiptDetail;
import com.xiaoyai.warehouse.service.IWarehouseTransferReceiptDetailService;

/**
 *  物品调拨明细Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-16
 */
@Service
public class WarehouseTransferReceiptDetailServiceImpl extends ServiceImpl<WarehouseTransferReceiptDetailMapper, WarehouseTransferReceiptDetail> implements IWarehouseTransferReceiptDetailService
{
    @Autowired
    private WarehouseTransferReceiptDetailMapper warehouseTransferReceiptDetailMapper;

    @Override
    public List<WarehouseGoodsDto> getTransferReceiptGoodsList(Long warehouseTransferReceiptId) {
        return warehouseTransferReceiptDetailMapper.getTransferReceiptGoodsList(warehouseTransferReceiptId);
    }



    @Override
    public List<WarehouseGoodsDto> getTransferReceiptGoodsWithSnCodeList(WarehouseTransferReceiptDto warehouseTransferReceipt) {
        return warehouseTransferReceiptDetailMapper.getTransferReceiptGoodsWithSnCodeList(warehouseTransferReceipt);
    }

    /**
     * 查询 物品调拨明细
     * 
     * @param transferReceiptDetailId  物品调拨明细主键
     * @return  物品调拨明细
     */
    @Override
    public WarehouseTransferReceiptDetail selectWarehouseTransferReceiptDetailByTransferReceiptDetailId(Long transferReceiptDetailId)
    {
        return warehouseTransferReceiptDetailMapper.selectWarehouseTransferReceiptDetailByTransferReceiptDetailId(transferReceiptDetailId);
    }

    /**
     * 查询 物品调拨明细列表
     * 
     * @param warehouseTransferReceiptDetail  物品调拨明细
     * @return  物品调拨明细
     */
    @Override
    public List<WarehouseTransferReceiptDetail> selectWarehouseTransferReceiptDetailList(WarehouseTransferReceiptDetail warehouseTransferReceiptDetail)
    {
        return warehouseTransferReceiptDetailMapper.selectWarehouseTransferReceiptDetailList(warehouseTransferReceiptDetail);
    }

    /**
     * 新增 物品调拨明细
     * 
     * @param warehouseTransferReceiptDetail  物品调拨明细
     * @return 结果
     */
    @Override
    public int insertWarehouseTransferReceiptDetail(WarehouseTransferReceiptDetail warehouseTransferReceiptDetail)
    {
        return warehouseTransferReceiptDetailMapper.insertWarehouseTransferReceiptDetail(warehouseTransferReceiptDetail);
    }

    /**
     * 修改 物品调拨明细
     * 
     * @param warehouseTransferReceiptDetail  物品调拨明细
     * @return 结果
     */
    @Override
    public int updateWarehouseTransferReceiptDetail(WarehouseTransferReceiptDetail warehouseTransferReceiptDetail)
    {
        return warehouseTransferReceiptDetailMapper.updateWarehouseTransferReceiptDetail(warehouseTransferReceiptDetail);
    }

    /**
     * 批量删除 物品调拨明细
     * 
     * @param transferReceiptDetailIds 需要删除的 物品调拨明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseTransferReceiptDetailByTransferReceiptDetailIds(Long[] transferReceiptDetailIds)
    {
        return warehouseTransferReceiptDetailMapper.deleteWarehouseTransferReceiptDetailByTransferReceiptDetailIds(transferReceiptDetailIds);
    }

    /**
     * 删除 物品调拨明细信息
     * 
     * @param transferReceiptDetailId  物品调拨明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseTransferReceiptDetailByTransferReceiptDetailId(Long transferReceiptDetailId)
    {
        return warehouseTransferReceiptDetailMapper.deleteWarehouseTransferReceiptDetailByTransferReceiptDetailId(transferReceiptDetailId);
    }
}
