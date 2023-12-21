package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseReceiptRel;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 调拨关系Service接口
 * 
 * @author junyang
 * @date 2023-09-16
 */
public interface IWarehouseReceiptRelService extends IService<WarehouseReceiptRel>
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
     * 批量删除调拨关系
     * 
     * @param relIds 需要删除的调拨关系主键集合
     * @return 结果
     */
    public int deleteWarehouseReceiptRelByRelIds(Long[] relIds);

    /**
     * 删除调拨关系信息
     * 
     * @param relId 调拨关系主键
     * @return 结果
     */
    public int deleteWarehouseReceiptRelByRelId(Long relId);
}
