package com.xiaoyai.activiti.core.service;

import java.util.List;

/**
 * @author xieyu
 * @date 2020-11-28
 * 流程公共组件
 */
public interface ProcessUserService {

    /**
     * 获取部门矩阵信息
     */
    List<String> getDeptLeader(String deptId, String jobTitleCode);

    List<String> getUserByRole(String roleId);

    /**
     * 获取部门矩阵对应的人员id
     */
    String getDeptUser(String deptId, String jobTitleCode);

    /**
     * 获取部门矩阵对应的人员集合
     */
    List<String> getDeptUserList(String deptId, String jobTitleCode);

    /**
     * 获取分部矩阵
     * @param orgId
     * @param jobTitleCode
     * @return
     */
    List<String> getOrgUserList(String orgId, String jobTitleCode);

    /**
     * 获取直接上级
     * @param userId
     * @return
     */
    String getUserLeader(String userId);
}
