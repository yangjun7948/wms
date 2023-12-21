package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseStockWarn;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 库存预警Service接口
 * 
 * @author junyang
 * @date 2023-10-08
 */
public interface IWarehouseStockWarnService extends IService<WarehouseStockWarn>
{

    void createWarnGoodsList();

    List<WarehouseStockWarn> stockWarnList();
}
