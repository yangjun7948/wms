package com.xiaoyai.warehouse.service;

import java.util.List;

import com.xiaoyai.warehouse.domain.WarehouseTransferReceipt;
import com.xiaoyai.warehouse.domain.WarehouseTransferReceiptDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseTransferReceiptDto;

/**
 *  物品调拨明细Service接口
 * 
 * @author junyang
 * @date 2023-09-16
 */
public interface IWarehouseTransferReceiptDetailService extends IService<WarehouseTransferReceiptDetail>
{
     List<WarehouseGoodsDto> getTransferReceiptGoodsList(Long warehouseTransferReceiptId);

     List<WarehouseGoodsDto> getTransferReceiptGoodsWithSnCodeList(WarehouseTransferReceiptDto transferReceipt);

    /**
     * 查询 物品调拨明细
     * 
     * @param transferReceiptDetailId  物品调拨明细主键
     * @return  物品调拨明细
     */
    public WarehouseTransferReceiptDetail selectWarehouseTransferReceiptDetailByTransferReceiptDetailId(Long transferReceiptDetailId);

    /**
     * 查询 物品调拨明细列表
     * 
     * @param warehouseTransferReceiptDetail  物品调拨明细
     * @return  物品调拨明细集合
     */
    public List<WarehouseTransferReceiptDetail> selectWarehouseTransferReceiptDetailList(WarehouseTransferReceiptDetail warehouseTransferReceiptDetail);

    /**
     * 新增 物品调拨明细
     * 
     * @param warehouseTransferReceiptDetail  物品调拨明细
     * @return 结果
     */
    public int insertWarehouseTransferReceiptDetail(WarehouseTransferReceiptDetail warehouseTransferReceiptDetail);

    /**
     * 修改 物品调拨明细
     * 
     * @param warehouseTransferReceiptDetail  物品调拨明细
     * @return 结果
     */
    public int updateWarehouseTransferReceiptDetail(WarehouseTransferReceiptDetail warehouseTransferReceiptDetail);

    /**
     * 批量删除 物品调拨明细
     * 
     * @param transferReceiptDetailIds 需要删除的 物品调拨明细主键集合
     * @return 结果
     */
    public int deleteWarehouseTransferReceiptDetailByTransferReceiptDetailIds(Long[] transferReceiptDetailIds);

    /**
     * 删除 物品调拨明细信息
     * 
     * @param transferReceiptDetailId  物品调拨明细主键
     * @return 结果
     */
    public int deleteWarehouseTransferReceiptDetailByTransferReceiptDetailId(Long transferReceiptDetailId);
}
