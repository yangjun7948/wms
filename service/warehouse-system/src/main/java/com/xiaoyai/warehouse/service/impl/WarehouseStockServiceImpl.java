package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockReportQueryDto;
import com.xiaoyai.warehouse.service.IWarehouseGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseStockMapper;
import com.xiaoyai.warehouse.domain.WarehouseStock;
import com.xiaoyai.warehouse.service.IWarehouseStockService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-13
 */
@Service
public class WarehouseStockServiceImpl extends ServiceImpl<WarehouseStockMapper, WarehouseStock> implements IWarehouseStockService
{
    @Autowired
    private WarehouseStockMapper warehouseStockMapper;


    /**
     * 查询库存
     * 
     * @param stockId 库存主键
     * @return 库存
     */
    @Override
    public WarehouseStock selectWarehouseStockByStockId(Long stockId)
    {
        return warehouseStockMapper.selectWarehouseStockByStockId(stockId);
    }

    /**
     * 查询库存列表
     * 
     * @param warehouseStock 库存
     * @return 库存
     */
    @Override
    public List<WarehouseStock> selectWarehouseStockList(WarehouseStock warehouseStock)
    {
        return warehouseStockMapper.selectWarehouseStockList(warehouseStock);
    }

    /**
     * 新增库存
     * 
     * @param warehouseStock 库存
     * @return 结果
     */
    @Override
    public int insertWarehouseStock(WarehouseStock warehouseStock)
    {
        return warehouseStockMapper.insertWarehouseStock(warehouseStock);
    }

    /**
     * 修改库存
     * 
     * @param warehouseStock 库存
     * @return 结果
     */
    @Override
    public int updateWarehouseStock(WarehouseStock warehouseStock)
    {
        return warehouseStockMapper.updateWarehouseStock(warehouseStock);
    }

    /**
     * 批量删除库存
     * 
     * @param stockIds 需要删除的库存主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseStockByStockIds(Long[] stockIds)
    {
        return warehouseStockMapper.deleteWarehouseStockByStockIds(stockIds);
    }

    /**
     * 删除库存信息
     * 
     * @param stockId 库存主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseStockByStockId(Long stockId)
    {
        return warehouseStockMapper.deleteWarehouseStockByStockId(stockId);
    }

    @Override
    public List<WarehouseStock> selectWarehouseStockSummaryList(WarehouseStockReportQueryDto reportQueryDto) {
        return warehouseStockMapper.selectWarehouseStockSummaryList(reportQueryDto);
    }

}
