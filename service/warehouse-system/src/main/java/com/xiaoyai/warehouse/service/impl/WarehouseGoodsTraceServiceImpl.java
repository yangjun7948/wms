package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import com.xiaoyai.warehouse.domain.dto.WarehouseCodeReportQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseGoodsTraceMapper;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTrace;
import com.xiaoyai.warehouse.service.IWarehouseGoodsTraceService;

/**
 * 物品溯源Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-10
 */
@Service
public class WarehouseGoodsTraceServiceImpl extends ServiceImpl<WarehouseGoodsTraceMapper, WarehouseGoodsTrace> implements IWarehouseGoodsTraceService
{
    @Autowired
    private WarehouseGoodsTraceMapper warehouseGoodsTraceMapper;

    /**
     * 查询物品溯源
     * 
     * @param traceId 物品溯源主键
     * @return 物品溯源
     */
    @Override
    public WarehouseGoodsTrace selectWarehouseGoodsTraceByTraceId(Long traceId)
    {
        return warehouseGoodsTraceMapper.selectWarehouseGoodsTraceByTraceId(traceId);
    }

    /**
     * 查询物品溯源列表
     * 
     * @param warehouseGoodsTrace 物品溯源
     * @return 物品溯源
     */
    @Override
    public List<WarehouseGoodsTrace> selectWarehouseGoodsTraceList(WarehouseGoodsTrace warehouseGoodsTrace)
    {
        return warehouseGoodsTraceMapper.selectWarehouseGoodsTraceList(warehouseGoodsTrace);
    }

    /**
     * 新增物品溯源
     * 
     * @param warehouseGoodsTrace 物品溯源
     * @return 结果
     */
    @Override
    public int insertWarehouseGoodsTrace(WarehouseGoodsTrace warehouseGoodsTrace)
    {
        warehouseGoodsTrace.setCreateTime(DateUtils.getNowDate());
        return warehouseGoodsTraceMapper.insertWarehouseGoodsTrace(warehouseGoodsTrace);
    }

    /**
     * 修改物品溯源
     * 
     * @param warehouseGoodsTrace 物品溯源
     * @return 结果
     */
    @Override
    public int updateWarehouseGoodsTrace(WarehouseGoodsTrace warehouseGoodsTrace)
    {
        warehouseGoodsTrace.setUpdateTime(DateUtils.getNowDate());
        return warehouseGoodsTraceMapper.updateWarehouseGoodsTrace(warehouseGoodsTrace);
    }

    /**
     * 批量删除物品溯源
     * 
     * @param traceIds 需要删除的物品溯源主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsTraceByTraceIds(Long[] traceIds)
    {
        return warehouseGoodsTraceMapper.deleteWarehouseGoodsTraceByTraceIds(traceIds);
    }

    /**
     * 删除物品溯源信息
     * 
     * @param traceId 物品溯源主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsTraceByTraceId(Long traceId)
    {
        return warehouseGoodsTraceMapper.deleteWarehouseGoodsTraceByTraceId(traceId);
    }

    @Override
    public List<WarehouseGoodsTraceDto> selectWarehouseGoodsTraceDtoList(WarehouseGoodsTraceRel warehouseGoodsTraceRel) {
        return warehouseGoodsTraceMapper.selectWarehouseGoodsTraceDtoList(warehouseGoodsTraceRel);
    }

    @Override
    public List<WarehouseGoodsTraceDto> selectGoodsTraceDtoList(WarehouseCodeReportQueryDto reportQueryDto) {
        return warehouseGoodsTraceMapper.selectGoodsTraceDtoList(reportQueryDto);
    }
}
