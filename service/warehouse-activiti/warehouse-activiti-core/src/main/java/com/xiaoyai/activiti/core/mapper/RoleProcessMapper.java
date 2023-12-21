package com.xiaoyai.activiti.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.common.activiti.api.model.activiti.ActRoleProcess;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface RoleProcessMapper extends BaseMapper<ActRoleProcess> {

    void deleteRoleProcessByRoleId(@Param("roleId") String roleId);
}
