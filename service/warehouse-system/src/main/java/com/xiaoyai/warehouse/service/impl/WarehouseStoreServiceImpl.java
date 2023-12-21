package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseStoreMapper;
import com.xiaoyai.warehouse.domain.WarehouseStore;
import com.xiaoyai.warehouse.service.IWarehouseStoreService;

/**
 * 门店信息Service业务层处理
 * 
 * @author junyang
 * @date 2023-09-13
 */
@Service
public class WarehouseStoreServiceImpl extends ServiceImpl<WarehouseStoreMapper, WarehouseStore> implements IWarehouseStoreService
{
    @Autowired
    private WarehouseStoreMapper warehouseStoreMapper;

    /**
     * 查询门店信息
     * 
     * @param storeId 门店信息主键
     * @return 门店信息
     */
    @Override
    public WarehouseStore selectWarehouseStoreByStoreId(Long storeId)
    {
        return warehouseStoreMapper.selectWarehouseStoreByStoreId(storeId);
    }

    /**
     * 查询门店信息列表
     * 
     * @param warehouseStore 门店信息
     * @return 门店信息
     */
    @Override
    public List<WarehouseStore> selectWarehouseStoreList(WarehouseStore warehouseStore)
    {
        return warehouseStoreMapper.selectWarehouseStoreList(warehouseStore);
    }

    /**
     * 新增门店信息
     * 
     * @param warehouseStore 门店信息
     * @return 结果
     */
    @Override
    public int insertWarehouseStore(WarehouseStore warehouseStore)
    {
        return warehouseStoreMapper.insertWarehouseStore(warehouseStore);
    }

    /**
     * 修改门店信息
     * 
     * @param warehouseStore 门店信息
     * @return 结果
     */
    @Override
    public int updateWarehouseStore(WarehouseStore warehouseStore)
    {
        return warehouseStoreMapper.updateWarehouseStore(warehouseStore);
    }

    /**
     * 批量删除门店信息
     * 
     * @param storeIds 需要删除的门店信息主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseStoreByStoreIds(Long[] storeIds)
    {
        return warehouseStoreMapper.deleteWarehouseStoreByStoreIds(storeIds);
    }

    /**
     * 删除门店信息信息
     * 
     * @param storeId 门店信息主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseStoreByStoreId(Long storeId)
    {
        return warehouseStoreMapper.deleteWarehouseStoreByStoreId(storeId);
    }
}
