package com.xiaoyai.common.activiti.api.model.analysis.dto;

import lombok.Data;

/**
 * @author junyang
 * @date 2021-12-30
 */
@Data
public class StaffLeaveAnalysisDto {
    /**
     * 分析类型：工龄、人员、职级、学历
     */
    private String analysisType;
    /**
     * 类型
     */
    private String category;

    /**
     * 值
     */
    private Integer value;

    /**
     * 机构id
     */
    private String orgId;
}
