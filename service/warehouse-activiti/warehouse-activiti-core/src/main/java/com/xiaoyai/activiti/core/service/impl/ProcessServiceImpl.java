package com.xiaoyai.activiti.core.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaoyai.activiti.common.dto.process.*;
import com.xiaoyai.activiti.core.config.ActivitiConfig;
import com.xiaoyai.activiti.core.mapper.ActApprovalRecordMapper;
import com.xiaoyai.activiti.core.mapper.ProcessServiceDaoImplMapper;
import com.xiaoyai.activiti.core.processHandle.ProcessHandleBase;
import com.xiaoyai.activiti.core.service.ImageService;
import com.xiaoyai.activiti.core.service.ProcessService;
import com.xiaoyai.activiti.core.service.RoleProcessService;
import com.xiaoyai.activiti.enums.*;
import com.xiaoyai.activiti.utils.ApplicationContextFactory;

import com.xiaoyai.common.activiti.api.model.activiti.ActAllProc;
import com.xiaoyai.common.activiti.api.model.activiti.ActApprovalRecord;
import com.xiaoyai.common.activiti.api.model.activiti.ActRoleProcess;
import com.xiaoyai.common.activiti.api.model.activiti.dto.*;
import com.xiaoyai.common.activiti.core.constant.CommonConstants;
import com.xiaoyai.common.activiti.core.constant.ProcessConstant;
import com.xiaoyai.common.activiti.core.constant.SecurityConstants;
import com.xiaoyai.activiti.enums.ProcessStatusEnum;
import com.xiaoyai.common.activiti.core.util.BeanConvertUtil;
import com.xiaoyai.common.activiti.core.util.DateUtil;
import com.xiaoyai.common.activiti.core.util.R;
import com.xiaoyai.common.activiti.core.util.ValidateUtils;
import com.xiaoyai.common.core.domain.entity.SysDept;
import com.xiaoyai.common.core.domain.entity.SysUser;
import com.xiaoyai.common.utils.SecurityUtils;
import com.xiaoyai.system.service.ISysUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author junyang
 * @date 2021-10-27
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProcessServiceImpl extends ServiceImpl<ProcessServiceDaoImplMapper, ActAllProc> implements ProcessService {

    @Autowired
    ActivitiConfig activitiConfig;
    @Autowired
    ImageService imageService;

    @Autowired
    public RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;

    @Autowired
    public TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;

    ProcessServiceDaoImplMapper daoImplMapper;

    ActApprovalRecordMapper actApprovalRecordMapper;

    @Autowired
    private RoleProcessService roleProcessService;

    @Autowired
    private ISysUserService iSysUserService;

    @Override
    public List<ProcessDto> queryProcesses(Map info) {
        return BeanConvertUtil.covertBeanList(daoImplMapper.queryProcesses(info), ProcessDto.class);
    }

    @Override
    public String startFlow(Map info) {
        return null;
    }

    @Override
    public MyProcessDto listMyTodoFlow(ProcessToDoListParamDto processToDoListParamDto) {
        MyProcessDto myProcessDto = new MyProcessDto();
        String userId = SecurityUtils.getUserId().toString();

        int pageNo = processToDoListParamDto.getPage();
        int pageSize = processToDoListParamDto.getRow();
        if (pageNo != com.java110.dto.PageDto.DEFAULT_PAGE) {
            pageNo = (pageNo - 1) * pageSize;
        } else {
            pageNo = processToDoListParamDto.getStart();
        }
        processToDoListParamDto.setPage(pageNo);
        processToDoListParamDto.setUserId(userId);
        int allTaskListCount = daoImplMapper.getMyToDoProcessCount(processToDoListParamDto);
        if (allTaskListCount <= 0) {
            return myProcessDto;
        }
        //获取待办详情
        List<MyProcessApplyDto> myProcessApplyDtos = BeanConvertUtil.covertBeanList(daoImplMapper.getTodoTaskDetail(processToDoListParamDto), MyProcessApplyDto.class);
        for (MyProcessApplyDto dto : myProcessApplyDtos) {
            if (StrUtil.isEmpty(dto.getDelegation())) {
                dto.setDelegation("");
            }
            if (StrUtil.isEmpty(dto.getTaskDescriptionSupplement())) {
                dto.setTaskDescriptionSupplement(dto.getTaskDescription());
            }
            if (StrUtil.isEmpty(dto.getNodeName())) {
                dto.setNodeName("已结束");
                dto.setStatusType(ProcessStatusEnum.RUNNING.getValue());
            } else if (ProcessNodeNameEnum.STARTER.getValue().equals(dto.getNodeName())) {
                dto.setNodeName(ProcessNodeNameEnum.STARTER.getDesc());
            }
        }
        myProcessDto.setTotal(allTaskListCount);
        myProcessDto.setRecords((int) Math.ceil((double) allTaskListCount / (double) pageSize));
        myProcessDto.setMyProcessApplyDtos(myProcessApplyDtos);
        return myProcessDto;
    }

    @Override
    public void addTaskDesc(Map info) {

    }

    @Override
    public List<Map> queryTaskDetail(Map info) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String processApproval(ProcessCompleteParamDto processCompleteParamDto) {
        com.xiaoyai.common.core.domain.entity.SysUser sysUser = SecurityUtils.getLoginUser().getUser();
        //任务id
        String taskId = processCompleteParamDto.getTaskId();
        //流程实例id
        String processInstanceId = processCompleteParamDto.getProcessInstanceId();
        //获取当前任务
        Task task = taskService.createTaskQuery().taskId(taskId).active().singleResult();
        if (StrUtil.isNotEmpty(processCompleteParamDto.getDelegation())
                && ProcessDelegationEnum.PENDING.getValue().equals(processCompleteParamDto.getDelegation())) {
            //当流程为委托人提交
            processOpinionSeekApproval(task, taskId, processCompleteParamDto);
            return "success";
        } else if (StrUtil.isNotEmpty(processCompleteParamDto.getDelegation())
                && ProcessDelegationEnum.FORWARD.getValue().equals(processCompleteParamDto.getDelegation())) {
            //当流程是转发提交时
            processTransferApproval(taskId, processCompleteParamDto);
            return "success";

        } else {
            Map map = new HashMap();
            List<ActFormPermission> actFormPermissionList = new ArrayList<>();
            if (task != null) {
                map.put("taskDefinitionKey", task.getTaskDefinitionKey());
                map.put("formId", task.getProcessDefinitionId().split(":")[0]);
                //根据task定义信息获取流程可编辑列表
                actFormPermissionList = BeanConvertUtil.covertBeanList(daoImplMapper.getAllEditFormByTaskDefinitionKey(map), ActFormPermission.class);
            }
            boolean updateFlag = getUpdateProcessFormDateFlag(actFormPermissionList);
            if (StrUtil.isNotEmpty(processCompleteParamDto.getWithdrawEditStatus())
                    && ProcessStatusEnum.WITHDRAW.getValue().equals(processCompleteParamDto.getWithdrawEditStatus())) {
                updateFlag = true;
            }
            if (updateFlag) {
                //更新流程表单数据
                log.info("审批更新流程表单：{}", processCompleteParamDto.toString());
                JSONObject jsonObject = processCompleteParamDto.getFormDate();
                if (jsonObject != null && jsonObject.size() > 0) {
                    jsonObject.put("processInstanceId", processInstanceId);
                    ProcessCreateRequest reqest = updateTaskFormDate(jsonObject);
                    processCompleteParamDto.setRelatedParameterData(reqest.getParamMap());
                }
            }
            //更新流程运行参数
            Map paramMap = processCompleteParamDto.getRelatedParameterData();
            if (paramMap != null && paramMap.size() > 0) {
                taskService.setVariables(taskId, paramMap);
            }
            updateProcessStatus(processInstanceId, ProcessStatusEnum.RUNNING.getValue());
            Task curTask = taskService.createTaskQuery().taskId(taskId).singleResult();
            if (curTask != null && StrUtil.isEmpty(curTask.getAssignee())) {
                //assignee为空说明是组任务
                //组任务需要先领用
                taskService.claim(taskId, sysUser.getUserId().toString());
            }
            //审批评论
            String comment = StrUtil.isEmpty(processCompleteParamDto.getComment()) ? "" : processCompleteParamDto.getComment();
            taskService.addComment(taskId, processInstanceId, comment);
            //保存流程审批记录
            ActApprovalRecord actApprovalRecordDto = getProcessApprovalDto(sysUser.getUserId().toString(), curTask, comment);
            actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.APPROVE.getValue());
            actApprovalRecordDto.setTaskCommentFile(processCompleteParamDto.getCommentFile());
            actApprovalRecordMapper.insert(actApprovalRecordDto);
            taskService.complete(taskId);
            return "success";
        }
    }

    private ProcessCreateRequest updateTaskFormDate(JSONObject reqParam) {
        String procNum = reqParam.getString("procNum");
        if (StrUtil.isNotEmpty(procNum) && procNum.indexOf("-detail") > 0) {
            procNum = procNum.replace("-detail", "");
        }
        ProcessHandleBase processHandleBase = (ProcessHandleBase) ApplicationContextFactory.getBean(procNum);
        //发起流程数据构建
        ProcessCreateRequest request = processHandleBase.beforeProcessHandle(reqParam);
        Map paramMap = new HashMap(16);
        paramMap.put("processInstanceId", reqParam.getString("processInstanceId"));
        paramMap.put("formValue", request.getFormComponentValues());
        daoImplMapper.updateProcessDesc(paramMap);
        return request;
    }

    /**
     * 是否更新表单数据
     */
    private boolean getUpdateProcessFormDateFlag(List<ActFormPermission> actFormPermissionList) {
        boolean flag = false;
        if (actFormPermissionList == null && actFormPermissionList.size() <= 0) {
            return flag;
        }
        for (ActFormPermission actFormPermission : actFormPermissionList) {
            if ("true".equals(actFormPermission.getReadOnly())) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public List<Map> queryApprovalList(Map info) {
        return null;
    }

    @Override
    public String searchTaskDetail(ProcessDetailParamDto processDetailParamDto) {
        return null;
    }

    @Override
    public MyProcessDto listMyDnoeProcess(ProcessDoneReqParamDto reqParamDto) {
        MyProcessDto myProcessDto = new MyProcessDto();
        int pageSize = reqParamDto.getRow();
        int pageNo = reqParamDto.getPage();
        if (pageNo != com.java110.dto.PageDto.DEFAULT_PAGE) {
            pageNo = (pageNo - 1) * pageSize;
        } else {
            pageNo = reqParamDto.getStart();
        }
        String userId = SecurityUtils.getUserId().toString();
        reqParamDto.setUserId(userId);
        reqParamDto.setPage(pageNo);
        List<MyProcessApplyDto> myProcessApplyDtos = new ArrayList();
        int allTaskListCount = 0;
        //获取已办总数
        List<Map> resultList = daoImplMapper.getMyDoneProcessCount(BeanConvertUtil.beanCovertMap(reqParamDto));
        allTaskListCount = resultList.size();
        if (allTaskListCount <= 0) {
            log.info("该用户没有已办事项，用户id：{}", reqParamDto.getUserId());
            return myProcessDto;
        }
        //获取已办详情
        List<Map> paramList = new ArrayList<>();
        if (pageNo > allTaskListCount) {
            log.info("分页超过最大数量：{}", reqParamDto.getUserId());
            myProcessDto.setTotal(allTaskListCount);
            return myProcessDto;
        } else if (pageNo + pageSize < allTaskListCount) {
            paramList = resultList.subList(pageNo, pageNo + pageSize);
        } else {
            paramList = resultList.subList(pageNo, allTaskListCount);
        }
        Map paramMap = new HashMap();
        paramMap.put("instanceIds", paramList);
        paramMap.put("userId", reqParamDto.getUserId());
        myProcessApplyDtos = BeanConvertUtil.covertBeanList(daoImplMapper.queryDoneTaskDetail(paramMap), MyProcessApplyDto.class);
        for (MyProcessApplyDto dto : myProcessApplyDtos) {
            //查询操作记录
            List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessEffectiveCommentList(BeanConvertUtil.beanCovertMap(dto)), ActApprovalRecordDto.class);
            //剔除掉自动审批的
            actApprovalRecordDtoList = actApprovalRecordDtoList.stream().filter(record -> !"自动审批".equals(record.getTaskComment())).collect(Collectors.toList());
            if (actApprovalRecordDtoList == null || actApprovalRecordDtoList.size() <= 0) {
                dto.setOwner("");
            } else {
                if (StrUtil.isNotEmpty(actApprovalRecordDtoList.get(0).getApprovalUserId())
                        && actApprovalRecordDtoList.get(0).getApprovalUserId().equals(reqParamDto.getUserId())
                        && ProcessApprovalSignEnum.APPROVE.getValue().equals(actApprovalRecordDtoList.get(0).getApprovalSign())) {
                    dto.setOwner("");
                } else {
                    dto.setOwner(actApprovalRecordDtoList.get(0).getApprovalUserId());
                }
            }
            if (StrUtil.isEmpty(dto.getTaskDescriptionSupplement())) {
                dto.setTaskDescriptionSupplement(dto.getTaskDescription());
            }
            //当前节点无信息 代表流程已完成
            if (StrUtil.isEmpty(dto.getNodeName())) {
                dto.setNodeName("已结束");
                dto.setOwner(actApprovalRecordDtoList.get(0).getApprovalUserId());
            } else if (ProcessNodeNameEnum.STARTER.getValue().equals(dto.getNodeName())) {
                dto.setNodeName(ProcessNodeNameEnum.STARTER.getDesc());
            }
        }
        myProcessDto.setMyProcessApplyDtos(myProcessApplyDtos);
        myProcessDto.setTotal(allTaskListCount);
        myProcessDto.setRecords((int) Math.ceil((double) allTaskListCount / (double) pageSize));

        return myProcessDto;
    }

    @Override
    public String saveFlow(Map info) {
        return null;
    }

    @Override
    public MyProcessDto listMyApplyProcess(ProcessMyRequestListParamDto requestListParamDto) {
        if (StrUtil.isEmpty(requestListParamDto.getType())) {
            requestListParamDto.setType("-1");
        }
        MyProcessDto myProcessDto = new MyProcessDto();
        int page = requestListParamDto.getPage();
        int row = requestListParamDto.getRow();
        if (page != com.java110.dto.PageDto.DEFAULT_PAGE) {
            requestListParamDto.setPage((page - 1) * requestListParamDto.getRow());
        }
        String employeeId = SecurityUtils.getUserId().toString();
        requestListParamDto.setUserId(employeeId);
        //查询我的请求
        Map info = BeanConvertUtil.beanCovertMap(requestListParamDto);
        List<MyProcessApplyDto> myProcessApplyDtos = new ArrayList();
        int allTaskListCount = 0;
        long startTime = System.currentTimeMillis();
        //获取我的请求总数
        List<Map> allList = daoImplMapper.getMyApplyProcessCount(info);
        List<Map> resultList = new ArrayList<>();
        if (requestListParamDto.getType().equalsIgnoreCase(ProcessMyRequestTypeEnum.ALL.getValue())) {
            resultList = allList;
        }
        if (requestListParamDto.getType().equalsIgnoreCase(ProcessMyRequestTypeEnum.END.getValue())) {
            List<Map> finalResultList = resultList;
            allList.forEach(item -> {
                if (item.get("defId") == null) {
                    finalResultList.add(item);
                }
            });
        }
        if (requestListParamDto.getType().equalsIgnoreCase(ProcessMyRequestTypeEnum.RUNNING.getValue())) {
            List<Map> finalResultList = resultList;
            allList.forEach(item -> {
                if (item.get("defId") != null) {
                    finalResultList.add(item);
                }
            });
        }
        allTaskListCount = resultList.size();
        myProcessDto.setTotal(allTaskListCount);
        if (allTaskListCount <= 0) {
            log.info("获取我的请求数量为零，参数：{}", requestListParamDto.toString());
            return myProcessDto;
        }
        List<Map> paramList = new ArrayList<>();
        if ((page - 1) * row > allTaskListCount) {
            return myProcessDto;
        } else if (page * row < allTaskListCount) {
            paramList = resultList.subList((page - 1) * row, page * row);
        } else {
            paramList = resultList.subList((page - 1) * row, allTaskListCount);
        }
        info.put("instanceIds", paramList);
        myProcessApplyDtos = listMyApplyProcess(info);

        if (myProcessApplyDtos != null && myProcessApplyDtos.size() > 0) {
            for (MyProcessApplyDto dto : myProcessApplyDtos) {
                //获取节点审批信息
                List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessEffectiveCommentList(BeanConvertUtil.beanCovertMap(dto)), ActApprovalRecordDto.class);
                if (actApprovalRecordDtoList != null && actApprovalRecordDtoList.size() > 0) {
                    dto.setShowWithdrawFlag(true);
                    for (ActApprovalRecordDto recordDto : actApprovalRecordDtoList) {
                        if (!ProcessNodeNameEnum.STARTER.getValue().equals(recordDto.getTaskDefinitionName())
                                && !"自动审批".equals(recordDto.getTaskComment())) {
                            dto.setShowWithdrawFlag(false);
                            break;
                        }
                    }
                }
                //当前节点无信息 代表流程已完成
                if (StrUtil.isEmpty(dto.getNodeName())) {
                    dto.setNodeName("已结束");
                } else if (ProcessNodeNameEnum.STARTER.getValue().equals(dto.getNodeName())) {
                    dto.setNodeName(ProcessNodeNameEnum.STARTER.getDesc());
                }
                if (!StrUtil.isEmpty(dto.getCurTaskHandler())) {
                    dto.setCurTaskHandler("true");
                } else {
                    dto.setCurTaskHandler("false");
                }
                if (!StrUtil.isEmpty(dto.getCurTaskId())) {
                    dto.setTaskId(dto.getCurTaskId());
                }
                if (StrUtil.isBlankIfStr(dto.getTaskDescriptionSupplement())) {
                    dto.setTaskDescriptionSupplement(dto.getTaskDescription());
                }
            }
        }
        //获取流程状态
        myProcessDto.setTotal(allTaskListCount);
        myProcessDto.setRecords((int) Math.ceil((double) allTaskListCount / (double) requestListParamDto.getRow()));
        myProcessDto.setMyProcessApplyDtos(myProcessApplyDtos);
        return myProcessDto;
    }

    private List<MyProcessApplyDto> listMyApplyProcess(Map info) {
        List<Map> queryProcesses = daoImplMapper.queryMyApplyProcess(info);

        List<MyProcessApplyDto> processApplyDtos = new ArrayList<>();
        if (queryProcesses == null || queryProcesses.size() <= 0) {
            return processApplyDtos;
        }
        processApplyDtos = BeanConvertUtil.covertBeanList(queryProcesses, MyProcessApplyDto.class);
        //获取额外需要的字段
        List<Map> resultList = daoImplMapper.queryMyApplyProcessOtherFiled(info);
        if (resultList != null && resultList.size() > 0) {
            AtomicInteger i = new AtomicInteger();
            processApplyDtos.forEach(item -> {
                item.setTaskId(StrUtil.isBlankIfStr(resultList.get(i.get()).get("taskId")) ? "" : resultList.get(i.get()).get("taskId") + "");
                item.setNodeName(StrUtil.isBlankIfStr(resultList.get(i.get()).get("nodeName")) ? "" : resultList.get(i.get()).get("nodeName") + "");
                i.getAndIncrement();
            });
        }
        return processApplyDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String backFlow(ProcessBackFlowParamDto processCompleteParamDto) {
        String result = "success";
        log.info("流程回退，流程信息：{}", processCompleteParamDto.toString());
        if (!"发起人".equals(processCompleteParamDto.getTaskDefinitionName())) {
            processCompleteParamDto.setStatusType(ProcessStatusEnum.RUNNING.getValue());
        }
        //判断流程退回是否选择退回节点
        if (StrUtil.isEmpty(processCompleteParamDto.getTaskDefinitionId())) {
            log.error("流程退回未选择节点，流程信息：{}", processCompleteParamDto.toString());
            return "fail";
        }
        //流程实例id
        String instanceId = processCompleteParamDto.getProcessInstanceId();
        String comment = processCompleteParamDto.getComment();
        //用户id
//        String userId = processCompleteParamDto.getUserId();
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(instanceId).active().list();
        if (taskList == null || taskList.size() <= 0) {
            result = "fail";
            log.error("流程退回获取流程当前任务为空，流程信息：{}", processCompleteParamDto.toString());
            return result;
        }
        Task task = taskList.get(0);
        String processDefinitionId = task.getProcessDefinitionId();
//        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);

        //变量 退回至任务定义结点
        String myActivityId = processCompleteParamDto.getTaskDefinitionId();
        FlowNode myFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(myActivityId);

        Execution execution = runtimeService.createExecutionQuery().executionId(task.getExecutionId()).singleResult();
        String activityId = execution.getActivityId();

        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);

        //记录原活动方向
        List<SequenceFlow> oriSequenceFlows = new ArrayList<SequenceFlow>();
        oriSequenceFlows.addAll(flowNode.getOutgoingFlows());

        //清理活动方向
        flowNode.getOutgoingFlows().clear();
        try {
            //建立新方向
            List<SequenceFlow> newSequenceFlowList = new ArrayList<SequenceFlow>();
            SequenceFlow newSequenceFlow = new SequenceFlow();
            newSequenceFlow.setId("newSequenceFlowId");
            newSequenceFlow.setSourceFlowElement(flowNode);
            newSequenceFlow.setTargetFlowElement(myFlowNode);
            newSequenceFlowList.add(newSequenceFlow);
            flowNode.setOutgoingFlows(newSequenceFlowList);

            taskService.addComment(task.getId(), task.getProcessInstanceId(), comment);
            //设置流程状态
            updateProcessStatus(instanceId, StrUtil.isEmpty(processCompleteParamDto.getStatusType())
                    ? ProcessStatusEnum.WITHDRAW.getValue() : processCompleteParamDto.getStatusType());
            Map<String, Object> currentVariables = new HashMap<String, Object>();
            //完成任务
            //清空回退节点之后的所有审批记录
            actApprovalRecordMapper.delete(Wrappers.<ActApprovalRecord>query().lambda()
                    .ge(ActApprovalRecord::getTaskId, processCompleteParamDto.getTaskId())
                    .eq(ActApprovalRecord::getProcInstanceId, processCompleteParamDto.getProcessInstanceId()));
            //保存流程审批记录
            Task runTask = taskService.createTaskQuery().processInstanceId(processCompleteParamDto.getProcessInstanceId()).active().list().get(0);
            String employeeId = SecurityUtils.getUserId().toString();
            ActApprovalRecord actApprovalRecordDto = getProcessApprovalDto(employeeId, runTask, comment);
            actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.SAND_BACK.getValue());
            if (!ProcessApprovalSignEnum.APPROVAL_BACK.getValue().equals(actApprovalRecordDto.getTaskComment())) {
                actApprovalRecordMapper.insert(actApprovalRecordDto);
            }
            for (Task taskDto : taskList) {
                taskService.complete(taskDto.getId());
            }
            flowNode.setOutgoingFlows(oriSequenceFlows);
            //退回后续操作
            Map info = BeanConvertUtil.beanCovertMap(processCompleteParamDto);
            //根据instanceId查询proc_num
            Map procMap = daoImplMapper.getProcInfoByInstanceId(MapUtil.builder("instanceId", processCompleteParamDto.getProcessInstanceId()).build());
            info.putAll(procMap);
            Task currentTask = taskService.createTaskQuery().processInstanceId(instanceId).active().singleResult();
            info.put("taskId", currentTask.getId());
            info.put("instanceId", instanceId);
            handleAfterProcessWithdraw(info);
            result = "success";
        } catch (Exception e) {
            flowNode.setOutgoingFlows(oriSequenceFlows);
            result = "fail";
            log.info("流程退回失败，流程相关信息：{}", processCompleteParamDto.toString());
        }

        return result;
    }

    private void handleAfterProcessWithdraw(Map info) {
        ProcessHandleBase processHandleBase = (ProcessHandleBase) ApplicationContextFactory.getBean(info.get("procNum") + "");
        processHandleBase.afterProcessBackHandle(info);
    }

    @Override
    public String listProcessStatus(Map info) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String startOrSaveProcess(JSONObject reqParam) {
        ProcessHandleBase processHandleBase = (ProcessHandleBase) ApplicationContextFactory.getBean(reqParam.getString("procNum"));
        if (processHandleBase == null) {
            log.error("{}流程提交获取不到处理类", reqParam.getString("procName"));
            return "failed";
        }
        //发起流程数据构建
        ProcessCreateRequest request = processHandleBase.beforeProcessHandle(reqParam);
        //设置请求类型
        request.setStartType(reqParam.getString("startType"));
        //设置流程实例id
        request.setProcessInstanceId(reqParam.getString("processInstanceId"));
        //设置流程发起签字意见
        String commentFile = reqParam.getString("commentFile");
        if (!StrUtil.isBlankIfStr(commentFile)) {
            request.setCommentFile(commentFile);
        }
        //设置流程参数
        Map paramMap = new HashMap();
        if (request.getParamMap() != null) {
            paramMap.putAll(request.getParamMap());
        }
        //根据申请人id获取申请人的安全级别
        paramMap.put("employeeId", request.getOriginatorUserId());
        paramMap.put("deptId", request.getDeptId() + "");
        paramMap.put("testFlag", reqParam.getString("testFlag"));
        //设置流程状态
        if (ProcessStartOrSaveEnum.PROCESS_SAVE.getValue().equals(request.getStartType())) {
            paramMap.put(ProcessConstant.PROCESS_STATUS, ProcessStatusEnum.SAVE.getValue());
        }
        if (ProcessStartOrSaveEnum.PROCESS_START.getValue().equals(request.getStartType())) {
            paramMap.put(ProcessConstant.PROCESS_STATUS, ProcessStatusEnum.RUNNING.getValue());
        }
        request.setParamMap(paramMap);

        //调用服务发起流程
        String instanceId = "";
        //流程是发起
        if (ProcessStartOrSaveEnum.PROCESS_START.getValue().equals(request.getStartType())) {
            log.info("发起流程，参数：{}", request.toString());
            Task task = processStartFirstStep(request);
            //提交流程
            //如果是测试则不用继续提交了 因为已经审核完了
            if (!ProcessConstant.TEST_FLAG.equals(request.getParamMap().get("testFlag"))) {
                String comment = StrUtil.isEmpty(request.getComment()) ? "" : request.getComment();
                taskService.addComment(task.getId(), task.getProcessInstanceId(), comment);
                instanceId = task.getProcessInstanceId();
                //保存审批记录
                ActApprovalRecord actApprovalRecordDto = getProcessApprovalDto(request.getOriginatorUserId(), task, comment);
                actApprovalRecordDto.setTaskCommentFile(StrUtil.isEmpty(request.getCommentFile()) ? "" : request.getCommentFile());
                actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.SUBMIT.getValue());
                actApprovalRecordMapper.insert(actApprovalRecordDto);

                taskService.complete(task.getId());
            }
            //当流程实例之前已经存在 先删除之前保存的流程
            if (StrUtil.isNotEmpty(request.getProcessInstanceId())) {
                deleteProcessByProcessInstanceId(request.getProcessInstanceId());
            }
        }
        //保存流程
        if (ProcessStartOrSaveEnum.PROCESS_SAVE.getValue().equals(request.getStartType())) {
            log.info("保存流程，参数：{}", request.toString());
            //当流程实例之前不存在时 保存流程
            Task task = processStartFirstStep(request);
            //当流程实例之前已经存在 1.更新表单内容
            if (StrUtil.isNotEmpty(request.getProcessInstanceId())) {
                deleteProcessByProcessInstanceId(request.getProcessInstanceId());
            }
            instanceId = task.getProcessInstanceId();
        }
        //流程调用成功之后处理
        reqParam.put("instanceId", instanceId);
        // processHandleBase.afterProcessHandle(reqParam);
        return instanceId;
    }

    private void deleteProcessByProcessInstanceId(String processInstanceId) {
        Map paramMap = new HashMap(16);
        paramMap.put("processInstanceId", processInstanceId);
        //删除之前的流程实例
        daoImplMapper.deleteProcessByProcessInstanceId(processInstanceId);
        if (runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).count() > 0) {
            runtimeService.deleteProcessInstance(processInstanceId, "");
        }
        //删除描述
        daoImplMapper.removeProcessDesc(paramMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String processWithdraw(ProcessWithdrawReqParam request) {
        boolean withdrawFlag = false;

        Task task = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).singleResult();
//        sendDeleteKafkaMsg(request.getProcessInstanceId(), task.getId());

        //获取节点审批信息
        List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessEffectiveCommentList(BeanConvertUtil.beanCovertMap(request)), ActApprovalRecordDto.class);
        if (actApprovalRecordDtoList != null && actApprovalRecordDtoList.size() > 0) {
            for (ActApprovalRecordDto recordDto : actApprovalRecordDtoList) {
                if (!ProcessNodeNameEnum.STARTER.getValue().equals(recordDto.getTaskDefinitionName())
                        && !"自动审批".equals(recordDto.getTaskComment())) {
                    withdrawFlag = true;
                    break;
                }
            }
        }
        if (withdrawFlag) {
            log.info("流程已被审批，撤回失败");
            return ProcessWithdrawResultEnum.FAIL.getValue();
        }
        //根据流程实例id获取所有运行参数
        Map paramMap = new HashMap();
        paramMap.put("processInstanceId", request.getProcessInstanceId());
        //发起流程需要相关参数
        Map runMap = new HashMap();
        List<Map> resultList = daoImplMapper.getAllRunVariableByInstanceId(paramMap);
        if (resultList != null && resultList.size() > 0) {
            for (Map map : resultList) {
                if (!StrUtil.isBlankIfStr(map.get("type")) && "integer".equals(map.get("type") + "")) {
                    runMap.put(map.get("name"), Integer.parseInt(map.get("text") + ""));
                } else {
                    runMap.put(map.get("name"), map.get("text") + "");
                }
            }
        }
        //发起新流程
        runMap.put("processStatus", ProcessStatusEnum.SAVE.getValue());
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(request.getProcCode(), runMap);
        //删除老流程实例
        runtimeService.deleteProcessInstance(request.getProcessInstanceId(), "用户撤回");
        //将老流程表单数据指向新流程
        paramMap.put("newProcessInstanceId", instance.getId());
        daoImplMapper.updateProcessDescByOldInstanceId(paramMap);
        log.info("用户撤回流程，参数：{}", request.toString());

        return ProcessWithdrawResultEnum.SUCCESS.getValue();
    }

    @Override
    public MyProcessDto listCompletedProcess(ProcessCompletedListParamDto reqParamDto) {
        MyProcessDto myProcessDto = new MyProcessDto();
        int pageSize = reqParamDto.getRow();
        int pageNo = reqParamDto.getPage();
        if (pageNo != com.java110.dto.PageDto.DEFAULT_PAGE) {
            pageNo = (pageNo - 1) * pageSize;
        }
        String userId = SecurityUtils.getUserId().toString();
        reqParamDto.setUserId(userId);
        reqParamDto.setPage(pageNo);
        List<MyProcessApplyDto> myProcessApplyDtos = new ArrayList();
        int allTaskListCount = 0;
        long startTime = System.currentTimeMillis();
        //获取流程办结总数
        List<Map> resultList = daoImplMapper.getProcessCompletedListCount(BeanConvertUtil.beanCovertMap(reqParamDto));
        allTaskListCount = resultList.size();
        if (allTaskListCount <= 0) {
            return myProcessDto;
        }
        myProcessDto.setTotal(allTaskListCount);
        //获取办结详情
        List<Map> paramList;
        if (pageNo > allTaskListCount) {
            return myProcessDto;
        } else if ((pageNo + 1) * pageSize < allTaskListCount) {
            paramList = resultList.subList(pageNo, (pageNo + 1) * pageSize);
        } else {
            paramList = resultList.subList(pageNo, allTaskListCount);
        }
        Map paramMap = new HashMap();
        paramMap.put("instanceIds", paramList);
        myProcessApplyDtos = BeanConvertUtil.covertBeanList(daoImplMapper.queryProcessCompletedDetail(paramMap), MyProcessApplyDto.class);
        myProcessDto.setMyProcessApplyDtos(myProcessApplyDtos);
        myProcessDto.setTotal((int) allTaskListCount);
        myProcessDto.setRecords((int) Math.ceil((double) allTaskListCount / (double) pageSize));
        return myProcessDto;
    }

    @Override
    public ProcessMonitorVo queryProcessMonitorList(ProcessMonitorReqParam reqParam) {
        return null;
    }

    @Override
    public String startRemindProcess(ProcessCreateRequest request) {
        return null;
    }

    @Override
    public String queryProcessBackNodeList(ProcessBackNodeListReqParam request) {
        List<ActApprovalRecordDto> resultList = new ArrayList<>();
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .processInstanceId(request.getProcessInstanceId())
                .orderByHistoricTaskInstanceStartTime()
                .asc()
                .list();
        if (list == null || list.size() <= 0) {
            return JSONObject.toJSONString(resultList);
        }
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).active().list();
        Set<String> nodeNameSet = new HashSet<>();
        for (HistoricTaskInstance dto : list) {
            if (!dto.getName().equals(taskList.get(0).getName())) {
                if (nodeNameSet.contains(dto.getName())) {
                    continue;
                }
                ActApprovalRecordDto actApprovalRecordDto = new ActApprovalRecordDto();
                actApprovalRecordDto.setProcInstanceId(request.getProcessInstanceId());
                actApprovalRecordDto.setTaskDefinitionKey(dto.getTaskDefinitionKey());
                actApprovalRecordDto.setTaskDefinitionName(dto.getName());
                actApprovalRecordDto.setTaskId(dto.getId());
                resultList.add(actApprovalRecordDto);
                nodeNameSet.add(dto.getName());
            } else {
                break;
            }
        }
        return JSONObject.toJSONString(resultList);
    }

    @Override
    public String deleteProcess(ProcessDeleteReqParam request) {
        //根据实例id获取实例详情
        List<ProcessInstance> List = runtimeService.createProcessInstanceQuery().processInstanceId(request.getProcessInstanceId()).active().list();
        if (List == null || List.size() <= 0) {
            //删除流程历史实例
            deleteHistoryProcessByProcessInstanceId(request.getProcessInstanceId());
            log.info("删除历史流程，参数：{}", request.toString());
        } else {
            deleteProcessByProcessInstanceId(request.getProcessInstanceId());
        }
        return "success";
    }

    /**
     * 根据流程实例id删除历史实例流程
     */
    private void deleteHistoryProcessByProcessInstanceId(String processInstanceId) {
        //删除之前的流程实例
        historyService.deleteHistoricProcessInstance(processInstanceId);
        //删除描述
        Map paramMap = new HashMap(16);
        paramMap.put("processInstanceId", processInstanceId);
        daoImplMapper.removeProcessDesc(paramMap);
    }

    @Override
    public String processApprovalWithdraw(ProcessApprovalWithdrawReqParam request) {
        String message = "";
        String instanceId = request.getProcessInstanceId();
        /**
         * 获取任务历史节点信息
         * */
        List<ProcessHistoryTaskInfoDto> taskInfoDtos = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessHistoryTaskInfoList(BeanConvertUtil.beanCovertMap(request)), ProcessHistoryTaskInfoDto.class);
        //获取流程审批记录
        Map map = new HashMap();
        map.put("processInstanceId", instanceId);
        List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessApprovalRecord(map), ActApprovalRecordDto.class);
        if (taskInfoDtos == null || taskInfoDtos.size() <= 0) {
            log.info("流程撤回审批查找历史节点为空，参数：{}", request.toString());
            return "服务器错误，请联系管理员";
        }
        //删除相关历史节点
        List<ProcessHistoryTaskInfoDto> paramList = new ArrayList<>();
        for (int i = 0; i < taskInfoDtos.size(); i++) {
            ProcessHistoryTaskInfoDto dto = taskInfoDtos.get(i);
            //当前节点未审批或是自动审批则继续判断
            if (StrUtil.isEmpty(dto.getEndTime()) || "自动审批".equals(dto.getTaskComment())) {
                paramList.add(dto);
                continue;
            }
            if (request.getEmployeeId().equals(dto.getAssignee())) {
                //如果上一结点办理人就是当前用户则可撤回
                ProcessBackFlowParamDto processBackFlowParamDto = new ProcessBackFlowParamDto();
                processBackFlowParamDto.setTaskDefinitionId(dto.getTaskDefinitionKey());
                processBackFlowParamDto.setProcessInstanceId(instanceId);
                processBackFlowParamDto.setComment(ProcessApprovalSignEnum.APPROVAL_BACK.getValue());
                processBackFlowParamDto.setStatusType(ProcessStatusEnum.RUNNING.getValue());
                processBackFlowParamDto.setTaskId(dto.getTaskId());
                backFlow(processBackFlowParamDto);
                log.info("流程已撤回，流程实例id：{}，撤回人id：{}", request.getProcessInstanceId(), request.getEmployeeId());
                //删除相关历史节点
                Map paramMap = new HashMap(16);
                paramList.add(dto);
                paramMap.put("taskList", paramList);
                daoImplMapper.deleteProcessHistoryTask(paramMap);
                //删除历史流转
                daoImplMapper.deleteProcessHisActInstance(paramMap);
                //删除撤回前的评论
                daoImplMapper.deleteProcessWithdrawTaskComment(BeanConvertUtil.beanCovertMap(dto));
                int index = 0;
                int count = 0;
                for (ActApprovalRecordDto recordDto : actApprovalRecordDtoList) {
                    if (dto.getTaskId().equals(recordDto.getTaskId())) {
                        count = index;
                        break;
                    }
                    index++;
                }
                if (count > 0) {
                    List<ActApprovalRecordDto> resultList = actApprovalRecordDtoList.subList(count, actApprovalRecordDtoList.size());
                    Map info = new HashMap();
                    info.put("taskIds", resultList);
                    if (resultList != null && resultList.size() > 0) {
                        daoImplMapper.deleteProcessApprovalRecordByTaskId(info);
                    }
                }
                message = "撤回成功";
                break;
            } else {
                message = "流程已被审批，无法撤回";
                log.info("流程已被审批，无法撤回，流程实例id：{}，撤回人id：{}", request.getProcessInstanceId(), request.getEmployeeId());
                break;
            }
        }
        return message;
    }

    @Override
    public String processForward(ProcessForwardReqParam request) {
        //向act_ru_task添加一条任务
        ActRunTaskDto actRunTaskDto = new ActRunTaskDto();
        ActApprovalRecord actApprovalRecordDto = new ActApprovalRecord();
        String taskId = IdUtil.fastSimpleUUID();
        actRunTaskDto.setId(taskId);
        actRunTaskDto.setProcessInstanceId(request.getProcessInstanceId());
        actRunTaskDto.setAssignee(request.getForwardEmployeeId());
        actRunTaskDto.setPriority(50);
        actRunTaskDto.setCreateTime(DateUtil.getFormatTimeString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        actRunTaskDto.setDelegation(ProcessDelegationEnum.FORWARD.getValue());
        int executionResult = daoImplMapper.getRunExecutionRecord(BeanConvertUtil.beanCovertMap(request));
        if (executionResult <= 0) {
            daoImplMapper.insertRecordToRunExecut(BeanConvertUtil.beanCovertMap(request));
        }
        List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).active().list();
        if (runTaskList != null && runTaskList.size() > 0) {
            actRunTaskDto.setName(runTaskList.get(0).getName());
            actRunTaskDto.setTaskDefinitionKey(runTaskList.get(0).getTaskDefinitionKey());
            actApprovalRecordDto.setTaskDefinitionName(runTaskList.get(0).getName());
            actApprovalRecordDto.setTaskDefinitionKey(runTaskList.get(0).getTaskDefinitionKey());
        } else {
            List<HistoricTaskInstance> hisTasklist = historyService.createHistoricTaskInstanceQuery().processInstanceId(request.getProcessInstanceId())
                    .orderByHistoricTaskInstanceEndTime().desc().list();
            if (hisTasklist != null && hisTasklist.size() > 0) {
                HistoricTaskInstance taskInstance = hisTasklist.get(0);
                actRunTaskDto.setName(taskInstance.getName());
                actRunTaskDto.setTaskDefinitionKey(taskInstance.getTaskDefinitionKey());
                actApprovalRecordDto.setTaskDefinitionName(taskInstance.getName());
                actApprovalRecordDto.setTaskDefinitionKey(taskInstance.getTaskDefinitionKey());
            }
        }
        //查找有没有此流程待办记录
        List<Map> procList = daoImplMapper.getProcessToDoListByProcessInstanceId(BeanConvertUtil.beanCovertMap(request));
        if (procList == null || procList.size() <= 0) {
            daoImplMapper.insertRecordToRunTask(BeanConvertUtil.beanCovertMap(actRunTaskDto));
            log.info("流程转发操作，流程实例：{}，被转发人id：{}", request.getProcessInstanceId(), request.getForwardEmployeeId());
        }
        //向act_approval_record添加一条操作记录保存转发意见

        actApprovalRecordDto.setTaskId(taskId);
        actApprovalRecordDto.setProcInstanceId(request.getProcessInstanceId());
        actApprovalRecordDto.setApprovalUserId(SecurityUtils.getUserId().toString());
        actApprovalRecordDto.setTaskComment(request.getForwardComment());
        actApprovalRecordDto.setReceiverId(request.getForwardEmployeeId());
        actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.FORWARD.getValue());
        actApprovalRecordMapper.insert(actApprovalRecordDto);
        return "转发成功";
    }

    @Override
    public boolean deployProcess(ProcessDeployReqParam request) {
        boolean result = false;

        ActAllProc proc = daoImplMapper.selectById(request.getProcId());
        if (proc != null) {

            ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
            Deployment deployment = engine.getRepositoryService().createDeployment()
                    .addString(proc.getProcNum() + ".bpmn", proc.getContext())
                    .key(proc.getProcessCode())
                    .deploy();
            result = StrUtil.isNotEmpty(deployment.getId());
        }
        return result;
    }

    @Override
    public String opinionSeek(ProcessOpinionSeekReqParam request) {
        //任务委派
        Task task = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).active().singleResult();
        if (task != null) {
            //删除转发记录
            Map paramMap = new HashMap();
            paramMap.put("processInstanceId", request.getProcessInstanceId());
            paramMap.put("employeeId", request.getOpinionSeekerId());
            daoImplMapper.deleteProcessForwardRecord(paramMap);
            taskService.delegateTask(task.getId(), request.getOpinionSeekerId());
            ActApprovalRecord actApprovalRecordDto = getProcessApprovalDto(SecurityUtils.getUserId().toString(), task, request.getOpinionSeekComment());
            actApprovalRecordDto.setReceiverId(request.getOpinionSeekerId());
            actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.CONSULTATION.getValue());
            actApprovalRecordMapper.insert(actApprovalRecordDto);
//            sendProcessApprovalNotice(actApprovalRecordDto, "您有一个意见征询的流程正待审批");
            log.info("流程意见征询操作，流程实例：{}，被征询人id：{}", request.getProcessInstanceId(), request.getOpinionSeekerId());
        }
        return "征询成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String processTransfer(ProcessTransferReqParam request) {
        List<Task> taskList = taskService.createTaskQuery().processInstanceId(request.getProcessInstanceId()).active().list();
        Task task = null;
        for (Task taskDto : taskList) {
            if (taskDto.getAssignee().equals(SecurityUtils.getUserId().toString())) {
                task = taskDto;
            }
        }
        if (task == null) {
            return "转办失败";
        }
        //删除转发记录
        Map paramMap = new HashMap();
        paramMap.put("processInstanceId", request.getProcessInstanceId());
        paramMap.put("employeeId", request.getTransferEmployeeId());
        daoImplMapper.deleteProcessForwardRecord(paramMap);
        taskService.setAssignee(task.getId(), request.getTransferEmployeeId());
        ActApprovalRecord actApprovalRecordDto = getProcessApprovalDto(SecurityUtils.getUserId().toString(), task, request.getTransferComment());
        actApprovalRecordDto.setReceiverId(request.getTransferEmployeeId());
        actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.TRANSFER.getValue());
        actApprovalRecordMapper.insert(actApprovalRecordDto);
        //转办向历史表里插入一条数据
        Map info = new HashMap();
        info.put("id", IdUtil.fastSimpleUUID());
        info.put("taskDefinitionKey", task.getTaskDefinitionKey());
        info.put("processInstanceId", task.getProcessInstanceId());
        info.put("name", task.getName());
        info.put("assignee", task.getAssignee());
        info.put("createTime", task.getCreateTime());
        info.put("endTime", new Date());
        daoImplMapper.insertProcessTransferRecordToHisTask(info);
//        sendProcessApprovalNotice(actApprovalRecordDto, "您有一个转办的流程正待审批");
//        sendProcessTransfer(actApprovalRecordDto, request);
        log.info("流程转办操作，流程实例：{}，被转办人id：{}", request.getProcessInstanceId(), request.getTransferEmployeeId());
        return "转办成功";
    }


    @Override
    public String remindProcessHandle(String processInstanceId) {
        return null;
    }

    @Override
    public List<ActFormPermission> getUserEditViewAuthority(Map info) {
        String userId = info.get("userId") + "";
        String taskId = info.get("taskId") + "";
        String instanceId = info.get("processInstanceId") + "";
        List<ActFormPermission> actFormPermissionList = new ArrayList<>();
        //根据流程实例id获取流程详情
        ProcessDescDto processDescDto = BeanConvertUtil.covertBean(daoImplMapper.queryProcessDescByInstanceId(instanceId), ProcessDescDto.class);
        List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.queryAllApprovalComment(info), ActApprovalRecordDto.class);
        if (actApprovalRecordDtoList == null || actApprovalRecordDtoList.size() <= 0) {
            return actFormPermissionList;
        }
        String employeeId = SecurityUtils.getUserId().toString();
        //查找当前任务处理人
        List<IdentityLink> identityLinks = new ArrayList<>();
        //当用户是系统管理员时
        if ("30518940136629616640".equals(userId)) {
            //根据流程code查询流程权限
            actFormPermissionList = BeanConvertUtil.covertBeanList(daoImplMapper.getActPermissionList(BeanConvertUtil.beanCovertMap(processDescDto)), ActFormPermission.class);
            if (actFormPermissionList != null && actFormPermissionList.size() > 0) {
                for (ActFormPermission permission : actFormPermissionList) {
                    permission.setReadOnly("false");
                    permission.setViewPermission("true");
                }
            }
            return actFormPermissionList;
        } else {
            //判断当前用户是不是发起人
            boolean isStart = sureUserIsProcessStarter(employeeId, actApprovalRecordDtoList);
            //判断流程是否结束
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();
            if (processInstance == null) {
                //如果是发起人
                if (isStart) {
                    actFormPermissionList = BeanConvertUtil.covertBeanList(daoImplMapper.getActPermissionList(BeanConvertUtil.beanCovertMap(processDescDto)), ActFormPermission.class);
                    if (actFormPermissionList != null && actFormPermissionList.size() > 0) {
                        for (ActFormPermission permission : actFormPermissionList) {
                            permission.setReadOnly("false");
                            permission.setViewPermission("true");
                        }
                    }
                } else {
                    actFormPermissionList = getActPermissionListWayOne(employeeId, actApprovalRecordDtoList, processDescDto);
                }
            } else {
                //判断当前用户是否是处理人
                info.put("employeeId", employeeId);
                boolean isCurTaskHandle = false;
                List<Map> curTaskAssigneeList = daoImplMapper.getCurrentTaskAssigneeList(info);
                if (curTaskAssigneeList != null && curTaskAssigneeList.size() > 0) {
                    isCurTaskHandle = true;
                }
//                boolean isCurTaskHandle = sureUserIsCurTaskHandeler(employeeId, identityLinks);
                if (isCurTaskHandle) {
                    //查找当前任务节点权限
                    Map map = new HashMap();
                    map.put("taskDefinitionKey", info.get("taskDefinitionKey") + "");
                    map.put("formId", processDescDto.getProcessCode());
                    actFormPermissionList = BeanConvertUtil.covertBeanList(daoImplMapper.getAllEditFormByTaskDefinitionKey(map), ActFormPermission.class);
                    if (actFormPermissionList != null && actFormPermissionList.size() > 0) {
                        //根据taskId获取节点记录
                        ActApprovalRecord actApprovalRecord = daoImplMapper.selectActApprovalRecord(info.get("taskId") + "");
                        for (ActFormPermission permission : actFormPermissionList) {
                            permission.setApprovalSign(!StrUtil.isEmptyIfStr(actApprovalRecord) ? actApprovalRecord.getApprovalSign() : "");
                        }
                    }
                } else {
                    //如果当前用户是发起人
                    if (isStart) {
                        actFormPermissionList = BeanConvertUtil.covertBeanList(daoImplMapper.getActPermissionList(BeanConvertUtil.beanCovertMap(processDescDto)), ActFormPermission.class);
                        if (actFormPermissionList != null && actFormPermissionList.size() > 0) {
                            //根据taskId获取节点记录
                            ActApprovalRecord actApprovalRecord = daoImplMapper.selectActApprovalRecord(info.get("taskId") + "");
                            for (ActFormPermission permission : actFormPermissionList) {
                                permission.setReadOnly("false");
                                permission.setViewPermission("true");
                                permission.setApprovalSign(!StrUtil.isEmptyIfStr(actApprovalRecord) ? actApprovalRecord.getApprovalSign() : "");
                            }
                        }
                    } else {
                        actFormPermissionList = getActPermissionListWayOne(employeeId, actApprovalRecordDtoList, processDescDto);
                    }
                }
            }
        }
        return actFormPermissionList;
    }

    @Override
    public void resignationForwarding(JSONObject reqParam) {

    }

    @Override
    public void addNewStaff(JSONObject reqParam) {

    }

    @Override
    public void userProcessOvertimeHandleRemind() {

    }

    @Override
    public byte[] searchProcessPicture(Map map) {
        byte[] processPicture = null;
        String processInstanceId = "";
        if (map.get("processInstanceId") == null) {
            return null;
        }
        processInstanceId = map.get("processInstanceId") + "";
        try {
            processPicture = imageService.getFlowImgByProcInstId(processInstanceId);
            return processPicture;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ProcessListDto> getUserProcessList() {
        List<ProcessListDto> processListDtoList = new ArrayList<>();
        List<Long> roleIds = SecurityUtils.getLoginUser().getUser().getRoles().stream().map(t -> t.getRoleId()).collect(Collectors.toList());
        List<ProcessDto> processDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessListByRoleIds(roleIds), ProcessDto.class);
        Map<String, List<ProcessDto>> processMapData = processDtoList.stream().collect(Collectors.groupingBy(ProcessDto::getProcTypeName));
        Iterator<Map.Entry<String, List<ProcessDto>>> it = processMapData.entrySet().iterator();
        while (it.hasNext()) {
            ProcessListDto newProcessListVo = new ProcessListDto();
            Map.Entry<String, List<ProcessDto>> entry = it.next();
            List<ProcessDto> dtos = entry.getValue();
            String processTypeName = entry.getKey();
            newProcessListVo.setProcTypeName(processTypeName);
            newProcessListVo.setProcessDtoList(dtos);
            processListDtoList.add(newProcessListVo);
        }
        return processListDtoList;
    }

    @Override
    public List<ProcessDto> getUserAllProcessList() {
        List<ProcessListDto> processListDtoList = new ArrayList<>();
        List<Long> roleIds = Arrays.asList(SecurityUtils.getLoginUser().getUser().getRoleIds());
        List<ProcessDto> processDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessListByRoleIds(roleIds), ProcessDto.class);
        return processDtoList;
    }

    @Override
    public ProcessMonitorVo getProcessMonitor(ProcessMonitorReqParam reqParam) {
        ProcessMonitorVo processMonitorVo = new ProcessMonitorVo();
        int pageSize = reqParam.getRow();
        int pageNo = reqParam.getPage();
        if (pageNo != com.java110.dto.PageDto.DEFAULT_PAGE) {
            pageNo = (pageNo - 1) * pageSize;
        }
        reqParam.setPage(pageNo);
        int monitorCount = 0;
        List<Map> resultList = daoImplMapper.getProcessMonitorListCount(BeanConvertUtil.beanCovertMap(reqParam));
        List<MyProcessApplyDto> processApplyDtos = new ArrayList<>();
        if (resultList != null && resultList.size() > 0) {
            monitorCount = resultList.size();
            List<Map> paramList = new ArrayList<>();
            if ((pageNo + pageSize) < monitorCount) {
                paramList = resultList.subList(pageNo, pageNo + pageSize);
            } else {
                paramList = resultList.subList(pageNo, monitorCount);
            }
            Map paramMap = new HashMap();
            paramMap.put("instanceIds", paramList);
            processApplyDtos = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessMonitorList(paramMap), MyProcessApplyDto.class);
            if (processApplyDtos.size() > 0) {
                processApplyDtos.forEach(dto -> {
                    if (!StrUtil.isEmpty(dto.getTaskDescription()) && dto.getTaskDescription().contains("流程测试")) {
                        dto.setProcessIsTest(true);
                    } else {
                        dto.setProcessIsTest(false);
                    }
                    if (StrUtil.isEmpty(dto.getTaskDescriptionSupplement())) {
                        dto.setTaskDescriptionSupplement(dto.getTaskDescription());
                    }
                });
            }
        }
        processMonitorVo.setTotal(monitorCount);
        processMonitorVo.setRecords((int) Math.ceil((double) monitorCount / (double) pageSize));
        processMonitorVo.setProcessApplyDtoList(processApplyDtos);
        return processMonitorVo;
    }

    @Override
    public Map processNewDetail(JSONObject reqParam) {
        String instanceId = reqParam.get("processInstanceId").toString();
        String userId = SecurityUtils.getUserId().toString();
        //获取流程详情
        Map paramMap = new HashMap();
        paramMap.put("processInstanceId", instanceId);
        JSONObject processDetail = new JSONObject();
        Map _detail = daoImplMapper.getProcessDetailInfo(paramMap);
        Object formComonent = JSON.parseObject(_detail.get("formValue").toString());
        processDetail.put("formComonent", formComonent);
        processDetail.put("taskDescription", _detail.get("taskDescription"));
        processDetail.put("taskApplyUserId", _detail.get("taskApplyUserId"));
        processDetail.put("userName", _detail.get("userName"));

        //获取审批意见节点
        List<ProcessCirculationDto> processCirculationDtoList = getProcessCirculationDtoList(instanceId);
        processApprovalRecordReceiver(instanceId);
        //根据流程实例id获取所有审批意见
        List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.queryAllApprovalComment(paramMap), ActApprovalRecordDto.class);
        //是否有提交权限供小程序判断
        boolean submitAuthority = false;
        if (StrUtil.isNotEmpty(reqParam.getString("taskId"))) {
            submitAuthority = sureCurTaskIsRunning(reqParam.getString("taskId"),
                    userId);
        }

        List<ProcessCirculationDto> processApprovalList = getProcessApprovalList(actApprovalRecordDtoList);

        //根据审批人id获取审批人所在部门
        paramMap.put("employeeIds", processCirculationDtoList);
        List<ProcessCommentCirculate> processCommentCirculates = queryProcessCommentCirculateList(paramMap, actApprovalRecordDtoList);

        //获取流程状态
        List<ProcessStatusListDto> processStatusListDtoList = getProcessStatusList(processCirculationDtoList);

        //根据实例id获取当前任务
        Map taskMap = daoImplMapper.getProcessCurrentTask(paramMap);
        if (taskMap == null) {
            taskMap = new HashMap();
        }
        //根据用户是否是当前任务处理人
        taskMap.put("userId", userId);
        taskMap.put("processInstanceId", instanceId);

        List<ActFormPermission> actFormPermissionList = getUserEditViewAuthority(taskMap);

        //获取流程操作判定条件
        Map conditionMap = new HashMap();
        if (StrUtil.isNotEmpty(reqParam.getString("taskId"))) {
            Map taskIdMap = new HashMap();
            taskIdMap.put("taskId", reqParam.getString("taskId"));
            conditionMap = daoImplMapper.getProcessOperationAuthorityByTaskId(taskIdMap);
        }
        processDetail.put("processApprovalList", processApprovalList);
        processDetail.put("processCommentList", processCommentCirculates);
        processDetail.put("processStatusInfo", processStatusListDtoList);
        processDetail.put("actFormPermissionList", actFormPermissionList);
        processDetail.put("processOperationAuthority", conditionMap);
        processDetail.put("submitAuthority", submitAuthority);
        processDetail.put("fileUrl", activitiConfig.getFileUrl());
        return processDetail;
    }

    @Override
    public Boolean saveUser(SysUser userDto) {
        identityService.deleteUser(userDto.getUserId().toString());
        User user = identityService.newUser(userDto.getUserId().toString());
        user.setFirstName(userDto.getNickName());
        identityService.saveUser(user);
        return true;
    }

    @Override
    public int handleAfterProcessCompleted(JSONObject reqParam) {
        ProcessHandleBase processHandleBase = (ProcessHandleBase) ApplicationContextFactory.getBean(reqParam.getString("procNum"));
        if (processHandleBase == null) {
            ValidateUtils.validate(false, "用户服务远程调用出错");
        }
        //发起流程数据构建
        processHandleBase.afterProcessHandle(reqParam);
        return 1;
    }

    @Override
    public List<String> getProcessCodeByRoleId(String roleId) {
        List<ActRoleProcess> list = roleProcessService.list(Wrappers.<ActRoleProcess>query().lambda().eq(ActRoleProcess::getRoleId, roleId));
        return list.stream().map(ActRoleProcess::getProcessCode).collect(Collectors.toList());
    }

    @Override
    public Boolean updateRoleProcessCode(RoleProcessCodeDto data) {
        // 删除之前的数据
        roleProcessService.remove(Wrappers.<ActRoleProcess>query().lambda().eq(ActRoleProcess::getRoleId, data.getRoleId()));
        // 保存新数据
        List<ActRoleProcess> list = data.getProcessCodeList().stream().map(item -> new ActRoleProcess(data.getRoleId(), item)).collect(Collectors.toList());
        return roleProcessService.saveBatch(list);
    }

    @Override
    public Boolean updateProc(ActAllProc proc) {
        return this.baseMapper.updateById(proc) > 0;
    }

    @Override
    public String getEhrProcessTaskList(JSONObject reqJson) {
        ProcessTaskVo processTaskVo = new ProcessTaskVo();
        Integer row = reqJson.getInteger("row");
        Integer page = reqJson.getInteger("page");
        //获取数量
        List<Map> result = queryProcessTaskCount(reqJson.to(Map.class));
        int total = result.size();
        if (total > 0) {
            List<Map> paramList = new ArrayList<>();
            if (page * row < total) {
                paramList = result.subList((page - 1) * row, page * row);
            } else {
                paramList = result.subList((page - 1) * row, total);
            }
            List<ProcessTask> processTasks = queryProcessTaskList(paramList);
            processTaskVo.setProcessTasks(processTasks);
        }
//        processTaskVo.setTotal(total);
//        processTaskVo.setRecords(total / row);
        return JSONObject.toJSONString(processTaskVo);
    }

    private List<ProcessTask> queryProcessTaskList(List<Map> result) {
        List<ProcessTask> processTaskList = new ArrayList<>();
        if (result == null || result.size() <= 0) {
            return processTaskList;
        }
        Map info = new HashMap();
        info.put("processInstanceIds", result);
        List<Map> mapList = daoImplMapper.queryProcessTaskList(info);
        return BeanConvertUtil.covertBeanList(mapList, ProcessTask.class);
    }

    private List<Map> queryProcessTaskCount(Map info) {
        if (!StrUtil.isBlankIfStr(info.get("applyTime"))) {
            info.put("startTime", info.get("applyTime") + " " + "00:00:00");
            info.put("endTime", info.get("applyTime") + " " + "23:59:59");
        }
        List<Map> listOne = daoImplMapper.queryAllProcessAboutUser(info);
        List<Map> listTwo = daoImplMapper.queryAllRunTask();
        List<Map> result = listOne.stream().filter(o -> !listTwo.contains(o)).collect(Collectors.toList());
        if (result == null || result.size() <= 0) {
            result = new ArrayList<>();
        }
        return result;

    }

    private List<ProcessCirculationDto> getProcessApprovalList(List<ActApprovalRecordDto> actApprovalRecordDtoList) {
        List<ProcessCirculationDto> processApprovalList = new ArrayList<>();
        Map<String, List<ActApprovalRecordDto>> approvalRecordDto = actApprovalRecordDtoList.stream().collect(Collectors.groupingBy(ActApprovalRecordDto::getTaskDefinitionName));
        Set nodeNameSet = new HashSet();
        String comment = "";
        if (actApprovalRecordDtoList != null && actApprovalRecordDtoList.size() > 0) {

            List<String> userIds = getReceiverIds(actApprovalRecordDtoList);
            List<SysUser> userInfoList = iSysUserService.selectUserByIds(userIds.stream().map(t -> Long.valueOf(t)).toArray(Long[]::new));
            for (ActApprovalRecordDto recordDto : actApprovalRecordDtoList) {
                if (nodeNameSet.contains(recordDto.getTaskDefinitionName()) || ProcessNodeNameEnum.STARTER.getValue().equals(recordDto.getTaskDefinitionName())) {
                    comment = "";
                    continue;
                } else {
                    comment = "";
                    List<ActApprovalRecordDto> dtoList = approvalRecordDto.get(recordDto.getTaskDefinitionName());
                    ProcessCirculationDto processCirculationDto = new ProcessCirculationDto();
                    dtoList.sort((ord1, ord2) -> ord2.getCreateTime().compareTo(ord1.getCreateTime()));
                    for (ActApprovalRecordDto dto : dtoList) {
                        Optional<SysUser> optionalUserInfo = userInfoList.stream().findFirst().filter(userInfo -> userInfo.getUserId().toString().equals(dto.getApprovalUserId()));
                        SysDept dept = optionalUserInfo.isPresent() ?
                                optionalUserInfo.get().getDept() : null;
                        if (StrUtil.isEmpty(comment)) {
                            comment = (dto.getTaskComment() == null ? "" : dto.getTaskComment()) + "&nbsp&nbsp"
                                    + ((dept == null ? "" : dept.getDeptName())
                                    + dto.getApprovalUserName() + "&nbsp&nbsp" + dto.getCreateTime());
                        } else {
                            comment = comment + "</br>" + (dto.getTaskComment() == null ? "" : dto.getTaskComment()) + "&nbsp&nbsp"
                                    + ((dept == null ? "" : dept.getDeptName())
                                    + dto.getApprovalUserName() + "&nbsp&nbsp" + dto.getCreateTime());
                        }
                    }
                    processCirculationDto.setNodeName(recordDto.getTaskDefinitionName());
                    processCirculationDto.setProcessComment(comment);
                    processApprovalList.add(processCirculationDto);
                    nodeNameSet.add(recordDto.getTaskDefinitionName());
                }
            }
        }
        return processApprovalList;
    }


    /**
     * 流程开始至发起人
     */
    private Task processStartFirstStep(ProcessCreateRequest request) {
        ProcessInstance instance = runtimeService.startProcessInstanceByKey(request.getProcessCode(), request.getParamMap());
        Task task = taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
        Map paramMap = getTaskDescParam(instance, request);
        //生成流程标题保存到流程标题描述表中
        daoImplMapper.addTaskDesc(paramMap);
        daoImplMapper.addTaskDescForm(paramMap);
        return task;
    }

    private Map getTaskDescParam(ProcessInstance instance, ProcessCreateRequest request) {
        String userId = request.getOriginatorUserId();
        String userName = request.getOriginatorUserName();
        String procName = request.getProcessName();
        if (StrUtil.isEmpty(procName)) {
            //根据流程code查询流程相关信息
            Map info = new HashMap();
            info.put("processCode", request.getProcessCode());
            List<ActProcessDto> actProcessDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.queryActProcessDtoByProcessCode(info), ActProcessDto.class);
            if (actProcessDtoList != null && actProcessDtoList.size() > 0) {
                procName = actProcessDtoList.get(0).getName();
            }
        } else {
            String[] nameArr = procName.split("-");
            if (nameArr.length >= 2) {
                procName = nameArr[0] + "-" + nameArr[1];
            }
        }
        Map paramMap = new HashMap();
        String processInstanceId = instance.getId();
        //调用用户服务获取用户信息
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        paramMap.put("taskDescId", UUID.fastUUID().toString(true));
        paramMap.put("processInstanceId", processInstanceId);
        paramMap.put("createTime", new Date());
        paramMap.put("taskDescription", ProcessConstant.TEST_FLAG.equals(request.getParamMap().get("testFlag")) ? "流程测试-" + procName + "-" + userName : procName + "-" + userName + "-" + sdf.format(new Date()));
        paramMap.put("applyUserId", userId);
        paramMap.put("statusCd", 0);
        paramMap.put("businessId", request.getBusinessId());

        String formValue = null;
        try {
            JSONObject form = JSON.parseObject(request.getFormComponentValues());
            form.put("processInstanceId", processInstanceId);
            formValue = JSON.toJSONString(form);
        } catch (Exception e) {
            formValue = request.getFormComponentValues();
        }
        paramMap.put("formValue", formValue);
        paramMap.put("processCode", request.getProcessCode());
        paramMap.put("originatorUserName", request.getOriginatorUserName());
        paramMap.put("taskDescriptionSupplement", procName + "-" + userName + "-" + sdf.format(new Date()) +
                (request.getTaskDescriptionSupplement() == null ? "" : request.getTaskDescriptionSupplement()));

        return paramMap;
    }

    private ActApprovalRecord getProcessApprovalDto(String userId, Task task, String comment) {
        ActApprovalRecord dto = new ActApprovalRecord();
        dto.setApprovalUserId(userId);
        dto.setProcInstanceId(task.getProcessInstanceId());
        dto.setTaskDefinitionKey(task.getTaskDefinitionKey());
        dto.setTaskDefinitionName(task.getName());
        dto.setTaskId(task.getId());
        dto.setTaskComment(comment);
        return dto;

    }

    /**
     * 获取流程详情审批意见
     */
    private List<ProcessCirculationDto> getProcessCirculationDtoList(String instanceId) {
        //获取流程经过的所有人
        Map paramMap = new HashMap(16);
        paramMap.put("processInstanceId", instanceId);
        List<ProcessCirculationDto> processCirculationDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.queryProcessCandidateOrParticipant(paramMap), ProcessCirculationDto.class);
        if (processCirculationDtoList == null || processCirculationDtoList.size() <= 0) {
            return processCirculationDtoList;
        }
        //根据
        return processCirculationDtoList;
    }

    private void processApprovalRecordReceiver(String processInstanceId) {
        Map map = new HashMap();
        map.put("processInstanceId", processInstanceId);
        //获取流程审批记录
        List<ActApprovalRecordDto> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(daoImplMapper.getProcessApprovalRecord(map), ActApprovalRecordDto.class);
        if (actApprovalRecordDtoList == null || actApprovalRecordDtoList.size() <= 0) {
            return;
        }
        List<ActApprovalRecordDto> recordDtoList = actApprovalRecordDtoList.stream().filter(item -> StrUtil.isEmpty(item.getReceiverId())).collect(Collectors.toList());
        if (recordDtoList == null || recordDtoList.size() <= 0) {
            return;
        }
        for (int i = 1; i <= recordDtoList.size(); i++) {
            ActApprovalRecordDto recordDto = recordDtoList.get(i - 1);
            String candidateIds = "";
            if (i < recordDtoList.size()) {
                ActApprovalRecordDto dto = recordDtoList.get(i);
                candidateIds = getProcessCandidateIdsByTaskId(dto.getTaskId());
                if (StrUtil.isEmpty(candidateIds)) {
                    candidateIds = getHisProcessCandidateIdsByTaskId(dto.getTaskId());
                }
            } else {
                List<Task> runTaskList = taskService.createTaskQuery().processInstanceId(processInstanceId).active().list();
                //已经结束了
                if (runTaskList == null || runTaskList.size() <= 0) {
                    for (ActApprovalRecordDto actApprovalRecordDto : actApprovalRecordDtoList) {
                        if ("发起人".equals(actApprovalRecordDto.getTaskDefinitionName())) {
                            candidateIds = actApprovalRecordDto.getApprovalUserId();
                        }
                    }
                } else {
                    for (Task task : runTaskList) {
                        if (StrUtil.isEmpty(candidateIds)) {
                            if (StrUtil.isNotEmpty(task.getAssignee())) {
                                candidateIds = task.getAssignee();
                            } else {
                                candidateIds = getProcessCandidateIdsByTaskId(task.getId());
                            }
                        } else {
                            if (StrUtil.isNotEmpty(task.getAssignee())) {
                                candidateIds = candidateIds + ";" + task.getAssignee();
                            } else {
                                candidateIds = candidateIds + ";" + getProcessCandidateIdsByTaskId(task.getId());
                            }
                        }
                    }
                }
            }
            recordDto.setReceiverId(candidateIds);
            daoImplMapper.updateProcessApprovalRecord(recordDto);
        }
    }

    /**
     * 根据任务id获取当前任务候选人
     */
    private String getProcessCandidateIdsByTaskId(String taskId) {
        //获取流程当前任务候选人
        String candidateIds = "";
        try {
            List<IdentityLink> links = taskService.getIdentityLinksForTask(taskId);
            if (links == null || links.size() <= 0) {
                return candidateIds;
            }
            for (IdentityLink identityLink : links) {
                if (StrUtil.isEmpty(candidateIds)) {
                    candidateIds = identityLink.getUserId();
                } else {
                    candidateIds = candidateIds + ";" + identityLink.getUserId();
                }

            }
        } catch (Exception e) {
            candidateIds = "";
        }
        return candidateIds;
    }

    /**
     * 根据任务id获取历史任务候选人
     */
    private String getHisProcessCandidateIdsByTaskId(String taskId) {
        //获取流程当前任务候选人
        String candidateIds = "";
        try {
            List<HistoricIdentityLink> links = historyService.getHistoricIdentityLinksForTask(taskId);
            if (links == null || links.size() <= 0) {
                return candidateIds;
            }
            for (HistoricIdentityLink identityLink : links) {
                if (StrUtil.isEmpty(candidateIds)) {
                    candidateIds = identityLink.getUserId();
                } else {
                    candidateIds = candidateIds + ";" + identityLink.getUserId();
                }

            }
        } catch (Exception e) {
            candidateIds = "";
        }
        return candidateIds;
    }

    private boolean sureCurTaskIsRunning(String taskId, String employeeId) {
        boolean result = true;
        Map info = new HashMap(16);
        info.put("taskId", taskId);
        List<Map> infos = daoImplMapper.sureCurTaskIsRunning(info);
        if (infos == null || infos.size() <= 0) {
            result = false;
        } else {
            Map runTask = infos.get(0);
            String ownerId = ObjectUtil.isNull(runTask.get("OWNER_")) ? "" : runTask.get("OWNER_").toString();
            String assignee = ObjectUtil.isNull(runTask.get("ASSIGNEE_")) ? "" : runTask.get("ASSIGNEE_").toString();
            result = employeeId.equals(ownerId) || employeeId.equals(assignee);
        }
        return result;
    }

    private List<ProcessCommentCirculate> queryProcessCommentCirculateList(Map paramMap, List<ActApprovalRecordDto> actApprovalRecordDtoList) {
        List<ProcessCommentCirculate> processCommentCirculates = new ArrayList();
        if (actApprovalRecordDtoList == null || actApprovalRecordDtoList.size() <= 0) {
            return processCommentCirculates;
        }
        List<String> userIds = getReceiverIds(actApprovalRecordDtoList);
        List<SysUser> userInfoList = iSysUserService.selectUserByIds(userIds.stream().map(t->Long.valueOf(t)).toArray(Long[]::new));
        for (int i = 0; i < actApprovalRecordDtoList.size(); i++) {
            ActApprovalRecordDto actApprovalRecordDto = actApprovalRecordDtoList.get(i);
            ProcessCommentCirculate processCommentCirculate = new ProcessCommentCirculate();
            Optional<SysUser> optionalUserInfo = userInfoList.stream().filter(userInfo -> userInfo.getUserId().toString().equals(actApprovalRecordDto.getApprovalUserId())).findFirst();
            SysDept dept = optionalUserInfo.isPresent() ?
                    optionalUserInfo.get().getDept() : null;
            if (dept != null) {
                processCommentCirculate.setDepartment(dept.getDeptName());
            } else {
                processCommentCirculate.setDepartment("");
            }
            processCommentCirculate.setApprovalTime(actApprovalRecordDto.getCreateTime());
            processCommentCirculate.setUserName(actApprovalRecordDto.getApprovalUserName());
            processCommentCirculate.setProcessNodeName(actApprovalRecordDto.getTaskDefinitionName() + "/" + actApprovalRecordDto.getApprovalSign());
            processCommentCirculate.setComment(actApprovalRecordDto.getTaskComment());
            if (StrUtil.isNotEmpty(actApprovalRecordDto.getTaskCommentFile())) {
                List<ProcessCommentFileDto> commentFileList = new ArrayList<>();
                String[] fileArr = actApprovalRecordDto.getTaskCommentFile().split(";");
                for (int j = 0; j < fileArr.length; j++) {
                    String[] file = fileArr[j].split(",");
                    ProcessCommentFileDto fileDto = new ProcessCommentFileDto();
                    fileDto.setFileName(file[0]);
                    //fileDto.setFileAddress(activitiConfig.getFileUrl() + file[1]);
                    //变更附件获取方式
                    fileDto.setFileAddress(file[1]);
                    commentFileList.add(fileDto);
                }
                processCommentCirculate.setCommentFileList(commentFileList);
            }
            if (StrUtil.isNotEmpty(actApprovalRecordDto.getReceiverName())) {
                processCommentCirculate.setReceiver(actApprovalRecordDto.getReceiverName());
            } else {
                String[] employeeIds = actApprovalRecordDto.getReceiverId().split(";");
                String employeeNames = "";
                if (employeeIds.length > 0) {
                    for (int k = 0; k < employeeIds.length; k++) {
                        if (StrUtil.isNotEmpty(employeeIds[k])) {
                            String employeeId = employeeIds[k];
                            List<SysUser> users = userInfoList.stream().filter(userInfo -> userInfo.getUserId().toString().equals(employeeId)).collect(Collectors.toList());
                            SysUser _user = users.size() > 0 ?
                                    users.get(0) : null;
                            if (StrUtil.isEmpty(employeeNames)) {
                                employeeNames = _user == null ? "" :
                                        _user.getNickName();
                            } else {
                                employeeNames = employeeNames + ";" + (_user == null ? "" :
                                        _user.getNickName());
                            }
                        }
                    }
                }
                processCommentCirculate.setReceiver(employeeNames);
            }
            processCommentCirculates.add(processCommentCirculate);
        }
        return processCommentCirculates;
    }

    private List<String> getReceiverIds(List<ActApprovalRecordDto> actApprovalRecordDtoList) {
        List<String> employeeIdList = new ArrayList<>();
        for (ActApprovalRecordDto dto : actApprovalRecordDtoList) {
            if (!StrUtil.isBlankIfStr(dto.getReceiverId())) {
                String[] employeeIdArr = dto.getReceiverId().split(";");
                for (int i = 0; i < employeeIdArr.length; i++) {
                    employeeIdList.add(employeeIdArr[i]);
                }

            }
            employeeIdList.add(dto.getApprovalUserId());
        }
        return employeeIdList;
    }

    /**
     * 获取流程状态列表
     */
    private List<ProcessStatusListDto> getProcessStatusList(List<ProcessCirculationDto> processCirculationDtoList) {

        List<ProcessStatusListDto> processStatusListDtoList = new ArrayList<>();
        if (processCirculationDtoList == null || processCirculationDtoList.size() <= 0) {
            return processStatusListDtoList;
        }
        if (processCirculationDtoList != null && processCirculationDtoList.size() > 0) {
            Map<String, List<ProcessCirculationDto>> processDtoMap = processCirculationDtoList.stream().collect(Collectors.groupingBy(ProcessCirculationDto::getNodeName));
            String nodeName = "";
            Set<String> nodeNameSet = new HashSet<>();
            for (ProcessCirculationDto dto : processCirculationDtoList) {
                if (nodeNameSet.contains(dto.getNodeName())) {
                    continue;
                }
                ProcessStatusListDto processStatusListDto = new ProcessStatusListDto();
                processStatusListDto.setNodeName(dto.getNodeName());
                List<ProcessStatusDto> processStatusDtoList = new ArrayList<>();
                if (StrUtil.isEmpty(nodeName)) {
                    List<ProcessCirculationDto> processCirculationDtos = processDtoMap.get(dto.getNodeName());
                    for (ProcessCirculationDto circulationDto : processCirculationDtos) {
                        ProcessStatusDto processStatusDto = new ProcessStatusDto();
                        processStatusDto.setStartTime(circulationDto.getStartTime());
                        processStatusDto.setOperateUserName(circulationDto.getEmployeeName());
                        processStatusDto.setEndTime(circulationDto.getEndTime());
                        processStatusDto.setOperateStatus(StrUtil.isEmpty(circulationDto.getEndTime()) ? "未操作" : "已提交");
                        processStatusDto.setDuration(StrUtil.isEmpty(circulationDto.getDunration()) ? "" : DateUtil.formatDateTime(Long.parseLong(circulationDto.getDunration())));
                        processStatusDtoList.add(processStatusDto);
                    }
                    processStatusDtoList.stream().sorted(Comparator.comparing(ProcessStatusDto::getStartTime).reversed()).collect(Collectors.toList());
                }
                nodeNameSet.add(dto.getNodeName());
                processStatusListDto.setProcessStatusDtoList(processStatusDtoList);
                processStatusListDtoList.add(processStatusListDto);

            }

        }
        return processStatusListDtoList;
    }

    /**
     * 流程意见征询提交处理
     */
    private void processOpinionSeekApproval(Task task, String taskId, ProcessCompleteParamDto processCompleteParamDto) {
        //当流程为委托人提交时
        Map query = new HashMap(16);
        query.put("taskId", task.getId());

        List<Map> resultList = daoImplMapper.selectRunTaskInfoByTaskId(query);
        if (resultList != null && resultList.size() > 0) {
            Map map = resultList.get(0);
            map.put("ID_", IdUtil.fastSimpleUUID());
            daoImplMapper.insertRecordToHistoryTask(map);
        }
        taskService.resolveTask(taskId);
        //增加审批记录
        ActApprovalRecord actApprovalRecord = new ActApprovalRecord();
        actApprovalRecord.setApprovalUserId(task.getAssignee());
        actApprovalRecord.setProcInstanceId(task.getProcessInstanceId());
        actApprovalRecord.setTaskDefinitionKey(task.getTaskDefinitionKey());
        actApprovalRecord.setTaskDefinitionName(task.getName());
        actApprovalRecord.setTaskId(task.getId());
        actApprovalRecord.setTaskComment(processCompleteParamDto.getComment());
        actApprovalRecord.setReceiverId(task.getOwner());
        actApprovalRecord.setApprovalSign(ProcessApprovalSignEnum.CONSULTATION_REPLY.getValue());
        actApprovalRecord.setTaskCommentFile(processCompleteParamDto.getCommentFile());
        actApprovalRecordMapper.insert(actApprovalRecord);
    }

    /**
     * 流程转发提交处理
     */
    private void processTransferApproval(String taskId, ProcessCompleteParamDto processCompleteParamDto) {
        Map map = new HashMap(16);
        map.put("taskId", taskId);
        List<Map> resultList = daoImplMapper.selectRunTaskInfoByTaskId(map);
        if (resultList != null && resultList.size() > 0) {
            Map info = resultList.get(0);
            info.put("ID_", IdUtil.fastSimpleUUID());
            daoImplMapper.insertRecordToHistoryTask(info);
        }
        //删除正在运行的任务
        daoImplMapper.deleteRunTaskByTaskId(map);
        ActApprovalRecord actApprovalRecordDto = actApprovalRecordMapper.selectOne(Wrappers.<ActApprovalRecord>query().lambda()
                .eq(ActApprovalRecord::getTaskId, taskId));
        actApprovalRecordDto.setActApprovalRecordId(IdUtil.fastSimpleUUID());
        String employeeId = actApprovalRecordDto.getApprovalUserId();
        actApprovalRecordDto.setApprovalUserId(actApprovalRecordDto.getReceiverId());
        actApprovalRecordDto.setReceiverId(employeeId);
        actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.FORWARD_REPLY.getValue());
        actApprovalRecordDto.setTaskComment(processCompleteParamDto.getComment());
        actApprovalRecordDto.setTaskCommentFile(processCompleteParamDto.getCommentFile());
        actApprovalRecordMapper.insert(actApprovalRecordDto);
    }

    /**
     * 更新流程状态
     */
    private void updateProcessStatus(String processInstanceId, String status) {
        Map map = new HashMap(16);
        map.put("processInstanceId", processInstanceId);
        map.put("name", ProcessConstant.PROCESS_STATUS);
        map.put("processStatus", status);
        daoImplMapper.updateTaskStatus(map);
    }

    /**
     * 判断用户是不是流程发起人
     */
    private boolean sureUserIsProcessStarter(String employeeId, List<ActApprovalRecordDto> actApprovalRecordDtoList) {
        boolean isStart = false;
        for (ActApprovalRecordDto dto : actApprovalRecordDtoList) {
            if ("发起人".equals(dto.getTaskDefinitionName()) && employeeId.equals(dto.getApprovalUserId())) {
                isStart = true;
            }
        }
        return isStart;
    }

    /**
     * 用户不是发起人 且流程已结束获取流程权限
     */
    private List<ActFormPermission> getActPermissionListWayOne(String employeeId, List<ActApprovalRecordDto> actApprovalRecordDtoList, ProcessDescDto processDescDto) {
        List<ActFormPermission> actFormPermissionList = new ArrayList<>();
        String taskDefinitionId = "";
        for (ActApprovalRecordDto dto : actApprovalRecordDtoList) {
            if (!"发起人".equals(dto.getTaskDefinitionName()) && employeeId.equals(dto.getApprovalUserId())
                    && ProcessApprovalSignEnum.APPROVE.getValue().equals(dto.getApprovalSign())) {
                taskDefinitionId = dto.getTaskDefinitionKey();
            }
            if (StrUtil.isNotEmpty(taskDefinitionId)) {
                Map taskDefinitionIdMap = new HashMap();
                taskDefinitionIdMap.put("taskDefinitionKey", taskDefinitionId);
                taskDefinitionIdMap.put("formId", processDescDto.getProcessCode());
                actFormPermissionList = BeanConvertUtil.covertBeanList(daoImplMapper.getAllEditFormByTaskDefinitionKey(taskDefinitionIdMap), ActFormPermission.class);
                if (actFormPermissionList != null && actFormPermissionList.size() > 0) {
                    for (ActFormPermission permission : actFormPermissionList) {
                        permission.setReadOnly("false");
                        permission.setViewPermission("true");
                    }
                    return actFormPermissionList;
                }
            }
        }
        return actFormPermissionList;
    }
}
