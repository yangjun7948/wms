const BPMN_PREFIX = 'bpmn:'; 
const CUSTOM_PROFIX = 'activiti:';
export const ElementType = {
    //以下为 bpmn规范的元素
    collaboration: BPMN_PREFIX+'Collaboration',
    textAnnotation: BPMN_PREFIX+'TextAnnotation',
    group: BPMN_PREFIX +'Group',
    process: BPMN_PREFIX +'Process',
    documentation:BPMN_PREFIX+'Documentation',

    startEvent:BPMN_PREFIX+'StartEvent',
    endEvent:BPMN_PREFIX+'EndEvent',
    throwEvent:BPMN_PREFIX+'IntermediateThrowEvent',
    exclusiveGateway:BPMN_PREFIX+'ExclusiveGateway',
    inclusiveGateway:BPMN_PREFIX+'InclusiveGateway',
    parallelGateway:BPMN_PREFIX+'ParallelGateway',
    sequenceFlow:BPMN_PREFIX+'SequenceFlow',
    
    userTask: BPMN_PREFIX+'UserTask',
    callActivity:BPMN_PREFIX+'CallActivity',
    //以下为activti的拓展元素
    listener: CUSTOM_PROFIX+'Listener',

    // 只读设计器
    startEventReadonly:BPMN_PREFIX+'StartEventReadonly',
    endEventReadonly:BPMN_PREFIX+'EndEventReadonly',
    throwEventReadonly:BPMN_PREFIX+'IntermediateThrowEventReadonly',
    exclusiveGatewayReadonly:BPMN_PREFIX+'ExclusiveGatewayReadonly',
    inclusiveGatewayReadonly:BPMN_PREFIX+'InclusiveGatewayReadonly',
    parallelGatewayReadonly:BPMN_PREFIX+'ParallelGatewayReadonly',
    sequenceFlowReadonly:BPMN_PREFIX+'SequenceFlowReadonly',
    
    userTaskReadonly: BPMN_PREFIX+'UserTaskReadonly',
    callActivityReadonly: BPMN_PREFIX+'CallActivityReadonly',
    
};
export const TxTypeMap = {1:'申请',4:'审批',5:'记账'};
export const NodeTypeMap = {0:'非关键节点',1:'关键节点',2:'开始节点'};