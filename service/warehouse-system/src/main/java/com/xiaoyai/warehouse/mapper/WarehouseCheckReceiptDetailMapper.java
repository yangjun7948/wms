package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseCheckReceiptDetail;
import com.xiaoyai.warehouse.domain.dto.WarehouseCheckReceiptDetailDto;

/**
 * 盘点单据明细Mapper接口
 * 
 * @author junyang
 * @date 2023-10-07
 */
public interface WarehouseCheckReceiptDetailMapper extends BaseMapper<WarehouseCheckReceiptDetail>
{
    /**
     * 查询盘点单据明细
     * 
     * @param checkReceiptDetailId 盘点单据明细主键
     * @return 盘点单据明细
     */
    public WarehouseCheckReceiptDetail selectWarehouseCheckReceiptDetailByCheckReceiptDetailId(Long checkReceiptDetailId);

    /**
     * 查询盘点单据明细列表
     * 
     * @param warehouseCheckReceiptDetail 盘点单据明细
     * @return 盘点单据明细集合
     */
    public List<WarehouseCheckReceiptDetail> selectWarehouseCheckReceiptDetailList(WarehouseCheckReceiptDetail warehouseCheckReceiptDetail);

    /**
     * 新增盘点单据明细
     * 
     * @param warehouseCheckReceiptDetail 盘点单据明细
     * @return 结果
     */
    public int insertWarehouseCheckReceiptDetail(WarehouseCheckReceiptDetail warehouseCheckReceiptDetail);

    /**
     * 修改盘点单据明细
     * 
     * @param warehouseCheckReceiptDetail 盘点单据明细
     * @return 结果
     */
    public int updateWarehouseCheckReceiptDetail(WarehouseCheckReceiptDetail warehouseCheckReceiptDetail);

    /**
     * 删除盘点单据明细
     * 
     * @param checkReceiptDetailId 盘点单据明细主键
     * @return 结果
     */
    public int deleteWarehouseCheckReceiptDetailByCheckReceiptDetailId(Long checkReceiptDetailId);

    /**
     * 批量删除盘点单据明细
     * 
     * @param checkReceiptDetailIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseCheckReceiptDetailByCheckReceiptDetailIds(Long[] checkReceiptDetailIds);

    List<WarehouseCheckReceiptDetailDto> getCheckReceiptDetailList(WarehouseCheckReceiptDetail receiptDetail);
}
