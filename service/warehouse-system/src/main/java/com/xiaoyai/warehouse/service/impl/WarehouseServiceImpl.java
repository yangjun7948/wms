package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.annotation.WarehouseScope;
import com.xiaoyai.common.constant.Constants;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.common.enums.warehouse.WarehouseType;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.warehouse.domain.WarehouseManager;
import com.xiaoyai.warehouse.service.IWarehouseCodeService;
import com.xiaoyai.warehouse.service.IWarehouseManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseMapper;
import com.xiaoyai.warehouse.domain.Warehouse;
import com.xiaoyai.warehouse.service.IWarehouseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仓库Service业务层处理
 *
 * @author junyang
 * @date 2023-09-09
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse> implements IWarehouseService {
    @Autowired
    private WarehouseMapper warehouseMapper;
    @Autowired
    private IWarehouseManagerService iWarehouseManagerService;
    @Autowired
    private IWarehouseCodeService iWarehouseCodeService;

    /**
     * 查询仓库
     *
     * @param warehouseId 仓库主键
     * @return 仓库
     */
    @Override
    public Warehouse selectWarehouseByWarehouseId(Long warehouseId) {
        return warehouseMapper.selectWarehouseByWarehouseId(warehouseId);
    }

    /**
     * 查询仓库列表
     *
     * @param warehouse 仓库
     * @return 仓库
     */
    @Override
    @WarehouseScope(warehouseAlias = "warehouse")
    public List<Warehouse> selectWarehouseList(Warehouse warehouse) {
        return warehouseMapper.selectWarehouseList(warehouse);
    }

    /**
     * 新增仓库
     *
     * @param warehouse 仓库
     * @return 结果
     */
    @Override
    public int insertWarehouse(Warehouse warehouse) {
        warehouse.setCreateTime(DateUtils.getNowDate());
        warehouse.setWarehouseCode(iWarehouseCodeService.getWarehouseCode());
        return warehouseMapper.insertWarehouse(warehouse);
    }

    /**
     * 修改仓库
     *
     * @param warehouse 仓库
     * @return 结果
     */
    @Override
    public int updateWarehouse(Warehouse warehouse) {
        warehouse.setUpdateTime(DateUtils.getNowDate());
        return warehouseMapper.updateWarehouse(warehouse);
    }

    /**
     * 批量删除仓库
     *
     * @param warehouseIds 需要删除的仓库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByWarehouseIds(Long[] warehouseIds) {
        return warehouseMapper.deleteWarehouseByWarehouseIds(warehouseIds);
    }

    /**
     * 删除仓库信息
     *
     * @param warehouseId 仓库主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseByWarehouseId(Long warehouseId) {
        return warehouseMapper.deleteWarehouseByWarehouseId(warehouseId);
    }


    @Override
    @Transactional
    public void insertPersonWarehouse(SysUser user) {
        //判断是否存在已删除的记录
        WarehouseManager oldWarehouseManager= iWarehouseManagerService.getOne(Wrappers.<WarehouseManager>lambdaQuery().eq(WarehouseManager::getUserId,user.getUserId()));
        if(oldWarehouseManager!=null){
            iWarehouseManagerService.update(Wrappers.<WarehouseManager>lambdaUpdate()
                    .set(WarehouseManager::getDelFlag, Constants.SUCCESS)
                    .eq(WarehouseManager::getUserId,user.getUserId()));

            this.update(Wrappers.<Warehouse>lambdaUpdate()
                    .set(Warehouse::getDelFlag,Constants.SUCCESS)
                    .eq(Warehouse::getWarehouseId,oldWarehouseManager.getWarehouseId()));
        }
        else{
            Warehouse warehouse = new Warehouse();
            warehouse.setWarehouseCode(iWarehouseCodeService.getWarehouseCode());
            warehouse.setWarehouseType(WarehouseType.FEIXIUSHIFU.getCode());
            warehouse.setWarehouseName(user.getNickName() + "个人仓库");
            this.insertWarehouse(warehouse);

            WarehouseManager warehouseManager = new WarehouseManager();
            warehouseManager.setWarehouseId(warehouse.getWarehouseId());
            warehouseManager.setUserId(user.getUserId());
            iWarehouseManagerService.insertWarehouseManager(warehouseManager);
        }

    }

    @Override
    @Transactional
    public void deletePersonWarehouse(Long[] userIds) {
        List<WarehouseManager> warehouseManagerList = iWarehouseManagerService.list(Wrappers.<WarehouseManager>lambdaQuery().in(WarehouseManager::getUserId, userIds));
        iWarehouseManagerService.deleteWarehouseManagerByUserIds(userIds);
        Long[] warehouseIds = warehouseManagerList.stream().map(WarehouseManager::getWarehouseId).toArray(Long[]::new);
        if (warehouseIds != null && warehouseIds.length > 0) {
            this.deleteWarehouseByWarehouseIds(warehouseIds);
        }

    }
}
