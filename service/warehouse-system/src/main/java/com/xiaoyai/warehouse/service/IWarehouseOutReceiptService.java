package com.xiaoyai.warehouse.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.xiaoyai.warehouse.domain.WarehouseOutReceipt;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.dto.WarehouseOutReceiptDto;
import com.xiaoyai.warehouse.domain.vo.WarehouseOutReceiptVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 出库单据Service接口
 * 
 * @author junyang
 * @date 2023-09-13
 */
public interface IWarehouseOutReceiptService extends IService<WarehouseOutReceipt>
{
    /**
     * 查询出库单据
     * 
     * @param warehouseOutReceiptId 出库单据主键
     * @return 出库单据
     */
    public WarehouseOutReceiptDto selectWarehouseOutReceiptByWarehouseOutReceiptId(Long warehouseOutReceiptId);

    /**
     * 查询出库单据列表
     * 
     * @param warehouseOutReceipt 出库单据
     * @return 出库单据集合
     */
    public List<WarehouseOutReceipt> selectWarehouseOutReceiptList(WarehouseOutReceipt warehouseOutReceipt);

    /**
     * 新增出库单据
     * 
     * @param warehouseOutReceipt 出库单据
     * @return 结果
     */
    public Boolean insertWarehouseOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt);

    /**
     * 修改出库单据
     * 
     * @param warehouseOutReceipt 出库单据
     * @return 结果
     */
    public Boolean updateWarehouseOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt);

    /**
     * 批量删除出库单据
     * 
     * @param warehouseOutReceiptIds 需要删除的出库单据主键集合
     * @return 结果
     */
    public int deleteWarehouseOutReceiptByWarehouseOutReceiptIds(Long[] warehouseOutReceiptIds);

    /**
     * 删除出库单据信息
     * 
     * @param warehouseOutReceiptId 出库单据主键
     * @return 结果
     */
    public boolean deleteWarehouseOutReceiptByWarehouseOutReceiptId(Long warehouseOutReceiptId);

    boolean approveWarehouseOutReceipt(WarehouseOutReceipt receiptId) throws Exception;


    boolean unApproveWarehouseOutReceipt(WarehouseOutReceiptDto warehouseOutReceipt) throws Exception;

    byte[] outReceiptPdf(Long warehouseOutReceiptId);

    PageInfo<WarehouseOutReceiptVo> getOutReceiptScanList(WarehouseOutReceipt outReceipt);

    WarehouseOutReceiptVo getOutReceiptScanDetail(Long receiptId);

    boolean saveOutReceiptScanDetail(WarehouseOutReceiptVo warehouseOutReceiptVo) throws Exception;
}
