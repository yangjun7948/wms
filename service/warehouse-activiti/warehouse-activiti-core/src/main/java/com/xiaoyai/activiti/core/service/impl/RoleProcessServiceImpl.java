package com.xiaoyai.activiti.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.activiti.core.mapper.RoleProcessMapper;
import com.xiaoyai.activiti.core.service.RoleProcessService;
import com.xiaoyai.common.activiti.api.model.activiti.ActRoleProcess;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class RoleProcessServiceImpl extends ServiceImpl<RoleProcessMapper, ActRoleProcess> implements RoleProcessService {

}
