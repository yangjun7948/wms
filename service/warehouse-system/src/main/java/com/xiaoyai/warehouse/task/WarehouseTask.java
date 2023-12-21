package com.xiaoyai.warehouse.task;

import com.xiaoyai.warehouse.service.IWarehouseStockService;
import com.xiaoyai.warehouse.service.IWarehouseStockWarnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author junyang
 * @date 2023/10/8
 */
@Component("warehouseTask")
public class WarehouseTask {
    public void ryNoParams()
    {
        System.out.println("执行无参方法");
    }

    @Autowired

    public IWarehouseStockWarnService iWarehouseStockWarnService;
    public void StockWarnJob(){
        iWarehouseStockWarnService.createWarnGoodsList();
    }
}
