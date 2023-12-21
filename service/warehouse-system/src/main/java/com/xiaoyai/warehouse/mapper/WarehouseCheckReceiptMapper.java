package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceipt;
import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDto;

/**
 * 库存盘点Mapper接口
 * 
 * @author junyang
 * @date 2023-10-07
 */
public interface WarehouseCheckReceiptMapper extends BaseMapper<WarehouseCheckReceipt>
{
    /**
     * 查询库存盘点
     * 
     * @param checkReceiptId 库存盘点主键
     * @return 库存盘点
     */
    public WarehouseCheckReceipt selectWarehouseCheckReceiptByCheckReceiptId(Long checkReceiptId);

    /**
     * 查询库存盘点列表
     * 
     * @param warehouseCheckReceipt 库存盘点
     * @return 库存盘点集合
     */
    public List<WarehouseCheckReceipt> selectWarehouseCheckReceiptList(WarehouseCheckReceipt warehouseCheckReceipt);

    /**
     * 新增库存盘点
     * 
     * @param warehouseCheckReceipt 库存盘点
     * @return 结果
     */
    public int insertWarehouseCheckReceipt(WarehouseCheckReceipt warehouseCheckReceipt);

    /**
     * 修改库存盘点
     * 
     * @param warehouseCheckReceipt 库存盘点
     * @return 结果
     */
    public int updateWarehouseCheckReceipt(WarehouseCheckReceipt warehouseCheckReceipt);

    /**
     * 删除库存盘点
     * 
     * @param checkReceiptId 库存盘点主键
     * @return 结果
     */
    public int deleteWarehouseCheckReceiptByCheckReceiptId(Long checkReceiptId);

    /**
     * 批量删除库存盘点
     * 
     * @param checkReceiptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseCheckReceiptByCheckReceiptIds(Long[] checkReceiptIds);

    List<WarehouseCheckReceiptDto> selectWarehouseCheckReceiptDtoList(WarehouseCheckReceipt warehouseCheckReceipt);
}
