package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseOutReceiptDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;

/**
 *  物品出库明细Service接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface IWarehouseOutReceiptDetailService extends IService<WarehouseOutReceiptDetail>
{
    /**
     * 查询 物品出库明细
     * 
     * @param outReceiptDetailId  物品出库明细主键
     * @return  物品出库明细
     */
    public WarehouseOutReceiptDetail selectWarehouseOutReceiptDetailByOutReceiptDetailId(Long outReceiptDetailId);

    /**
     * 查询 物品出库明细列表
     * 
     * @param warehouseOutReceiptDetail  物品出库明细
     * @return  物品出库明细集合
     */
    public List<WarehouseOutReceiptDetail> selectWarehouseOutReceiptDetailList(WarehouseOutReceiptDetail warehouseOutReceiptDetail);

    /**
     * 新增 物品出库明细
     * 
     * @param warehouseOutReceiptDetail  物品出库明细
     * @return 结果
     */
    public int insertWarehouseOutReceiptDetail(WarehouseOutReceiptDetail warehouseOutReceiptDetail);

    /**
     * 修改 物品出库明细
     * 
     * @param warehouseOutReceiptDetail  物品出库明细
     * @return 结果
     */
    public int updateWarehouseOutReceiptDetail(WarehouseOutReceiptDetail warehouseOutReceiptDetail);

    /**
     * 批量删除 物品出库明细
     * 
     * @param outReceiptDetailIds 需要删除的 物品出库明细主键集合
     * @return 结果
     */
    public int deleteWarehouseOutReceiptDetailByOutReceiptDetailIds(Long[] outReceiptDetailIds);

    /**
     * 删除 物品出库明细信息
     * 
     * @param outReceiptDetailId  物品出库明细主键
     * @return 结果
     */
    public int deleteWarehouseOutReceiptDetailByOutReceiptDetailId(Long outReceiptDetailId);

    List<WarehouseGoodsDto> getOutReceiptGoodsList(Long warehouseOutReceiptId);

    List<WarehouseGoodsDto> getOutReceiptGoodsWithSnCodeList(Long warehouseOutReceiptId);

}
