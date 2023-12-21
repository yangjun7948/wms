package com.xiaoyai.warehouse.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.xiaoyai.common.constant.PrintConst;
import com.xiaoyai.common.enums.warehouse.ReceiptType;
import com.xiaoyai.common.enums.warehouse.TraceState;
import com.xiaoyai.common.utils.DateUtils;
import com.xiaoyai.common.utils.DictUtils;
import com.xiaoyai.common.utils.PrintUtils;
import com.xiaoyai.common.utils.barcode.BarCodeUtils;
import com.xiaoyai.warehouse.domain.*;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsQueryDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsTraceDto;
import com.xiaoyai.warehouse.domain.dto.WarehouseStockGoodsDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseGoodsVo;
import com.xiaoyai.warehouse.mapper.WarehouseCodeMapper;
import com.xiaoyai.warehouse.service.IWarehouseCodeService;
import com.xiaoyai.warehouse.service.IWarehouseGoodsCategoryService;
import com.xiaoyai.warehouse.service.IWarehouseGoodsTraceService;
import lombok.SneakyThrows;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import com.xiaoyai.warehouse.mapper.WarehouseGoodsMapper;
import com.xiaoyai.warehouse.service.IWarehouseGoodsService;

import javax.imageio.ImageIO;

/**
 * 物品Service业务层处理
 *
 * @author junyang
 * @date 2023-09-07
 */
@Service
public class WarehouseGoodsServiceImpl extends ServiceImpl<WarehouseGoodsMapper, WarehouseGoods> implements IWarehouseGoodsService {
    @Autowired
    private WarehouseGoodsMapper warehouseGoodsMapper;

    @Autowired
    private IWarehouseCodeService warehouseCodeService;
    @Autowired
    private IWarehouseGoodsCategoryService warehouseGoodsCategoryService;
    @Autowired
    private IWarehouseGoodsTraceService iWarehouseGoodsTraceService;

    /**
     * 查询物品
     *
     * @param goodsId 物品主键
     * @return 物品
     */
    @Override
    public WarehouseGoods selectWarehouseGoodsByGoodsId(Long goodsId) {
        return warehouseGoodsMapper.selectWarehouseGoodsByGoodsId(goodsId);
    }


    /**
     * 查询物品列表
     *
     * @param warehouseGoods 物品
     * @return 物品
     */
    @Override
    public List<WarehouseGoods> selectWarehouseGoodsList(WarehouseGoodsQueryDto warehouseGoods) {
        return warehouseGoodsMapper.selectWarehouseGoodsList(warehouseGoods);
    }

    /**
     * 新增物品
     *
     * @param warehouseGoods 物品
     * @return 结果
     */
    @Override
    public int insertWarehouseGoods(WarehouseGoods warehouseGoods) throws Exception {
        WarehouseGoodsCategory category = warehouseGoodsCategoryService.getOne(Wrappers.<WarehouseGoodsCategory>lambdaQuery()
                .eq(WarehouseGoodsCategory::getCategoryId, warehouseGoods.getCategoryId()));
        warehouseGoods.setCategoryCode(category.getCategoryCode());
        warehouseGoods.setCreateTime(DateUtils.getNowDate());
        warehouseGoods.setGoodsCode(warehouseCodeService.getGoodsCode(warehouseGoods));
        return warehouseGoodsMapper.insertWarehouseGoods(warehouseGoods);
    }

    /**
     * 修改物品
     *
     * @param warehouseGoods 物品
     * @return 结果
     */
    @Override
    public int updateWarehouseGoods(WarehouseGoods warehouseGoods) {
        warehouseGoods.setUpdateTime(DateUtils.getNowDate());
        return warehouseGoodsMapper.updateWarehouseGoods(warehouseGoods);
    }

    /**
     * 批量删除物品
     *
     * @param goodsIds 需要删除的物品主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsByGoodsIds(Long[] goodsIds) {
        return warehouseGoodsMapper.deleteWarehouseGoodsByGoodsIds(goodsIds);
    }

    /**
     * 删除物品信息
     *
     * @param goodsId 物品主键
     * @return 结果
     */
    @Override
    public int deleteWarehouseGoodsByGoodsId(Long goodsId) {
        return warehouseGoodsMapper.deleteWarehouseGoodsByGoodsId(goodsId);
    }

    @Override
    public byte[] getGoodsSnImage(Long receiptId, Long goodsId) throws IOException {
        WarehouseGoodsTraceRel warehouseGoodsTrace = new WarehouseGoodsTraceRel();
        warehouseGoodsTrace.setGoodsId(goodsId);
        warehouseGoodsTrace.setRelId(receiptId);
        warehouseGoodsTrace.setRecordType(ReceiptType.InReceipt.getCode());
        List<WarehouseGoodsTraceDto> goodsTraceList = iWarehouseGoodsTraceService.selectWarehouseGoodsTraceDtoList(warehouseGoodsTrace);

        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
        try (ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(zipOutputStream)) {
            for (WarehouseGoodsTraceDto trace : goodsTraceList) {
                BufferedImage image = BarCodeUtils.getBarCodeWithWords(trace.getSnCode(), "SN:" + trace.getSnCode(), trace.getGoodsName() + ";" + trace.getCategoryName(), "");
                byte[] imageBytes = bufferedImageToByteArray(image, "jpg");
                ZipArchiveEntry zipEntry = new ZipArchiveEntry(trace.getSnCode() + ".jpg");
                zipArchiveOutputStream.putArchiveEntry(zipEntry);
                zipArchiveOutputStream.write(imageBytes);
                zipArchiveOutputStream.closeArchiveEntry();
            }
        }

        return zipOutputStream.toByteArray();
    }

    @Override
    public byte[] getGoodsSnPdf(Long receiptId, Long goodsId) throws IOException {
        WarehouseGoodsTraceRel warehouseGoodsTraceRel = new WarehouseGoodsTraceRel();
        warehouseGoodsTraceRel.setGoodsId(goodsId);
        warehouseGoodsTraceRel.setRelId(receiptId);
        warehouseGoodsTraceRel.setRecordType(ReceiptType.InReceipt.getCode());
        List<WarehouseGoodsTraceDto> goodsTraceList = iWarehouseGoodsTraceService.selectWarehouseGoodsTraceDtoList(warehouseGoodsTraceRel);
        List<Map<String, Object>> mapList = new ArrayList<>();
        ByteArrayOutputStream zipOutputStream = new ByteArrayOutputStream();
        try (ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(zipOutputStream)) {
            for (WarehouseGoodsTraceDto trace : goodsTraceList) {
                Map params = new HashMap<>();
//                BufferedImage image = BarCodeUtils.getBarCodeWithWords(trace.getSnCode(), "SN:" + trace.getSnCode(), "物品名称:" + trace.getGoodsName(), "");
//                params.put("imageBuffer", bufferImageToInPutStream(image));
                params.put("snCode", trace.getSnCode());
                params.put("goodsName", trace.getGoodsName());
                params.put("categoryName", trace.getCategoryName());
                mapList.add(params);
            }
        }
        return PrintUtils.exportPdf(PrintUtils.getExportPrint(PrintConst.GOODS_SN_CODE, new HashMap(), mapList)).toByteArray();
    }

    /**
     * 查询仓库物品信息
     *
     * @param warehouseGoods
     * @return
     */
    @Override
    public List<WarehouseStockGoodsDto> selectStockGoodsList(WarehouseStockGoodsDto warehouseGoods) {
        return warehouseGoodsMapper.selectStockGoodsList(warehouseGoods);
    }

    @Override
    public PageInfo<WarehouseGoodsDto> selectWarehouseGoodsVoList(WarehouseGoodsQueryDto warehouseGoods) {
        List<WarehouseGoods> goodsList = warehouseGoodsMapper.selectWarehouseGoodsList(warehouseGoods);
        PageInfo<WarehouseGoods> pageInfo = new PageInfo<>(goodsList);
        PageInfo<WarehouseGoodsDto> newPageInfo = new PageInfo<>();
        newPageInfo.setTotal(pageInfo.getTotal());
        newPageInfo.setList(pageInfo.getList().stream().map(t -> {
            WarehouseGoodsDto dto = new WarehouseGoodsDto();
            BeanUtil.copyProperties(t, dto);
            if (!StrUtil.isBlankIfStr(t.getSpecificationType())) {
                dto.setSpecificationName(DictUtils.getDictLabel("goods_specification", t.getSpecificationType().toString()));
            }
            if (!StrUtil.isBlankIfStr(t.getColorType())) {
                dto.setColorName(DictUtils.getDictLabel("goods_color", t.getColorType().toString()));
            }
            if (!StrUtil.isBlankIfStr(t.getUnitType())) {
                dto.setUnitName(DictUtils.getDictLabel("goods_unit", t.getUnitType().toString()));
            }
            return dto;
        }).collect(Collectors.toList()));
        return newPageInfo;
    }

    @Override
    public WarehouseGoodsVo getSnCodeDetail(String code) {
        WarehouseGoodsTrace goodsTrace = iWarehouseGoodsTraceService.getOne(Wrappers.<WarehouseGoodsTrace>lambdaQuery().eq(WarehouseGoodsTrace::getSnCode, code));
        WarehouseGoods goods = this.getById(goodsTrace.getGoodsId());
        WarehouseGoodsVo vo = new WarehouseGoodsVo();
        BeanUtil.copyProperties(goods, vo);
        vo.setSnCode(code);
        return vo;
    }

    public static byte[] bufferedImageToByteArray(BufferedImage bufferedImage, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, format, baos);
        baos.flush();
        byte[] byteArray = baos.toByteArray();
        baos.close();
        return byteArray;
    }

    @SneakyThrows
    public InputStream bufferImageToInPutStream(BufferedImage bufferedImage) {
        // 将 BufferedImage 转换为 byte 数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();

        // 将 byte 数组转换为 ByteArrayInputStream
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

        return byteArrayInputStream;
    }
}
