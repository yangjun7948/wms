package com.xiaoyai.common.activiti.api.model.activiti.dto;


import com.java110.dto.PageDto;
import lombok.Data;

/**
 * @author junyang
 * @date 2021-01-09
 */
@Data
public class ProcessMonitorReqParam extends PageDto {
    private String employeeId;
    private String procCode;

    private String procTitle;
}
