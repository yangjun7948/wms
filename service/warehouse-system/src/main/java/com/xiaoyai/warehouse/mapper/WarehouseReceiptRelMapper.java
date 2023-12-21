package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseReceiptRel;

/**
 * 调拨关系Mapper接口
 * 
 * @author junyang
 * @date 2023-09-16
 */
public interface WarehouseReceiptRelMapper extends BaseMapper<WarehouseReceiptRel>
{
    /**
     * 查询调拨关系
     * 
     * @param relId 调拨关系主键
     * @return 调拨关系
     */
    public WarehouseReceiptRel selectWarehouseReceiptRelByRelId(Long relId);

    /**
     * 查询调拨关系列表
     * 
     * @param warehouseReceiptRel 调拨关系
     * @return 调拨关系集合
     */
    public List<WarehouseReceiptRel> selectWarehouseReceiptRelList(WarehouseReceiptRel warehouseReceiptRel);

    /**
     * 新增调拨关系
     * 
     * @param warehouseReceiptRel 调拨关系
     * @return 结果
     */
    public int insertWarehouseReceiptRel(WarehouseReceiptRel warehouseReceiptRel);

    /**
     * 修改调拨关系
     * 
     * @param warehouseReceiptRel 调拨关系
     * @return 结果
     */
    public int updateWarehouseReceiptRel(WarehouseReceiptRel warehouseReceiptRel);

    /**
     * 删除调拨关系
     * 
     * @param relId 调拨关系主键
     * @return 结果
     */
    public int deleteWarehouseReceiptRelByRelId(Long relId);

    /**
     * 批量删除调拨关系
     * 
     * @param relIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseReceiptRelByRelIds(Long[] relIds);
}
