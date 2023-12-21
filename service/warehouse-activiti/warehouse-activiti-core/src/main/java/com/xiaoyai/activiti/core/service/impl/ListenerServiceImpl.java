package com.xiaoyai.activiti.core.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoyai.activiti.core.mapper.ActApprovalRecordMapper;
import com.xiaoyai.activiti.core.mapper.ProcessServiceDaoImplMapper;
import com.xiaoyai.activiti.core.processHandle.ProcessHandleBase;
import com.xiaoyai.activiti.utils.ApplicationContextFactory;
import com.xiaoyai.common.activiti.api.model.activiti.ActApprovalRecord;
import com.xiaoyai.common.activiti.core.constant.CommonConstants;
import com.xiaoyai.common.activiti.core.constant.SecurityConstants;
import com.xiaoyai.common.activiti.core.util.R;
import com.xiaoyai.common.activiti.core.util.ValidateUtils;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.system.domain.SysDeptJobTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2021-11-04
 */
@Component("listenerServiceImpl")
public class ListenerServiceImpl {

    @Autowired
    ProcessServiceDaoImplMapper serviceDaoImplMapper;

    @Autowired
    ActApprovalRecordMapper actApprovalRecordMapper;

    /**
     * 通过角色id获取用户
     *
     * @param roleIds
     * @return
     */
    public List<SysUser> getUserByRoleIds(List<String> roleIds) {
        //todo
        return new ArrayList<>();
    }

    public String getTaskApplyIdByInstanceId(Map info) {
        return serviceDaoImplMapper.getUserIdByInstanceId(info);
    }

    public void saveActApprovalRecordBatch(List<ActApprovalRecord> actApprovalRecordDtoList) {
        actApprovalRecordDtoList.forEach(actApprovalRecord -> {
            actApprovalRecordMapper.insert(actApprovalRecord);
        });
    }

    public int handleAfterProcessCompleted(JSONObject reqParam) {
        ProcessHandleBase processHandleBase = (ProcessHandleBase) ApplicationContextFactory.getBean(reqParam.getString("procNum"));
        if (processHandleBase == null) {
            ValidateUtils.validate(false, "用户服务远程调用出错");
        }
        //发起流程数据构建
        processHandleBase.afterProcessHandle(reqParam);
        return 1;
    }


    public List<SysDeptJobTitle> getDepartmentLeaderList(SysDeptJobTitle deptJobTitleDto) {
//        R<List<SysDeptJobTitle>> r = remoteUserService.getDepartmentLeaderList(deptJobTitleDto, SecurityConstants.FROM_IN);
//        ValidateUtils.validate(CommonConstants.SUCCESS.equals(r.getCode()), "远程调用用户服务获取部门矩阵失败");
//        return r.getData();
        //todo
        return new ArrayList<>();
    }

    public SysUser getDepartmentLeaderInfo(SysUser sysUser) {
//        R<SysUser> r = remoteUserService.getDepartmentLeaderInfo(sysUser, SecurityConstants.FROM_IN);
//        ValidateUtils.validate(CommonConstants.SUCCESS.equals(r.getCode()), "远程调用用户服务获取部门矩阵失败");
//        return r.getData();
        //todo
        return new SysUser();

    }

    public Map getProcessDetailInfo(Map paramMap) {
        return serviceDaoImplMapper.getProcessDetailInfo(paramMap);
    }
}
