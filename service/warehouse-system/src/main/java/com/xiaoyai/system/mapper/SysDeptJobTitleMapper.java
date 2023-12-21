package com.xiaoyai.system.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.system.domain.SysDeptJobTitle;

/**
 * 部门矩阵Mapper接口
 * 
 * @author junyang
 * @date 2023-10-10
 */
public interface SysDeptJobTitleMapper extends BaseMapper<SysDeptJobTitle>
{
    /**
     * 查询部门矩阵
     * 
     * @param deptId 部门矩阵主键
     * @return 部门矩阵
     */
    public SysDeptJobTitle selectSysDeptJobTitleByDeptId(Long deptId);

    /**
     * 查询部门矩阵列表
     * 
     * @param sysDeptJobTitle 部门矩阵
     * @return 部门矩阵集合
     */
    public List<SysDeptJobTitle> selectSysDeptJobTitleList(SysDeptJobTitle sysDeptJobTitle);

    /**
     * 新增部门矩阵
     * 
     * @param sysDeptJobTitle 部门矩阵
     * @return 结果
     */
    public int insertSysDeptJobTitle(SysDeptJobTitle sysDeptJobTitle);

    /**
     * 修改部门矩阵
     * 
     * @param sysDeptJobTitle 部门矩阵
     * @return 结果
     */
    public int updateSysDeptJobTitle(SysDeptJobTitle sysDeptJobTitle);

    /**
     * 删除部门矩阵
     * 
     * @param deptId 部门矩阵主键
     * @return 结果
     */
    public int deleteSysDeptJobTitleByDeptId(Long deptId);

    /**
     * 批量删除部门矩阵
     * 
     * @param deptIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysDeptJobTitleByDeptIds(Long[] deptIds);
}
