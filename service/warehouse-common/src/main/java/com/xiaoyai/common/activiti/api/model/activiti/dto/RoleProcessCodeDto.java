package com.xiaoyai.common.activiti.api.model.activiti.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色流程保存参数
 */
@Data
public class RoleProcessCodeDto {

    /**
     * 角色id
     */
    private String roleId;
    /**
     * 流程code列表
     */
    private List<String> processCodeList;
}
