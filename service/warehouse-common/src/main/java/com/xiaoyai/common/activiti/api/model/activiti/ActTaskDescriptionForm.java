package com.xiaoyai.common.activiti.api.model.activiti;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author junyang
 * @date 2021-10-28
 */
@Data
@ApiModel(description = "流程详情记录表单表")
public class ActTaskDescriptionForm implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("taskDescId")
    private String taskDescId;
    private String formValue;

}
