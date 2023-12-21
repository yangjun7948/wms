package com.xiaoyai.warehouse.domain.dto;

import com.xiaoyai.warehouse.domain.WarehouseCheckReceipt;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceiptDetail;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2023/10/8
 */
@Data
public class WarehouseCheckReceiptDto extends WarehouseCheckReceipt {
    private String checkUserName;
    private String warehouseName;
    private List<WarehouseCheckReceiptDetailDto> receiptGoodsList;
}
