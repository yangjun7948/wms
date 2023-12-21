package com.xiaoyai.warehouse.service;

import java.util.List;
import com.xiaoyai.warehouse.domain.WarehouseCode;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoyai.warehouse.domain.WarehouseGoods;
import com.xiaoyai.warehouse.domain.dto.WarehouseGoodsDto;

/**
 *  仓库编号Service接口
 * 
 * @author junyang
 * @date 2023-09-08
 */
public interface IWarehouseCodeService extends IService<WarehouseCode>
{

    /**
     * 新增 仓库编号
     *
     * @param warehouseCode  仓库编号
     * @return 结果
     */
    public int insertWarehouseCode(WarehouseCode warehouseCode);
    /**
     * 生成物品编号
     * @param warehouseGoods
     * @return
     */
    public String getGoodsCode(WarehouseGoods warehouseGoods) throws Exception;

    String getInReceiptCode();

    /**
     * 生成物品溯源码
     * @param goods
     * @return
     */
    List<String> getGoodSnCodes(WarehouseGoodsDto goods);

    /**
     * 生成出库单编号
     * @return
     */
    String getOutReceiptCode();
    /**
     * 生成调拨单编号
     * @return
     */
    String getTransferCode();

    /**
     * 生成要货单编号
     * @return
     */
    String getApplyCode();

    /**
     * 盘点单
     * @return
     */
    String getCheckCode();

    /**
     * 仓库编号
     * @return
     */
    String getWarehouseCode();
}
