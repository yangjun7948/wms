package com.xiaoyai.activiti.core.listener;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xiaoyai.activiti.core.mapper.ActApprovalRecordMapper;
import com.xiaoyai.activiti.core.mapper.ProcessServiceDaoImplMapper;
import com.xiaoyai.activiti.core.service.impl.ListenerServiceImpl;
import com.xiaoyai.activiti.enums.ProcessApprovalSignEnum;
import com.xiaoyai.activiti.enums.ProcessDelegationEnum;
import com.xiaoyai.activiti.utils.ApplicationContextFactory;
import com.xiaoyai.common.activiti.api.model.activiti.ActApprovalRecord;
import com.xiaoyai.common.activiti.api.model.activiti.dto.ActFormPermission;
import com.xiaoyai.common.activiti.api.model.activiti.dto.ProcessDescDto;
import com.xiaoyai.common.activiti.core.constant.ProcessConstant;
import com.xiaoyai.common.activiti.core.util.BeanConvertUtil;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.delegate.event.impl.ActivitiEntityEventImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.task.IdentityLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2021-10-27
 */
@Component
public class GlobalActivitiEventListener implements ActivitiEventListener, Serializable {

    private final static Logger logger = LoggerFactory.getLogger(GlobalActivitiEventListener.class);
    private boolean autoApprovalFlag = false;
    @Autowired
    ProcessServiceDaoImplMapper serviceDaoImplMapper;
    @Autowired
    ActApprovalRecordMapper actApprovalRecordMapper;

    @Override
    public void onEvent(ActivitiEvent event) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        if (StrUtil.isEmpty(event.getExecutionId())) {
            return;
        }
        Object testFlag = engine.getRuntimeService().getVariable(event.getExecutionId(), "testFlag");
        boolean isTest = ProcessConstant.TEST_FLAG.equals(testFlag);
        switch (event.getType()) {
            case TASK_CREATED:
                //处理转发的记录，如果存在转发则删除转发记录。保证待办只有唯一一个
                delForwardRecord(event);
                //自动化测试开始
                if (isTest) {
                    automaticApproval(event);
                    break;
                }
                //自动审批处理
                if (!automaticApprovalHandle(event)) {
                    sendNextApprover(event);
                }
                break;
            case TASK_COMPLETED:
                if (!isTest) {
                    sendApprover(event);
                }
                break;
            case PROCESS_COMPLETED:
                if (!isTest) {
                    sendCreater(event);
                    processCompletedHandle(event);
                    //处理还未完成的转发请求
                    completeForward(event);
                }
                break;
        }

    }

    /**
     * 处理转发请求
     *
     * @param event
     */
    private void completeForward(ActivitiEvent event) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = engine.getTaskService();
        String instanceId = event.getProcessInstanceId();
        Map query = MapUtil.builder("instanceId", instanceId)
                .put("delegation", ProcessDelegationEnum.FORWARD.getValue()).build();
        List<Map> taskList = serviceDaoImplMapper.queryRunTaskByInstanceId(query);
        taskList.forEach(task->{
            processTransferApproval(task.get("ID_").toString());
        });

    }
    /**
     * 流程转发提交处理
     */
    private void processTransferApproval(String taskId) {
        Map map = new HashMap(16);
        map.put("taskId", taskId);
        List<Map> resultList = serviceDaoImplMapper.selectRunTaskInfoByTaskId(map);
        if (resultList != null && resultList.size() > 0) {
            Map info = resultList.get(0);
            info.put("ID_", IdUtil.fastSimpleUUID());
            serviceDaoImplMapper.insertRecordToHistoryTask(info);
        }
        //删除正在运行的任务
        serviceDaoImplMapper.deleteRunTaskByTaskId(map);
        ActApprovalRecord actApprovalRecordDto = actApprovalRecordMapper.selectOne(Wrappers.<ActApprovalRecord>query().lambda()
                .eq(ActApprovalRecord::getTaskId, taskId));
        actApprovalRecordDto.setActApprovalRecordId(IdUtil.fastSimpleUUID());
        String employeeId = actApprovalRecordDto.getApprovalUserId();
        actApprovalRecordDto.setApprovalUserId(actApprovalRecordDto.getReceiverId());
        actApprovalRecordDto.setReceiverId(employeeId);
        actApprovalRecordDto.setApprovalSign(ProcessApprovalSignEnum.FORWARD_REPLY.getValue());
        actApprovalRecordDto.setTaskComment("自动审批");
        actApprovalRecordDto.setTaskCommentFile("");
        actApprovalRecordMapper.insert(actApprovalRecordDto);
    }
    /**
     * 删除转发记录
     *
     * @param event
     */
    private void delForwardRecord(ActivitiEvent event) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        TaskEntityImpl taskEntity = (TaskEntityImpl) activitiEntityEvent.getEntity();
        String userId = taskEntity.getAssignee();
        TaskService taskService = engine.getTaskService();
        if (StrUtil.isEmpty(userId)) {
            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskEntity.getId());
            if (identityLinks != null && identityLinks.size() > 0) {
                userId = identityLinks.get(0).getUserId();
                Map prams = MapUtil.builder("processInstanceId", taskEntity.getProcessInstanceId())
                        .put("employeeId", userId).build();
                serviceDaoImplMapper.deleteProcessForwardRecord(prams);
            }
        } else {
            Map prams = MapUtil.builder("processInstanceId", taskEntity.getProcessInstanceId())
                    .put("employeeId", userId).build();
            serviceDaoImplMapper.deleteProcessForwardRecord(prams);
        }
    }

    @Override
    public boolean isFailOnException() {
        return false;
    }

    /**
     * 发送给下一节点审批人
     */
    private void sendNextApprover(ActivitiEvent event) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        TaskEntityImpl taskEntity = (TaskEntityImpl) activitiEntityEvent.getEntity();
        TaskService taskService = engine.getTaskService();
        if (taskEntity.getName().equals("发起人")) {
            return;
        }
        String instanceId = event.getProcessInstanceId();
        ProcessDescDto processDescDto = BeanConvertUtil.covertBean(serviceDaoImplMapper.queryProcessDescByInstanceId(event.getProcessInstanceId()), ProcessDescDto.class);
        //获取当前流程
        List<IdentityLink> links = taskService.getIdentityLinksForTask(taskEntity.getId());
        Map noticeInfo = new HashMap();
        noticeInfo.put("title", "您有一个流程正待审批");
        noticeInfo.put("remark", "快去查看审批详情吧");
//        links.forEach(t -> {
//            R<SysUser> r = remoteUserService.getUserInfoById(t.getUserId(), SecurityConstants.FROM_IN);
//            ValidateUtils.validate(CommonConstants.SUCCESS.equals(r.getCode()), "远程调用用户服务失败");
//            SysUser userInfoDto = r.getData();
//            //删除转发记录
//            Map paramMap = new HashMap();
//            paramMap.put("processInstanceId", instanceId);
//            paramMap.put("employeeId", t.getUserId());
//            serviceDaoImplMapper.deleteProcessForwardRecord(paramMap);
//            ProcessNotifyMsgDto msgDto = new ProcessNotifyMsgDto();
//            msgDto.setTaskId(taskEntity.getId());
//            msgDto.setProcessTemplateCode(processDescDto.getProcNum());
//            msgDto.setApplyUser(processDescDto.getTaskApplyUserName());
//            msgDto.setReceiveOpenId(userInfoDto.getWxOpenid());
//            msgDto.setNodeName(taskEntity.getName());
//            msgDto.setProcessName(processDescDto.getTaskDescription());
//            msgDto.setProcessInstanceId(instanceId);
//            msgDto.setApplyDate(DateUtil.getNow(DateUtil.DATE_FORMATE_STRING_A));
//            msgDto.setNoticeInfo(noticeInfo);
//            List<String> employeeList = new ArrayList<>();
//            employeeList.add(userInfoDto.getUserId());
//            msgDto.setEmployeeIdList(employeeList);
//            if (StrUtil.isNotEmpty(userInfoDto.getWxOpenid())) {
////                iWechartAppletInnerService.sendProcessApproveMessage(msgDto);
//            }
//        });

    }


    /**
     * 发送给流程发起人
     */
    private void sendCreater(ActivitiEvent event) {

    }

    /**
     * 流程完成后操作
     *
     * @param event
     */
    private void processCompletedHandle(ActivitiEvent event) {
        String instanceId = event.getProcessInstanceId();
        ProcessDescDto processDescDto = BeanConvertUtil.covertBean(serviceDaoImplMapper.queryProcessDescByInstanceId(instanceId), ProcessDescDto.class);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("processValue", processDescDto.getFormValue());
        jsonObject.put("procNum", processDescDto.getProcNum());
        ListenerServiceImpl processUserServiceImpl = (ListenerServiceImpl) ApplicationContextFactory.getBean("listenerServiceImpl", ListenerServiceImpl.class);
        processUserServiceImpl.handleAfterProcessCompleted(jsonObject);
    }

    /**
     * 根据employeeId查找流程有无审批记录
     */
    private boolean hasProcessApprovalRecord(String employeeId, String instanceId) {
//        Map map = new HashMap();
//        map.put("employeeId", employeeId);
//        map.put("instanceId", instanceId);
//        boolean result = serviceDaoImplMapper.hasProcessApprovalRecord(map);
//        return result;
        return false;
    }

    /**
     * 发送给发起人
     */
    private void sendApprover(ActivitiEvent event) {
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        TaskEntityImpl taskEntity = (TaskEntityImpl) activitiEntityEvent.getEntity();
        if (taskEntity.getName().equals("发起人")) {
            return;
        }
        String instanceId = event.getProcessInstanceId();
        ProcessDescDto processDescDto = BeanConvertUtil.covertBean(serviceDaoImplMapper.queryProcessDescByInstanceId(event.getProcessInstanceId()), ProcessDescDto.class);

        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = engine.getTaskService();
        List<IdentityLink> links = taskService.getIdentityLinksForTask(taskEntity.getId());
        StringBuilder sb = new StringBuilder();
        Map noticeInfo = new HashMap();
        noticeInfo.put("title", "当前节点审批已完成");
        noticeInfo.put("remark", "");
        //暂时不发送给审批人
//        links.forEach(t -> {
//            UserInfoDto approveUserDto = iUserInnerServiceSMO.queryUserInfoByEmployeeId(t.getUserId());
//            ProcessNotifyMsgDto msgDto = new ProcessNotifyMsgDto();
//            // sb.append(approveUserDto.getUserName());
//            msgDto.setTaskId(taskEntity.getId());
//            msgDto.setNodeName(taskEntity.getName() + "-" + approveUserDto.getUserName());
//            msgDto.setProcessTemplateCode(processDescDto.getProcNum());
//            msgDto.setApplyUser(processDescDto.getTaskApplyUserName());
//            msgDto.setReceiveOpenId(approveUserDto.getOpenId());
//            msgDto.setProcessName(processDescDto.getTaskDescription());
//            msgDto.setProcessInstanceId(instanceId);
//            msgDto.setApplyDate(DateUtil.getNow(DateUtil.DATE_FORMATE_STRING_A));
//            msgDto.setNoticeInfo(noticeInfo);
//
//            //向消息中放消息
//            sendKafkaMsg(msgDto, approveUserDto, MsgTypeConstant.UPDATE, "DONE");
//
////            if (StrUtil.isNotEmpty(approveUserDto.getOpenId())) {
////                iWechartAppletInnerService.sendProcessApprovedMessage(msgDto);
////            }
//        });
//        UserInfoDto createUserDto = iUserInnerServiceSMO.queryUserInfoByEmployeeId(processDescDto.getTaskApplyUserId());
//
//        ProcessNotifyMsgDto msgDto = new ProcessNotifyMsgDto();
//        msgDto.setTaskId(taskEntity.getId());
//        msgDto.setNodeName(taskEntity.getName() + "-" + sb.toString());
//        msgDto.setProcessTemplateCode(processDescDto.getProcNum());
//        msgDto.setApplyUser(processDescDto.getTaskApplyUserName());
//        msgDto.setReceiveOpenId(createUserDto.getOpenId());
//        msgDto.setProcessName(processDescDto.getTaskDescription());
//        msgDto.setProcessInstanceId(instanceId);
//        msgDto.setApplyDate(DateUtil.getNow(DateUtil.DATE_FORMATE_STRING_A));
//        msgDto.setNoticeInfo(noticeInfo);
//        List<String> employeeList = new ArrayList<>();
//        employeeList.add(createUserDto.getEmployeeId());
//        msgDto.setEmployeeIdList(employeeList);
//        if (StrUtil.isNotEmpty(createUserDto.getOpenId())) {
//            iWechartAppletInnerService.sendProcessApprovedMessage(msgDto);
//        }
//        //app消息推送
//        if (StrUtil.isNotEmpty(createUserDto.getAppCid())) {
//            List list = new ArrayList();
//            list.add(createUserDto.getAppCid());
//            msgDto.setAppCids(list);
//            appMsgInnerServiceSMO.sendAppProcessApprovedMessage(msgDto);
//            logger.info("app流程发送提醒，详情：{}", msgDto.toString());
//        }

    }

    public boolean automaticApprovalHandle(ActivitiEvent event) {
        logger.info("进入是否自动审批处理");
        autoApprovalFlag = false;
        boolean result = false;
        Map map = new HashMap();
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        TaskEntityImpl taskEntity = (TaskEntityImpl) activitiEntityEvent.getEntity();
        TaskService taskService = engine.getTaskService();
        String userId = taskEntity.getAssignee();
        if (StrUtil.isEmpty(userId)) {
            logger.info("组任务不能自动审批");
            return result;
        }
        if (taskEntity.getName().equals("发起人")) {
            result = true;
            logger.info("发起人不提醒");
            return result;
        }
        map.put("processInstanceId", taskEntity.getProcessInstanceId());
        //查询此任务是否有编辑状态
        Map paramMap = new HashMap();
        ProcessDescDto processDescDto = BeanConvertUtil.covertBean(serviceDaoImplMapper.queryProcessDescByInstanceId(taskEntity.getProcessInstanceId()), ProcessDescDto.class);
        paramMap.put("taskDefinitionKey", taskEntity.getTaskDefinitionKey());
        paramMap.put("formId", processDescDto.getProcessCode());
        List<ActFormPermission> actFormPermissionList = BeanConvertUtil.covertBeanList(serviceDaoImplMapper.getAllEditFormByTaskDefinitionKey(paramMap), ActFormPermission.class);
        if (actFormPermissionList != null && actFormPermissionList.size() > 0) {
            for (ActFormPermission actFormPermission : actFormPermissionList) {
                if ("true".equals(actFormPermission.getReadOnly())) {
                    logger.info("任务有可编辑表单,不自动审批");
                    return result;
                }
                if ("true".equals(actFormPermission.getViewPermission())) {
                    logger.info("当前审批人有查看权限，不自动审批");
                    return result;
                }
            }
        }
        //获取流程审批记录
        List<ActApprovalRecord> actApprovalRecordDtoList = BeanConvertUtil.covertBeanList(serviceDaoImplMapper.getProcessApprovalRecord(map), ActApprovalRecord.class);
        if (actApprovalRecordDtoList == null || actApprovalRecordDtoList.size() <= 0) {
            logger.info("流程审批记录列表为空，流程实例id：{}", taskEntity.getProcessInstanceId());
            return result;
        }
        //获取最后退回操作下标
        int backIndex = -1;
        int indexOne = 0;
        for (ActApprovalRecord dto : actApprovalRecordDtoList) {
            if ("退回".equals(dto.getApprovalSign())) {
                backIndex = indexOne;
            }
            indexOne++;
        }
        int indexTwo = 0;
        for (ActApprovalRecord dto : actApprovalRecordDtoList) {
            if (StrUtil.isNotEmpty(userId) && userId.equals(dto.getApprovalUserId()) && indexTwo > backIndex) {
                logger.info("用户：{}，已有过审批记录，自动审批,流程实例id：{}", dto.getApprovalUserId(), dto.getProcInstanceId());
                saveApprovalRecord(event);
                taskService.addComment(taskEntity.getId(), taskEntity.getProcessInstanceId(), "自动审批");
                taskService.complete(taskEntity.getId());
                result = true;
                break;
            }
            indexTwo++;
        }
        return result;
    }

    private void automaticApproval(ActivitiEvent event) {
        logger.info("进入自动审批处理");
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        TaskEntityImpl taskEntity = (TaskEntityImpl) activitiEntityEvent.getEntity();
        String userId = taskEntity.getAssignee();
        TaskService taskService = engine.getTaskService();
        if (StrUtil.isEmpty(userId)) {
            List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(taskEntity.getId());
            if (identityLinks != null && identityLinks.size() > 0) {
                userId = identityLinks.get(0).getUserId();
                taskService.claim(taskEntity.getId(), userId);
            }
        }
        taskService.addComment(taskEntity.getId(), taskEntity.getProcessInstanceId(), "");
        taskService.complete(taskEntity.getId());
    }

    /**
     * 保存流程审批记录
     */
    private void saveApprovalRecord(ActivitiEvent event) {
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ActivitiEntityEventImpl activitiEntityEvent = (ActivitiEntityEventImpl) event;
        TaskEntityImpl taskEntity = (TaskEntityImpl) activitiEntityEvent.getEntity();
        TaskService taskService = engine.getTaskService();
        //保存流程审批记录
        String userId = taskEntity.getAssignee();
        ActApprovalRecord dto = new ActApprovalRecord();

        dto.setApprovalUserId(userId);
        dto.setProcInstanceId(taskEntity.getProcessInstanceId());
        dto.setTaskDefinitionKey(taskEntity.getTaskDefinitionKey());
        dto.setTaskDefinitionName(taskEntity.getName());
        dto.setTaskId(taskEntity.getId());
        dto.setTaskComment("自动审批");
        dto.setApprovalSign(ProcessApprovalSignEnum.APPROVE.getValue());
        actApprovalRecordMapper.insert(dto);
        logger.info("保存流程审批记录，{}", dto.toString());
    }
}
