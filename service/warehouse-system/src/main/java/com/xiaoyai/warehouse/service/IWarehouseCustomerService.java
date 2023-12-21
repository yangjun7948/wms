package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 客户信息Service接口
 * 
 * @author junyang
 * @date 2023-09-09
 */
public interface IWarehouseCustomerService extends IService<WarehouseCustomer>
{
    /**
     * 查询客户信息
     * 
     * @param customerId 客户信息主键
     * @return 客户信息
     */
    public WarehouseCustomer selectWarehouseCustomerByCustomerId(Long customerId);

    /**
     * 查询客户信息列表
     * 
     * @param warehouseCustomer 客户信息
     * @return 客户信息集合
     */
    public List<WarehouseCustomer> selectWarehouseCustomerList(WarehouseCustomer warehouseCustomer);

    /**
     * 新增客户信息
     * 
     * @param warehouseCustomer 客户信息
     * @return 结果
     */
    public int insertWarehouseCustomer(WarehouseCustomer warehouseCustomer);

    /**
     * 修改客户信息
     * 
     * @param warehouseCustomer 客户信息
     * @return 结果
     */
    public int updateWarehouseCustomer(WarehouseCustomer warehouseCustomer);

    /**
     * 批量删除客户信息
     * 
     * @param customerIds 需要删除的客户信息主键集合
     * @return 结果
     */
    public int deleteWarehouseCustomerByCustomerIds(Long[] customerIds);

    /**
     * 删除客户信息信息
     * 
     * @param customerId 客户信息主键
     * @return 结果
     */
    public int deleteWarehouseCustomerByCustomerId(Long customerId);
}
