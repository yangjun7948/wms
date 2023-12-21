package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDetailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseCheckReceiptDetailMapper;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceiptDetail;
import com.xiaoyai.warehouse.service.IWarehouseCheckReceiptDetailService;

/**
 * 盘点单据明细Service业务层处理
 * 
 * @author junyang
 * @date 2023-10-07
 */
@Service
public class WarehouseCheckReceiptDetailServiceImpl extends ServiceImpl<WarehouseCheckReceiptDetailMapper, WarehouseCheckReceiptDetail> implements IWarehouseCheckReceiptDetailService
{
    @Autowired
    private WarehouseCheckReceiptDetailMapper warehouseCheckReceiptDetailMapper;

    /**
     * 查询盘点单据明细
     * 
     * @param checkReceiptDetailId 盘点单据明细主键
     * @return 盘点单据明细
     */
    @Override
    public WarehouseCheckReceiptDetail selectWarehouseCheckReceiptDetailByCheckReceiptDetailId(Long checkReceiptDetailId)
    {
        return warehouseCheckReceiptDetailMapper.selectWarehouseCheckReceiptDetailByCheckReceiptDetailId(checkReceiptDetailId);
    }

    /**
     * 查询盘点单据明细列表
     * 
     * @param warehouseCheckReceiptDetail 盘点单据明细
     * @return 盘点单据明细
     */
    @Override
    public List<WarehouseCheckReceiptDetail> selectWarehouseCheckReceiptDetailList(WarehouseCheckReceiptDetail warehouseCheckReceiptDetail)
    {
        return warehouseCheckReceiptDetailMapper.selectWarehouseCheckReceiptDetailList(warehouseCheckReceiptDetail);
    }

    /**
     * 新增盘点单据明细
     * 
     * @param warehouseCheckReceiptDetail 盘点单据明细
     * @return 结果
     */
    @Override
    public int insertWarehouseCheckReceiptDetail(WarehouseCheckReceiptDetail warehouseCheckReceiptDetail)
    {
        return warehouseCheckReceiptDetailMapper.insertWarehouseCheckReceiptDetail(warehouseCheckReceiptDetail);
    }

    /**
     * 修改盘点单据明细
     * 
     * @param warehouseCheckReceiptDetail 盘点单据明细
     * @return 结果
     */
    @Override
    public int updateWarehouseCheckReceiptDetail(WarehouseCheckReceiptDetail warehouseCheckReceiptDetail)
    {
        return warehouseCheckReceiptDetailMapper.updateWarehouseCheckReceiptDetail(warehouseCheckReceiptDetail);
    }

    /**
     * 批量删除盘点单据明细
     * 
     * @param checkReceiptDetailIds 需要删除的盘点单据明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseCheckReceiptDetailByCheckReceiptDetailIds(Long[] checkReceiptDetailIds)
    {
        return warehouseCheckReceiptDetailMapper.deleteWarehouseCheckReceiptDetailByCheckReceiptDetailIds(checkReceiptDetailIds);
    }

    /**
     * 删除盘点单据明细信息
     * 
     * @param checkReceiptDetailId 盘点单据明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseCheckReceiptDetailByCheckReceiptDetailId(Long checkReceiptDetailId)
    {
        return warehouseCheckReceiptDetailMapper.deleteWarehouseCheckReceiptDetailByCheckReceiptDetailId(checkReceiptDetailId);
    }

    @Override
    public List<WarehouseCheckReceiptDetailDto> getCheckReceiptDetailList(WarehouseCheckReceiptDetail receiptDetail) {
        return warehouseCheckReceiptDetailMapper.getCheckReceiptDetailList(receiptDetail);
    }
}
