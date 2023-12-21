package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTrace;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import com.xiaoyai.warehouse.domain.dto.WarehouseCodeReportQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceDto;

/**
 * 物品溯源Service接口
 * 
 * @author junyang
 * @date 2023-09-10
 */
public interface IWarehouseGoodsTraceService extends IService<WarehouseGoodsTrace>
{
    /**
     * 查询物品溯源
     * 
     * @param traceId 物品溯源主键
     * @return 物品溯源
     */
    public WarehouseGoodsTrace selectWarehouseGoodsTraceByTraceId(Long traceId);

    /**
     * 查询物品溯源列表
     * 
     * @param warehouseGoodsTrace 物品溯源
     * @return 物品溯源集合
     */
    public List<WarehouseGoodsTrace> selectWarehouseGoodsTraceList(WarehouseGoodsTrace warehouseGoodsTrace);

    /**
     * 新增物品溯源
     * 
     * @param warehouseGoodsTrace 物品溯源
     * @return 结果
     */
    public int insertWarehouseGoodsTrace(WarehouseGoodsTrace warehouseGoodsTrace);

    /**
     * 修改物品溯源
     * 
     * @param warehouseGoodsTrace 物品溯源
     * @return 结果
     */
    public int updateWarehouseGoodsTrace(WarehouseGoodsTrace warehouseGoodsTrace);

    /**
     * 批量删除物品溯源
     * 
     * @param traceIds 需要删除的物品溯源主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsTraceByTraceIds(Long[] traceIds);

    /**
     * 删除物品溯源信息
     * 
     * @param traceId 物品溯源主键
     * @return 结果
     */
    public int deleteWarehouseGoodsTraceByTraceId(Long traceId);


    List<WarehouseGoodsTraceDto> selectWarehouseGoodsTraceDtoList(WarehouseGoodsTraceRel warehouseGoodsTrace);

    List<WarehouseGoodsTraceDto> selectGoodsTraceDtoList(WarehouseCodeReportQueryDto reportQueryDto);
}
