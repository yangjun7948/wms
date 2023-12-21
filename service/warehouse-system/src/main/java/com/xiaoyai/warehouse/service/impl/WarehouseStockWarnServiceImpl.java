package com.xiaoyai.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.warehouse.domain.Warehouse;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.WarehouseStock;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockReportQueryDto;
import com.xiaoyai.warehouse.service.IWarehouseGoodsService;
import com.xiaoyai.warehouse.service.IWarehouseService;
import com.xiaoyai.warehouse.service.IWarehouseStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseStockWarnMapper;
import com.xiaoyai.warehouse.domain.WarehouseStockWarn;
import com.xiaoyai.warehouse.service.IWarehouseStockWarnService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存预警Service业务层处理
 *
 * @author junyang
 * @date 2023-10-08
 */
@Service
public class WarehouseStockWarnServiceImpl extends ServiceImpl<WarehouseStockWarnMapper, WarehouseStockWarn> implements IWarehouseStockWarnService {
    @Autowired
    private WarehouseStockWarnMapper warehouseStockWarnMapper;
    @Autowired
    IWarehouseGoodsService iWarehouseGoodsService;
    @Autowired
    IWarehouseStockService iWarehouseStockService;
    @Autowired
    IWarehouseService iWarehouseService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createWarnGoodsList() {
        //获取当前所有物品
        List<WarehouseGoods> goodsList = iWarehouseGoodsService.selectWarehouseGoodsList(new WarehouseGoodsQueryDto());
        //根据仓库ID获取当前仓库中所有物品的数量
        List<WarehouseStock> warehouseStocks = iWarehouseStockService.selectWarehouseStockSummaryList(new WarehouseStockReportQueryDto());
        List<Warehouse> warehouseList =iWarehouseService.list();
                //告警物品列表
                List < WarehouseStockWarn > stockWarnList = new ArrayList<>();
        for (WarehouseGoods goods : goodsList) {
            BigDecimal minStock = goods.getMinStock();
            BigDecimal maxStock = goods.getMaxStock();
            List<WarehouseStock> temp = warehouseStocks.stream().filter(t -> t.getGoodsId().equals(goods.getGoodsId())).collect(Collectors.toList());
            WarehouseStockWarn stockWarn = new WarehouseStockWarn();
            for (WarehouseStock warehouseStock : temp) {
                if (warehouseStock.getStockNumber().compareTo(minStock) < 0 ||
                        warehouseStock.getStockNumber().compareTo(maxStock) > 0) {
                    stockWarn.setGoodsId(goods.getGoodsId());
                    stockWarn.setGoodsName(goods.getGoodsName());
                    stockWarn.setMinStock(minStock);
                    stockWarn.setMaxStock(maxStock);
                    stockWarn.setStockNumber(warehouseStock.getStockNumber());
                    stockWarn.setWarnTime(DateUtils.getNowDate());
                    stockWarn.setWarehouseId(warehouseStock.getWarehouseId());
                    Warehouse warehouse= warehouseList.stream().filter(t->t.getWarehouseId().equals(warehouseStock.getWarehouseId())).findFirst().orElse(new Warehouse());
                    stockWarn.setWarehouseName(warehouse.getWarehouseName());
                    stockWarnList.add(stockWarn);
                }
            }
            //删除旧的告警信息
//            this.remove(Wrappers.<WarehouseStockWarn>lambdaQuery().eq(WarehouseStockWarn::getGoodsId,goods.getGoodsId()));
        }
        this.baseMapper.deleteAll();
        //保存新的告警信息
        this.saveBatch(stockWarnList);
    }

    @Override
    public List<WarehouseStockWarn> stockWarnList() {
        return this.baseMapper.selectWarehouseStockWarnList(new WarehouseStockWarn());
    }

}
