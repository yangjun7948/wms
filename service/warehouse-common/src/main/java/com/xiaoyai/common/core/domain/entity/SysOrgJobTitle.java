package com.xiaoyai.common.core.domain.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "机构矩阵表")
public class SysOrgJobTitle {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("机构ID")
    private String orgId;
    @ApiModelProperty("用户ID")
    private String userId;
    @ApiModelProperty("机构角色编码")
    private String jobTitleCode;

}
