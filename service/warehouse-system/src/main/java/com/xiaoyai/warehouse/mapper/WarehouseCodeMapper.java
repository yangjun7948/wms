package com.xiaoyai.warehouse.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.warehouse.domain.WarehouseCode;

/**
 *  仓库编号Mapper接口
 * 
 * @author junyang
 * @date 2023-09-08
 */
public interface WarehouseCodeMapper extends BaseMapper<WarehouseCode>
{
    /**
     * 查询 仓库编号
     * 
     * @param codeType  仓库编号主键
     * @return  仓库编号
     */
    public WarehouseCode selectWarehouseCodeByCodeType(String codeType);

    /**
     * 查询 仓库编号列表
     * 
     * @param warehouseCode  仓库编号
     * @return  仓库编号集合
     */
    public List<WarehouseCode> selectWarehouseCodeList(WarehouseCode warehouseCode);

    /**
     * 新增 仓库编号
     * 
     * @param warehouseCode  仓库编号
     * @return 结果
     */
    public int insertWarehouseCode(WarehouseCode warehouseCode);

    /**
     * 修改 仓库编号
     * 
     * @param warehouseCode  仓库编号
     * @return 结果
     */
    public int updateWarehouseCode(WarehouseCode warehouseCode);

    /**
     * 删除 仓库编号
     * 
     * @param codeType  仓库编号主键
     * @return 结果
     */
    public int deleteWarehouseCodeByCodeType(String codeType);

    /**
     * 批量删除 仓库编号
     * 
     * @param codeTypes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWarehouseCodeByCodeTypes(String[] codeTypes);
}
