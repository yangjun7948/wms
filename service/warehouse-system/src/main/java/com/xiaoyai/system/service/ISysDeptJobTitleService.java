package com.xiaoyai.system.service;

import java.util.List;
import com.xiaoyai.system.domain.SysDeptJobTitle;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 部门矩阵Service接口
 * 
 * @author junyang
 * @date 2023-10-10
 */
public interface ISysDeptJobTitleService extends IService<SysDeptJobTitle>
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
     * 批量删除部门矩阵
     * 
     * @param deptIds 需要删除的部门矩阵主键集合
     * @return 结果
     */
    public int deleteSysDeptJobTitleByDeptIds(Long[] deptIds);

    /**
     * 删除部门矩阵信息
     * 
     * @param deptId 部门矩阵主键
     * @return 结果
     */
    public int deleteSysDeptJobTitleByDeptId(Long deptId);
}
