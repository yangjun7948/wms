package com.xiaoyai.warehouse.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.Warehouse;
import com.xiaoyai.warehouse.domain.WarehouseSupplier;

/**
 * 供应商库Mapper接口
 * 
 * @author junyang
 * @date 2023-09-07
 */
public interface WarehouseSupplierMapper extends BaseMapper<WarehouseSupplier>
{
    /**
     * 查询供应商库
     * 
     * @param supplierId 供应商库主键
     * @return 供应商库
     */
    public WarehouseSupplier selectWarehouseSupplierBySupplierId(Long supplierId);

    /**
     * 查询供应商库列表
     * 
     * @param warehouseSupplier 供应商库
     * @return 供应商库集合
     */
    public List<WarehouseSupplier> selectWarehouseSupplierList(WarehouseSupplier warehouseSupplier);

    /**
     * 新增供应商库
     * 
     * @param warehouseSupplier 供应商库
     * @return 结果
     */
    public int insertWarehouseSupplier(WarehouseSupplier warehouseSupplier);

    /**
     * 修改供应商库
     * 
     * @param warehouseSupplier 供应商库
     * @return 结果
     */
    public int updateWarehouseSupplier(WarehouseSupplier warehouseSupplier);

    /**
     * 删除供应商库
     * 
     * @param supplierId 供应商库主键
     * @return 结果
     */
    public int deleteWarehouseSupplierBySupplierId(Long supplierId);

    /**
     * 批量删除供应商库
     * 
     * @param supplierIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseSupplierBySupplierIds(Long[] supplierIds);
}
