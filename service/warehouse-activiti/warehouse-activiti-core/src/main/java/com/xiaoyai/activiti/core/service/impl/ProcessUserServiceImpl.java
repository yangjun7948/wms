package com.xiaoyai.activiti.core.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.activiti.core.service.ProcessUserService;


import com.xiaoyai.common.activiti.core.constant.CommonConstants;
import com.xiaoyai.common.activiti.core.util.R;
import com.xiaoyai.common.activiti.core.util.ValidateUtils;

import com.xiaoyai.common.core.domain.entity.SysOrgJobTitle;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.system.domain.SysDeptJobTitle;
import com.xiaoyai.system.service.ISysDeptJobTitleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xieyu
 * @date 2020-11-28
 */
@Component("processUserService")
public class ProcessUserServiceImpl implements ProcessUserService {
    private static Logger logger = LoggerFactory.getLogger(ProcessUserServiceImpl.class);

    @Autowired
    ISysDeptJobTitleService iSysDeptJobTitleService;

    @Override
    public List<String> getDeptLeader(String deptId, String jobTitleCode) {
        logger.info("获取部门矩阵对应的人员id");
        SysDeptJobTitle sysDeptJobTitle = new SysDeptJobTitle();
        sysDeptJobTitle.setDeptId(deptId);
        sysDeptJobTitle.setJobTitleCode(jobTitleCode);
        //todo
        R<List<SysDeptJobTitle>> r = new R<>();
//        R<List<SysDeptJobTitle>> r = remoteUserService.getDepartmentLeaderList(sysDeptJobTitle, SecurityConstants.FROM_IN);
        ValidateUtils.validate(CommonConstants.SUCCESS.equals(r.getCode()), "远程调用用户服务获取部门矩阵失败");

        List<SysDeptJobTitle> sysDeptJobTitles = r.getData();

        return sysDeptJobTitles.stream().map(SysDeptJobTitle::getUserId).collect(Collectors.toList());
    }

    @Override
    public String getDeptUser(String deptId, String jobTitleCode) {
        SysDeptJobTitle sysDeptJobTitle = iSysDeptJobTitleService.list(Wrappers.<SysDeptJobTitle>lambdaQuery()
                .eq(SysDeptJobTitle::getDeptId,deptId)
                .eq(SysDeptJobTitle::getJobTitleCode,jobTitleCode)).stream().findFirst().orElse(new SysDeptJobTitle());
        Assert.notNull(sysDeptJobTitle.getUserId(),"获取部门矩阵人员失败");
        return sysDeptJobTitle.getUserId();
    }

    @Override
    public List<String> getDeptUserList(String deptId, String jobTitleCode) {

        List<SysDeptJobTitle> sysDeptJobTitles = iSysDeptJobTitleService.list(Wrappers.<SysDeptJobTitle>lambdaQuery()
                .eq(SysDeptJobTitle::getDeptId,deptId)
                .eq(SysDeptJobTitle::getJobTitleCode,jobTitleCode));
        Assert.isTrue(sysDeptJobTitles.size()>0,"获取部门矩阵人员失败");
        return sysDeptJobTitles.stream().map(SysDeptJobTitle::getUserId).collect(Collectors.toList());
    }

    @Override
    public List<String> getUserByRole(String roleId) {
        String[] _r = roleId.split(",");
        List<String> roleIds = Arrays.asList(_r);
        //todo
        R<List<SysUser>> r = new R<>();
//        R<List<SysUser>> r = remoteUserService.getUserByRoleIds(roleIds, SecurityConstants.FROM_IN);
        ValidateUtils.validate(CommonConstants.SUCCESS.equals(r.getCode()), "远程调用用户服务失败");
        List<SysUser> sysUsers = r.getData();

        return sysUsers.stream().map(t->t.getUserId().toString()).collect(Collectors.toList());
    }

    /**
     * 获取分部矩阵信息
     * @param orgId
     * @param jobTitleCode
     * @return
     */
    public List<String> getOrgUserList(String orgId, String jobTitleCode) {
        String[] _r = jobTitleCode.split(",");
        List<String> jobTitleCodes = Arrays.asList(_r);
        List<SysOrgJobTitle> orgJobTitleList = new ArrayList<>();

        jobTitleCodes.forEach(code -> {
            SysOrgJobTitle orgJobTitle = new SysOrgJobTitle();
            orgJobTitle.setOrgId(orgId);
            orgJobTitle.setJobTitleCode(code);
            //todo
            R<List<SysOrgJobTitle>> r = new R<>();
//            R<List<SysOrgJobTitle>> r = remoteUserService.getOrgLeaderList(orgJobTitle, SecurityConstants.FROM_IN);
            ValidateUtils.validate(CommonConstants.SUCCESS.equals(r.getCode()), "远程调用用户服务失败");

            orgJobTitleList.addAll(r.getData());
        });

        return orgJobTitleList.stream().map(SysOrgJobTitle::getUserId).collect(Collectors.toList());
    }

    /**
     * 获取直接上级
     * @param userId
     * @return
     */
    public String getUserLeader(String userId) {
       //todo
        return "";
    }
}
