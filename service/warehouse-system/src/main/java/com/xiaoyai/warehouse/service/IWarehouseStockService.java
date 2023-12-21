package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseStock;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockReportQueryDto;

/**
 * 库存Service接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface IWarehouseStockService extends IService<WarehouseStock>
{
    /**
     * 查询库存
     * 
     * @param stockId 库存主键
     * @return 库存
     */
    public WarehouseStock selectWarehouseStockByStockId(Long stockId);

    /**
     * 查询库存列表
     * 
     * @param warehouseStock 库存
     * @return 库存集合
     */
    public List<WarehouseStock> selectWarehouseStockList(WarehouseStock warehouseStock);

    /**
     * 新增库存
     * 
     * @param warehouseStock 库存
     * @return 结果
     */
    public int insertWarehouseStock(WarehouseStock warehouseStock);

    /**
     * 修改库存
     * 
     * @param warehouseStock 库存
     * @return 结果
     */
    public int updateWarehouseStock(WarehouseStock warehouseStock);

    /**
     * 批量删除库存
     * 
     * @param stockIds 需要删除的库存主键集合
     * @return 结果
     */
    public int deleteWarehouseStockByStockIds(Long[] stockIds);

    /**
     * 删除库存信息
     * 
     * @param stockId 库存主键
     * @return 结果
     */
    public int deleteWarehouseStockByStockId(Long stockId);

    List<WarehouseStock> selectWarehouseStockSummaryList(WarehouseStockReportQueryDto reportQueryDto);
}
