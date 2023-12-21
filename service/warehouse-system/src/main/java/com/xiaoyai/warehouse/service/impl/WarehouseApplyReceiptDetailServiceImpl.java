package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseApplyReceiptDetailMapper;
import com.xiaoyai.warehouse.domain.WarehouseApplyReceiptDetail;
import com.xiaoyai.warehouse.service.IWarehouseApplyReceiptDetailService;

/**
 * 要货明细Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-18
 */
@Service
public class WarehouseApplyReceiptDetailServiceImpl extends ServiceImpl<WarehouseApplyReceiptDetailMapper, WarehouseApplyReceiptDetail> implements IWarehouseApplyReceiptDetailService
{
    @Autowired
    private WarehouseApplyReceiptDetailMapper warehouseApplyReceiptDetailMapper;

    /**
     * 查询要货明细
     * 
     * @param applyReceiptDetailId 要货明细主键
     * @return 要货明细
     */
    @Override
    public WarehouseApplyReceiptDetail selectWarehouseApplyReceiptDetailByApplyReceiptDetailId(Long applyReceiptDetailId)
    {
        return warehouseApplyReceiptDetailMapper.selectWarehouseApplyReceiptDetailByApplyReceiptDetailId(applyReceiptDetailId);
    }

    /**
     * 查询要货明细列表
     * 
     * @param warehouseApplyReceiptDetail 要货明细
     * @return 要货明细
     */
    @Override
    public List<WarehouseApplyReceiptDetail> selectWarehouseApplyReceiptDetailList(WarehouseApplyReceiptDetail warehouseApplyReceiptDetail)
    {
        return warehouseApplyReceiptDetailMapper.selectWarehouseApplyReceiptDetailList(warehouseApplyReceiptDetail);
    }

    /**
     * 新增要货明细
     * 
     * @param warehouseApplyReceiptDetail 要货明细
     * @return 结果
     */
    @Override
    public int insertWarehouseApplyReceiptDetail(WarehouseApplyReceiptDetail warehouseApplyReceiptDetail)
    {
        return warehouseApplyReceiptDetailMapper.insertWarehouseApplyReceiptDetail(warehouseApplyReceiptDetail);
    }

    /**
     * 修改要货明细
     * 
     * @param warehouseApplyReceiptDetail 要货明细
     * @return 结果
     */
    @Override
    public int updateWarehouseApplyReceiptDetail(WarehouseApplyReceiptDetail warehouseApplyReceiptDetail)
    {
        return warehouseApplyReceiptDetailMapper.updateWarehouseApplyReceiptDetail(warehouseApplyReceiptDetail);
    }

    /**
     * 批量删除要货明细
     * 
     * @param applyReceiptDetailIds 需要删除的要货明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseApplyReceiptDetailByApplyReceiptDetailIds(Long[] applyReceiptDetailIds)
    {
        return warehouseApplyReceiptDetailMapper.deleteWarehouseApplyReceiptDetailByApplyReceiptDetailIds(applyReceiptDetailIds);
    }

    /**
     * 删除要货明细信息
     * 
     * @param applyReceiptDetailId 要货明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseApplyReceiptDetailByApplyReceiptDetailId(Long applyReceiptDetailId)
    {
        return warehouseApplyReceiptDetailMapper.deleteWarehouseApplyReceiptDetailByApplyReceiptDetailId(applyReceiptDetailId);
    }

    @Override
    public List<WarehouseGoodsDto> getApplyReceiptGoodsList(Long applyReceiptId) {
        return warehouseApplyReceiptDetailMapper.getApplyReceiptGoodsList(applyReceiptId);
    }
}
