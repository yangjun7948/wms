package com.xiaoyai.warehouse.service;

import java.util.List;

import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.warehouse.domain.Warehouse;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 仓库Service接口
 * 
 * @author junyang
 * @date 2023-09-09
 */
public interface IWarehouseService extends IService<Warehouse>
{
    /**
     * 查询仓库
     * 
     * @param warehouseId 仓库主键
     * @return 仓库
     */
    public Warehouse selectWarehouseByWarehouseId(Long warehouseId);

    /**
     * 查询仓库列表
     * 
     * @param warehouse 仓库
     * @return 仓库集合
     */
    public List<Warehouse> selectWarehouseList(Warehouse warehouse);

    /**
     * 新增仓库
     * 
     * @param warehouse 仓库
     * @return 结果
     */
    public int insertWarehouse(Warehouse warehouse);

    /**
     * 修改仓库
     * 
     * @param warehouse 仓库
     * @return 结果
     */
    public int updateWarehouse(Warehouse warehouse);

    /**
     * 批量删除仓库
     * 
     * @param warehouseIds 需要删除的仓库主键集合
     * @return 结果
     */
    public int deleteWarehouseByWarehouseIds(Long[] warehouseIds);

    /**
     * 删除仓库信息
     * 
     * @param warehouseId 仓库主键
     * @return 结果
     */
    public int deleteWarehouseByWarehouseId(Long warehouseId);

    void insertPersonWarehouse(SysUser user);

    void deletePersonWarehouse(Long[] userIds);
}
