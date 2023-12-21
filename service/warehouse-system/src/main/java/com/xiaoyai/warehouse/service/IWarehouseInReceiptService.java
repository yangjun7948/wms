package com.xiaoyai.warehouse.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.warehouse.domain.WarehouseInReceipt;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseInReceiptDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseInReceiptVo;
import com.xiaoyai.warehouse.domain.vo.WarehouseOutReceiptVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入库单据Service接口
 * 
 * @author junyang
 * @date 2023-09-09
 */
public interface IWarehouseInReceiptService extends IService<WarehouseInReceipt>
{
    /**
     * 查询入库单据
     * 
     * @param warehouseInReceiptId 入库单据主键
     * @return 入库单据
     */
    public WarehouseInReceiptDto selectWarehouseInReceiptByWarehouseInReceiptId(Long warehouseInReceiptId);

    /**
     * 查询入库单据列表
     * 
     * @param warehouseInReceipt 入库单据
     * @return 入库单据集合
     */
    public List<WarehouseInReceipt> selectWarehouseInReceiptList(WarehouseInReceipt warehouseInReceipt);

    /**
     * 新增入库单据
     * 
     * @param warehouseInReceipt 入库单据
     * @return 结果
     */
    public boolean insertWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt);

    /**
     * 修改入库单据
     * 
     * @param warehouseInReceipt 入库单据
     * @return 结果
     */
    public boolean updateWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt);

    /**
     * 批量删除入库单据
     * 
     * @param warehouseInReceiptIds 需要删除的入库单据主键集合
     * @return 结果
     */
    public int deleteWarehouseInReceiptByWarehouseInReceiptIds(Long[] warehouseInReceiptIds);

    /**
     * 删除入库单据信息
     * 
     * @param warehouseInReceiptId 入库单据主键
     * @return 结果
     */
    public boolean deleteWarehouseInReceiptByWarehouseInReceiptId(Long warehouseInReceiptId);

    /**
     * 生成入库单PDF
     * @param warehouseInReceiptId
     * @return
     */
    byte[] inReceiptPdf(Long warehouseInReceiptId);

    /**
     *  审核入库单据
     * @param warehouseInReceipt
     * @return
     */
    boolean approveWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt) throws Exception;

    /**
     * 取消入库单据审核
     * @param warehouseInReceipt
     * @return
     */
    @Transactional
    boolean unApproveWarehouseInReceipt(WarehouseInReceiptDto warehouseInReceipt);

    PageInfo<WarehouseInReceiptVo> getInReceiptScanList(WarehouseInReceipt warehouseInReceipt);

    WarehouseInReceiptVo getInReceiptScanDetail(Long receiptId);

    /**
     * 提交扫码入库结果
     * @param warehouseInReceiptVo
     * @return
     */
    boolean saveInReceiptScanDetail(WarehouseInReceiptVo warehouseInReceiptVo) throws Exception;

    /**
     * 生成申请入库单
     * @param applyReceiptId
     */
    void doApplyInReceipt(Long applyReceiptId);
}
