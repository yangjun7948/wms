package com.xiaoyai.activiti.core.service;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoyai.activiti.common.dto.process.ProcessCreateRequest;
import com.xiaoyai.activiti.common.dto.process.ProcessDeleteReqParam;
import com.xiaoyai.activiti.common.dto.process.ProcessWithdrawReqParam;
import com.xiaoyai.common.activiti.api.model.activiti.ActAllProc;
import com.xiaoyai.common.activiti.api.model.activiti.dto.*;
import com.xiaoyai.common.core.domain.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2021-10-27
 */
public interface ProcessService {
    /**
     * 获取所有流程
     */
    List<ProcessDto> queryProcesses(Map info);

    /**
     * 新建流程
     */
    String startFlow(Map info);

    /**
     * 我的待办
     * */
    MyProcessDto listMyTodoFlow(ProcessToDoListParamDto processToDoListParamDto);

    /**
     * 保存流程标题描述
     */
    void addTaskDesc(Map info);

    /**
     * 查询任务详情
     */
    List<Map> queryTaskDetail(Map info);

    /**
     * 流程审批
     */
    String processApproval(ProcessCompleteParamDto processCompleteParamDto);

    /**
     * 获取流程所走过的审批节点
     */
    List<Map> queryApprovalList(Map info);

    /***
     *获取流程详情
     */
    String searchTaskDetail(ProcessDetailParamDto processDetailParamDto);

    /**
     * 我的待办
     * */
    MyProcessDto listMyDnoeProcess(ProcessDoneReqParamDto reqParamDto);

    /**
     * 保存流程
     * @param info
     * @return
     */
    String saveFlow(Map info);

    /**
     * 获取我的请求
     * */
    MyProcessDto listMyApplyProcess(ProcessMyRequestListParamDto dto);

    /**
     * 回退流程
     * */
    String backFlow(ProcessBackFlowParamDto processCompleteParamDto);

    /**
     * 流程状态
     * */
    String listProcessStatus(Map info);

    /**
     * 发起或保存流程
     * */
    String startOrSaveProcess(JSONObject request);

    /**
     * 撤回流程
     * */
    String processWithdraw(ProcessWithdrawReqParam request);

    /**
     * 流程办结
     * */
    MyProcessDto listCompletedProcess(ProcessCompletedListParamDto paramDto);

    /**
     * 查询流程监控列表
     * @param reqParam
     * @return
     */
    ProcessMonitorVo queryProcessMonitorList(ProcessMonitorReqParam reqParam);

    /**
     * 提醒流程发起
     * */
    String startRemindProcess(ProcessCreateRequest request);

    /**
     * 流程退回节点列表查询
     * */
    String queryProcessBackNodeList(ProcessBackNodeListReqParam request);

    /**
     * 流程删除
     * */
    String deleteProcess(ProcessDeleteReqParam request);

    /**
     * 流程撤回审批
     * */
    String processApprovalWithdraw(ProcessApprovalWithdrawReqParam request);

    /**
     * 流程转发
     * */
    String processForward(ProcessForwardReqParam request);

    /**
     * 部署流程
     * @param request
     * @return
     */
    boolean deployProcess(ProcessDeployReqParam request);

    /**
     * 流程意见征询
     * */
    String opinionSeek(ProcessOpinionSeekReqParam request);

    /**
     * 流程转办
     * */
    String processTransfer(ProcessTransferReqParam request);


    String remindProcessHandle(String processInstanceId);

    List<ActFormPermission> getUserEditViewAuthority(Map info);

    /**
     * WY30结束处理
     */
    void resignationForwarding(JSONObject reqParam);

    void addNewStaff(JSONObject reqParam);

    /**
     * 流程超时未处理提醒
     */
    void userProcessOvertimeHandleRemind();

    byte[] searchProcessPicture(Map map);

    /**
     * 获取用户流程列表
     * @return
     */
    List<ProcessListDto> getUserProcessList();

    Map processNewDetail(JSONObject reqParam);

    Boolean saveUser(SysUser userDto);

    int handleAfterProcessCompleted(JSONObject reqParam);

    /**
     * 根据角色id获取角色所分配的流程code列表
     *
     * @param roleId 角色id
     * @return 流程id列表
     */
    List<String> getProcessCodeByRoleId(String roleId);

    /**
     * 修改保存角色流程信息
     *
     * @param data 修改参数
     */
    Boolean updateRoleProcessCode(RoleProcessCodeDto data);

    String getEhrProcessTaskList(JSONObject object);

    /**
     * 更新流程文件
     * @param proc
     * @return
     */
    Boolean updateProc(ActAllProc proc);

    List<ProcessDto> getUserAllProcessList();

    ProcessMonitorVo getProcessMonitor(ProcessMonitorReqParam reqParam);
}
