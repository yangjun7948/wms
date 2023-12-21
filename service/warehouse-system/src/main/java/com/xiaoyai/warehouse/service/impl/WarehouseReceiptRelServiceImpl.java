package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseReceiptRelMapper;
import com.xiaoyai.warehouse.domain.WarehouseReceiptRel;
import com.xiaoyai.warehouse.service.IWarehouseReceiptRelService;

/**
 * 调拨关系Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-16
 */
@Service
public class WarehouseReceiptRelServiceImpl extends ServiceImpl<WarehouseReceiptRelMapper, WarehouseReceiptRel> implements IWarehouseReceiptRelService
{
    @Autowired
    private WarehouseReceiptRelMapper warehouseReceiptRelMapper;

    /**
     * 查询调拨关系
     * 
     * @param relId 调拨关系主键
     * @return 调拨关系
     */
    @Override
    public WarehouseReceiptRel selectWarehouseReceiptRelByRelId(Long relId)
    {
        return warehouseReceiptRelMapper.selectWarehouseReceiptRelByRelId(relId);
    }

    /**
     * 查询调拨关系列表
     * 
     * @param warehouseReceiptRel 调拨关系
     * @return 调拨关系
     */
    @Override
    public List<WarehouseReceiptRel> selectWarehouseReceiptRelList(WarehouseReceiptRel warehouseReceiptRel)
    {
        return warehouseReceiptRelMapper.selectWarehouseReceiptRelList(warehouseReceiptRel);
    }

    /**
     * 新增调拨关系
     * 
     * @param warehouseReceiptRel 调拨关系
     * @return 结果
     */
    @Override
    public int insertWarehouseReceiptRel(WarehouseReceiptRel warehouseReceiptRel)
    {
        return warehouseReceiptRelMapper.insertWarehouseReceiptRel(warehouseReceiptRel);
    }

    /**
     * 修改调拨关系
     * 
     * @param warehouseReceiptRel 调拨关系
     * @return 结果
     */
    @Override
    public int updateWarehouseReceiptRel(WarehouseReceiptRel warehouseReceiptRel)
    {
        return warehouseReceiptRelMapper.updateWarehouseReceiptRel(warehouseReceiptRel);
    }

    /**
     * 批量删除调拨关系
     * 
     * @param relIds 需要删除的调拨关系主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseReceiptRelByRelIds(Long[] relIds)
    {
        return warehouseReceiptRelMapper.deleteWarehouseReceiptRelByRelIds(relIds);
    }

    /**
     * 删除调拨关系信息
     * 
     * @param relId 调拨关系主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseReceiptRelByRelId(Long relId)
    {
        return warehouseReceiptRelMapper.deleteWarehouseReceiptRelByRelId(relId);
    }
}
