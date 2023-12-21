package com.xiaoyai.warehouse.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.warehouse.domain.Warehouse;
import com.xiaoyai.warehouse.mapper.WarehouseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xiaoyai.warehouse.mapper.WarehouseSupplierMapper;
import com.xiaoyai.warehouse.domain.WarehouseSupplier;
import com.xiaoyai.warehouse.service.IWarehouseSupplierService;

/**
 * 供应商库Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-07
 */
@Service
public class WarehouseSupplierServiceImpl extends ServiceImpl<WarehouseSupplierMapper, WarehouseSupplier>  implements IWarehouseSupplierService
{
    @Autowired
    private WarehouseSupplierMapper warehouseSupplierMapper;

    /**
     * 查询供应商库
     * 
     * @param supplierId 供应商库主键
     * @return 供应商库
     */
    @Override
    public WarehouseSupplier selectWarehouseSupplierBySupplierId(Long supplierId)
    {
        return warehouseSupplierMapper.selectWarehouseSupplierBySupplierId(supplierId);
    }

    /**
     * 查询供应商库列表
     * 
     * @param warehouseSupplier 供应商库
     * @return 供应商库
     */
    @Override
    public List<WarehouseSupplier> selectWarehouseSupplierList(WarehouseSupplier warehouseSupplier)
    {
        return warehouseSupplierMapper.selectWarehouseSupplierList(warehouseSupplier);
    }

    /**
     * 新增供应商库
     * 
     * @param warehouseSupplier 供应商库
     * @return 结果
     */
    @Override
    public int insertWarehouseSupplier(WarehouseSupplier warehouseSupplier)
    {
        warehouseSupplier.setCreateTime(DateUtils.getNowDate());
        return warehouseSupplierMapper.insertWarehouseSupplier(warehouseSupplier);
    }

    /**
     * 修改供应商库
     * 
     * @param warehouseSupplier 供应商库
     * @return 结果
     */
    @Override
    public int updateWarehouseSupplier(WarehouseSupplier warehouseSupplier)
    {
        warehouseSupplier.setUpdateTime(DateUtils.getNowDate());
        return warehouseSupplierMapper.updateWarehouseSupplier(warehouseSupplier);
    }

    /**
     * 批量删除供应商库
     * 
     * @param supplierIds 需要删除的供应商库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseSupplierBySupplierIds(Long[] supplierIds)
    {
        return warehouseSupplierMapper.deleteWarehouseSupplierBySupplierIds(supplierIds);
    }

    /**
     * 删除供应商库信息
     * 
     * @param supplierId 供应商库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseSupplierBySupplierId(Long supplierId)
    {
        return warehouseSupplierMapper.deleteWarehouseSupplierBySupplierId(supplierId);
    }
}
