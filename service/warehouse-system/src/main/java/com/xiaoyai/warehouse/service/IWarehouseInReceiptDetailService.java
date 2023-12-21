package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseInReceiptDetail;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;

/**
 *  物品入库明细Service接口
 * 
 * @author junyang
 * @date 2023-09-10
 */
public interface IWarehouseInReceiptDetailService extends IService<WarehouseInReceiptDetail>
{
    /**
     * 查询 物品入库明细
     * 
     * @param inReceiptDetailId  物品入库明细主键
     * @return  物品入库明细
     */
    public WarehouseInReceiptDetail selectWarehouseInReceiptDetailByInReceiptDetailId(Long inReceiptDetailId);

    /**
     * 查询 物品入库明细列表
     * 
     * @param warehouseInReceiptDetail  物品入库明细
     * @return  物品入库明细集合
     */
    public List<WarehouseInReceiptDetail> selectWarehouseInReceiptDetailList(WarehouseInReceiptDetail warehouseInReceiptDetail);

    /**
     * 新增 物品入库明细
     * 
     * @param warehouseInReceiptDetail  物品入库明细
     * @return 结果
     */
    public int insertWarehouseInReceiptDetail(WarehouseInReceiptDetail warehouseInReceiptDetail);

    /**
     * 修改 物品入库明细
     * 
     * @param warehouseInReceiptDetail  物品入库明细
     * @return 结果
     */
    public int updateWarehouseInReceiptDetail(WarehouseInReceiptDetail warehouseInReceiptDetail);

    /**
     * 批量删除 物品入库明细
     * 
     * @param inReceiptDetailIds 需要删除的 物品入库明细主键集合
     * @return 结果
     */
    public int deleteWarehouseInReceiptDetailByInReceiptDetailIds(Long[] inReceiptDetailIds);

    /**
     * 删除 物品入库明细信息
     * 
     * @param inReceiptDetailId  物品入库明细主键
     * @return 结果
     */
    public int deleteWarehouseInReceiptDetailByInReceiptDetailId(Long inReceiptDetailId);

    /**
     * 获取入库单物品明细
     * @param warehouseInReceiptId
     * @return
     */
    List<WarehouseGoodsDto> getInReceiptGoodsList(Long warehouseInReceiptId);

    List<WarehouseGoodsVo> getInReceiptGoodsListWithSnCode(Long receiptId);
}
