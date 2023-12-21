package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseStockWarn;

/**
 * 库存预警Mapper接口
 * 
 * @author junyang
 * @date 2023-10-08
 */
public interface WarehouseStockWarnMapper extends BaseMapper<WarehouseStockWarn>
{

    void deleteAll();

    List<WarehouseStockWarn> selectWarehouseStockWarnList(WarehouseStockWarn warehouseStockWarn);
}
