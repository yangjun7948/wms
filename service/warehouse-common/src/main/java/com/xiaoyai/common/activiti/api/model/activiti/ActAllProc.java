package com.xiaoyai.common.activiti.api.model.activiti;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author junyang
 * @date 2021-10-28
 */
@Data
@ApiModel(description = "流程模板表")
public class ActAllProc implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("procId")
    @TableId(value = "proc_id", type = IdType.UUID)
    private String procId;

    private String procNum;

    private String name;

    private String iconUrl;

    private String url;

    private String processCode;

    private String type;

    private String createTime;

    private String showFlag;

    private String statusCd;

    private String context;

    private String systemType;
}
