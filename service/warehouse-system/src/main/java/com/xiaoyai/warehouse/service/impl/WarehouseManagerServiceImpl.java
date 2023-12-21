package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.constant.Constants;
import com.xiaoyai.warehouse.domain.Warehouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseManagerMapper;
import com.xiaoyai.warehouse.domain.WarehouseManager;
import com.xiaoyai.warehouse.service.IWarehouseManagerService;

/**
 * 仓库权限Service业务层处理
 *
 * @author junyang
 * @date 2023-11-13
 */
@Service
public class WarehouseManagerServiceImpl extends ServiceImpl<WarehouseManagerMapper, WarehouseManager> implements IWarehouseManagerService {
    @Autowired
    private WarehouseManagerMapper warehouseManagerMapper;

    /**
     * 查询仓库权限
     *
     * @param userId 仓库权限主键
     * @return 仓库权限
     */
    @Override
    public WarehouseManager selectWarehouseManagerByUserId(Long userId) {
        return warehouseManagerMapper.selectWarehouseManagerByUserId(userId);
    }

    /**
     * 查询仓库权限列表
     *
     * @param warehouseManager 仓库权限
     * @return 仓库权限
     */
    @Override
    public List<WarehouseManager> selectWarehouseManagerList(WarehouseManager warehouseManager) {
        return warehouseManagerMapper.selectWarehouseManagerList(warehouseManager);
    }

    /**
     * 新增仓库权限
     *
     * @param warehouseManager 仓库权限
     * @return 结果
     */
    @Override
    public int insertWarehouseManager(WarehouseManager warehouseManager) {
        return warehouseManagerMapper.insertWarehouseManager(warehouseManager);
    }

    /**
     * 修改仓库权限
     *
     * @param warehouseManager 仓库权限
     * @return 结果
     */
    @Override
    public int updateWarehouseManager(WarehouseManager warehouseManager) {
        return warehouseManagerMapper.updateWarehouseManager(warehouseManager);
    }

    /**
     * 批量删除仓库权限
     *
     * @param userIds 需要删除的仓库权限主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseManagerByUserIds(Long[] userIds) {
        return warehouseManagerMapper.deleteWarehouseManagerByUserIds(userIds);
    }

    /**
     * 删除仓库权限信息
     *
     * @param userId 仓库权限主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseManagerByUserId(Long userId) {
        return warehouseManagerMapper.deleteWarehouseManagerByUserId(userId);
    }

    @Override
    public List<Long> selectWarehouseIdsByUserId(Long userId) {
        return this.list(Wrappers.<WarehouseManager>lambdaQuery()
                        .eq(WarehouseManager::getUserId, userId)
                        .eq(WarehouseManager::getDelFlag, Constants.SUCCESS)).stream()
                .map(WarehouseManager::getWarehouseId).collect(Collectors.toList());
    }
}
