package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceRelDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseGoodsTraceRelMapper;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import com.xiaoyai.warehouse.service.IWarehouseGoodsTraceRelService;

/**
 * 物品流转记录Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-10
 */
@Service
public class WarehouseGoodsTraceRelServiceImpl extends ServiceImpl<WarehouseGoodsTraceRelMapper, WarehouseGoodsTraceRel> implements IWarehouseGoodsTraceRelService
{
    @Autowired
    private WarehouseGoodsTraceRelMapper warehouseGoodsTraceRelMapper;

    /**
     * 查询物品流转记录
     * 
     * @param traceRecordId 物品流转记录主键
     * @return 物品流转记录
     */
    @Override
    public WarehouseGoodsTraceRel selectWarehouseGoodsTraceRelByTraceRecordId(Long traceRecordId)
    {
        return warehouseGoodsTraceRelMapper.selectWarehouseGoodsTraceRelByTraceRecordId(traceRecordId);
    }

    /**
     * 查询物品流转记录列表
     *
     * @param warehouseGoodsTraceRel 物品流转记录
     * @return 物品流转记录
     */
    @Override
    public List<WarehouseGoodsTraceRelDto> selectWarehouseGoodsTraceRelList(WarehouseGoodsTraceRel warehouseGoodsTraceRel)
    {
        return warehouseGoodsTraceRelMapper.selectWarehouseGoodsTraceRelList(warehouseGoodsTraceRel);
    }

    /**
     * 新增物品流转记录
     * 
     * @param warehouseGoodsTraceRel 物品流转记录
     * @return 结果
     */
    @Override
    public int insertWarehouseGoodsTraceRel(WarehouseGoodsTraceRel warehouseGoodsTraceRel)
    {
        return warehouseGoodsTraceRelMapper.insertWarehouseGoodsTraceRel(warehouseGoodsTraceRel);
    }

    /**
     * 修改物品流转记录
     * 
     * @param warehouseGoodsTraceRel 物品流转记录
     * @return 结果
     */
    @Override
    public int updateWarehouseGoodsTraceRel(WarehouseGoodsTraceRel warehouseGoodsTraceRel)
    {
        return warehouseGoodsTraceRelMapper.updateWarehouseGoodsTraceRel(warehouseGoodsTraceRel);
    }

    /**
     * 批量删除物品流转记录
     * 
     * @param traceRecordIds 需要删除的物品流转记录主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsTraceRelByTraceRecordIds(Long[] traceRecordIds)
    {
        return warehouseGoodsTraceRelMapper.deleteWarehouseGoodsTraceRelByTraceRecordIds(traceRecordIds);
    }

    /**
     * 删除物品流转记录信息
     * 
     * @param traceRecordId 物品流转记录主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsTraceRelByTraceRecordId(Long traceRecordId)
    {
        return warehouseGoodsTraceRelMapper.deleteWarehouseGoodsTraceRelByTraceRecordId(traceRecordId);
    }
}
