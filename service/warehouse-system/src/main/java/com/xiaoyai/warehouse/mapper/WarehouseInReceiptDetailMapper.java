package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseInReceiptDetail;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;

/**
 *  物品入库明细Mapper接口
 * 
 * @author junyang
 * @date 2023-09-10
 */
public interface WarehouseInReceiptDetailMapper extends BaseMapper<WarehouseInReceiptDetail>
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
     * 删除 物品入库明细
     * 
     * @param inReceiptDetailId  物品入库明细主键
     * @return 结果
     */
    public int deleteWarehouseInReceiptDetailByInReceiptDetailId(Long inReceiptDetailId);

    /**
     * 批量删除 物品入库明细
     * 
     * @param inReceiptDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseInReceiptDetailByInReceiptDetailIds(Long[] inReceiptDetailIds);

    List<WarehouseGoodsDto> getInReceiptGoodsList(Long warehouseInReceiptId);

    List<WarehouseGoodsVo> getInReceiptGoodsListWithSnCode(Long receiptId);
}
