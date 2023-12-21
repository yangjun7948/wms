package com.xiaoyai.common.activiti.api.model.activiti.dto;

import com.java110.dto.PageDto;
import lombok.Data;

import java.util.List;

/**
 * @author junyang
 * @date 2021-01-09
 */
@Data
public class ProcessMonitorVo extends PageDto {
    private List<MyProcessApplyDto> processApplyDtoList;
}
