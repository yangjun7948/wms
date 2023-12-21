package com.xiaoyai.warehouse.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceiptDetail;
import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDetailDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDto;
import com.xiaoyai.warehouse.service.IWarehouseCheckReceiptDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseCheckReceiptMapper;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceipt;
import com.xiaoyai.warehouse.service.IWarehouseCheckReceiptService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存盘点Service业务层处理
 *
 * @author junyang
 * @date 2023-10-07
 */
@Service
public class WarehouseCheckReceiptServiceImpl extends ServiceImpl<WarehouseCheckReceiptMapper, WarehouseCheckReceipt> implements IWarehouseCheckReceiptService {
    @Autowired
    private WarehouseCheckReceiptMapper warehouseCheckReceiptMapper;

    @Autowired
    private IWarehouseCheckReceiptDetailService iWarehouseCheckReceiptDetailService;

    /**
     * 查询库存盘点
     *
     * @param checkReceiptId 库存盘点主键
     * @return 库存盘点
     */
    @Override
    public WarehouseCheckReceiptDto selectWarehouseCheckReceiptByCheckReceiptId(Long checkReceiptId) {
        WarehouseCheckReceipt warehouseCheckReceipt = this.getById(checkReceiptId);
        WarehouseCheckReceiptDto warehouseCheckReceiptDto = new WarehouseCheckReceiptDto();
        BeanUtil.copyProperties(warehouseCheckReceipt, warehouseCheckReceiptDto);
        WarehouseCheckReceiptDetail receiptDetail = new WarehouseCheckReceiptDetail();
        receiptDetail.setCheckReceiptId(checkReceiptId);
        warehouseCheckReceiptDto.setReceiptGoodsList(iWarehouseCheckReceiptDetailService.getCheckReceiptDetailList(receiptDetail));
        return warehouseCheckReceiptDto;
    }

    /**
     * 查询库存盘点列表
     *
     * @param warehouseCheckReceipt 库存盘点
     * @return 库存盘点
     */
    @Override
    public List<WarehouseCheckReceipt> selectWarehouseCheckReceiptList(WarehouseCheckReceipt warehouseCheckReceipt) {
        return warehouseCheckReceiptMapper.selectWarehouseCheckReceiptList(warehouseCheckReceipt);
    }

    /**
     * 新增库存盘点
     *
     * @param warehouseCheckReceipt 库存盘点
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertWarehouseCheckReceipt(WarehouseCheckReceiptDto warehouseCheckReceipt) {
        warehouseCheckReceipt.setCheckTime(DateUtils.getNowDate());
        warehouseCheckReceipt.setCreateTime(DateUtils.getNowDate());
        WarehouseCheckReceipt checkReceipt = new WarehouseCheckReceipt();
        BeanUtil.copyProperties(warehouseCheckReceipt, checkReceipt);
        this.save(checkReceipt);
        List<WarehouseCheckReceiptDetail> warehouseCheckReceiptDetailList = warehouseCheckReceipt.getReceiptGoodsList().stream().map(t -> {
            WarehouseCheckReceiptDetail checkReceiptDetail = new WarehouseCheckReceiptDetail();
            BeanUtil.copyProperties(t, checkReceiptDetail);
            checkReceiptDetail.setCheckReceiptId(checkReceipt.getCheckReceiptId());
            return checkReceiptDetail;
        }).collect(Collectors.toList());
        iWarehouseCheckReceiptDetailService.saveBatch(warehouseCheckReceiptDetailList);
        return 1;
    }

    /**
     * 修改库存盘点
     *
     * @param warehouseCheckReceipt 库存盘点
     * @return 结果
     */
    @Override
    public int updateWarehouseCheckReceipt(WarehouseCheckReceipt warehouseCheckReceipt) {
        warehouseCheckReceipt.setUpdateTime(DateUtils.getNowDate());
        return warehouseCheckReceiptMapper.updateWarehouseCheckReceipt(warehouseCheckReceipt);
    }

    /**
     * 批量删除库存盘点
     *
     * @param checkReceiptIds 需要删除的库存盘点主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseCheckReceiptByCheckReceiptIds(Long[] checkReceiptIds) {
        return warehouseCheckReceiptMapper.deleteWarehouseCheckReceiptByCheckReceiptIds(checkReceiptIds);
    }

    /**
     * 删除库存盘点信息
     *
     * @param checkReceiptId 库存盘点主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseCheckReceiptByCheckReceiptId(Long checkReceiptId) {
        return warehouseCheckReceiptMapper.deleteWarehouseCheckReceiptByCheckReceiptId(checkReceiptId);
    }

    @Override
    public List<WarehouseCheckReceiptDto> selectWarehouseCheckReceiptDtoList(WarehouseCheckReceipt warehouseCheckReceipt) {
        return warehouseCheckReceiptMapper.selectWarehouseCheckReceiptDtoList(warehouseCheckReceipt);
    }
}
