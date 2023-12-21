package com.xiaoyai.warehouse.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.common.constant.WareHouseConstants;
import com.xiaoyai.common.utils.StringUtils;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.warehouse.mapper.WarehouseCodeMapper;
import com.xiaoyai.warehouse.domain.WarehouseCode;
import com.xiaoyai.warehouse.service.IWarehouseCodeService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仓库编号Service业务层处理
 *
 * @author junyang
 * @date 2023-09-08
 */
@Service
public class WarehouseCodeServiceImpl extends ServiceImpl<WarehouseCodeMapper, WarehouseCode> implements IWarehouseCodeService {
    @Autowired
    private WarehouseCodeMapper warehouseCodeMapper;


    /**
     * 新增 仓库编号
     *
     * @param warehouseCode 仓库编号
     * @return 结果
     */
    @Override
    public int insertWarehouseCode(WarehouseCode warehouseCode) {
        return warehouseCodeMapper.insertWarehouseCode(warehouseCode);
    }


    /**
     * 获取物品编号
     *
     * @param warehouseGoods
     * @return
     * @throws Exception
     */
    @Override
    public synchronized String getGoodsCode(WarehouseGoods warehouseGoods) throws Exception {
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.GOODS_CODE_TYPE, warehouseGoods.getCategoryCode(), 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}-{}", warehouseGoods.getCategoryCode(), newCodeStr);

    }

    /**
     * 获取入库单编号
     *
     * @return
     */
    @Override
    public String getInReceiptCode() {
        String codeName = WareHouseConstants.IN_RECEIPT_CODE_NAME + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.IN_RECEIPT_CODE_TYPE, codeName, 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}{}", codeName, newCodeStr);
    }

    /**
     * 生成物品溯源码
     *
     * @param goods
     * @return
     */
    @Override
    public List<String> getGoodSnCodes(WarehouseGoodsDto goods) {
        String goodCategory = goods.getCategoryCode();
        String codeName = goodCategory + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<Integer> snCodes = getWarehouseCode(WareHouseConstants.GOODS_SN_CODE, codeName, goods.getGoodsNumber());
        return snCodes.stream().map(
                t -> StrUtil.format("{}{}", codeName, StrUtil.padPre(String.valueOf(t), 4, '0'))).collect(Collectors.toList());

    }

    @Override
    public String getOutReceiptCode() {
        String codeName = WareHouseConstants.OUT_RECEIPT_CODE_NAME + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.OUT_RECEIPT_CODE_TYPE, codeName, 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}{}", codeName, newCodeStr);
    }

    @Override
    public String getTransferCode() {
        String codeName = WareHouseConstants.TRANSFER_RECEIPT_CODE_NAME + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.TRANSFER_RECEIPT_CODE_TYPE, codeName, 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}{}", codeName, newCodeStr);
    }

    @Override
    public String getApplyCode() {
        String codeName = WareHouseConstants.APPLY_RECEIPT_CODE_NAME + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.APPLY_RECEIPT_CODE_TYPE, codeName, 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}{}", codeName, newCodeStr);
    }

    @Override
    public String getCheckCode() {
        String codeName = WareHouseConstants.CHECK_RECEIPT_CODE_NAME + "-" + DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.CHECK_RECEIPT_CODE_TYPE, codeName, 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}{}", codeName, newCodeStr);
    }
    @Override
    public String getWarehouseCode() {
        String codeName = WareHouseConstants.WAREHOUSE_CODE_NAME;
        List<Integer> newCode = getWarehouseCode(WareHouseConstants.WAREHOUSE_CODE_TYPE, codeName, 1);
        String newCodeStr = StrUtil.padPre(String.valueOf(newCode.get(0)), 4, '0');
        return StrUtil.format("{}{}", codeName, newCodeStr);
    }


    /**
     * 根据类型和名称返回编码
     *
     * @param codeType
     * @param codeName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    protected synchronized List<Integer> getWarehouseCode(String codeType, String codeName, int number) {
        List<Integer> codeList = new ArrayList<>();
        int startCode = 1;
        int endCode = 1;
        WarehouseCode code = this.getOne(Wrappers.<WarehouseCode>lambdaQuery().eq(WarehouseCode::getCodeType, codeType)
                .eq(WarehouseCode::getCodeName, codeName));
        if (code == null) {
            endCode += number;
            WarehouseCode inCode = new WarehouseCode();
            inCode.setCodeName(codeName);
            inCode.setCodeType(codeType);
            inCode.setCodeNumber(endCode);
            this.insertWarehouseCode(inCode);
        } else {
            startCode = code.getCodeNumber();
            endCode = code.getCodeNumber() + number;
            this.update(Wrappers.<WarehouseCode>lambdaUpdate().eq(WarehouseCode::getCodeType, codeType)
                    .eq(WarehouseCode::getCodeName, codeName)
                    .set(WarehouseCode::getCodeNumber, endCode));
        }
        for (int i = startCode; i < endCode; i++) {
            codeList.add(i);
        }
        return codeList;
    }
}
