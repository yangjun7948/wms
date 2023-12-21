package com.xiaoyai.activiti.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyai.activiti.common.dto.process.ActApprovalRecordDto;
import com.xiaoyai.common.activiti.api.model.activiti.ActAllProc;
import com.xiaoyai.common.activiti.api.model.activiti.ActApprovalRecord;
import com.xiaoyai.common.activiti.api.model.activiti.dto.MyProcessApplyDto;
import com.xiaoyai.common.activiti.api.model.activiti.dto.ProcessToDoListParamDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ProcessServiceDaoImplMapper extends BaseMapper<ActAllProc> {

    List<Map> queryProcesses(@Param("query") Map info);

    List<Map> getProcessListByRoleIds(@Param("roleIds") List<Long> roleIds);

    void deleteProcessByProcessInstanceId(@Param("processInstanceId") String processInstanceId);

    void addTaskDesc(@Param("query") Map info);
    void addTaskDescForm(@Param("query") Map info);

    List<Map> queryActProcessDtoByProcessCode(@Param("query") Map info);

    int getMyToDoProcessCount(@Param("query") ProcessToDoListParamDto params);

    List<Map> getTodoTaskDetail(@Param("query") ProcessToDoListParamDto params);

    Map getProcessDetailInfo(@Param("query") Map info);

    List<Map> queryProcessCandidateOrParticipant(@Param("query") Map info);

    List<Map> getProcessApprovalRecord(@Param("query") Map info);

    void updateProcessApprovalRecord(@Param("query") ActApprovalRecordDto recordDto);

    List<Map> queryAllApprovalComment(@Param("query") Map info);

    List<Map> sureCurTaskIsRunning(@Param("query") Map info);

    Map getProcessCurrentTask(@Param("query") Map info);

    Map getProcessOperationAuthorityByTaskId(@Param("query") Map info);

    void insertRecordToHistoryTask(@Param("query") Map map);

    List<Map> selectRunTaskInfoByTaskId(@Param("query") Map map);

    void deleteRunTaskByTaskId(@Param("query") Map map);

    List<Map> getAllEditFormByTaskDefinitionKey(@Param("query") Map map);

    void updateTaskStatus(@Param("query") Map map);

    void updateProcessDesc(@Param("query") Map paramMap);

    List<Map> getMyDoneProcessCount(@Param("query") Map beanCovertMap);

    List<Map> queryDoneTaskDetail(@Param("query") Map paramMap);

    List<Map> getProcessEffectiveCommentList(@Param("query") Map beanCovertMap);

    List<Map> getMyApplyProcessCount(@Param("query") Map info);

    List<Map> queryMyApplyProcess(@Param("query") Map info);

    List<Map> queryMyApplyProcessOtherFiled(@Param("query") Map info);

    int getRunExecutionRecord(@Param("query") Map info);

    void insertRecordToRunExecut(@Param("query") Map info);

    List<Map> getProcessToDoListByProcessInstanceId(@Param("query") Map info);

    void insertRecordToRunTask(@Param("query") Map info);

    void deleteProcessForwardRecord(@Param("query") Map info);

    void insertProcessTransferRecordToHisTask(@Param("query") Map info);

    void clearProcessApprovalRecord(@Param("query") Map info);

    Map getProcInfoByInstanceId(@Param("query") Map info);

    Map queryProcessDescByInstanceId(@Param("instanceId") String instanceId);

    String getUserIdByInstanceId(@Param("query") Map info);

    List<Map> getActPermissionList(@Param("query")  Map info);

    List<Map> getCurrentTaskAssigneeList(@Param("query") Map info);

    List<Map> getAllRunVariableByInstanceId(@Param("query") Map paramMap);

    void updateProcessDescByOldInstanceId(@Param("query") Map paramMap);

    ActApprovalRecord selectActApprovalRecord(String taskId);

    void removeProcessDesc(@Param("query") Map paramMap);

    List<Map> queryAllProcessAboutUser(@Param("query") Map info);

    List<Map> queryAllRunTask();

    List<Map> queryProcessTaskList(@Param("query") Map info);

    List<Map> getProcessHistoryTaskInfoList(@Param("query") Map info);

    void deleteProcessHistoryTask(@Param("query") Map paramMap);

    void deleteProcessWithdrawTaskComment(@Param("query") Map beanCovertMap);

    void deleteProcessApprovalRecordByTaskId(@Param("query") Map info);

    List<Map> queryRunTaskByInstanceId(@Param("query") Map info);

    List<Map> getProcessMonitorListCount(@Param("query") Map info);

    List<Map> getProcessMonitorList(@Param("query")  Map info);

    List<Map> getProcessCompletedListCount(@Param("query") Map info);

    List<Map> queryProcessCompletedDetail(@Param("query") Map info);

    void deleteProcessHisActInstance(@Param("query") Map info);
}
