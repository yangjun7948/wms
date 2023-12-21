package com.xiaoyai.activiti.core.listener;

import com.xiaoyai.activiti.core.service.impl.ListenerServiceImpl;
import com.xiaoyai.activiti.utils.ApplicationContextFactory;
import com.xiaoyai.common.core.domain.entity.SysUser;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xieyu
 * @date 2020-12-04
 */
public class ProcessUserListener implements TaskListener {

    private Expression value;

    protected static Logger logger = LoggerFactory.getLogger(ProcessUserListener.class);


    @Override
    public void notify(DelegateTask delegateTask) {
        ListenerServiceImpl processUserServiceImpl = (ListenerServiceImpl) ApplicationContextFactory.getBean("listenerServiceImpl", ListenerServiceImpl.class);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        //获取任务服务组件
        TaskService taskService = engine.getTaskService();
        String level = value.getExpressionText();
        String employeeId = (String) taskService.getVariable(delegateTask.getId(), "employeeId");
        SysUser leaderDto =new SysUser();
        //根据主管级别循环获取
        SysUser sysUser =new SysUser();
        sysUser.setUserId(Long.parseLong(employeeId));
        leaderDto = processUserServiceImpl.getDepartmentLeaderInfo(sysUser);
        String directLeaderId = leaderDto.getUserId().toString();
        logger.info("获取直接级主管，employeeId：{}，主管id：{}", employeeId, directLeaderId);
        /**直接主管目前只有一个人。所以采用直接制定Assignee的方式**/
        taskService.setAssignee(delegateTask.getId(),directLeaderId);
    }
}
