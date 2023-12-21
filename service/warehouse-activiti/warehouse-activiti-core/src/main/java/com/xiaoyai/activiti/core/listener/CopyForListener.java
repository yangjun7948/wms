package com.xiaoyai.activiti.core.listener;

import cn.hutool.core.util.IdUtil;
import com.xiaoyai.activiti.core.service.impl.ListenerServiceImpl;
import com.xiaoyai.activiti.utils.ApplicationContextFactory;
import com.xiaoyai.common.activiti.api.model.activiti.ActApprovalRecord;

import com.xiaoyai.common.core.domain.entity.SysUser;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;

/**
 * @author xieyu
 * @date 2020-11-28
 */
@Component
public class CopyForListener implements Serializable, TaskListener {
    private Expression value;
    private static Logger logger = LoggerFactory.getLogger(CopyForListener.class);

    @Override
    public void notify(DelegateTask delegateTask) {
        ListenerServiceImpl listenerService = ApplicationContextFactory.getBean("listenerServiceImpl", ListenerServiceImpl.class);
        // 创建流程引擎
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        //获取任务服务组件
        TaskService taskService = engine.getTaskService();
        String eventName = delegateTask.getEventName();
        logger.info("事件名称:{}", eventName);
        String userIds = value.getExpressionText();
        List<String> roleIds = Arrays.asList(userIds.split(","));
        //根据roleIds获取employeeIds
        Map paramMap = new HashMap();
        paramMap.put("roleIds", roleIds);
        List<SysUser> userList= listenerService.getUserByRoleIds(roleIds);
        //获取流程发起人
        Map instanceMap = new HashMap(16);
        instanceMap.put("processInstanceId", delegateTask.getProcessInstanceId());
        String taskApplyUserId = listenerService.getTaskApplyIdByInstanceId(instanceMap);
        List<ActApprovalRecord> actApprovalRecordDtoList = new ArrayList<>();
        if (userList != null && userList.size() > 0) {
            List<String> userIdList = new ArrayList<>();
            userList.forEach(item -> {
                ActApprovalRecord actApprovalRecordDto = new ActApprovalRecord();
                actApprovalRecordDto.setActApprovalRecordId(IdUtil.fastSimpleUUID());
                actApprovalRecordDto.setProcInstanceId(delegateTask.getProcessInstanceId());
                actApprovalRecordDto.setApprovalUserId(taskApplyUserId);
                actApprovalRecordDto.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
                actApprovalRecordDto.setTaskDefinitionName("抄送人");
                actApprovalRecordDto.setTaskId(delegateTask.getId());

                actApprovalRecordDto.setReceiverId(item.getUserId().toString());
                actApprovalRecordDto.setApprovalSign("已收");
                actApprovalRecordDtoList.add(actApprovalRecordDto);
                userIdList.add(item.getUserId().toString());
            });
            delegateTask.addCandidateUsers(userIdList);
            logger.info("抄送任务抄送人：{}", userIdList);
        }
        //保存抄送记录
        listenerService.saveActApprovalRecordBatch(actApprovalRecordDtoList);
        taskService.complete(delegateTask.getId());

    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }

}
