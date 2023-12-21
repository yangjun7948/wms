package com.xiaoyai.activiti.core.listener;

import com.xiaoyai.activiti.core.service.impl.ListenerServiceImpl;
import com.xiaoyai.activiti.utils.ApplicationContextFactory;
import com.xiaoyai.system.domain.SysDeptJobTitle;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyu
 * @date 2020-12-04
 */
public class ProcessDepartmentListener implements TaskListener {

    private Expression value;

    protected static Logger logger = LoggerFactory.getLogger(ProcessDepartmentListener.class);


    @Override
    public void notify(DelegateTask delegateTask) {
        ListenerServiceImpl processUserServiceImpl = (ListenerServiceImpl) ApplicationContextFactory.getBean("listenerServiceImpl", ListenerServiceImpl.class);
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        //获取任务服务组件
        TaskService taskService = engine.getTaskService();
        String jobId = value.getExpressionText();
        String deptId = (String) taskService.getVariable(delegateTask.getId(), "deptId");
        //根据部门id和职位id
        SysDeptJobTitle deptJobTitleDto =new SysDeptJobTitle();

        deptJobTitleDto.setDeptId(deptId);
        deptJobTitleDto.setJobTitleCode(jobId);

        List<SysDeptJobTitle> mapList = processUserServiceImpl.getDepartmentLeaderList(deptJobTitleDto);
        logger.info("获取部门领导，部门id：{}，职位：{}，结果：{}", deptId, jobId, mapList.toString());
        if (mapList == null || mapList.size() <= 0) {
            return;
        }
        if (mapList.size() == 1) {
            taskService.setAssignee(delegateTask.getId(),mapList.get(0).getUserId());
        } else {
            List<String> assignee = new ArrayList<>();
            if (mapList != null && mapList.size() > 0) {
                for (SysDeptJobTitle result : mapList) {
                    assignee.add(result.getUserId());
                }
            }
            /**部门矩阵没有采用制定候选人方式是因为当前OA的业务没有这种审批方式
             如果后续有这种业务，需要改成下面这种候选人方式**/
            delegateTask.addCandidateUsers(assignee);
        }

    }

    public Expression getValue() {
        return value;
    }

    public void setValue(Expression value) {
        this.value = value;
    }
}
