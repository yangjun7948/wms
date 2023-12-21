package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTrace;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import com.xiaoyai.warehouse.domain.dto.WarehouseCodeReportQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceDto;

/**
 * 物品溯源Mapper接口
 * 
 * @author junyang
 * @date 2023-09-10
 */
public interface WarehouseGoodsTraceMapper extends BaseMapper<WarehouseGoodsTrace>
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
     * 删除物品溯源
     * 
     * @param traceId 物品溯源主键
     * @return 结果
     */
    public int deleteWarehouseGoodsTraceByTraceId(Long traceId);

    /**
     * 批量删除物品溯源
     * 
     * @param traceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsTraceByTraceIds(Long[] traceIds);

    List<WarehouseGoodsTraceDto> selectWarehouseGoodsTraceDtoList(WarehouseGoodsTraceRel warehouseGoodsTraceRel);

    List<WarehouseGoodsTraceDto> selectGoodsTraceDtoList(WarehouseCodeReportQueryDto reportQueryDto);
}
