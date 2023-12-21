package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseTransferReceipt;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseTransferReceiptDto;

/**
 * 调拨单据Service接口
 * 
 * @author junyang
 * @date 2023-09-16
 */
public interface IWarehouseTransferReceiptService extends IService<WarehouseTransferReceipt>
{
    /**
     * 查询调拨单据
     * 
     * @param warehouseTransferReceiptId 调拨单据主键
     * @return 调拨单据
     */
    public WarehouseTransferReceiptDto selectWarehouseTransferReceiptById(Long warehouseTransferReceiptId);

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
     * @param warehouseTransferReceiptDto 调拨单据
     * @return 结果
     */
    public boolean insertWarehouseTransferReceipt(WarehouseTransferReceiptDto warehouseTransferReceiptDto);

    /**
     * 修改调拨单据
     * 
     * @param warehouseTransferReceipt 调拨单据
     * @return 结果
     */
    public boolean updateWarehouseTransferReceipt(WarehouseTransferReceiptDto warehouseTransferReceipt);

    /**
     * 批量删除调拨单据
     * 
     * @param warehouseTransferReceiptIds 需要删除的调拨单据主键集合
     * @return 结果
     */
    public int deleteWarehouseTransferReceiptByWarehouseTransferReceiptIds(Long[] warehouseTransferReceiptIds);

    /**
     * 删除调拨单据信息
     * 
     * @param warehouseTransferReceiptId 调拨单据主键
     * @return 结果
     */
    public boolean deleteWarehouseTransferReceiptByWarehouseTransferReceiptId(Long warehouseTransferReceiptId);

    boolean approveWarehouseTransferReceipt(WarehouseTransferReceiptDto warehouseTransferReceipt) throws Exception;

    byte[] transferReceiptPdf(Long warehouseTransferReceiptId);

    boolean unApproveWarehouseOutReceipt(WarehouseTransferReceiptDto warehouseTransferReceipt);

    /**
     *  创建调拨入库单
     * @param transferId
     */
    void doTransferInReceipt(Long transferId);

    /**
     * 更新调拨单状态
     * @param warehouseInReceiptId
     */
    void updateTransferByInReceiptId(Long warehouseInReceiptId);
}
