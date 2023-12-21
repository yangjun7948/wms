package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseGoodsTraceRel;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceRelDto;

/**
 * 物品流转记录Mapper接口
 * 
 * @author junyang
 * @date 2023-09-10
 */
public interface WarehouseGoodsTraceRelMapper extends BaseMapper<WarehouseGoodsTraceRel>
{
    /**
     * 查询物品流转记录
     * 
     * @param traceRecordId 物品流转记录主键
     * @return 物品流转记录
     */
    public WarehouseGoodsTraceRel selectWarehouseGoodsTraceRelByTraceRecordId(Long traceRecordId);

    /**
     * 查询物品流转记录列表
     * 
     * @param warehouseGoodsTraceRel 物品流转记录
     * @return 物品流转记录集合
     */
    public List<WarehouseGoodsTraceRelDto> selectWarehouseGoodsTraceRelList(WarehouseGoodsTraceRel warehouseGoodsTraceRel);

    /**
     * 新增物品流转记录
     * 
     * @param warehouseGoodsTraceRel 物品流转记录
     * @return 结果
     */
    public int insertWarehouseGoodsTraceRel(WarehouseGoodsTraceRel warehouseGoodsTraceRel);

    /**
     * 修改物品流转记录
     * 
     * @param warehouseGoodsTraceRel 物品流转记录
     * @return 结果
     */
    public int updateWarehouseGoodsTraceRel(WarehouseGoodsTraceRel warehouseGoodsTraceRel);

    /**
     * 删除物品流转记录
     * 
     * @param traceRecordId 物品流转记录主键
     * @return 结果
     */
    public int deleteWarehouseGoodsTraceRelByTraceRecordId(Long traceRecordId);

    /**
     * 批量删除物品流转记录
     * 
     * @param traceRecordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseGoodsTraceRelByTraceRecordIds(Long[] traceRecordIds);
}
