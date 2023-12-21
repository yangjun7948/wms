package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseOutReceiptDetail;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;

/**
 *  物品出库明细Mapper接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface WarehouseOutReceiptDetailMapper extends BaseMapper<WarehouseOutReceiptDetail>
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
     * 删除 物品出库明细
     * 
     * @param outReceiptDetailId  物品出库明细主键
     * @return 结果
     */
    public int deleteWarehouseOutReceiptDetailByOutReceiptDetailId(Long outReceiptDetailId);

    /**
     * 批量删除 物品出库明细
     * 
     * @param outReceiptDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseOutReceiptDetailByOutReceiptDetailIds(Long[] outReceiptDetailIds);

    List<WarehouseGoodsDto> getOutReceiptGoodsList(Long warehouseOutReceiptId);

    List<WarehouseGoodsDto> getOutReceiptGoodsWithSnCodeList(Long warehouseOutReceiptId);

    List<WarehouseGoodsVo> getOutReceiptGoodsListWithSnCode(Long receiptId);
}
