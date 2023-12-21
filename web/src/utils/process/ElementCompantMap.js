//元素和组件的映射表
import {ElementType} from './activiti-const';
var ElementCompantMap = {};
//执行流程属性组件
ElementCompantMap[ElementType.process] = 'ProcessProps';

ElementCompantMap[ElementType.startEvent] = 'StartEventProps';
ElementCompantMap[ElementType.endEvent] = 'EndEventProps';
ElementCompantMap[ElementType.throwEvent] = 'IntermediateThrowEventProps';
ElementCompantMap[ElementType.exclusiveGateway] = 'ExclusiveGatewayProps';
ElementCompantMap[ElementType.inclusiveGateway] = 'InclusiveGatewayProps';
ElementCompantMap[ElementType.parallelGateway] = 'ParallelGatewayProps';
ElementCompantMap[ElementType.sequenceFlow] = 'SequenceFlowProps';

ElementCompantMap[ElementType.userTask] = 'UserTaskProps';
ElementCompantMap[ElementType.callActivity] = 'CallActivityProps';

// 只读设计器
ElementCompantMap[ElementType.startEventReadonly] = 'StartEventPropsReadonly';
ElementCompantMap[ElementType.endEventReadonly] = 'EndEventPropsReadonly';
ElementCompantMap[ElementType.throwEventReadonly] = 'IntermediateThrowEventPropsReadonly';
ElementCompantMap[ElementType.exclusiveGatewayReadonly] = 'ExclusiveGatewayPropsReadonly';
ElementCompantMap[ElementType.inclusiveGatewayReadonly] = 'InclusiveGatewayPropsReadonly';
ElementCompantMap[ElementType.parallelGatewayReadonly] = 'ParallelGatewayPropsReadonly';
ElementCompantMap[ElementType.sequenceFlowReadonly] = 'SequenceFlowPropsReadonly';

ElementCompantMap[ElementType.userTaskReadonly] = 'UserTaskPropsReadonly';
ElementCompantMap[ElementType.callActivityReadonly] = 'CallActivityPropsReadonly';
export default ElementCompantMap;