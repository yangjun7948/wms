package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseInReceipt;
import com.xiaoyai.warehouse.domain.vo.WarehouseInReceiptVo;

/**
 * 入库单据Mapper接口
 * 
 * @author junyang
 * @date 2023-09-09
 */
public interface WarehouseInReceiptMapper extends BaseMapper<WarehouseInReceipt>
{
    /**
     * 查询入库单据
     * 
     * @param warehouseInReceiptId 入库单据主键
     * @return 入库单据
     */
    public WarehouseInReceipt selectWarehouseInReceiptByWarehouseInReceiptId(Long warehouseInReceiptId);

    /**
     * 查询入库单据列表
     * 
     * @param warehouseInReceipt 入库单据
     * @return 入库单据集合
     */
    public List<WarehouseInReceipt> selectWarehouseInReceiptList(WarehouseInReceipt warehouseInReceipt);

    /**
     * 新增入库单据
     * 
     * @param warehouseInReceipt 入库单据
     * @return 结果
     */
    public Long insertWarehouseInReceipt(WarehouseInReceipt warehouseInReceipt);

    /**
     * 修改入库单据
     * 
     * @param warehouseInReceipt 入库单据
     * @return 结果
     */
    public int updateWarehouseInReceipt(WarehouseInReceipt warehouseInReceipt);

    /**
     * 删除入库单据
     * 
     * @param warehouseInReceiptId 入库单据主键
     * @return 结果
     */
    public int deleteWarehouseInReceiptByWarehouseInReceiptId(Long warehouseInReceiptId);

    /**
     * 批量删除入库单据
     * 
     * @param warehouseInReceiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseInReceiptByWarehouseInReceiptIds(Long[] warehouseInReceiptIds);

    List<WarehouseInReceiptVo> getInReceiptScanList(WarehouseInReceipt warehouseInReceipt);

    WarehouseInReceiptVo getInReceiptScanDetail(Long receiptId);
}
