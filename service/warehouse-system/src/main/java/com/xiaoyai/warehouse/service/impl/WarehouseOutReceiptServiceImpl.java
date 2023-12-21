package com.xiaoyai.warehouse.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.xiaoyai.common.annotation.WarehouseScope;
import com.xiaoyai.common.constant.PrintConst;
import com.xiaoyai.common.core.domain.JasperTableSource;
import com.xiaoyai.common.enums.warehouse.*;
import com.xiaoyai.common.utils.DictUtils;
import com.xiaoyai.common.utils.PrintUtils;
import com.xiaoyai.warehouse.domain.*;

import com.xiaoyai.warehouse.domain.dto.ReceiptDetailFields;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseOutReceiptDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;
import com.xiaoyai.warehouse.domain.vo.WarehouseOutReceiptVo;
import com.xiaoyai.warehouse.service.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseOutReceiptMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出库单据Service业务层处理
 *
 * @author junyang
 * @date 2023-09-13
 */
@Service
public class WarehouseOutReceiptServiceImpl extends ServiceImpl<WarehouseOutReceiptMapper, WarehouseOutReceipt> implements IWarehouseOutReceiptService {
    @Autowired
    private WarehouseOutReceiptMapper warehouseOutReceiptMapper;
    @Autowired
    private IWarehouseOutReceiptDetailService iWarehouseOutReceiptDetailService;
    @Autowired
    private IWarehouseStockService iWarehouseStockService;
    @Autowired
    private IWarehouseService iWarehouseService;
    @Autowired
    private IWarehouseStoreService iWarehouseStoreService;
    @Autowired
    private IWarehouseGoodsTraceRelService iWarehouseGoodsTraceRelService;
    @Autowired
    private IWarehouseGoodsTraceService iWarehouseGoodsTraceService;
    @Autowired
    private IWarehouseReceiptRelService iWarehouseReceiptRelService;
    @Autowired
    private IWarehouseTransferReceiptService iWarehouseTransferReceiptService;
    @Autowired
    private IWarehouseApplyReceiptService iWarehouseApplyReceiptService;
    @Autowired
    private IWarehouseInReceiptService iWarehouseInReceiptService;
    @Autowired
    private IWarehouseInReceiptDetailService iWarehouseInReceiptDetailService;

    /**
     * 查询出库单据
     *
     * @param warehouseOutReceiptId 出库单据主键
     * @return 出库单据
     */
    @Override
    public WarehouseOutReceiptDto selectWarehouseOutReceiptByWarehouseOutReceiptId(Long warehouseOutReceiptId) {
        WarehouseOutReceiptDto warehouseOutReceiptDto = new WarehouseOutReceiptDto();
        WarehouseOutReceipt outReceipt = this.getOne(Wrappers.<WarehouseOutReceipt>lambdaQuery()
                .eq(WarehouseOutReceipt::getWarehouseOutReceiptId, warehouseOutReceiptId));
        BeanUtil.copyProperties(outReceipt, warehouseOutReceiptDto);
        warehouseOutReceiptDto.setOutWarehouseTypeName(DictUtils.getDictLabel(DictCodes.OUT_RECEIPT_TYPE.getCode(), outReceipt.getOutWarehouseType()));
        //获取物品明细
        List<WarehouseGoodsDto> warehouseOutReceiptDetailList = new ArrayList<>();
        if (outReceipt.getState().equals(ReceiptState.INIT.getCode())) {
            warehouseOutReceiptDetailList = iWarehouseOutReceiptDetailService.getOutReceiptGoodsList(warehouseOutReceiptId);
            warehouseOutReceiptDto.setReceiptGoodsList(warehouseOutReceiptDetailList);
        } else if (outReceipt.getState().equals(ReceiptState.SCAN.getCode())) {
            List<WarehouseGoodsDto> warehouseGoodsDtoList = new ArrayList<>();
            warehouseOutReceiptDetailList = iWarehouseOutReceiptDetailService.getOutReceiptGoodsWithSnCodeList(warehouseOutReceiptId);
            for (WarehouseGoodsDto goodsDto : warehouseOutReceiptDetailList) {
                if (goodsDto.getSnValidate().equals(ValidateState.YES.getCode())) {
                    for (int i = 0; i < goodsDto.getGoodsNumber(); i++) {
                        WarehouseGoodsDto tempGoods = new WarehouseGoodsDto();
                        BeanUtil.copyProperties(goodsDto, tempGoods);
                        tempGoods.setGoodsNumber(1);
                        warehouseGoodsDtoList.add(tempGoods);
                    }
                } else {
                    warehouseGoodsDtoList.add(goodsDto);
                }
            }
            warehouseOutReceiptDto.setReceiptGoodsList(warehouseGoodsDtoList);
        } else {
            warehouseOutReceiptDetailList = iWarehouseOutReceiptDetailService.getOutReceiptGoodsWithSnCodeList(warehouseOutReceiptId);
            for (WarehouseGoodsDto goodsDto : warehouseOutReceiptDetailList) {
                if (!StrUtil.isBlankIfStr(goodsDto.getSnCode())) {
                    goodsDto.setGoodsNumber(1);
                    goodsDto.setGoodsAmount(goodsDto.getGoodsPrice().multiply(new BigDecimal(goodsDto.getGoodsNumber()), MathContext.DECIMAL32));
                }
            }
            warehouseOutReceiptDto.setReceiptGoodsList(warehouseOutReceiptDetailList);
        }


        return warehouseOutReceiptDto;
    }

    /**
     * 查询出库单据列表
     *
     * @param warehouseOutReceipt 出库单据
     * @return 出库单据
     */
    @Override
    @WarehouseScope(warehouseAlias = "warehouse_out_receipt")
    public List<WarehouseOutReceipt> selectWarehouseOutReceiptList(WarehouseOutReceipt warehouseOutReceipt) {
        return warehouseOutReceiptMapper.selectWarehouseOutReceiptList(warehouseOutReceipt);
    }

    /**
     * 新增出库单据
     *
     * @param warehouseOutReceipt 出库单据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertWarehouseOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt) {
        warehouseOutReceipt.setOutWarehouseTime(DateTime.now());
        WarehouseOutReceipt outReceipt = new WarehouseOutReceipt();
        BeanUtil.copyProperties(warehouseOutReceipt, outReceipt);
        //保存出库单
        if (warehouseOutReceipt.getState().equals(ReceiptState.SCAN.getCode())
                && (warehouseOutReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0
                || (warehouseOutReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0))) {
            warehouseOutReceipt.setState(ReceiptState.APPROVE.getCode());
        }
        this.save(warehouseOutReceipt);
        //保存出库物品明细
        List<WarehouseOutReceiptDetail> outReceiptDetailList = warehouseOutReceipt.getReceiptGoodsList().stream().map(t -> {
            WarehouseOutReceiptDetail detail = new WarehouseOutReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setOutReceiptId(warehouseOutReceipt.getWarehouseOutReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseOutReceiptDetailService.saveBatch(outReceiptDetailList);
        return true;
    }

    /**
     * 修改出库单据
     *
     * @param warehouseOutReceipt 出库单据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateWarehouseOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt) {
        WarehouseOutReceipt outReceipt = new WarehouseOutReceipt();
        BeanUtil.copyProperties(warehouseOutReceipt, outReceipt);
        //保存出库单
        if (warehouseOutReceipt.getState().equals(ReceiptState.SCAN.getCode())
                && (warehouseOutReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0
                || (warehouseOutReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0))) {
            outReceipt.setState(ReceiptState.APPROVE.getCode());
        }
        this.updateById(outReceipt);
        //先删除入库物品明细
        iWarehouseOutReceiptDetailService.remove(Wrappers.<WarehouseOutReceiptDetail>lambdaQuery()
                .eq(WarehouseOutReceiptDetail::getOutReceiptId, warehouseOutReceipt.getWarehouseOutReceiptId()));
        //保存新的物品明细
        List<WarehouseOutReceiptDetail> outReceiptDetailList = warehouseOutReceipt.getReceiptGoodsList().stream().map(t -> {
            WarehouseOutReceiptDetail detail = new WarehouseOutReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setOutReceiptId(warehouseOutReceipt.getWarehouseOutReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseOutReceiptDetailService.saveBatch(outReceiptDetailList);
        return true;
    }

    /**
     * 批量删除出库单据
     *
     * @param warehouseOutReceiptIds 需要删除的出库单据主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseOutReceiptByWarehouseOutReceiptIds(Long[] warehouseOutReceiptIds) {
        return warehouseOutReceiptMapper.deleteWarehouseOutReceiptByWarehouseOutReceiptIds(warehouseOutReceiptIds);
    }

    /**
     * 删除出库单据信息
     *
     * @param warehouseOutReceiptId 出库单据主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWarehouseOutReceiptByWarehouseOutReceiptId(Long warehouseOutReceiptId) {
        this.removeById(warehouseOutReceiptId);
        iWarehouseOutReceiptDetailService.remove(Wrappers.<WarehouseOutReceiptDetail>lambdaQuery()
                .eq(WarehouseOutReceiptDetail::getOutReceiptId, warehouseOutReceiptId));

        iWarehouseGoodsTraceRelService.remove(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                .eq(WarehouseGoodsTraceRel::getRelId, warehouseOutReceiptId)
                .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.OutReceipt.getCode()));
        return true;

    }

    /**
     * 出库单审核
     *
     * @param warehouseOutReceipt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveWarehouseOutReceipt(WarehouseOutReceipt warehouseOutReceipt) throws Exception {
        WarehouseOutReceipt outReceipt = this.getById(warehouseOutReceipt.getWarehouseOutReceiptId());
        if (!outReceipt.getState().equals(ReceiptState.APPROVE.getCode())) {
            throw new Exception("当前单据状态不是待审核状态，请检查！");
        }
        //物品 出库
        warehouseOutReceipt.setState(ReceiptState.COMPLETE.getCode());
        this.updateById(warehouseOutReceipt);
        //重新获取一下出库信息，防止前端修改
        WarehouseOutReceiptDto warehouseOutReceiptDto = this.selectWarehouseOutReceiptByWarehouseOutReceiptId(warehouseOutReceipt.getWarehouseOutReceiptId());
        doOutReceipt(warehouseOutReceiptDto);
        updateOutReceiptTraceRel(warehouseOutReceiptDto);
        //如果是调拨单生成的，需要创建调入仓库的入库单
        WarehouseReceiptRel receiptRel = iWarehouseReceiptRelService.getOne(Wrappers.<WarehouseReceiptRel>lambdaQuery().eq(WarehouseReceiptRel::getOutId, warehouseOutReceipt.getWarehouseOutReceiptId()));
        if (receiptRel != null && receiptRel.getTransferId() > 0) {
            iWarehouseTransferReceiptService.doTransferInReceipt(receiptRel.getTransferId());
        }
        //如果是申请单生成的，需要创建要货仓库入库单
        WarehouseApplyReceipt applyReceipt = iWarehouseApplyReceiptService.getOne(Wrappers.<WarehouseApplyReceipt>lambdaQuery()
                .eq(WarehouseApplyReceipt::getOutReceiptId, warehouseOutReceipt.getWarehouseOutReceiptId()));
        if (applyReceipt != null) {
            iWarehouseInReceiptService.doApplyInReceipt(applyReceipt.getApplyReceiptId());
        }
        return true;
    }

    /**
     * 出库单据取消审核
     *
     * @param warehouseOutReceipt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unApproveWarehouseOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt) throws Exception {
        //物品入库
        WarehouseOutReceipt outReceipt = new WarehouseOutReceipt();
        BeanUtil.copyProperties(warehouseOutReceipt, outReceipt);
        outReceipt.setState(ReceiptState.SCAN.getCode());
        this.updateById(outReceipt);
        cancelOutReceipt(warehouseOutReceipt);
        return true;
    }

    /**
     * 打印
     *
     * @param warehouseOutReceiptId
     * @return
     */
    @Override
    public byte[] outReceiptPdf(Long warehouseOutReceiptId) {
        Map printParams = new HashMap<>();
        WarehouseOutReceipt outReceipt = this.getById(warehouseOutReceiptId);
        List<WarehouseGoodsDto> warehouseOutReceiptDetailList = iWarehouseOutReceiptDetailService.getOutReceiptGoodsWithSnCodeList(warehouseOutReceiptId);
        Warehouse warehouse = iWarehouseService.getById(outReceipt.getWarehouseId());
        WarehouseStore warehouseStore = iWarehouseStoreService.getById(outReceipt.getReceiveId());
        printParams.put("receiptName", "出库单");
        printParams.put("warehouseName", warehouse.getWarehouseName());
        printParams.put("receiveName", outReceipt.getReceiveName());
        printParams.put("outWarehouseTypeName", DictUtils.getDictLabel(DictCodes.OUT_RECEIPT_TYPE.getCode(), outReceipt.getOutWarehouseType()));
        printParams.put("documentNumber", outReceipt.getDocumentNumber());
        printParams.put("outWarehouseTime", DateUtil.format(outReceipt.getOutWarehouseTime(), "yyyy-MM-dd HH:mm:ss"));

        List<ReceiptDetailFields> receiptDetailFieldsList = warehouseOutReceiptDetailList.stream().map(t -> {
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
            if (!StrUtil.isBlankIfStr(t.getSnCode())) {
                t.setGoodsNumber(1);
                t.setGoodsAmount(t.getGoodsPrice());
            }
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
        printParams.put("totalCount", warehouseOutReceiptDetailList.stream().map(t -> t.getGoodsNumber()).reduce(0, (a, b) -> a + b).toString());
        printParams.put("totalAmount", warehouseOutReceiptDetailList.stream().map(t -> t.getGoodsAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).toString());
        return PrintUtils.exportPdf(PrintUtils.getExportPrint(PrintConst.OUT_RECEIPT_TPL, printParams, mlist)).toByteArray();
    }

    /**
     * 待扫码单据列表
     *
     * @return
     */
    @Override
    @WarehouseScope(warehouseAlias = "a")
    public PageInfo<WarehouseOutReceiptVo> getOutReceiptScanList(WarehouseOutReceipt outReceipt) {
        List<WarehouseOutReceiptVo> warehouseInReceiptVoList = this.baseMapper.getOutReceiptScanList(outReceipt);
        PageInfo<WarehouseOutReceiptVo> pageInfo = new PageInfo<>(warehouseInReceiptVoList);
        pageInfo.setList(warehouseInReceiptVoList.stream().map(vo -> {
            vo.setOutWarehouseTypeName(DictUtils.getDictLabel(DictCodes.OUT_RECEIPT_TYPE.getCode(), vo.getOutWarehouseType()));
            return vo;
        }).collect(Collectors.toList()));
        return pageInfo;
    }

    /**
     * 扫码出库单明细
     *
     * @param receiptId
     * @return
     */
    @Override
    public WarehouseOutReceiptVo getOutReceiptScanDetail(Long receiptId) {
        WarehouseOutReceiptVo warehouseOutReceiptVo = this.baseMapper.getOutReceiptScanDetail(receiptId);
        warehouseOutReceiptVo.setOutWarehouseTypeName(DictUtils.getDictLabel(DictCodes.OUT_RECEIPT_TYPE.getCode(), warehouseOutReceiptVo.getOutWarehouseType()));
        //查询物品明细
        List<WarehouseGoodsDto> warehouseOutReceiptDetailList = iWarehouseOutReceiptDetailService.getOutReceiptGoodsList(receiptId);
        //获取物品明细
        List<WarehouseGoodsVo> warehouseGoodsDtoList = new ArrayList<>();

        for (WarehouseGoodsDto goodsDto : warehouseOutReceiptDetailList) {
            WarehouseGoodsVo tempGoods = new WarehouseGoodsVo();
            BeanUtil.copyProperties(goodsDto, tempGoods);
            if (goodsDto.getSnValidate().equals(ValidateState.YES.getCode())) {
                for (int i = 0; i < goodsDto.getGoodsNumber(); i++) {
                    warehouseGoodsDtoList.add(tempGoods);
                }
            } else {
                warehouseGoodsDtoList.add(tempGoods);
            }
        }
        warehouseOutReceiptVo.setGoodsVoList(warehouseGoodsDtoList);
        return warehouseOutReceiptVo;
    }

    /**
     * 扫码出库提交
     *
     * @param warehouseOutReceiptVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOutReceiptScanDetail(WarehouseOutReceiptVo warehouseOutReceiptVo) throws Exception {

        WarehouseOutReceipt outReceipt = this.getById(warehouseOutReceiptVo.getWarehouseOutReceiptId());
        Warehouse warehouse = iWarehouseService.getById(outReceipt.getWarehouseId());
        //异常判断
        if (!outReceipt.getState().equals(ReceiptState.SCAN.getCode())) {
            throw new Exception("当前单据不是待扫码状态，请检查！");
        }
        //判断是否都扫码完成
        for (WarehouseGoodsVo warehouseGoodsVo : warehouseOutReceiptVo.getGoodsVoList()) {
            if (ValidateState.YES.getCode().equals(warehouseGoodsVo.getSnValidate()) && (!warehouseGoodsVo.getScanSuccess() || StrUtil.isBlankIfStr(warehouseGoodsVo.getSnCode()))) {
                throw new Exception("存在未扫码的物品，请检查！");
            }
            WarehouseGoodsTrace goodsTrace = iWarehouseGoodsTraceService.getOne(Wrappers.<WarehouseGoodsTrace>lambdaQuery()
                    .eq(WarehouseGoodsTrace::getSnCode, warehouseGoodsVo.getSnCode())
                    .eq(WarehouseGoodsTrace::getWarehouseId, outReceipt.getWarehouseId()));
            if (goodsTrace == null) {
                throw new Exception("[" + warehouseGoodsVo.getSnCode() + "]不属于本仓库！");
            }
        }
        int snCodeCount = warehouseOutReceiptVo.getGoodsVoList().stream().filter(t -> ValidateState.YES.getCode().equals(t.getSnValidate())).map(t -> t.getSnCode()).distinct().collect(Collectors.toList()).size();
        int snCodeGoodCount = warehouseOutReceiptVo.getGoodsVoList().stream().filter(t -> ValidateState.YES.getCode().equals(t.getSnValidate())).collect(Collectors.toList()).size();
        if (snCodeCount != snCodeGoodCount) {
            throw new Exception("存在相同的溯源码，请检查！");
        }
        //个人仓库，扫码直接出库，无需审核
        if (warehouse.getWarehouseType().equals(WarehouseType.FEIXIUSHIFU.getCode())) {
            //根据溯源码生成出库物品信息集合
            List<WarehouseGoodsDto> warehouseGoodsDtoList = warehouseOutReceiptVo.getGoodsVoList().stream().map(warehouseGoodsVo -> {
                //根据溯源码查询物品信息
                WarehouseGoodsDto goodsDto = new WarehouseGoodsDto();
                WarehouseGoodsTrace goodsTrace = iWarehouseGoodsTraceService.getOne(Wrappers.<WarehouseGoodsTrace>lambdaQuery()
                        .eq(WarehouseGoodsTrace::getSnCode, warehouseGoodsVo.getSnCode())
                        .eq(WarehouseGoodsTrace::getWarehouseId, outReceipt.getWarehouseId()));
                //通过溯源码获取物品入库时的信息
                WarehouseInReceiptDetail inReceiptDetail = iWarehouseInReceiptDetailService.getOne(Wrappers.<WarehouseInReceiptDetail>lambdaQuery()
                        .eq(WarehouseInReceiptDetail::getInReceiptId, goodsTrace.getInReceiptId())
                        .eq(WarehouseInReceiptDetail::getGoodsId, goodsTrace.getGoodsId()));
                goodsDto.setGoodsId(inReceiptDetail.getGoodsId());
                goodsDto.setGoodsPrice(inReceiptDetail.getGoodsPrice());
                goodsDto.setGoodsNumber(1);
                goodsDto.setSnCode(warehouseGoodsVo.getSnCode());
                goodsDto.setGoodsAmount(inReceiptDetail.getGoodsPrice());
                goodsDto.setSnValidate("Y");
                iWarehouseOutReceiptDetailService.update(Wrappers.<WarehouseOutReceiptDetail>lambdaUpdate()
                        .eq(WarehouseOutReceiptDetail::getOutReceiptId, outReceipt.getWarehouseOutReceiptId())
                        .eq(WarehouseOutReceiptDetail::getGoodsId, goodsDto.getGoodsId())
                        .set(WarehouseOutReceiptDetail::getGoodsAmount, goodsDto.getGoodsAmount())
                        .set(WarehouseOutReceiptDetail::getGoodsNumber, goodsDto.getGoodsNumber())
                        .set(WarehouseOutReceiptDetail::getGoodsPrice, goodsDto.getGoodsPrice()));
                return goodsDto;
            }).collect(Collectors.toList());
            //物品 出库
            outReceipt.setState(ReceiptState.COMPLETE.getCode());
            this.updateById(outReceipt);
            WarehouseOutReceiptDto warehouseOutReceiptDto = new WarehouseOutReceiptDto();
            BeanUtil.copyProperties(outReceipt, warehouseOutReceiptDto);
            warehouseOutReceiptDto.setReceiptGoodsList(warehouseGoodsDtoList);
            //执行出库逻辑，扣库存
            doOutReceipt(warehouseOutReceiptDto);
            //生成出库溯源记录
            addOutReceiptTraceRel(warehouseOutReceiptDto);
            //如果是调拨单生成的，需要创建调入仓库的入库单
            WarehouseReceiptRel receiptRel = iWarehouseReceiptRelService.getOne(Wrappers.<WarehouseReceiptRel>lambdaQuery().eq(WarehouseReceiptRel::getOutId, outReceipt.getWarehouseOutReceiptId()));
            if (receiptRel != null && receiptRel.getTransferId() > 0) {
                iWarehouseTransferReceiptService.doTransferInReceipt(receiptRel.getTransferId());
            }
            //如果是申请单生成的，需要创建要货仓库入库单
            WarehouseApplyReceipt applyReceipt = iWarehouseApplyReceiptService.getOne(Wrappers.<WarehouseApplyReceipt>lambdaQuery()
                    .eq(WarehouseApplyReceipt::getOutReceiptId, outReceipt.getWarehouseOutReceiptId()));
            if (applyReceipt != null) {
                iWarehouseInReceiptService.doApplyInReceipt(applyReceipt.getApplyReceiptId());
            }

        } else {
            //更新状态
            this.update(Wrappers.<WarehouseOutReceipt>lambdaUpdate()
                    .eq(WarehouseOutReceipt::getWarehouseOutReceiptId, warehouseOutReceiptVo.getWarehouseOutReceiptId())
                    .set(WarehouseOutReceipt::getState, ReceiptState.APPROVE.getCode()));
            //物品溯源信息
            for (WarehouseGoodsVo warehouseGoodsVo : warehouseOutReceiptVo.getGoodsVoList()) {
                if (warehouseGoodsVo.getSnValidate().equals("N")) {
                    continue;
                }
                WarehouseGoodsTrace goodsTrace = iWarehouseGoodsTraceService.getOne(Wrappers.<WarehouseGoodsTrace>lambdaQuery()
                        .eq(WarehouseGoodsTrace::getSnCode, warehouseGoodsVo.getSnCode()));
                goodsTrace.setTraceState(TraceState.OUT_WAREHOUSE_APPROVE.getCode());
                //更新出库状态
                iWarehouseGoodsTraceService.updateById(goodsTrace);
                //添加追踪关系表
                WarehouseGoodsTraceRel traceRel = new WarehouseGoodsTraceRel();
                traceRel.setTraceId(goodsTrace.getTraceId());
                traceRel.setGoodsId(warehouseGoodsVo.getGoodsId());
                traceRel.setRecordTime(DateTime.now());
                traceRel.setRecordType(ReceiptType.OutReceipt.getCode());
                traceRel.setRelId(warehouseOutReceiptVo.getWarehouseOutReceiptId());
                iWarehouseGoodsTraceRelService.save(traceRel);
            }
        }

        return true;
    }

    protected synchronized void cancelOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt) throws Exception {
        //修改库存
        for (WarehouseGoodsDto warehouseGoodsDto : warehouseOutReceipt.getReceiptGoodsList()) {

            //查询现有物品库存信息
            WarehouseStock warehouseStock = iWarehouseStockService.getOne(Wrappers.<WarehouseStock>lambdaQuery()
                    .eq(WarehouseStock::getWarehouseId, warehouseOutReceipt.getWarehouseId())
                    .eq(WarehouseStock::getGoodsId, warehouseGoodsDto.getGoodsId())
                    .eq(WarehouseStock::getGoodsPrice, warehouseGoodsDto.getGoodsPrice()));

            if (warehouseStock != null) {
                warehouseStock.setStockAmount(warehouseStock.getStockAmount().add(warehouseGoodsDto.getGoodsAmount()));
                warehouseStock.setStockNumber(warehouseStock.getStockNumber().add(new BigDecimal(warehouseGoodsDto.getGoodsNumber())));
                iWarehouseStockService.updateById(warehouseStock);
            } else {
                throw new Exception("物品不存在");
            }
        }
    }

    protected synchronized void doOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt) throws Exception {
        //修改库存
        for (WarehouseGoodsDto warehouseGoodsDto : warehouseOutReceipt.getReceiptGoodsList()) {
            //查询现有物品库存信息
            WarehouseStock warehouseStock = iWarehouseStockService.getOne(Wrappers.<WarehouseStock>lambdaQuery()
                    .eq(WarehouseStock::getWarehouseId, warehouseOutReceipt.getWarehouseId())
                    .eq(WarehouseStock::getGoodsId, warehouseGoodsDto.getGoodsId())
                    .eq(WarehouseStock::getGoodsPrice, warehouseGoodsDto.getGoodsPrice()));

            if (warehouseStock != null) {
                if (warehouseStock.getStockNumber().compareTo(new BigDecimal(warehouseGoodsDto.getGoodsNumber())) < 0) {
                    throw new Exception((warehouseGoodsDto.getGoodsName() == null ? "" : warehouseGoodsDto.getGoodsName()) + "物品库存不足");
                }
                warehouseStock.setStockAmount(warehouseStock.getStockAmount().subtract(warehouseGoodsDto.getGoodsAmount()));
                warehouseStock.setStockNumber(warehouseStock.getStockNumber().subtract(new BigDecimal(warehouseGoodsDto.getGoodsNumber())));
                iWarehouseStockService.updateById(warehouseStock);
            } else {
                throw new Exception("物品不存在");
            }
            //
        }
    }

    protected void updateOutReceiptTraceRel(WarehouseOutReceiptDto warehouseOutReceipt) {
        //更新物品溯源码记录
        //获取traceId
        List<Long> traceIds = iWarehouseGoodsTraceRelService.list(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                        .eq(WarehouseGoodsTraceRel::getRelId, warehouseOutReceipt.getWarehouseOutReceiptId())
                        .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.OutReceipt.getCode()))
                .stream().map(t -> t.getTraceId()).collect(Collectors.toList());

        traceIds.forEach(t -> {
            iWarehouseGoodsTraceService.update(Wrappers.<WarehouseGoodsTrace>lambdaUpdate()
                    .eq(WarehouseGoodsTrace::getTraceId, t)
                    .set(WarehouseGoodsTrace::getWarehouseId, null)
                    .set(WarehouseGoodsTrace::getTraceState, TraceState.OUT_WAREHOUSE.getCode()));
        });
    }

    private void addOutReceiptTraceRel(WarehouseOutReceiptDto warehouseOutReceipt) {
        for (WarehouseGoodsDto goodsDto : warehouseOutReceipt.getReceiptGoodsList()) {
            if (goodsDto.getSnValidate().equals("N")) {
                continue;
            }
            WarehouseGoodsTrace goodsTrace = iWarehouseGoodsTraceService.getOne(Wrappers.<WarehouseGoodsTrace>lambdaQuery()
                    .eq(WarehouseGoodsTrace::getSnCode, goodsDto.getSnCode()));
            goodsTrace.setTraceState(TraceState.OUT_WAREHOUSE.getCode());
            goodsTrace.setWarehouseId(warehouseOutReceipt.getWarehouseId());
            //更新出库状态
            iWarehouseGoodsTraceService.updateById(goodsTrace);
            //添加追踪关系表
            WarehouseGoodsTraceRel traceRel = new WarehouseGoodsTraceRel();
            traceRel.setTraceId(goodsTrace.getTraceId());
            traceRel.setGoodsId(goodsDto.getGoodsId());
            traceRel.setRecordTime(DateTime.now());
            traceRel.setRecordType(ReceiptType.OutReceipt.getCode());
            traceRel.setRelId(warehouseOutReceipt.getWarehouseOutReceiptId());
            iWarehouseGoodsTraceRelService.save(traceRel);
        }
    }
}
