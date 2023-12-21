package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseApplyReceiptDetail;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;

/**
 * 要货明细Mapper接口
 * 
 * @author junyang
 * @date 2023-09-18
 */
public interface WarehouseApplyReceiptDetailMapper extends BaseMapper<WarehouseApplyReceiptDetail>
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
     * 删除要货明细
     * 
     * @param applyReceiptDetailId 要货明细主键
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptDetailByApplyReceiptDetailId(Long applyReceiptDetailId);

    /**
     * 批量删除要货明细
     * 
     * @param applyReceiptDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseApplyReceiptDetailByApplyReceiptDetailIds(Long[] applyReceiptDetailIds);

    /**
     * 获取要货物品明细
     * @param applyReceiptId
     * @return
     */
    List<WarehouseGoodsDto> getApplyReceiptGoodsList(Long applyReceiptId);
}
