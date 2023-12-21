package com.xiaoyai.common.activiti.api.model.activiti;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Data
@ApiModel(description = "流程审批记录表")
public class ActApprovalRecord {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("流程审批记录表")
    @TableId(value = "act_approval_record_id", type = IdType.UUID)
    private String actApprovalRecordId;
    @ApiModelProperty("流程实例id")
    private String procInstanceId;
    @ApiModelProperty("审批人id")
    private String approvalUserId;
    @ApiModelProperty("任务节点定义key")
    private String taskDefinitionKey;
    @ApiModelProperty("任务节点定义名称")
    private String taskDefinitionName;
    @ApiModelProperty("任务id")
    private String taskId;
    @ApiModelProperty("流程提交意见")
    private String taskComment;
    @ApiModelProperty("流程提交意见文件")
    private String taskCommentFile;
    @ApiModelProperty("接收人id")
    private String receiverId;
    @ApiModelProperty("流程操作标记")
    private String approvalSign;

}
