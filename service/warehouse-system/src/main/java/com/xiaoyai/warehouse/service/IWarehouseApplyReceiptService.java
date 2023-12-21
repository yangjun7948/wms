package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseApplyReceipt;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseApplyReceiptDto;

/**
 * 要货申请Service接口
 * 
 * @author junyang
 * @date 2023-09-18
 */
public interface IWarehouseApplyReceiptService extends IService<WarehouseApplyReceipt>
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
    public boolean updateWarehouseApplyReceipt(WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception;

    /**
     * 批量删除要货申请
     * 
     * @param applyReceiptIds 需要删除的要货申请主键集合
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptByApplyReceiptIds(Long[] applyReceiptIds);

    /**
     * 删除要货申请信息
     * 
     * @param applyReceiptId 要货申请主键
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptByApplyReceiptId(Long applyReceiptId);

    /**
     * 保存要货单
     * @param applyReceiptDto
     * @return
     */
    Boolean saveApplyReceiptDto(WarehouseApplyReceiptDto applyReceiptDto);

    /**
     * 处理要货申请逻辑
     * @param receiptId
     */
    Boolean warehouseApplyReceiptDo(Long receiptId);

    void updateApplyReceiptByInReceiptId(Long warehouseInReceiptId);
}
