package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.warehouse.domain.WarehouseTransferReceipt;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/9/16
 */
@Data
public class WarehouseTransferReceiptDto extends WarehouseTransferReceipt {
    private List<WarehouseGoodsDto> receiptGoodsList;
    @Excel(name = "调出仓库")
    private String transferOutWarehouseName;
    @Excel(name = "调入仓库")
    private String transferInWarehouseName;
    /**
     * 入库单号
     */
    private String inDocumentNumber;
    private String inReceiptId;
    private String outReceiptId;
    /**
     * 出库单号
     */
    private String outDocumentNumber;
}
