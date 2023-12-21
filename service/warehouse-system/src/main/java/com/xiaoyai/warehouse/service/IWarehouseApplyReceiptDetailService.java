package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseApplyReceiptDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;

/**
 * 要货明细Service接口
 * 
 * @author junyang
 * @date 2023-09-18
 */
public interface IWarehouseApplyReceiptDetailService extends IService<WarehouseApplyReceiptDetail>
{
    /**
     * 查询要货明细
     * 
     * @param applyReceiptDetailId 要货明细主键
     * @return 要货明细
     */
    public WarehouseApplyReceiptDetail selectWarehouseApplyReceiptDetailByApplyReceiptDetailId(Long applyReceiptDetailId);

    /**
     * 查询要货明细列表
     * 
     * @param warehouseApplyReceiptDetail 要货明细
     * @return 要货明细集合
     */
    public List<WarehouseApplyReceiptDetail> selectWarehouseApplyReceiptDetailList(WarehouseApplyReceiptDetail warehouseApplyReceiptDetail);

    /**
     * 新增要货明细
     * 
     * @param warehouseApplyReceiptDetail 要货明细
     * @return 结果
     */
    public int insertWarehouseApplyReceiptDetail(WarehouseApplyReceiptDetail warehouseApplyReceiptDetail);

    /**
     * 修改要货明细
     * 
     * @param warehouseApplyReceiptDetail 要货明细
     * @return 结果
     */
    public int updateWarehouseApplyReceiptDetail(WarehouseApplyReceiptDetail warehouseApplyReceiptDetail);

    /**
     * 批量删除要货明细
     * 
     * @param applyReceiptDetailIds 需要删除的要货明细主键集合
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptDetailByApplyReceiptDetailIds(Long[] applyReceiptDetailIds);

    /**
     * 删除要货明细信息
     * 
     * @param applyReceiptDetailId 要货明细主键
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptDetailByApplyReceiptDetailId(Long applyReceiptDetailId);

    /**
     * 要货单物品明细
     * @param applyReceiptId
     * @return
     */
    List<WarehouseGoodsDto> getApplyReceiptGoodsList(Long applyReceiptId);
}
