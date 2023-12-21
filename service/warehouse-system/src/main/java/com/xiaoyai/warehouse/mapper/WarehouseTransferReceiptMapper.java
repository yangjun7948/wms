package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseTransferReceipt;
import com.xiaoyai.warehouse.domain.dto.WarehouseTransferReceiptDto;

/**
 * 调拨单据Mapper接口
 * 
 * @author junyang
 * @date 2023-09-16
 */
public interface WarehouseTransferReceiptMapper extends BaseMapper<WarehouseTransferReceipt>
{


    /**
     * 查询调拨单据列表
     * 
     * @param warehouseTransferReceipt 调拨单据
     * @return 调拨单据集合
     */
    public List<WarehouseTransferReceiptDto> selectWarehouseTransferReceiptList(WarehouseTransferReceipt warehouseTransferReceipt);

    /**
     * 新增调拨单据
     * 
     * @param warehouseTransferReceipt 调拨单据
     * @return 结果
     */
    public int insertWarehouseTransferReceipt(WarehouseTransferReceipt warehouseTransferReceipt);

    /**
     * 修改调拨单据
     * 
     * @param warehouseTransferReceipt 调拨单据
     * @return 结果
     */
    public int updateWarehouseTransferReceipt(WarehouseTransferReceipt warehouseTransferReceipt);

    /**
     * 删除调拨单据
     * 
     * @param warehouseTransferReceiptId 调拨单据主键
     * @return 结果
     */
    public int deleteWarehouseTransferReceiptByWarehouseTransferReceiptId(Long warehouseTransferReceiptId);

    /**
     * 批量删除调拨单据
     * 
     * @param warehouseTransferReceiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseTransferReceiptByWarehouseTransferReceiptIds(Long[] warehouseTransferReceiptIds);
}
