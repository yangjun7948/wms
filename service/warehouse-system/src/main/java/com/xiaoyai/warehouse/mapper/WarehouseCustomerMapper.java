package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseCustomer;

/**
 * 客户信息Mapper接口
 * 
 * @author junyang
 * @date 2023-09-09
 */
public interface WarehouseCustomerMapper extends BaseMapper<WarehouseCustomer>
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
     * 删除客户信息
     * 
     * @param customerId 客户信息主键
     * @return 结果
     */
    public int deleteWarehouseCustomerByCustomerId(Long customerId);

    /**
     * 批量删除客户信息
     * 
     * @param customerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseCustomerByCustomerIds(Long[] customerIds);
}
