package com.xiaoyai.warehouse.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.xiaoyai.warehouse.domain.Warehouse;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import com.xiaoyai.warehouse.domain.WarehouseStock;
import com.xiaoyai.warehouse.domain.dto.*;
import com.xiaoyai.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author junyang
 * @date 2023/9/20
 */
@Service
public class WarehouseReportServiceImpl implements IWarehouseReportService {
    @Autowired
    IWarehouseGoodsService iWarehouseGoodsService;
    @Autowired
    IWarehouseStockService iWarehouseStockService;
    @Autowired
    IWarehouseService iWarehouseService;
    @Autowired
    IWarehouseGoodsTraceService iWarehouseGoodsTraceService;
    @Autowired
    IWarehouseGoodsTraceRelService iWarehouseGoodsTraceRelService;
    @Override
    public PageInfo<Map> selectStockReportList(WarehouseStockReportQueryDto reportQueryDto) {
        PageInfo<Map> pageInfo = new PageInfo<>();
        //先取物品分页
        WarehouseGoodsQueryDto goodsQuery = new WarehouseGoodsQueryDto();
        BeanUtil.copyProperties(reportQueryDto, goodsQuery);
        List<WarehouseGoods> goodsList = iWarehouseGoodsService.selectWarehouseGoodsList(goodsQuery);
        List<Warehouse> warehouseList = new ArrayList<>();
        if (reportQueryDto.getWarehouseIds()==null||reportQueryDto.getWarehouseIds().size() == 0) {
            warehouseList = iWarehouseService.selectWarehouseList(new Warehouse());
        } else {
            warehouseList = iWarehouseService.list(Wrappers.<Warehouse>lambdaQuery().in(Warehouse::getWarehouseId, reportQueryDto.getWarehouseIds()));
        }
        PageInfo<WarehouseGoods> goodsPageInfo = new PageInfo<>(goodsList);
        pageInfo.setTotal(goodsPageInfo.getTotal());

        List<Long> goodsIds = goodsList.stream().map(t -> t.getGoodsId()).collect(Collectors.toList());
        if (goodsIds.size() == 0) {
            return new PageInfo<>();
        }
        reportQueryDto.setGoodsIds(goodsIds);
        //根据物品和仓库ID查询库存记录
        List<WarehouseStock> goodsStockList = iWarehouseStockService.selectWarehouseStockSummaryList(reportQueryDto);
        //构建返回对象
        List<Map> result = new ArrayList<>();
        for (WarehouseGoods goods : goodsList) {
            Map _goods = BeanUtil.beanToMap(goods);
            _goods.put("id", IdUtil.fastSimpleUUID());
            result.add(_goods);
            //先循环仓库
            for (Warehouse warehouse : warehouseList) {
                List<WarehouseStock> tempStock = goodsStockList.stream().filter(t -> t.getGoodsId() == goods.getGoodsId() &&
                        t.getWarehouseId() == warehouse.getWarehouseId()).collect(Collectors.toList());
                if (tempStock.size() == 0 || tempStock.size() > 1) {
                    _goods.put(warehouse.getWarehouseId().toString() + "1", 0);
                    _goods.put(warehouse.getWarehouseId().toString() + "2", 0);
                    _goods.put(warehouse.getWarehouseId().toString() + "3", 0);
                    _goods.put(warehouse.getWarehouseId().toString() + "4", "");
                } else {
                    _goods.put(warehouse.getWarehouseId().toString() + "1", tempStock.get(0).getStockNumber());
                    _goods.put(warehouse.getWarehouseId().toString() + "2", tempStock.get(0).getGoodsPrice());
                    _goods.put(warehouse.getWarehouseId().toString() + "3", tempStock.get(0).getStockAmount());
                    _goods.put(warehouse.getWarehouseId().toString() + "4", tempStock.get(0).getGoodsDeadlineTime());
                }
                List<Map> children = new ArrayList<>();
                for (WarehouseStock warehouseStock : tempStock) {
                    Map _temp = BeanUtil.beanToMap(goods);
                    _temp.put("id", IdUtil.fastSimpleUUID());
                    _temp.put(warehouse.getWarehouseId().toString() + "1", warehouseStock.getStockNumber());
                    _temp.put(warehouse.getWarehouseId().toString() + "2", warehouseStock.getGoodsPrice());
                    _temp.put(warehouse.getWarehouseId().toString() + "3", warehouseStock.getStockAmount());
                    _temp.put(warehouse.getWarehouseId().toString() + "4", warehouseStock.getGoodsDeadlineTime());
                    children.add(_temp);
                }
                if (children.size() > 1) {
                    _goods.put("children", children);
                    _goods.put(warehouse.getWarehouseId().toString() + "1", tempStock.stream().map(t -> t.getStockNumber()).reduce((a, b) -> a.add(b)));
                    _goods.put(warehouse.getWarehouseId().toString() + "2", "");
                    _goods.put(warehouse.getWarehouseId().toString() + "3", "");
                    _goods.put(warehouse.getWarehouseId().toString() + "4", "");
                }
            }


        }
        pageInfo.setList(result);
        return pageInfo;
    }

    @Override
    public List<WarehouseGoodsTraceDto> selectGoodsTraceDtoList(WarehouseCodeReportQueryDto reportQueryDto) {
        return iWarehouseGoodsTraceService.selectGoodsTraceDtoList(reportQueryDto);
    }

    @Override
    public WarehouseGoodsTraceDto selectGoodsTraceDto(String snCode) {
        WarehouseGoodsTraceDto traceDto = new WarehouseGoodsTraceDto();
        WarehouseCodeReportQueryDto queryDto = new WarehouseCodeReportQueryDto();
        queryDto.setSnCode(snCode);
        List<WarehouseGoodsTraceDto> traceDtos = iWarehouseGoodsTraceService.selectGoodsTraceDtoList(queryDto);
        if (traceDtos.size() > 0) {
            traceDto = traceDtos.get(0);
        }
        WarehouseGoodsTraceRel traceRel =new WarehouseGoodsTraceRel();
        traceRel.setTraceId(traceDto.getTraceId());
        List<WarehouseGoodsTraceRelDto> traceRelDtoList = this.iWarehouseGoodsTraceRelService.selectWarehouseGoodsTraceRelList(traceRel);
        traceDto.setTraceRelDtoList(traceRelDtoList);
        return traceDto;
    }
}
