package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseStock;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockReportQueryDto;
import org.apache.ibatis.annotations.Param;

/**
 * 库存Mapper接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface WarehouseStockMapper extends BaseMapper<WarehouseStock>
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
     * 删除库存
     * 
     * @param stockId 库存主键
     * @return 结果
     */
    public int deleteWarehouseStockByStockId(Long stockId);

    /**
     * 批量删除库存
     * 
     * @param stockIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseStockByStockIds(Long[] stockIds);

    List<WarehouseStock> selectWarehouseStockSummaryList( WarehouseStockReportQueryDto reportQueryDto);
}
