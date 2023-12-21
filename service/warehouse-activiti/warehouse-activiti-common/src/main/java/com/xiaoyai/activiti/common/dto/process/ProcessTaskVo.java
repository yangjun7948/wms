package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @Author Cying
 * @Date 2020-11-11
 */
public class ProcessTaskVo {

    private List<ProcessTask> processTasks;

    public List<ProcessTask> getProcessTasks() {
        return processTasks;
    }

    public void setProcessTasks(List<ProcessTask> processTasks) {
        this.processTasks = processTasks;
    }
}
