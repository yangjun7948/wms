package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import com.xiaoyai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseCustomerMapper;
import com.xiaoyai.warehouse.domain.WarehouseCustomer;
import com.xiaoyai.warehouse.service.IWarehouseCustomerService;

/**
 * 客户信息Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-09
 */
@Service
public class WarehouseCustomerServiceImpl extends ServiceImpl<WarehouseCustomerMapper, WarehouseCustomer> implements IWarehouseCustomerService
{
    @Autowired
    private WarehouseCustomerMapper warehouseCustomerMapper;

    /**
     * 查询客户信息
     * 
     * @param customerId 客户信息主键
     * @return 客户信息
     */
    @Override
    public WarehouseCustomer selectWarehouseCustomerByCustomerId(Long customerId)
    {
        return warehouseCustomerMapper.selectWarehouseCustomerByCustomerId(customerId);
    }

    /**
     * 查询客户信息列表
     * 
     * @param warehouseCustomer 客户信息
     * @return 客户信息
     */
    @Override
    public List<WarehouseCustomer> selectWarehouseCustomerList(WarehouseCustomer warehouseCustomer)
    {
        return warehouseCustomerMapper.selectWarehouseCustomerList(warehouseCustomer);
    }

    /**
     * 新增客户信息
     * 
     * @param warehouseCustomer 客户信息
     * @return 结果
     */
    @Override
    public int insertWarehouseCustomer(WarehouseCustomer warehouseCustomer)
    {
        warehouseCustomer.setCreateTime(DateUtils.getNowDate());
        return warehouseCustomerMapper.insertWarehouseCustomer(warehouseCustomer);
    }

    /**
     * 修改客户信息
     * 
     * @param warehouseCustomer 客户信息
     * @return 结果
     */
    @Override
    public int updateWarehouseCustomer(WarehouseCustomer warehouseCustomer)
    {
        warehouseCustomer.setUpdateTime(DateUtils.getNowDate());
        return warehouseCustomerMapper.updateWarehouseCustomer(warehouseCustomer);
    }

    /**
     * 批量删除客户信息
     * 
     * @param customerIds 需要删除的客户信息主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseCustomerByCustomerIds(Long[] customerIds)
    {
        return warehouseCustomerMapper.deleteWarehouseCustomerByCustomerIds(customerIds);
    }

    /**
     * 删除客户信息信息
     * 
     * @param customerId 客户信息主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseCustomerByCustomerId(Long customerId)
    {
        return warehouseCustomerMapper.deleteWarehouseCustomerByCustomerId(customerId);
    }
}
