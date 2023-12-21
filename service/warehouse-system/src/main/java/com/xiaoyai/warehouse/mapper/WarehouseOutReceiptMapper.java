package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseOutReceipt;
import com.xiaoyai.warehouse.domain.vo.WarehouseOutReceiptVo;

/**
 * 出库单据Mapper接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface WarehouseOutReceiptMapper extends BaseMapper<WarehouseOutReceipt>
{
    /**
     * 查询出库单据
     * 
     * @param warehouseOutReceiptId 出库单据主键
     * @return 出库单据
     */
    public WarehouseOutReceipt selectWarehouseOutReceiptByWarehouseOutReceiptId(Long warehouseOutReceiptId);

    /**
     * 查询出库单据列表
     * 
     * @param warehouseOutReceipt 出库单据
     * @return 出库单据集合
     */
    public List<WarehouseOutReceipt> selectWarehouseOutReceiptList(WarehouseOutReceipt warehouseOutReceipt);

    /**
     * 新增出库单据
     * 
     * @param warehouseOutReceipt 出库单据
     * @return 结果
     */
    public int insertWarehouseOutReceipt(WarehouseOutReceipt warehouseOutReceipt);

    /**
     * 修改出库单据
     * 
     * @param warehouseOutReceipt 出库单据
     * @return 结果
     */
    public int updateWarehouseOutReceipt(WarehouseOutReceipt warehouseOutReceipt);

    /**
     * 删除出库单据
     * 
     * @param warehouseOutReceiptId 出库单据主键
     * @return 结果
     */
    public int deleteWarehouseOutReceiptByWarehouseOutReceiptId(Long warehouseOutReceiptId);

    /**
     * 批量删除出库单据
     * 
     * @param warehouseOutReceiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseOutReceiptByWarehouseOutReceiptIds(Long[] warehouseOutReceiptIds);

    WarehouseOutReceiptVo getOutReceiptScanDetail(Long receiptId);

    List<WarehouseOutReceiptVo> getOutReceiptScanList(WarehouseOutReceipt outReceipt);
}
