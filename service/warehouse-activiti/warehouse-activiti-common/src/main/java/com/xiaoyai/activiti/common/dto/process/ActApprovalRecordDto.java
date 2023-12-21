package com.xiaoyai.activiti.common.dto.process;

import lombok.Data;

/**
 * @author xieyu
 * @date 2021-01-14
 */
@Data
public class ActApprovalRecordDto {
    /***
     *审批记录id
     */
    private String actApprovalRecordId;
    /***
     *流程任务id
     */
    private String taskId;
    /***
     *'流程实例id'
     */
    private String procInstanceId;
    /***
     *'审批人id'
     */
    private String approvalUserId;
    /***
     *''任务节点定义key''
     */
    private String taskDefinitionKey;
    /***
     *''任务节点定义名称
     */
    private String taskDefinitionName;
    /***
     *任务审批意见
     */
    private String taskComment;
    /***
     *任务审批意见文件
     */
    private String taskCommentFile;
    /***
     *''任务创建时间
     */
    private String createTime;
    /***
     *'有效状态 0：有效 1：无效'
     */
    private String delFlag;

    /**
     * 节点操作标志
     * */
    private String approvalSign;

    /**
     * 接收人id
     * */
    private String receiverId;

    /**
     * 审批人名称
     * */
    private String approvalUserName;

    /**
     * 接受人名称
     * */
    private String receiverName;



    @Override
    public String toString() {
        return "ActApprovalRecordDto{" +
                "actApprovalRecordId='" + actApprovalRecordId + '\'' +
                ", taskId='" + taskId + '\'' +
                ", procInstanceId='" + procInstanceId + '\'' +
                ", approvalUserId='" + approvalUserId + '\'' +
                ", taskDefinitionKey='" + taskDefinitionKey + '\'' +
                ", taskDefinitionName='" + taskDefinitionName + '\'' +
                ", taskComment='" + taskComment + '\'' +
                ", taskCommentFile='" + taskCommentFile + '\'' +
                ", createTime='" + createTime + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", approvalSign='" + approvalSign + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", approvalUserName='" + approvalUserName + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
