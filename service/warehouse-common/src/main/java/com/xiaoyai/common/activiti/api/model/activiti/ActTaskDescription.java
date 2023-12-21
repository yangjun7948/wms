package com.xiaoyai.common.activiti.api.model.activiti;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author junyang
 * @date 2021-10-28
 */
@Data
@ApiModel(description = "流程详情记录表")
public class ActTaskDescription implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("taskDescId")
    @TableId(value = "task_desc_id", type = IdType.UUID)
    private String taskDescId;

    private String taskDescription;
    private String taskApplyUserName;
    private String taskApplyUserId;
    private String businessId;
    private String processCode;
    private String formValue;
    private String procInstanceId;
    private String statusCd;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String communityId;
    private String taskDescriptionSupplement;

}
