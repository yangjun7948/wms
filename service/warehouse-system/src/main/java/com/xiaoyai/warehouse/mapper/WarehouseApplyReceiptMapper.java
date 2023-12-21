package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseApplyReceipt;
import com.xiaoyai.warehouse.domain.dto.WarehouseApplyReceiptDto;

/**
 * 要货申请Mapper接口
 * 
 * @author junyang
 * @date 2023-09-18
 */
public interface WarehouseApplyReceiptMapper extends BaseMapper<WarehouseApplyReceipt>
{
    /**
     * 查询要货申请
     * 
     * @param applyReceiptId 要货申请主键
     * @return 要货申请
     */
    public WarehouseApplyReceiptDto selectWarehouseApplyReceiptByApplyReceiptId(Long applyReceiptId);

    /**
     * 查询要货申请列表
     * 
     * @param warehouseApplyReceipt 要货申请
     * @return 要货申请集合
     */
    public List<WarehouseApplyReceiptDto> selectWarehouseApplyReceiptList(WarehouseApplyReceiptDto warehouseApplyReceipt);

    /**
     * 新增要货申请
     * 
     * @param warehouseApplyReceipt 要货申请
     * @return 结果
     */
    public int insertWarehouseApplyReceipt(WarehouseApplyReceipt warehouseApplyReceipt);

    /**
     * 修改要货申请
     * 
     * @param warehouseApplyReceipt 要货申请
     * @return 结果
     */
    public int updateWarehouseApplyReceipt(WarehouseApplyReceipt warehouseApplyReceipt);

    /**
     * 删除要货申请
     * 
     * @param applyReceiptId 要货申请主键
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptByApplyReceiptId(Long applyReceiptId);

    /**
     * 批量删除要货申请
     * 
     * @param applyReceiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptByApplyReceiptIds(Long[] applyReceiptIds);
}
