package com.xiaoyai.activiti.core.service.impl;

import com.xiaoyai.activiti.core.service.ImageService;
import com.xiaoyai.activiti.utils.CustomProcessDiagramGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.Execution;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2021-11-01
 */
@Slf4j
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {


    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public byte[] getFlowImgByProcInstId(String procInstId) throws IOException {
        if (StringUtils.isEmpty(procInstId)) {
            return null;
        }
        InputStream imageStream = null;
        try {
            long l1 = System.currentTimeMillis();
            // 通过流程实例ID获取历史流程实例
            HistoricProcessInstance historicProcessInstance = getHistoricProcInst(procInstId);

            // 通过流程实例ID获取流程中已经执行的节点，按照执行先后顺序排序
            List<HistoricActivityInstance> historicActivityInstanceList = getHistoricActivityInstAsc(procInstId);


            // 将已经执行的节点ID放入高亮显示节点集合
            List<String> highLightedActivitiIdList = new ArrayList<>();
            for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
                highLightedActivitiIdList.add(historicActivityInstance.getActivityId());
                log.info("已执行的节点[{}-{}-{}-{}]", historicActivityInstance.getId(), historicActivityInstance
                        .getActivityId(), historicActivityInstance.getActivityName(), historicActivityInstance
                        .getAssignee());
            }

            // 通过流程实例ID获取流程中正在执行的节点
            List<Execution> runningActivityInstanceList = getRunningActivityInst(procInstId);
            List<String> runningActivitiIdList = new ArrayList<String>();
            for (Execution execution : runningActivityInstanceList) {
                if (StringUtils.isNotEmpty(execution.getActivityId())) {
                    runningActivitiIdList.add(execution.getActivityId());
                    log.info("执行中的节点[{}-{}-{}]", execution.getId(), execution.getActivityId(), execution.getName());
                }
            }

            // 通过流程实例ID获取已经完成的历史流程实例
            //List<HistoricProcessInstance> historicFinishedProcessInstanceList = getHistoricFinishedProcInst(procInstId);

            // 定义流程画布生成器
            ProcessDiagramGenerator processDiagramGenerator = null;
            // 如果还没完成，流程图高亮颜色为绿色，如果已经完成为红色
            // if (!CollectionUtils.isEmpty(historicFinishedProcessInstanceList)) {
            // // 如果不为空，说明已经完成
            // processDiagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
            // } else {
            processDiagramGenerator = new CustomProcessDiagramGenerator();
            // }
            long l2 = System.currentTimeMillis();
            System.out.println("==============="+(l2-l1));
            // 获取流程定义Model对象
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());


            // 获取已流经的流程线，需要高亮显示高亮流程已发生流转的线id集合
            List<String> highLightedFlowIds = getHighLightedFlows(bpmnModel, historicActivityInstanceList);
            long l3 = System.currentTimeMillis();
            System.out.println("==============="+(l3-l2));
            // 使用默认配置获得流程图表生成器，并生成追踪图片字符流
            imageStream = ((CustomProcessDiagramGenerator) processDiagramGenerator)
                    .generateDiagramCustom(bpmnModel, "png",
                            highLightedActivitiIdList, runningActivitiIdList, highLightedFlowIds,
                            "simsun", "simsun", "simsun",
                            null, 2.0);
            log.info("设置流程图字体格式 activityFontName:{},labelFontName:{}","宋体","宋体");
            long l4 = System.currentTimeMillis();
            System.out.println("==============="+(l4-l3));
            // 将InputStream数据流转换为byte[]
            byte[] buffer = new byte[imageStream.available()];
            imageStream.read(buffer);
            long l5 = System.currentTimeMillis();
            System.out.println("==============="+(l5-l4));
            return buffer;
        } catch (Exception e) {
            log.error("通过流程实例ID[{}]获取流程图时出现异常！", e);
        } finally {
            if (imageStream != null) {
                imageStream.close();
            }
        }
        return null;
    }


    /**
     * Desc: 通过流程实例ID获取历史流程实例
     *
     * @param procInstId
     * @return
     * @author liuxz
     */
    public HistoricProcessInstance getHistoricProcInst(String procInstId) {
        return historyService
                .createHistoricProcessInstanceQuery()
                .processInstanceId(procInstId)
                .singleResult();
    }

    /**
     * Desc: 通过流程实例ID获取流程中已经执行的节点，按照执行先后顺序排序
     *
     * @param procInstId
     * @return
     * @author liuxz
     */
    public List<HistoricActivityInstance> getHistoricActivityInstAsc(String procInstId) {
        return historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(procInstId)
                .orderByHistoricActivityInstanceId()
                .asc()
                .list();
    }
    /**
     * Desc: 通过流程实例ID获取流程中正在执行的节点
     *
     * @param procInstId
     * @return
     * @author liuxz
     */
    public List<Execution> getRunningActivityInst(String procInstId) {
        return runtimeService.createExecutionQuery().processInstanceId(procInstId).list();
    }
    /**
     * 获取已流经的流程线，需要高亮显示高亮流程已发生流转的线id集合
     *
     * @param bpmnModel
     * @param historicActivityInstanceList
     * @return
     */
    public List<String> getHighLightedFlows(BpmnModel bpmnModel,
                                            List<HistoricActivityInstance> historicActivityInstanceList) {
        // 已流经的流程线，需要高亮显示
        List<String> highLightedFlowIdList = new ArrayList<>();
        // 全部活动节点
        List<FlowNode> allHistoricActivityNodeList = new ArrayList<>();
        // 已完成的历史活动节点
        List<HistoricActivityInstance> finishedActivityInstanceList = new ArrayList<>();

        for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
            // 获取流程节点
            FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(historicActivityInstance
                    .getActivityId(), true);
            allHistoricActivityNodeList.add(flowNode);
            // 结束时间不为空，当前节点则已经完成
            if (historicActivityInstance.getEndTime() != null) {
                finishedActivityInstanceList.add(historicActivityInstance);
            }
        }

        FlowNode currentFlowNode = null;
        FlowNode targetFlowNode = null;
        HistoricActivityInstance currentActivityInstance;
        // 遍历已完成的活动实例，从每个实例的outgoingFlows中找到已执行的
        for (int k = 0; k < finishedActivityInstanceList.size(); k++) {
            currentActivityInstance = finishedActivityInstanceList.get(k);
            // 获得当前活动对应的节点信息及outgoingFlows信息
            currentFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(currentActivityInstance
                    .getActivityId(), true);
            // 当前节点的所有流出线
            List<SequenceFlow> outgoingFlowList = currentFlowNode.getOutgoingFlows();

            /**
             * 遍历outgoingFlows并找到已流转的 满足如下条件认为已流转：
             * 1.当前节点是并行网关或兼容网关，则通过outgoingFlows能够在历史活动中找到的全部节点均为已流转
             * 2.当前节点是以上两种类型之外的，通过outgoingFlows查找到的时间最早的流转节点视为有效流转
             * (第2点有问题，有过驳回的，会只绘制驳回的流程线，通过走向下一级的流程线没有高亮显示)
             */
            if ("parallelGateway".equals(currentActivityInstance.getActivityType()) || "inclusiveGateway".equals(
                    currentActivityInstance.getActivityType())) {
                // 遍历历史活动节点，找到匹配流程目标节点的
                for (SequenceFlow outgoingFlow : outgoingFlowList) {
                    // 获取当前节点流程线对应的下级节点
                    targetFlowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(outgoingFlow.getTargetRef(),
                            true);
                    // 如果下级节点包含在所有历史节点中，则将当前节点的流出线高亮显示
                    if (allHistoricActivityNodeList.contains(targetFlowNode)) {
                        highLightedFlowIdList.add(outgoingFlow.getId());
                    }
                }
            } else {
                /**
                 * 2、当前节点不是并行网关或兼容网关
                 * 【已解决-问题】如果当前节点有驳回功能，驳回到申请节点，
                 * 则因为申请节点在历史节点中，导致当前节点驳回到申请节点的流程线被高亮显示，但实际并没有进行驳回操作
                 */
                List<Map<String, Object>> tempMapList = new ArrayList<>();
                // 当前节点ID
                String currentActivityId = currentActivityInstance.getActivityId();
                int size = historicActivityInstanceList.size();
                boolean ifStartFind = false;
                boolean ifFinded = false;
                HistoricActivityInstance historicActivityInstance;
                // 循环当前节点的所有流出线
                // 循环所有历史节点
                for (int i = 0; i < historicActivityInstanceList.size(); i++) {
                    // 历史节点
                    historicActivityInstance = historicActivityInstanceList.get(i);
                    // 如果循环历史节点中的id等于当前节点id，从当前历史节点继续先后查找是否有当前节点流程线等于的节点
                    // 历史节点的序号需要大于等于已完成历史节点的序号，防止驳回重审一个节点经过两次是只取第一次的流出线高亮显示，第二次的不显示
                    if (i >= k && historicActivityInstance.getActivityId().equals(currentActivityId)) {
                        ifStartFind = true;
                        // 跳过当前节点继续查找下一个节点
                        continue;
                    }
                    if (ifStartFind) {
                        ifFinded = false;
                        for (SequenceFlow sequenceFlow : outgoingFlowList) {
                            // 如果当前节点流程线对应的下级节点在其后面的历史节点中，则该条流程线进行高亮显示
                            // 【问题】
                            if (historicActivityInstance.getActivityId().equals(sequenceFlow.getTargetRef())) {
                                highLightedFlowIdList.add(sequenceFlow.getId());
                                // 暂时默认找到离当前节点最近的下一级节点即退出循环，否则有多条流出线时将全部被高亮显示
                                ifFinded = true;
                                break;
                            }
                        }
                    }
                    if (ifFinded) {
                        // 暂时默认找到离当前节点最近的下一级节点即退出历史节点循环，否则有多条流出线时将全部被高亮显示
                        break;
                    }
                }
            }

        }
        return highLightedFlowIdList;
    }
}
