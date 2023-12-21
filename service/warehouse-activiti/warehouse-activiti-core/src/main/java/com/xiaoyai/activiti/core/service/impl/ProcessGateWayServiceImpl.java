package com.xiaoyai.activiti.core.service.impl;

import com.xiaoyai.activiti.core.service.ProcessGateWayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


/**
 * @author xieyu
 * @date 2020-11-28
 */
@Component("processGateWayService")
public class ProcessGateWayServiceImpl implements ProcessGateWayService {
    private static Logger logger = LoggerFactory.getLogger(ProcessGateWayServiceImpl.class);


    @Override
    public boolean isApplyDept(String applyDeptId, String deptId) {
        logger.info("判断申请部门是否在指定的部门里");
        List<String> deptIds = getDeptIds(deptId);

        return deptIds.contains(applyDeptId);
    }

    @Override
    public boolean isNotApplyDept(String applyDeptId, String deptId) {
        logger.info("判断申请人不在指定的部门里");
        List<String> deptIds = getDeptIds(deptId);

        return !deptIds.contains(applyDeptId);
    }

    private List<String> getDeptIds(String deptId) {
//        R<List<SysDeptRelation>> sysDeptRelationR = remoteUserService.sysDeptRelationList(deptId, SecurityConstants.FROM_IN);
//        ValidateUtils.validate(CommonConstants.SUCCESS.equals(sysDeptRelationR.getCode()), "远程调用用户服务获取部门子集失败");
//
//        List<SysDeptRelation> sysDeptRelations = sysDeptRelationR.getData();
//        List<String> ids = sysDeptRelations.stream().map(SysDeptRelation::getDescendant).collect(Collectors.toList());
//        ids.add(deptId);
//        return ids;
        //todo
        return new ArrayList<>();
    }
}
