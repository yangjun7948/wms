package com.xiaoyai.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.system.mapper.SysDeptJobTitleMapper;
import com.xiaoyai.system.domain.SysDeptJobTitle;
import com.xiaoyai.system.service.ISysDeptJobTitleService;

/**
 * 部门矩阵Service业务层处理
 * 
 * @author junyang
 * @date 2023-10-10
 */
@Service
public class SysDeptJobTitleServiceImpl extends ServiceImpl<SysDeptJobTitleMapper, SysDeptJobTitle> implements ISysDeptJobTitleService
{
    @Autowired
    private SysDeptJobTitleMapper sysDeptJobTitleMapper;

    /**
     * 查询部门矩阵
     * 
     * @param deptId 部门矩阵主键
     * @return 部门矩阵
     */
    @Override
    public SysDeptJobTitle selectSysDeptJobTitleByDeptId(Long deptId)
    {
        return sysDeptJobTitleMapper.selectSysDeptJobTitleByDeptId(deptId);
    }

    /**
     * 查询部门矩阵列表
     * 
     * @param sysDeptJobTitle 部门矩阵
     * @return 部门矩阵
     */
    @Override
    public List<SysDeptJobTitle> selectSysDeptJobTitleList(SysDeptJobTitle sysDeptJobTitle)
    {
        return sysDeptJobTitleMapper.selectSysDeptJobTitleList(sysDeptJobTitle);
    }

    /**
     * 新增部门矩阵
     * 
     * @param sysDeptJobTitle 部门矩阵
     * @return 结果
     */
    @Override
    public int insertSysDeptJobTitle(SysDeptJobTitle sysDeptJobTitle)
    {
        return sysDeptJobTitleMapper.insertSysDeptJobTitle(sysDeptJobTitle);
    }

    /**
     * 修改部门矩阵
     * 
     * @param sysDeptJobTitle 部门矩阵
     * @return 结果
     */
    @Override
    public int updateSysDeptJobTitle(SysDeptJobTitle sysDeptJobTitle)
    {
        return sysDeptJobTitleMapper.updateSysDeptJobTitle(sysDeptJobTitle);
    }

    /**
     * 批量删除部门矩阵
     * 
     * @param deptIds 需要删除的部门矩阵主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptJobTitleByDeptIds(Long[] deptIds)
    {
        return sysDeptJobTitleMapper.deleteSysDeptJobTitleByDeptIds(deptIds);
    }

    /**
     * 删除部门矩阵信息
     * 
     * @param deptId 部门矩阵主键
     * @return 结果
     */
    @Override
    public int deleteSysDeptJobTitleByDeptId(Long deptId)
    {
        return sysDeptJobTitleMapper.deleteSysDeptJobTitleByDeptId(deptId);
    }
}
