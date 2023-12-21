package com.xiaoyai.common.activiti.api.model.activiti;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@ApiModel(description = "角色流程信息表")
@Data
public class ActRoleProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("actRoleProcessId")
    @TableId(value = "act_role_process_id", type = IdType.UUID)
    private String actRoleProcessId;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 流程code
     */
    private String processCode;

    public ActRoleProcess(String roleId, String processCode) {
        this.roleId = roleId;
        this.processCode = processCode;
    }
}
