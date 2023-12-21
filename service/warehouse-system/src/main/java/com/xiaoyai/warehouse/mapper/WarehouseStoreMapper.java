package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseStore;

/**
 * 门店信息Mapper接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface WarehouseStoreMapper extends BaseMapper<WarehouseStore>
{
    /**
     * 查询门店信息
     * 
     * @param storeId 门店信息主键
     * @return 门店信息
     */
    public WarehouseStore selectWarehouseStoreByStoreId(Long storeId);

    /**
     * 查询门店信息列表
     * 
     * @param warehouseStore 门店信息
     * @return 门店信息集合
     */
    public List<WarehouseStore> selectWarehouseStoreList(WarehouseStore warehouseStore);

    /**
     * 新增门店信息
     * 
     * @param warehouseStore 门店信息
     * @return 结果
     */
    public int insertWarehouseStore(WarehouseStore warehouseStore);

    /**
     * 修改门店信息
     * 
     * @param warehouseStore 门店信息
     * @return 结果
     */
    public int updateWarehouseStore(WarehouseStore warehouseStore);

    /**
     * 删除门店信息
     * 
     * @param storeId 门店信息主键
     * @return 结果
     */
    public int deleteWarehouseStoreByStoreId(Long storeId);

    /**
     * 批量删除门店信息
     * 
     * @param storeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseStoreByStoreIds(Long[] storeIds);
}
