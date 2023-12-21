package com.xiaoyai.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.constant.WareHouseConstants;
import com.xiaoyai.common.enums.warehouse.*;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.warehouse.domain.*;
import com.xiaoyai.warehouse.domain.dto.WarehouseApplyReceiptDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseApplyReceiptMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 要货申请Service业务层处理
 *
 * @author junyang
 * @date 2023-09-18
 */
@Service
public class WarehouseApplyReceiptServiceImpl extends ServiceImpl<WarehouseApplyReceiptMapper, WarehouseApplyReceipt> implements IWarehouseApplyReceiptService {
    @Autowired
    private WarehouseApplyReceiptMapper warehouseApplyReceiptMapper;

    @Autowired
    private IWarehouseApplyReceiptDetailService iWarehouseApplyReceiptDetailService;
    @Autowired
    private IWarehouseOutReceiptService iWarehouseOutReceiptService;
    @Autowired
    private IWarehouseCodeService iWarehouseCodeService;
    @Autowired
    private IWarehouseOutReceiptDetailService iWarehouseOutReceiptDetailService;
    @Autowired
    private IWarehouseManagerService iWarehouseManagerService;
    @Autowired
    private IWarehouseGoodsTraceService iWarehouseGoodsTraceService;

    /**
     * 查询要货申请
     *
     * @param applyReceiptId 要货申请主键
     * @return 要货申请
     */
    @Override
    public WarehouseApplyReceiptDto selectWarehouseApplyReceiptByApplyReceiptId(Long applyReceiptId) {
        WarehouseApplyReceiptDto warehouseApplyReceiptDto = warehouseApplyReceiptMapper.selectWarehouseApplyReceiptByApplyReceiptId(applyReceiptId);
        List<WarehouseGoodsDto> warehouseGoodsDtos = iWarehouseApplyReceiptDetailService.getApplyReceiptGoodsList(applyReceiptId);
        warehouseApplyReceiptDto.setReceiptGoodsList(warehouseGoodsDtos);
        return warehouseApplyReceiptDto;
    }

    /**
     * 查询要货申请列表
     *
     * @param warehouseApplyReceipt 要货申请
     * @return 要货申请
     */
    @Override
    public List<WarehouseApplyReceiptDto> selectWarehouseApplyReceiptList(WarehouseApplyReceiptDto warehouseApplyReceipt) {
        return warehouseApplyReceiptMapper.selectWarehouseApplyReceiptList(warehouseApplyReceipt);
    }

    /**
     * 新增要货申请
     *
     * @param warehouseApplyReceipt 要货申请
     * @return 结果
     */
    @Override
    public int insertWarehouseApplyReceipt(WarehouseApplyReceipt warehouseApplyReceipt) {
        //查询要货人的仓库
        WarehouseManager warehouseManager = iWarehouseManagerService.getOne(Wrappers.<WarehouseManager>lambdaQuery().eq(WarehouseManager::getUserId, warehouseApplyReceipt.getApplyUserId()));
        warehouseApplyReceipt.setApplyWarehouseId(warehouseManager.getWarehouseId());
        warehouseApplyReceipt.setCreateTime(DateUtils.getNowDate());
        return warehouseApplyReceiptMapper.insertWarehouseApplyReceipt(warehouseApplyReceipt);
    }

    /**
     * 修改要货申请
     *
     * @param warehouseApplyReceipt 要货申请
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWarehouseApplyReceipt(WarehouseApplyReceiptDto warehouseApplyReceipt) throws Exception {
        warehouseApplyReceipt.setUpdateTime(DateUtils.getNowDate());
        //生成出库单
        WarehouseOutReceipt outReceipt = new WarehouseOutReceipt();
        outReceipt.setOutWarehouseType(WareHouseConstants.APPLY_OUT_TYPE_CODE);
        outReceipt.setDocumentNumber(iWarehouseCodeService.getOutReceiptCode());
        outReceipt.setWarehouseId(warehouseApplyReceipt.getOutWarehouseId());
        outReceipt.setOutWarehouseTime(DateTime.now());
        outReceipt.setReceiveId(warehouseApplyReceipt.getApplyWarehouseId());
        outReceipt.setReceiveName(warehouseApplyReceipt.getApplyWarehouseName());
        outReceipt.setState(ReceiptState.SCAN.getCode());
        //保存出库单
        if (warehouseApplyReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0) {
            outReceipt.setState(ReceiptState.APPROVE.getCode());
        }
        iWarehouseOutReceiptService.save(outReceipt);
        List<WarehouseOutReceiptDetail> receiptDetailList = new ArrayList<>();
        if (warehouseApplyReceipt.getApplyType().equals(ApplyType.PERSON.getCode())) {
            //个人申请，只允许要货存在溯源码的产品
            for (WarehouseGoodsDto goodsDto : warehouseApplyReceipt.getReceiptGoodsList()) {
                //查询库存数量
                List<WarehouseGoodsTrace> goodsSnList = iWarehouseGoodsTraceService.list(Wrappers.<WarehouseGoodsTrace>lambdaQuery()
                        .eq(WarehouseGoodsTrace::getWarehouseId, warehouseApplyReceipt.getOutWarehouseId())
                        .eq(WarehouseGoodsTrace::getGoodsId, goodsDto.getGoodsId()));
                if (goodsSnList.size() < goodsDto.getGoodsNumber()) {
                    throw new Exception("物品" + goodsDto.getGoodsName() + "库存数量不足！");
                }
                WarehouseOutReceiptDetail detail = new WarehouseOutReceiptDetail();
                BeanUtil.copyProperties(goodsDto, detail);
                detail.setOutReceiptId(outReceipt.getWarehouseOutReceiptId());
                receiptDetailList.add(detail);
            }
        } else {
            //保存出库物品明细
            receiptDetailList = warehouseApplyReceipt.getReceiptGoodsList().stream().map(t -> {
                WarehouseOutReceiptDetail detail = new WarehouseOutReceiptDetail();
                BeanUtil.copyProperties(t, detail);
                detail.setOutReceiptId(outReceipt.getWarehouseOutReceiptId());
                return detail;
            }).collect(Collectors.toList());
        }

        iWarehouseOutReceiptDetailService.saveBatch(receiptDetailList);
        this.update(Wrappers.<WarehouseApplyReceipt>lambdaUpdate()
                .set(WarehouseApplyReceipt::getState, warehouseApplyReceipt.getState())
                .set(WarehouseApplyReceipt::getOutReceiptId, outReceipt.getWarehouseOutReceiptId())
                .set(WarehouseApplyReceipt::getOutDocumentNumber, outReceipt.getDocumentNumber())
                .eq(WarehouseApplyReceipt::getApplyReceiptId, warehouseApplyReceipt.getApplyReceiptId()));
        return true;
    }

    /**
     * 批量删除要货申请
     *
     * @param applyReceiptIds 需要删除的要货申请主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWarehouseApplyReceiptByApplyReceiptIds(Long[] applyReceiptIds) {
        return warehouseApplyReceiptMapper.deleteWarehouseApplyReceiptByApplyReceiptIds(applyReceiptIds);
    }

    /**
     * 删除要货申请信息
     *
     * @param applyReceiptId 要货申请主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteWarehouseApplyReceiptByApplyReceiptId(Long applyReceiptId) {
        iWarehouseApplyReceiptDetailService.remove(Wrappers.<WarehouseApplyReceiptDetail>lambdaQuery()
                .eq(WarehouseApplyReceiptDetail::getApplyReceiptId, applyReceiptId));
        return warehouseApplyReceiptMapper.deleteWarehouseApplyReceiptByApplyReceiptId(applyReceiptId);
    }

    /**
     * 新增要货申请
     *
     * @param applyReceiptDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveApplyReceiptDto(WarehouseApplyReceiptDto applyReceiptDto) {
        WarehouseApplyReceipt receipt = new WarehouseApplyReceiptDto();
        BeanUtil.copyProperties(applyReceiptDto, receipt);
        WarehouseManager warehouseManager = iWarehouseManagerService.getOne(Wrappers.<WarehouseManager>lambdaQuery().eq(WarehouseManager::getUserId, applyReceiptDto.getApplyUserId()));
        receipt.setState(ApplyState.APPROVE.getCode());
        receipt.setApplyWarehouseId(warehouseManager.getWarehouseId());
        this.save(receipt);
        List<WarehouseApplyReceiptDetail> receiptDetails = applyReceiptDto.getReceiptGoodsList().stream().map(t -> {
            WarehouseApplyReceiptDetail detail = new WarehouseApplyReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setApplyReceiptId(receipt.getApplyReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseApplyReceiptDetailService.saveBatch(receiptDetails);
        return true;
    }

    @Override
    @Transactional
    public Boolean warehouseApplyReceiptDo(Long receiptId) {
        WarehouseApplyReceipt applyReceipt = this.getById(receiptId);
        //减自己库存
        List<WarehouseApplyReceiptDetail> detailList = iWarehouseApplyReceiptDetailService.list(Wrappers.<WarehouseApplyReceiptDetail>lambdaQuery()
                .eq(WarehouseApplyReceiptDetail::getApplyReceiptId, receiptId));

        return true;
    }

    @Override
    public void updateApplyReceiptByInReceiptId(Long warehouseInReceiptId) {
        this.update(Wrappers.<WarehouseApplyReceipt>lambdaUpdate()
                .set(WarehouseApplyReceipt::getState, ApplyState.COMPLETE.getCode())
                .eq(WarehouseApplyReceipt::getInReceiptId, warehouseInReceiptId));
    }
}
