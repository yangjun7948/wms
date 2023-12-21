package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseInReceiptDetailMapper;
import com.xiaoyai.warehouse.domain.WarehouseInReceiptDetail;
import com.xiaoyai.warehouse.service.IWarehouseInReceiptDetailService;

/**
 *  物品入库明细Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-10
 */
@Service
public class WarehouseInReceiptDetailServiceImpl extends ServiceImpl<WarehouseInReceiptDetailMapper, WarehouseInReceiptDetail> implements IWarehouseInReceiptDetailService
{
    @Autowired
    private WarehouseInReceiptDetailMapper warehouseInReceiptDetailMapper;

    /**
     * 查询 物品入库明细
     * 
     * @param inReceiptDetailId  物品入库明细主键
     * @return  物品入库明细
     */
    @Override
    public WarehouseInReceiptDetail selectWarehouseInReceiptDetailByInReceiptDetailId(Long inReceiptDetailId)
    {
        return warehouseInReceiptDetailMapper.selectWarehouseInReceiptDetailByInReceiptDetailId(inReceiptDetailId);
    }

    /**
     * 查询 物品入库明细列表
     * 
     * @param warehouseInReceiptDetail  物品入库明细
     * @return  物品入库明细
     */
    @Override
    public List<WarehouseInReceiptDetail> selectWarehouseInReceiptDetailList(WarehouseInReceiptDetail warehouseInReceiptDetail)
    {
        return warehouseInReceiptDetailMapper.selectWarehouseInReceiptDetailList(warehouseInReceiptDetail);
    }

    /**
     * 新增 物品入库明细
     * 
     * @param warehouseInReceiptDetail  物品入库明细
     * @return 结果
     */
    @Override
    public int insertWarehouseInReceiptDetail(WarehouseInReceiptDetail warehouseInReceiptDetail)
    {
        return warehouseInReceiptDetailMapper.insertWarehouseInReceiptDetail(warehouseInReceiptDetail);
    }

    /**
     * 修改 物品入库明细
     * 
     * @param warehouseInReceiptDetail  物品入库明细
     * @return 结果
     */
    @Override
    public int updateWarehouseInReceiptDetail(WarehouseInReceiptDetail warehouseInReceiptDetail)
    {
        return warehouseInReceiptDetailMapper.updateWarehouseInReceiptDetail(warehouseInReceiptDetail);
    }

    /**
     * 批量删除 物品入库明细
     * 
     * @param inReceiptDetailIds 需要删除的 物品入库明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseInReceiptDetailByInReceiptDetailIds(Long[] inReceiptDetailIds)
    {
        return warehouseInReceiptDetailMapper.deleteWarehouseInReceiptDetailByInReceiptDetailIds(inReceiptDetailIds);
    }

    /**
     * 删除 物品入库明细信息
     * 
     * @param inReceiptDetailId  物品入库明细主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseInReceiptDetailByInReceiptDetailId(Long inReceiptDetailId)
    {
        return warehouseInReceiptDetailMapper.deleteWarehouseInReceiptDetailByInReceiptDetailId(inReceiptDetailId);
    }

    @Override
    public List<WarehouseGoodsDto> getInReceiptGoodsList(Long warehouseInReceiptId) {
        return warehouseInReceiptDetailMapper.getInReceiptGoodsList(warehouseInReceiptId);
    }

    @Override
    public List<WarehouseGoodsVo> getInReceiptGoodsListWithSnCode(Long receiptId) {
        return warehouseInReceiptDetailMapper.getInReceiptGoodsListWithSnCode(receiptId);
    }
}
