package com.xiaoyai.activiti.core.service;

/**
 * @author xieyu
 * @date 2020-12-03
 */
public interface ProcessGateWayService {

    /**
     * 判断申请人的申请部门是不是在指定部门
     */
    boolean isApplyDept(String applyDeptId, String deptId);



    /**
     * 判断人员是否不属于某个部门
     */
    boolean isNotApplyDept(String applyDeptId, String deptId);

}
