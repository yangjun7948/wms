package com.xiaoyai.warehouse.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.xiaoyai.common.annotation.WarehouseScope;
import com.xiaoyai.common.constant.PrintConst;
import com.xiaoyai.common.constant.WareHouseConstants;
import com.xiaoyai.common.core.domain.JasperTableSource;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.common.enums.warehouse.*;
import com.xiaoyai.common.utils.DictUtils;
import com.xiaoyai.common.utils.PrintUtils;
import com.xiaoyai.warehouse.domain.*;
import com.xiaoyai.warehouse.domain.dto.ReceiptDetailFields;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseInReceiptDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;
import com.xiaoyai.warehouse.domain.vo.WarehouseInReceiptVo;
import com.xiaoyai.warehouse.service.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseInReceiptMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入库单据Service业务层处理
 *
 * @author junyang
 * @date 2023-09-09
 */
@Service
public class WarehouseInReceiptServiceImpl extends ServiceImpl<WarehouseInReceiptMapper, WarehouseInReceipt> implements IWarehouseInReceiptService {
    @Autowired
    private WarehouseInReceiptMapper warehouseInReceiptMapper;
    @Autowired
    private IWarehouseInReceiptDetailService iWarehouseInReceiptDetailService;
    @Autowired
    private IWarehouseCodeService iWarehouseCodeService;
    @Autowired
    private IWarehouseGoodsTraceService iWarehouseGoodsTraceService;
    @Autowired
    private IWarehouseGoodsTraceRelService iWarehouseGoodsTraceRelService;
    @Autowired
    private IWarehouseService iWarehouseService;
    @Autowired
    private IWarehouseSupplierService iWarehouseSupplierService;

    @Autowired
    private IWarehouseStockService iWarehouseStockService;
    @Autowired
    private IWarehouseTransferReceiptService iWarehouseTransferReceiptService;

    @Autowired
    private IWarehouseOutReceiptService iWarehouseOutReceiptService;
    @Autowired
    private IWarehouseApplyReceiptService iWarehouseApplyReceiptService;
    @Autowired
    private IWarehouseOutReceiptDetailService iWarehouseOutReceiptDetailService;

    /**
     * 查询入库单据
     *
     * @param warehouseInReceiptId 入库单据主键
     * @return 入库单据
     */
    @Override
    public WarehouseInReceiptDto selectWarehouseInReceiptByWarehouseInReceiptId(Long warehouseInReceiptId) {
        WarehouseInReceiptDto warehouseInReceiptDto = new WarehouseInReceiptDto();
        WarehouseInReceipt inReceipt = this.getOne(Wrappers.<WarehouseInReceipt>lambdaQuery().eq(WarehouseInReceipt::getWarehouseInReceiptId, warehouseInReceiptId));
        BeanUtil.copyProperties(inReceipt, warehouseInReceiptDto);
        warehouseInReceiptDto.setInWarehouseTypeName(DictUtils.getDictLabel(DictCodes.IN_RECEIPT_TYPE.getCode(), inReceipt.getInWarehouseType()));
        //获取物品明细
        List<WarehouseGoodsDto> warehouseInReceiptDetailList = iWarehouseInReceiptDetailService.getInReceiptGoodsList(warehouseInReceiptId);

        warehouseInReceiptDto.setReceiptGoodsList(warehouseInReceiptDetailList);
        return warehouseInReceiptDto;
    }

    /**
     * 查询入库单据列表
     *
     * @param warehouseInReceipt 入库单据
     * @return 入库单据
     */
    @Override
    @WarehouseScope(warehouseAlias = "warehouse_in_receipt")
    public List<WarehouseInReceipt> selectWarehouseInReceiptList(WarehouseInReceipt warehouseInReceipt) {
        return warehouseInReceiptMapper.selectWarehouseInReceiptList(warehouseInReceipt);
    }

    /**
     * 新增入库单据，默认草稿状态
     *
     * @param warehouseInReceipt 入库单据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt) {
        String submitState = warehouseInReceipt.getState();
        warehouseInReceipt.setInWarehouseTime(DateTime.now());
        WarehouseInReceipt inReceipt = new WarehouseInReceipt();
        BeanUtil.copyProperties(warehouseInReceipt, inReceipt);
        //保存入库单
        if (warehouseInReceipt.getState().equals(ReceiptState.SCAN.getCode())
                && (warehouseInReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0
                || (warehouseInReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0))) {
            inReceipt.setState(ReceiptState.APPROVE.getCode());
        }
        this.baseMapper.insertWarehouseInReceipt(inReceipt);
        //保存入库物品明细
        List<WarehouseInReceiptDetail> inReceiptDetailList = warehouseInReceipt.getReceiptGoodsList().stream().map(t -> {
            WarehouseInReceiptDetail detail = new WarehouseInReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setInReceiptId(inReceipt.getWarehouseInReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseInReceiptDetailService.saveBatch(inReceiptDetailList);
        //todo 保存入库单价
        //待审核状态提交生成溯源码
        if (submitState.equals(ReceiptState.SCAN.getCode())) {
            //判断重点物资，生成溯源码
            for (WarehouseGoodsDto warehouseGoodsDto : warehouseInReceipt.getReceiptGoodsList()) {
                if (warehouseGoodsDto.getSnValidate().equals(ValidateState.YES.getCode())) {
                    List<String> snCodeList = iWarehouseCodeService.getGoodSnCodes(warehouseGoodsDto);
                    List<WarehouseGoodsTrace> goodsTraceList = snCodeList.stream().map(sn -> {
                        WarehouseGoodsTrace trace = new WarehouseGoodsTrace();
                        trace.setGoodsId(warehouseGoodsDto.getGoodsId());
                        trace.setSnCode(sn);
                        trace.setTraceState(TraceState.NEED_SCAN.getCode());
                        trace.setWarehouseId(warehouseInReceipt.getWarehouseId());
                        trace.setInReceiptId(inReceipt.getWarehouseInReceiptId());
                        return trace;
                    }).collect(Collectors.toList());
                    iWarehouseGoodsTraceService.saveBatch(goodsTraceList);
                    List<WarehouseGoodsTraceRel> relList = goodsTraceList.stream().map(trace -> {
                        WarehouseGoodsTraceRel rel = new WarehouseGoodsTraceRel();
                        rel.setTraceId(trace.getTraceId());
                        rel.setRecordTime(DateTime.now());
                        rel.setGoodsId(warehouseGoodsDto.getGoodsId());
                        rel.setRecordType(ReceiptType.InReceipt.getCode());
                        rel.setRelId(inReceipt.getWarehouseInReceiptId());
                        rel.setRecordTime(DateTime.now());
                        return rel;
                    }).collect(Collectors.toList());
                    iWarehouseGoodsTraceRelService.saveBatch(relList);
                }
            }
        }

        return true;
    }

    /**
     * 修改入库单据
     *
     * @param warehouseInReceipt 入库单据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt) {
        String submitState = warehouseInReceipt.getState();
        WarehouseInReceipt inReceipt = new WarehouseInReceipt();
        BeanUtil.copyProperties(warehouseInReceipt, inReceipt);
        //保存入库单
        if (warehouseInReceipt.getState().equals(ReceiptState.SCAN.getCode())
                && (warehouseInReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnValidate().equals(ValidateState.YES.getCode())).count() == 0
                || (warehouseInReceipt.getReceiptGoodsList().stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0))) {
            inReceipt.setState(ReceiptState.APPROVE.getCode());
        }
        this.updateById(inReceipt);
        //先删除入库物品明细
        iWarehouseInReceiptDetailService.remove(Wrappers.<WarehouseInReceiptDetail>lambdaQuery()
                .eq(WarehouseInReceiptDetail::getInReceiptId, warehouseInReceipt.getWarehouseInReceiptId()));
        //保存新的物品明细
        List<WarehouseInReceiptDetail> inReceiptDetailList = warehouseInReceipt.getReceiptGoodsList().stream().map(t -> {
            WarehouseInReceiptDetail detail = new WarehouseInReceiptDetail();
            BeanUtil.copyProperties(t, detail);
            detail.setInReceiptId(warehouseInReceipt.getWarehouseInReceiptId());
            return detail;
        }).collect(Collectors.toList());
        iWarehouseInReceiptDetailService.saveBatch(inReceiptDetailList);
        //待审核状态提交生成溯源码
        if (warehouseInReceipt.getState().equals(ReceiptState.SCAN.getCode())) {
            //判断重点物资，生成溯源码
            for (WarehouseGoodsDto warehouseGoodsDto : warehouseInReceipt.getReceiptGoodsList()) {
                if (warehouseGoodsDto.getSnValidate().equals(ValidateState.YES.getCode())) {
                    List<String> snCodeList = iWarehouseCodeService.getGoodSnCodes(warehouseGoodsDto);
                    List<WarehouseGoodsTrace> goodsTraceList = snCodeList.stream().map(sn -> {
                        WarehouseGoodsTrace trace = new WarehouseGoodsTrace();
                        trace.setGoodsId(warehouseGoodsDto.getGoodsId());
                        trace.setSnCode(sn);
                        trace.setInReceiptId(inReceipt.getWarehouseInReceiptId());
                        return trace;
                    }).collect(Collectors.toList());
                    iWarehouseGoodsTraceService.saveBatch(goodsTraceList);
                    List<WarehouseGoodsTraceRel> relList = goodsTraceList.stream().map(trace -> {
                        WarehouseGoodsTraceRel rel = new WarehouseGoodsTraceRel();
                        rel.setTraceId(trace.getTraceId());
                        rel.setRecordTime(DateTime.now());
                        rel.setRecordType(ReceiptType.InReceipt.getCode());
                        rel.setRelId(inReceipt.getWarehouseInReceiptId());
                        rel.setRecordTime(DateTime.now());
                        return rel;
                    }).collect(Collectors.toList());
                    iWarehouseGoodsTraceRelService.saveBatch(relList);
                }
            }

        }

        return true;
    }

    /**
     * 批量删除入库单据
     *
     * @param warehouseInReceiptIds 需要删除的入库单据主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseInReceiptByWarehouseInReceiptIds(Long[] warehouseInReceiptIds) {
        return warehouseInReceiptMapper.deleteWarehouseInReceiptByWarehouseInReceiptIds(warehouseInReceiptIds);
    }

    /**
     * 删除入库单据信息
     *
     * @param warehouseInReceiptId 入库单据主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteWarehouseInReceiptByWarehouseInReceiptId(Long warehouseInReceiptId) {
        this.removeById(warehouseInReceiptId);
        iWarehouseInReceiptDetailService.remove(Wrappers.<WarehouseInReceiptDetail>lambdaQuery()
                .eq(WarehouseInReceiptDetail::getInReceiptId, warehouseInReceiptId));
        iWarehouseGoodsTraceService.remove(Wrappers.<WarehouseGoodsTrace>lambdaQuery()
                .eq(WarehouseGoodsTrace::getInReceiptId, warehouseInReceiptId));
        iWarehouseGoodsTraceRelService.remove(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                .eq(WarehouseGoodsTraceRel::getRelId, warehouseInReceiptId)
                .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.InReceipt.getCode()));
        return true;
    }

    /**
     * 生成入库单PDF
     *
     * @param warehouseInReceiptId
     * @return
     */
    @Override
    public byte[] inReceiptPdf(Long warehouseInReceiptId) {
        Map printParams = new HashMap<>();
        WarehouseInReceipt inReceipt = this.getById(warehouseInReceiptId);
        List<WarehouseGoodsDto> warehouseInReceiptDetailList = iWarehouseInReceiptDetailService.getInReceiptGoodsList(warehouseInReceiptId);
        Warehouse warehouse = iWarehouseService.getById(inReceipt.getWarehouseId());
        if (!StrUtil.isBlankIfStr(inReceipt.getSupplierId())) {
            WarehouseSupplier supplier = iWarehouseSupplierService.getById(inReceipt.getSupplierId());
            printParams.put("supplierName", supplier.getSupplierName());
        }
        printParams.put("receiptName", "入库单");
        printParams.put("warehouseName", warehouse.getWarehouseName());
        printParams.put("inWarehouseTypeName", DictUtils.getDictLabel(DictCodes.IN_RECEIPT_TYPE.getCode(), inReceipt.getInWarehouseType()));
        printParams.put("documentNumber", inReceipt.getDocumentNumber());
        printParams.put("inWarehouseTime", DateUtil.format(inReceipt.getInWarehouseTime(), "yyyy-MM-dd HH:mm:ss"));

        List<ReceiptDetailFields> receiptDetailFieldsList = warehouseInReceiptDetailList.stream().map(t -> {
            ReceiptDetailFields receiptDetailFields = new ReceiptDetailFields();
            receiptDetailFields.setGoodsCode(t.getGoodsCode().toString());
            receiptDetailFields.setGoodsName(t.getGoodsName());
            receiptDetailFields.setSpecification(DictUtils.getDictLabel(DictCodes.GOODS_SPECIFICATION.getCode(), t.getSpecificationType().toString()));
            receiptDetailFields.setUnitMeasurementName(DictUtils.getDictLabel(DictCodes.GOODS_UNIT.getCode(), t.getUnitType().toString()));
            receiptDetailFields.setUnitPrice(t.getGoodsPrice().toString());
            receiptDetailFields.setGoodsCount(t.getGoodsNumber().toString());
            receiptDetailFields.setGoodsAmount(t.getGoodsAmount().toString());
            receiptDetailFields.setGoodsRemark(t.getRemark());
            return receiptDetailFields;
        }).collect(Collectors.toList());
        JasperTableSource jts = new JasperTableSource();
        jts.setTableData(new JRBeanCollectionDataSource(receiptDetailFieldsList));
        List<JasperTableSource> mlist = new ArrayList<JasperTableSource>();
        mlist.add(jts);
        printParams.put("totalCount", warehouseInReceiptDetailList.stream().map(t -> t.getGoodsNumber()).reduce(0, (a, b) -> a + b).toString());
        printParams.put("totalAmount", warehouseInReceiptDetailList.stream().map(t -> t.getGoodsAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)).toString());
        return PrintUtils.exportPdf(PrintUtils.getExportPrint(PrintConst.IN_RECEIPT_TPL, printParams, mlist)).toByteArray();
    }

    /**
     * 入库单据审核
     *
     * @param warehouseInReceipt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean approveWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt) throws Exception {
        //物品入库
        WarehouseInReceipt inReceipt = this.getById(warehouseInReceipt.getWarehouseInReceiptId());
        if (!inReceipt.getState().equals(ReceiptState.APPROVE.getCode())) {
            throw new Exception("当前单据状态不是待审核状态，请检查！");
        }
        this.doApprove(inReceipt);
        return true;
    }

    /**
     * 入库单据取消审核
     *
     * @param warehouseInReceipt
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unApproveWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt) {
        //物品入库
        WarehouseInReceipt inReceipt = new WarehouseInReceipt();
        BeanUtil.copyProperties(inReceipt, warehouseInReceipt);
        inReceipt.setState(ReceiptState.SCAN.getCode());
        this.updateById(inReceipt);
        warehouseInReceipt.setReceiptGoodsList(iWarehouseInReceiptDetailService.getInReceiptGoodsList(warehouseInReceipt.getWarehouseInReceiptId()));
        cancelInReceipt(warehouseInReceipt);
        return true;
    }

    /**
     * 获取待扫码入库单
     *
     * @return
     */
    @Override
    @WarehouseScope(warehouseAlias = "a")
    public PageInfo<WarehouseInReceiptVo> getInReceiptScanList(WarehouseInReceipt warehouseInReceipt) {
        List<WarehouseInReceiptVo> warehouseInReceiptVoList = this.baseMapper.getInReceiptScanList(warehouseInReceipt);
        PageInfo<WarehouseInReceiptVo> pageInfo = new PageInfo<>(warehouseInReceiptVoList);
        pageInfo.setList(warehouseInReceiptVoList.stream().map(vo -> {
            vo.setInWarehouseTypeName(DictUtils.getDictLabel(DictCodes.IN_RECEIPT_TYPE.getCode(), vo.getInWarehouseType()));
            return vo;
        }).collect(Collectors.toList()));
        return pageInfo;
    }

    /**
     * 获取待入库扫码单据明细
     *
     * @param receiptId
     * @return
     */
    @Override
    public WarehouseInReceiptVo getInReceiptScanDetail(Long receiptId) {
        WarehouseInReceiptVo warehouseInReceiptVo = this.baseMapper.getInReceiptScanDetail(receiptId);
        warehouseInReceiptVo.setInWarehouseTypeName(DictUtils.getDictLabel(DictCodes.IN_RECEIPT_TYPE.getCode(), warehouseInReceiptVo.getInWarehouseType()));
        //查询物品明细
        //获取物品明细
        List<WarehouseGoodsVo> warehouseInReceiptDetailList = iWarehouseInReceiptDetailService.getInReceiptGoodsListWithSnCode(receiptId);
        warehouseInReceiptVo.setGoodsVoList(warehouseInReceiptDetailList);
        return warehouseInReceiptVo;
    }

    @Override
    @Transactional
    public boolean saveInReceiptScanDetail(WarehouseInReceiptVo warehouseInReceiptVo) throws Exception {
        //判断当前状态
        WarehouseInReceipt inReceipt = this.getById(warehouseInReceiptVo.getWarehouseInReceiptId());
        Warehouse warehouse = iWarehouseService.getById(inReceipt.getWarehouseId());
        if (warehouse != null && warehouse.getWarehouseType().equals(WarehouseType.FEIXIUSHIFU.getCode())) {
            //个人仓库，直接进行审核
            inReceipt.setState(ReceiptState.APPROVE.getCode());
            this.doApprove(inReceipt);
        }
        //公司仓库需要进行一下审核
        else {
            if (!inReceipt.getState().equals(ReceiptState.SCAN.getCode())) {
                throw new Exception("当前单据状态不是待扫码状态，请检查！");
            }
            for (WarehouseGoodsVo warehouseGoodsVo : warehouseInReceiptVo.getGoodsVoList()) {
                if (ValidateState.YES.getCode().equals(warehouseGoodsVo.getSnValidate()) && !warehouseGoodsVo.getScanSuccess()) {
                    throw new Exception("物品未扫码完成，请检查！");
                }
            }
            this.update(Wrappers.<WarehouseInReceipt>lambdaUpdate()
                    .eq(WarehouseInReceipt::getWarehouseInReceiptId, warehouseInReceiptVo.getWarehouseInReceiptId())
                    .set(WarehouseInReceipt::getState, ReceiptState.APPROVE.getCode()));

            List<Long> traceIds = iWarehouseGoodsTraceRelService.list(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                            .eq(WarehouseGoodsTraceRel::getRelId, warehouseInReceiptVo.getWarehouseInReceiptId())
                            .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.InReceipt.getCode()))
                    .stream().map(t -> t.getTraceId()).collect(Collectors.toList());

            traceIds.forEach(t -> {
                iWarehouseGoodsTraceService.update(Wrappers.<WarehouseGoodsTrace>lambdaUpdate()
                        .eq(WarehouseGoodsTrace::getTraceId, t)
                        .set(WarehouseGoodsTrace::getTraceState, TraceState.IN_WAREHOUSE_APPROVE.getCode()));
            });
        }
        return true;

    }

    /**
     * 创建申请入库单
     *
     * @param applyReceiptId
     */
    @Override
    @Transactional
    public void doApplyInReceipt(Long applyReceiptId) {
        WarehouseApplyReceipt applyReceipt = iWarehouseApplyReceiptService.getById(applyReceiptId);
        if (applyReceipt.getOutReceiptId() != null && applyReceipt.getOutReceiptId() > 0) {
            //更新申请单状态
            applyReceipt.setState(ApplyState.INING.getCode());
            iWarehouseApplyReceiptService.updateById(applyReceipt);
            //获取物品明细
            List<WarehouseGoodsDto> warehouseOutReceiptDetailGoodsList =
                    iWarehouseOutReceiptDetailService.getOutReceiptGoodsList(applyReceipt.getOutReceiptId());
            //处理入库逻辑
            WarehouseInReceipt inReceipt = new WarehouseInReceipt();
            inReceipt.setWarehouseId(applyReceipt.getApplyWarehouseId());
            inReceipt.setDocumentNumber(iWarehouseCodeService.getInReceiptCode());
            inReceipt.setInWarehouseTime(DateTime.now());
            inReceipt.setInWarehouseType(WareHouseConstants.APPLY_IN_TYPE_CODE);
            inReceipt.setState(ReceiptState.SCAN.getCode());
            if (warehouseOutReceiptDetailGoodsList.stream().filter(t -> t.getSnCodeValidate() == null || !t.getSnCodeValidate()).count() == 0) {
                inReceipt.setState(ReceiptState.APPROVE.getCode());
            }
            inReceipt.setCreateBy(applyReceipt.getAuditName());
            inReceipt.setCreateTime(new Date());
            this.save(inReceipt);
            //保存入库物品明细
            List<WarehouseInReceiptDetail> inReceiptDetailList = warehouseOutReceiptDetailGoodsList.stream().map(t -> {
                WarehouseInReceiptDetail detail = new WarehouseInReceiptDetail();
                BeanUtil.copyProperties(t, detail);
                detail.setInReceiptId(inReceipt.getWarehouseInReceiptId());
                return detail;
            }).collect(Collectors.toList());
            iWarehouseInReceiptDetailService.saveBatch(inReceiptDetailList);
            // 更新入库关系表中的入库单号
            iWarehouseApplyReceiptService.update(Wrappers.<WarehouseApplyReceipt>lambdaUpdate()
                    .eq(WarehouseApplyReceipt::getApplyReceiptId, applyReceiptId)
                    .set(WarehouseApplyReceipt::getInReceiptId, inReceipt.getWarehouseInReceiptId())
                    .set(WarehouseApplyReceipt::getInDocumentNumber, inReceipt.getDocumentNumber()));
            // 生成溯源码记录,通过出库单id确定出库的物品溯源码信息
            List<WarehouseGoodsTraceRel> traceRels = iWarehouseGoodsTraceRelService.list(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                    .eq(WarehouseGoodsTraceRel::getRelId, applyReceipt.getOutReceiptId())
                    .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.OutReceipt.getCode()));
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
    }

    protected void doApprove(WarehouseInReceipt inReceipt) {
        WarehouseInReceiptDto warehouseInReceipt = new WarehouseInReceiptDto();
        BeanUtil.copyProperties(inReceipt, warehouseInReceipt);
        inReceipt.setState(ReceiptState.COMPLETE.getCode());
        this.updateById(inReceipt);
        warehouseInReceipt.setReceiptGoodsList(iWarehouseInReceiptDetailService.getInReceiptGoodsList(warehouseInReceipt.getWarehouseInReceiptId()));
        doInReceipt(warehouseInReceipt);
        //如果是调拨单生成，修改调拨单状态
        iWarehouseTransferReceiptService.updateTransferByInReceiptId(warehouseInReceipt.getWarehouseInReceiptId());
        //如果是要货申请，修改要货申请单状态
        iWarehouseApplyReceiptService.updateApplyReceiptByInReceiptId(warehouseInReceipt.getWarehouseInReceiptId());
    }

    /**
     * 增加库存操作
     *
     * @param warehouseInReceipt
     */
    protected synchronized void doInReceipt(WarehouseInReceiptDto warehouseInReceipt) {
        //修改库存
        for (WarehouseGoodsDto warehouseGoodsDto : warehouseInReceipt.getReceiptGoodsList()) {
            Date deadLineTime = DateUtil.parseDate("2099-12-31 00:00:00");
            //todo 取消有效期，后续需要改成批次管理
            //有效期天数小于等于0默认长期有效
//            if (warehouseGoodsDto.getExpirationDate() <= 0) {
//                deadLineTime = DateUtil.parseDate("2099-12-31 00:00:00");
//            } else {
//                Date inWareHouseTime = DateUtil.parseDate(DateUtil.format(warehouseInReceipt.getInWarehouseTime(), "yyyy-MM-dd"));
//                //年
//                if (warehouseGoodsDto.getExpirationDateType() == 1) {
//                    deadLineTime = DateUtil.offset(inWareHouseTime, DateField.YEAR, warehouseGoodsDto.getExpirationDate());
//                }
//                //月
//                else if (warehouseGoodsDto.getExpirationDateType() == 2) {
//                    deadLineTime = DateUtil.offset(inWareHouseTime, DateField.MONTH, warehouseGoodsDto.getExpirationDate());
//                }
//                //日
//                else if (warehouseGoodsDto.getExpirationDateType() == 3) {
//                    deadLineTime = DateUtil.offset(inWareHouseTime, DateField.DAY_OF_MONTH, warehouseGoodsDto.getExpirationDate());
//                } else {
//                    deadLineTime = DateUtil.offset(inWareHouseTime, DateField.DAY_OF_MONTH, warehouseGoodsDto.getExpirationDate());
//                }
//            }
            //查询现有物品库存信息
            WarehouseStock warehouseStock = iWarehouseStockService.getOne(Wrappers.<WarehouseStock>lambdaQuery()
                    .eq(WarehouseStock::getWarehouseId, warehouseInReceipt.getWarehouseId())
                    .eq(WarehouseStock::getGoodsId, warehouseGoodsDto.getGoodsId())
                    .eq(WarehouseStock::getGoodsPrice, warehouseGoodsDto.getGoodsPrice())
                    .eq(WarehouseStock::getGoodsDeadlineTime, deadLineTime));

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
                warehouseStock.setWarehouseId(warehouseInReceipt.getWarehouseId());
                warehouseStock.setGoodsDeadlineTime(deadLineTime);
                iWarehouseStockService.save(warehouseStock);
            }
            //更新物品溯源码记录
            //获取traceId
            List<Long> traceIds = iWarehouseGoodsTraceRelService.list(Wrappers.<WarehouseGoodsTraceRel>lambdaQuery()
                            .eq(WarehouseGoodsTraceRel::getRelId, warehouseInReceipt.getWarehouseInReceiptId())
                            .eq(WarehouseGoodsTraceRel::getRecordType, ReceiptType.InReceipt.getCode()))
                    .stream().map(t -> t.getTraceId()).collect(Collectors.toList());

            traceIds.forEach(t -> {
                iWarehouseGoodsTraceService.update(Wrappers.<WarehouseGoodsTrace>lambdaUpdate()
                        .eq(WarehouseGoodsTrace::getTraceId, t)
                        .set(WarehouseGoodsTrace::getWarehouseId, warehouseInReceipt.getWarehouseId())
                        .set(WarehouseGoodsTrace::getTraceState, TraceState.IN_WAREHOUSE.getCode()));
            });
        }
    }

    protected synchronized void cancelInReceipt(WarehouseInReceiptDto warehouseInReceipt) {
        //修改库存
        for (WarehouseGoodsDto warehouseGoodsDto : warehouseInReceipt.getReceiptGoodsList()) {
            Date deadLineTime;
            //有效期天数小于等于0默认长期有效
            if (warehouseGoodsDto.getExpirationDate() <= 0) {
                deadLineTime = DateUtil.parseDate("2099-12-31 00:00:00");
            } else {
                //年
                if (warehouseGoodsDto.getExpirationDateType() == 1) {
                    deadLineTime = DateUtil.offset(warehouseInReceipt.getInWarehouseTime(), DateField.YEAR, warehouseGoodsDto.getExpirationDate());
                }
                //月
                else if (warehouseGoodsDto.getExpirationDateType() == 2) {
                    deadLineTime = DateUtil.offset(warehouseInReceipt.getInWarehouseTime(), DateField.MONTH, warehouseGoodsDto.getExpirationDate());
                }
                //日
                else if (warehouseGoodsDto.getExpirationDateType() == 3) {
                    deadLineTime = DateUtil.offset(warehouseInReceipt.getInWarehouseTime(), DateField.DAY_OF_MONTH, warehouseGoodsDto.getExpirationDate());
                } else {
                    deadLineTime = DateUtil.offset(warehouseInReceipt.getInWarehouseTime(), DateField.DAY_OF_MONTH, warehouseGoodsDto.getExpirationDate());
                }
            }
            //查询现有物品库存信息
            WarehouseStock warehouseStock = iWarehouseStockService.getOne(Wrappers.<WarehouseStock>lambdaQuery()
                    .eq(WarehouseStock::getWarehouseId, warehouseInReceipt.getWarehouseId())
                    .eq(WarehouseStock::getGoodsId, warehouseGoodsDto.getGoodsId())
                    .eq(WarehouseStock::getGoodsPrice, warehouseGoodsDto.getGoodsPrice())
                    .eq(WarehouseStock::getGoodsDeadlineTime, deadLineTime));

            if (warehouseStock != null) {
                warehouseStock.setStockAmount(warehouseStock.getStockAmount().subtract(warehouseGoodsDto.getGoodsAmount()));
                warehouseStock.setStockNumber(warehouseStock.getStockNumber().subtract(new BigDecimal(warehouseGoodsDto.getGoodsNumber())));
                iWarehouseStockService.updateById(warehouseStock);
            } else {
                warehouseStock = new WarehouseStock();
                warehouseStock.setStockAmount(warehouseGoodsDto.getGoodsAmount().negate());
                warehouseStock.setStockNumber(new BigDecimal(warehouseGoodsDto.getGoodsNumber()).negate());
                warehouseStock.setGoodsPrice(warehouseGoodsDto.getGoodsPrice());
                warehouseStock.setGoodsId(warehouseGoodsDto.getGoodsId());
                warehouseStock.setWarehouseId(warehouseInReceipt.getWarehouseId());
                warehouseStock.setGoodsDeadlineTime(deadLineTime);
                iWarehouseStockService.save(warehouseStock);
            }
        }
    }
}
