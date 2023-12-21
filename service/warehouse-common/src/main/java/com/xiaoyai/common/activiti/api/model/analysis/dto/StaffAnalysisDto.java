package com.xiaoyai.common.activiti.api.model.analysis.dto;

import lombok.Data;

/**
 * @author junyang
 * @date 2021-12-30
 */
@Data
public class StaffAnalysisDto {
    /**
     * 类型
     */
    private String category;

    /**
     * 值
     */
    private String value;
}
