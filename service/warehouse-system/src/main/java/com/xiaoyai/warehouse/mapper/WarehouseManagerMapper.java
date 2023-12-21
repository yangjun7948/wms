package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseManager;

/**
 * 仓库权限Mapper接口
 * 
 * @author junyang
 * @date 2023-11-13
 */
public interface WarehouseManagerMapper extends BaseMapper<WarehouseManager>
{
    /**
     * 查询仓库权限
     * 
     * @param userId 仓库权限主键
     * @return 仓库权限
     */
    public WarehouseManager selectWarehouseManagerByUserId(Long userId);

    /**
     * 查询仓库权限列表
     * 
     * @param warehouseManager 仓库权限
     * @return 仓库权限集合
     */
    public List<WarehouseManager> selectWarehouseManagerList(WarehouseManager warehouseManager);

    /**
     * 新增仓库权限
     * 
     * @param warehouseManager 仓库权限
     * @return 结果
     */
    public int insertWarehouseManager(WarehouseManager warehouseManager);

    /**
     * 修改仓库权限
     * 
     * @param warehouseManager 仓库权限
     * @return 结果
     */
    public int updateWarehouseManager(WarehouseManager warehouseManager);

    /**
     * 删除仓库权限
     * 
     * @param userId 仓库权限主键
     * @return 结果
     */
    public int deleteWarehouseManagerByUserId(Long userId);

    /**
     * 批量删除仓库权限
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseManagerByUserIds(Long[] userIds);
}
