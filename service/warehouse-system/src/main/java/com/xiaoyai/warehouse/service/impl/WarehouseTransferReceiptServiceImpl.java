package com.xiaoyai.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.constant.PrintConst;
import com.xiaoyai.common.constant.WareHouseConstants;
import com.xiaoyai.common.core.domain.JasperTableSource;
import com.xiaoyai.common.enums.warehouse.ReceiptState;
import com.xiaoyai.common.enums.warehouse.ReceiptType;
import com.xiaoyai.common.enums.warehouse.TransferState;
import com.xiaoyai.common.enums.warehouse.ValidateState;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.common.utils.DictUtils;
import com.xiaoyai.common.utils.PrintUtils;
import com.xiaoyai.warehouse.domain.*;
import com.xiaoyai.warehouse.domain.dto.ReceiptDetailFields;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseTransferReceiptDto;
import com.xiaoyai.warehouse.service.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseTransferReceiptMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 调拨单据Service业务层处理
 *
 * @author junyang
 * @date 2023-09-16
 */
@Service
public class WarehouseTransferReceiptServiceImpl extends ServiceImpl<WarehouseTransferReceiptMapper, WarehouseTransferReceipt> implements IWarehouseTransferReceiptService {
    @Autowired
    private WarehouseTransferReceiptMapper warehouseTransferReceiptMapper;

    @Autowired
    private IWarehouseTransferReceiptDetailService iWarehouseTransferReceiptDetailService;

    @Autowired
    private IWarehouseService iWarehouseService;
    @Autowired
    private IWarehouseCodeService iWarehouseCodeService;
    @Autowired
    private IWarehouseOutReceiptService iWarehouseOutReceiptService;
    @Autowired
    private IWarehouseOutReceiptDetailService iWarehouseOutReceiptDetailService;
    @Autowired
    private IWarehouseInReceiptService iWarehouseInReceiptService;
    @Autowired
    private IWarehouseInReceiptDetailService iWarehouseInReceiptDetailService;
    @Autowired
    private IWarehouseStockService iWarehouseStockService;
    @Autowired
    private IWarehouseGoodsTraceService iWarehouseGoodsTraceService;
    @Autowired
    private IWarehouseGoodsTraceRelService iWarehouseGoodsTraceRelService;
    @Autowired
    private IWarehouseReceiptRelService iWarehouseReceiptRelService;

    /**
     * 查询调拨单据
     *
     * @param warehouseTransferReceiptId 调拨单据主键
     * @return 调拨单据
     */
    @Override
    public WarehouseTransferReceiptDto selectWarehouseTransferReceiptById(Long warehouseTransferReceiptId) {
        WarehouseTransferReceiptDto receiptDto = new WarehouseTransferReceiptDto();
        WarehouseTransferReceipt receipt = this.getById(warehouseTransferReceiptId);
        WarehouseReceiptRel receiptRel = iWarehouseReceiptRelService.getOne(Wrappers.<WarehouseReceiptRel>lambdaQuery().eq(WarehouseReceiptRel::getTransferId, receipt.getWarehouseTransferReceiptId()));

        BeanUtil.copyProperties(receipt, receiptDto);
        receiptDto.setInReceiptId(receiptRel.getInId() != null ? receiptRel.getInId().toString() : "");
        receiptDto.setOutReceiptId(receiptRel.getOutId() != null ? receiptRel.getOutId().toString() : "");
        //获取物品明细
        List<WarehouseGoodsDto> warehouseTransferReceiptDetailList =
                (receipt.getState().equals(TransferState.TRANSFER) || receipt.getState().equals(TransferState.COMPLETE)) ?
                        iWarehouseTransferReceiptDetailService.getTransferReceiptGoodsList(warehouseTransferReceiptId) :
                        iWarehouseTransferReceiptDetailService.getTransferReceiptGoodsWithSnCodeList(receiptDto);


        receiptDto.setReceiptGoodsList(warehouseTransferReceiptDetailList);
        return receiptDto;
    }

    /**
     * 查询调拨单据列表
     *
     * @param warehouseTransferReceipt 调拨单据
     * @return 调拨单据
     */
    @Override
    public List<WarehouseTransferReceiptDto> selectWarehouseTransferReceiptList(WarehouseTransferReceipt warehouseTransferReceipt) {
        return warehouseTransferReceiptMapper.selectWarehouseTransferReceiptList(warehouseTransferReceipt);
    }

    /**
     * 新增调拨单据,自动生成出库单
     *
     * @param warehouseTransferReceiptDto 调拨单据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertWarehouseTransferReceipt(WarehouseTransferReceiptDto warehouseTransferReceiptDto) {
        warehouseTransferReceiptDto.setCreateTime(DateUtils.getNowDate());
        WarehouseTransferReceipt receipt = new WarehouseTransferReceipt();
        BeanUtil.copyProperties(warehouseTransferReceiptDto, receipt);

        //保存调拨明细
        //保存出库单
        this.save(receipt);
        List<WarehouseTransferReceiptDetail> transferReceiptDetailList = warehouseTransferReceiptDto.getReceiptGoodsList().stream().map(t -> {
            WarehouseTransferReceiptDetail detail = new WarehouseTransferReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setTransferReceiptId(receipt.getWarehouseTransferReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseTransferReceiptDetailService.saveBatch(transferReceiptDetailList);
        if (warehouseTransferReceiptDto.getState().equals(TransferState.TRANSFER.getCode())) {
            //生成带扫码状态出库单
            //出库单生成出库信息
            WarehouseOutReceipt outReceipt = new WarehouseOutReceipt();
            outReceipt.setWarehouseId(warehouseTransferReceiptDto.getTransferOutWarehouseId());
            outReceipt.setDocumentNumber(iWarehouseCodeService.getOutReceiptCode());
            outReceipt.setOutWarehouseTime(warehouseTransferReceiptDto.getTransferWarehouseTime());
            outReceipt.setOutWarehouseType(WareHouseConstants.TRANSFER_OUT_TYPE_CODE);
            outReceipt.setReceiveTime(new Date());
            outReceipt.setReceiveId(warehouseTransferReceiptDto.getTransferInWarehouseId());
            outReceipt.setReceiveName(warehouseTransferReceiptDto.getTransferInWarehouseName());
            outReceipt.setAuditTime(warehouseTransferReceiptDto.getAuditTime());
            outReceipt.setAuditId(warehouseTransferReceiptDto.getAuditId());
            outReceipt.setAuditName(warehouseTransferReceiptDto.getAuditName());
            outReceipt.setState(ReceiptState.SCAN.getCode());
            if (warehouseTransferReceiptDto.getReceiptGoodsList().stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0) {
                outReceipt.setState(ReceiptState.APPROVE.getCode());
            }

            outReceipt.setCreateBy(warehouseTransferReceiptDto.getCreateBy());
            iWarehouseOutReceiptService.save(outReceipt);
            //保存出库物品明细
            List<WarehouseOutReceiptDetail> outReceiptDetailList = warehouseTransferReceiptDto.getReceiptGoodsList().stream().map(t -> {
                WarehouseOutReceiptDetail detail = new WarehouseOutReceiptDetail();
                BeanUtil.copyProperties(t, detail);
                detail.setOutReceiptId(outReceipt.getWarehouseOutReceiptId());
                return detail;
            }).collect(Collectors.toList());
            iWarehouseOutReceiptDetailService.saveBatch(outReceiptDetailList);
            WarehouseReceiptRel receiptRel = new WarehouseReceiptRel();
            receiptRel.setTransferId(receipt.getWarehouseTransferReceiptId());
            receiptRel.setInDocumentNumber("");
            receiptRel.setOutDocumentNumber(outReceipt.getDocumentNumber());
            receiptRel.setTransferDocumentNumber(receipt.getDocumentNumber());
            receiptRel.setOutId(outReceipt.getWarehouseOutReceiptId());
            iWarehouseReceiptRelService.save(receiptRel);
        }
        return true;
    }

    /**
     * 修改调拨单据
     *
     * @param warehouseTransferReceiptDto 调拨单据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWarehouseTransferReceipt(WarehouseTransferReceiptDto warehouseTransferReceiptDto) {

        WarehouseTransferReceipt receipt = new WarehouseTransferReceipt();
        BeanUtil.copyProperties(warehouseTransferReceiptDto, receipt);
        //保存出库单
        if (warehouseTransferReceiptDto.getState().equals(ReceiptState.SCAN.getCode())
                && (warehouseTransferReceiptDto.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0
                || (warehouseTransferReceiptDto.getReceiptGoodsList().stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0))) {
            receipt.setState(ReceiptState.APPROVE.getCode());
        }
        this.updateById(receipt);
        //先删除入库物品明细
        iWarehouseTransferReceiptDetailService.remove(Wrappers.<WarehouseTransferReceiptDetail>lambdaQuery()
                .eq(WarehouseTransferReceiptDetail::getTransferReceiptId, warehouseTransferReceiptDto.getWarehouseTransferReceiptId()));
        //保存新的物品明细
        List<WarehouseTransferReceiptDetail> receiptDetailList = warehouseTransferReceiptDto.getReceiptGoodsList().stream().map(t -> {
            WarehouseTransferReceiptDetail detail = new WarehouseTransferReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setTransferReceiptId(warehouseTransferReceiptDto.getWarehouseTransferReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseTransferReceiptDetailService.saveBatch(receiptDetailList);
        return true;
    }

    /**
     * 批量删除调拨单据
     *
     * @param warehouseTransferReceiptIds 需要删除的调拨单据主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseTransferReceiptByWarehouseTransferReceiptIds(Long[] warehouseTransferReceiptIds) {
        return warehouseTransferReceiptMapper.deleteWarehouseTransferReceiptByWarehouseTransferReceiptIds(warehouseTransferReceiptIds);
    }

    /**
     * 删除调拨单据信息
     *
     * @param warehouseTransferReceiptId 调拨单据主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWarehouseTransferReceiptByWarehouseTransferReceiptId(Long warehouseTransferReceiptId) {
        this.removeById(warehouseTransferReceiptId);
        iWarehouseTransferReceiptDetailService.remove(Wrappers.<WarehouseTransferReceiptDetail>lambdaQuery()
                .eq(WarehouseTransferReceiptDetail::getTransferReceiptId, warehouseTransferReceiptId));

        iWarehouseGoodsTraceRelService.remove(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                .eq(WarehouseGoodsTraceRel::getRelId, warehouseTransferReceiptId)
                .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.TransferReceipt.getCode()));
        return true;
    }

    /**
     * 审核
     *
     * @param warehouseTransferReceipt
     * @return
     * @throws Exception
     */
    @Override
    public boolean approveWarehouseTransferReceipt(WarehouseTransferReceiptDto warehouseTransferReceipt) throws Exception {
        doTransfer(warehouseTransferReceipt);
        return true;
    }

    /**
     * 处理调拨逻辑 废弃！！
     *
     * @param warehouseTransferReceiptDto
     */
    @Transactional(rollbackFor = Exception.class)
    protected synchronized void doTransfer(WarehouseTransferReceiptDto warehouseTransferReceiptDto) throws Exception {
        //判断当前状态
        if (warehouseTransferReceiptDto.getState().equals(ReceiptState.SCAN.getCode())) {
            if ((warehouseTransferReceiptDto.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0)) {
                warehouseTransferReceiptDto.setState(ReceiptState.APPROVE.getCode());
            }
            return;
        }
        //生成出库和入库单，同时分别计算库存
        else if (warehouseTransferReceiptDto.getState().equals(ReceiptState.APPROVE.getCode())) {
            //出库单生成出库信息
            WarehouseOutReceipt outReceipt = new WarehouseOutReceipt();
            outReceipt.setWarehouseId(warehouseTransferReceiptDto.getTransferOutWarehouseId());
            outReceipt.setDocumentNumber(iWarehouseCodeService.getOutReceiptCode());
            outReceipt.setOutWarehouseTime(warehouseTransferReceiptDto.getTransferWarehouseTime());
            outReceipt.setOutWarehouseType(WareHouseConstants.TRANSFER_OUT_TYPE_CODE);
            outReceipt.setReceiveTime(new Date());
            outReceipt.setReceiveId(warehouseTransferReceiptDto.getTransferInWarehouseId());
            outReceipt.setReceiveName(warehouseTransferReceiptDto.getTransferInWarehouseName());
            outReceipt.setAuditTime(warehouseTransferReceiptDto.getAuditTime());
            outReceipt.setAuditId(warehouseTransferReceiptDto.getAuditId());
            outReceipt.setAuditName(warehouseTransferReceiptDto.getAuditName());
            outReceipt.setState(ReceiptState.COMPLETE.getCode());
            outReceipt.setCreateBy(warehouseTransferReceiptDto.getCreateBy());
            iWarehouseOutReceiptService.save(outReceipt);

            //保存出库物品明细
            List<WarehouseOutReceiptDetail> outReceiptDetailList = warehouseTransferReceiptDto.getReceiptGoodsList().stream().map(t -> {
                WarehouseOutReceiptDetail detail = new WarehouseOutReceiptDetail();
                BeanUtil.copyProperties(t, detail);
                detail.setOutReceiptId(outReceipt.getWarehouseOutReceiptId());
                return detail;
            }).collect(Collectors.toList());
            iWarehouseOutReceiptDetailService.saveBatch(outReceiptDetailList);
            //扣除库存
            //修改库存
            for (WarehouseGoodsDto warehouseGoodsDto : warehouseTransferReceiptDto.getReceiptGoodsList()) {

                //查询现有物品库存信息
                WarehouseStock warehouseStock = iWarehouseStockService.getOne(Wrappers.<WarehouseStock>lambdaQuery()
                        .eq(WarehouseStock::getWarehouseId, warehouseTransferReceiptDto.getTransferOutWarehouseId())
                        .eq(WarehouseStock::getGoodsId, warehouseGoodsDto.getGoodsId())
                        .eq(WarehouseStock::getGoodsPrice, warehouseGoodsDto.getGoodsPrice())
                        .eq(WarehouseStock::getGoodsDeadlineTime, warehouseGoodsDto.getGoodsDeadlineTime()));

                if (warehouseStock != null) {
                    if (warehouseStock.getStockNumber().compareTo(new BigDecimal(warehouseGoodsDto.getGoodsNumber())) < 0) {
                        throw new Exception(warehouseGoodsDto.getGoodsName() + "物品库存不足");
                    }
                    warehouseStock.setStockAmount(warehouseStock.getStockAmount().subtract(warehouseGoodsDto.getGoodsAmount()));
                    warehouseStock.setStockNumber(warehouseStock.getStockNumber().subtract(new BigDecimal(warehouseGoodsDto.getGoodsNumber())));
                    iWarehouseStockService.updateById(warehouseStock);
                } else {
                    throw new Exception("物品不存在");
                }
            }
            //处理入库逻辑
            WarehouseInReceipt inReceipt = new WarehouseInReceipt();
            inReceipt.setWarehouseId(warehouseTransferReceiptDto.getTransferInWarehouseId());
            inReceipt.setDocumentNumber(iWarehouseCodeService.getInReceiptCode());
            inReceipt.setInWarehouseTime(warehouseTransferReceiptDto.getTransferWarehouseTime());
            inReceipt.setInWarehouseType(WareHouseConstants.TRANSFER_IN_TYPE_CODE);
            inReceipt.setAuditTime(warehouseTransferReceiptDto.getAuditTime());
            inReceipt.setAuditId(warehouseTransferReceiptDto.getAuditId());
            inReceipt.setAuditName(warehouseTransferReceiptDto.getAuditName());
            inReceipt.setState(ReceiptState.COMPLETE.getCode());
            inReceipt.setCreateBy(warehouseTransferReceiptDto.getCreateBy());
            inReceipt.setCreateTime(new Date());
            iWarehouseInReceiptService.save(inReceipt);

            //保存入库物品明细
            List<WarehouseInReceiptDetail> inReceiptDetailList = warehouseTransferReceiptDto.getReceiptGoodsList().stream().map(t -> {
                WarehouseInReceiptDetail detail = new WarehouseInReceiptDetail();
                BeanUtil.copyProperties(t, detail);
                detail.setInReceiptId(inReceipt.getWarehouseInReceiptId());
                return detail;
            }).collect(Collectors.toList());
            iWarehouseInReceiptDetailService.saveBatch(inReceiptDetailList);
            //查询现有物品库存信息
            for (WarehouseGoodsDto warehouseGoodsDto : warehouseTransferReceiptDto.getReceiptGoodsList()) {

                //查询现有物品库存信息
                WarehouseStock warehouseStock = iWarehouseStockService.getOne(Wrappers.<WarehouseStock>lambdaQuery()
                        .eq(WarehouseStock::getWarehouseId, warehouseTransferReceiptDto.getTransferInWarehouseId())
                        .eq(WarehouseStock::getGoodsId, warehouseGoodsDto.getGoodsId())
                        .eq(WarehouseStock::getGoodsPrice, warehouseGoodsDto.getGoodsPrice())
                        .eq(WarehouseStock::getGoodsDeadlineTime, warehouseGoodsDto.getGoodsDeadlineTime()));

                if (warehouseStock != null) {
                    warehouseStock.setStockAmount(warehouseStock.getStockAmount().add(warehouseGoodsDto.getGoodsAmount()));
                    warehouseStock.setStockNumber(warehouseStock.getStockNumber().add(new BigDecimal(warehouseGoodsDto.getGoodsNumber())));
                    iWarehouseStockService.updateById(warehouseStock);
                } else {
                    warehouseStock = new WarehouseStock();
                    warehouseStock.setStockAmount(warehouseGoodsDto.getGoodsAmount());
                    warehouseStock.setStockNumber(new BigDecimal(warehouseGoodsDto.getGoodsNumber()));
                    warehouseStock.setGoodsPrice(warehouseGoodsDto.getGoodsPrice());
                    warehouseStock.setGoodsId(warehouseGoodsDto.getGoodsId());
                    warehouseStock.setWarehouseId(warehouseTransferReceiptDto.getTransferInWarehouseId());
                    warehouseStock.setGoodsDeadlineTime(warehouseGoodsDto.getGoodsDeadlineTime());
                    iWarehouseStockService.save(warehouseStock);
                }
            }
            WarehouseTransferReceipt warehouseTransferReceipt = new WarehouseTransferReceipt();
            warehouseTransferReceipt.setState(ReceiptState.COMPLETE.getCode());
            warehouseTransferReceipt.setWarehouseTransferReceiptId(warehouseTransferReceiptDto.getWarehouseTransferReceiptId());
            this.updateById(warehouseTransferReceipt);
            WarehouseReceiptRel receiptRel = new WarehouseReceiptRel();
            receiptRel.setTransferId(warehouseTransferReceiptDto.getWarehouseTransferReceiptId());
            receiptRel.setInDocumentNumber(inReceipt.getDocumentNumber());
            receiptRel.setOutDocumentNumber(outReceipt.getDocumentNumber());
            receiptRel.setInId(inReceipt.getWarehouseInReceiptId());
            receiptRel.setOutId(outReceipt.getWarehouseOutReceiptId());

            iWarehouseReceiptRelService.save(receiptRel);
            return;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    protected synchronized void undoTransfer(WarehouseTransferReceiptDto warehouseTransferReceiptDto) {

    }

    @Override
    public byte[] transferReceiptPdf(Long warehouseTransferReceiptId) {
        Map printParams = new HashMap<>();
        WarehouseTransferReceiptDto receipt = this.selectWarehouseTransferReceiptById(warehouseTransferReceiptId);
        Warehouse warehouse = iWarehouseService.getById(receipt.getTransferOutWarehouseId());
        Warehouse warehouse1 = iWarehouseService.getById(receipt.getTransferInWarehouseId());
        printParams.put("receiptName", "调拨单");
        printParams.put("warehouseName", warehouse.getWarehouseName());
        printParams.put("inWarehouseName", warehouse1.getWarehouseName());
        printParams.put("transferType", DictUtils.getDictLabel("transfer_type", receipt.getTransferType()));
        printParams.put("documentNumber", receipt.getDocumentNumber());
        printParams.put("receiveTime", DateUtil.format(receipt.getTransferWarehouseTime(), "yyyy-MM-dd HH:mm:ss"));

        List<ReceiptDetailFields> receiptDetailFieldsList = receipt.getReceiptGoodsList().stream().map(t -> {
            ReceiptDetailFields receiptDetailFields = new ReceiptDetailFields();
            receiptDetailFields.setGoodsCode(t.getGoodsCode());
            receiptDetailFields.setGoodsName(t.getGoodsName());
            if (!StrUtil.isBlankIfStr(t.getSpecificationType())) {
                receiptDetailFields.setSpecification(DictUtils.getDictLabel("goods_specification", t.getSpecificationType().toString()));
            } else {
                receiptDetailFields.setSpecification("");
            }
            if (!StrUtil.isBlankIfStr(t.getUnitType())) {
                receiptDetailFields.setUnitMeasurementName(DictUtils.getDictLabel("goods_unit", t.getUnitType().toString()));
            } else {
                receiptDetailFields.setUnitMeasurementName("");
            }
            receiptDetailFields.setUnitPrice(t.getGoodsPrice().toString());
            receiptDetailFields.setGoodsCount(t.getGoodsNumber().toString());
            receiptDetailFields.setGoodsAmount(t.getGoodsAmount().toString());
            receiptDetailFields.setGoodsRemark(t.getRemark());
            receiptDetailFields.setSnCode(t.getSnCode());
            return receiptDetailFields;
        }).collect(Collectors.toList());
        JasperTableSource jts = new JasperTableSource();
        jts.setTableData(new JRBeanCollectionDataSource(receiptDetailFieldsList));
        List<JasperTableSource> mlist = new ArrayList<JasperTableSource>();
        mlist.add(jts);
        printParams.put("totalCount", receipt.getReceiptGoodsList().stream().map(t -> t.getGoodsNumber()).reduce(0, (a, b) -> a + b).toString());
        printParams.put("totalAmount", receipt.getReceiptGoodsList().stream().map(t -> t.getGoodsAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).toString());
        return PrintUtils.exportPdf(PrintUtils.getExportPrint(PrintConst.TRANSFER_RECEIPT_TPL, printParams, mlist)).toByteArray();
    }

    /**
     * 取消审核
     *
     * @param warehouseTransferReceipt
     * @return
     */
    @Override
    public synchronized boolean unApproveWarehouseOutReceipt(WarehouseTransferReceiptDto warehouseTransferReceipt) {
        undoTransfer(warehouseTransferReceipt);
        return true;
    }

    /**
     * 处理调拨入库逻辑
     *
     * @param transferId
     */
    @Override
    @Transactional
    public void doTransferInReceipt(Long transferId) {
        WarehouseTransferReceipt receipt = this.getById(transferId);
        WarehouseReceiptRel receiptRel = iWarehouseReceiptRelService.getOne(Wrappers.<WarehouseReceiptRel>lambdaQuery().eq(WarehouseReceiptRel::getTransferId, transferId));
        //获取物品明细
        List<WarehouseGoodsDto> warehouseTransferReceiptDetailList =
                iWarehouseTransferReceiptDetailService.getTransferReceiptGoodsList(transferId);
        //处理入库逻辑
        WarehouseInReceipt inReceipt = new WarehouseInReceipt();
        inReceipt.setWarehouseId(receipt.getTransferInWarehouseId());
        inReceipt.setDocumentNumber(iWarehouseCodeService.getInReceiptCode());
        inReceipt.setInWarehouseTime(DateTime.now());
        inReceipt.setInWarehouseType(WareHouseConstants.TRANSFER_IN_TYPE_CODE);
        inReceipt.setState(ReceiptState.SCAN.getCode());
        if (warehouseTransferReceiptDetailList.stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0) {
            inReceipt.setState(ReceiptState.APPROVE.getCode());
        }
        inReceipt.setCreateBy(receipt.getCreateBy());
        inReceipt.setCreateTime(new Date());
        iWarehouseInReceiptService.save(inReceipt);

        //保存入库物品明细
        List<WarehouseInReceiptDetail> inReceiptDetailList = warehouseTransferReceiptDetailList.stream().map(t -> {
            WarehouseInReceiptDetail detail = new WarehouseInReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setInReceiptId(inReceipt.getWarehouseInReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseInReceiptDetailService.saveBatch(inReceiptDetailList);
        // 更新入库关系表中的入库单号
        iWarehouseReceiptRelService.update(Wrappers.<WarehouseReceiptRel>lambdaUpdate()
                .eq(WarehouseReceiptRel::getTransferId, transferId)
                .set(WarehouseReceiptRel::getInId, inReceipt.getWarehouseInReceiptId())
                .set(WarehouseReceiptRel::getInDocumentNumber, inReceipt.getDocumentNumber()));
        // 生成溯源码记录,通过出库单id确定出库的物品溯源码信息
        List<WarehouseGoodsTraceRel> traceRels = iWarehouseGoodsTraceRelService.list(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                .eq(WarehouseGoodsTraceRel::getRelId, receiptRel.getOutId())
                .eq(WarehouseGoodsTraceRel::getRecordType,ReceiptType.OutReceipt.getCode()));
        List<WarehouseGoodsTraceRel> newtraceRels = traceRels.stream().map(t -> {
            WarehouseGoodsTraceRel rel = new WarehouseGoodsTraceRel();
            rel.setTraceId(t.getTraceId());
            rel.setRecordTime(DateTime.now());
            rel.setGoodsId(t.getGoodsId());
            rel.setRecordType(ReceiptType.InReceipt.getCode());
            rel.setRelId(inReceipt.getWarehouseInReceiptId());
            rel.setRecordTime(DateTime.now());
            return rel;
        }).collect(Collectors.toList());
        iWarehouseGoodsTraceRelService.saveBatch(newtraceRels);
    }

    @Override
    public void updateTransferByInReceiptId(Long warehouseInReceiptId) {
        WarehouseReceiptRel receiptRel = iWarehouseReceiptRelService.getOne(Wrappers.<WarehouseReceiptRel>lambdaQuery().eq(WarehouseReceiptRel::getInId, warehouseInReceiptId));
        if (receiptRel != null) {
            this.update(Wrappers.<WarehouseTransferReceipt>lambdaUpdate()
                    .eq(WarehouseTransferReceipt::getWarehouseTransferReceiptId, receiptRel.getTransferId())
                    .set(WarehouseTransferReceipt::getState, TransferState.COMPLETE.getCode()));
        }
    }
}
