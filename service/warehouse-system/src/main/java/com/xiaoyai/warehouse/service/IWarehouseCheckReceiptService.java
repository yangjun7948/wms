package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceipt;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDto;

/**
 * 库存盘点Service接口
 * 
 * @author junyang
 * @date 2023-10-07
 */
public interface IWarehouseCheckReceiptService extends IService<WarehouseCheckReceipt>
{
    /**
     * 查询库存盘点
     * 
     * @param checkReceiptId 库存盘点主键
     * @return 库存盘点
     */
    public WarehouseCheckReceiptDto selectWarehouseCheckReceiptByCheckReceiptId(Long checkReceiptId);

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
    public int insertWarehouseCheckReceipt(WarehouseCheckReceiptDto warehouseCheckReceipt);

    /**
     * 修改库存盘点
     * 
     * @param warehouseCheckReceipt 库存盘点
     * @return 结果
     */
    public int updateWarehouseCheckReceipt(WarehouseCheckReceipt warehouseCheckReceipt);

    /**
     * 批量删除库存盘点
     * 
     * @param checkReceiptIds 需要删除的库存盘点主键集合
     * @return 结果
     */
    public int deleteWarehouseCheckReceiptByCheckReceiptIds(Long[] checkReceiptIds);

    /**
     * 删除库存盘点信息
     * 
     * @param checkReceiptId 库存盘点主键
     * @return 结果
     */
    public int deleteWarehouseCheckReceiptByCheckReceiptId(Long checkReceiptId);

    List<WarehouseCheckReceiptDto> selectWarehouseCheckReceiptDtoList(WarehouseCheckReceipt warehouseCheckReceipt);
}
