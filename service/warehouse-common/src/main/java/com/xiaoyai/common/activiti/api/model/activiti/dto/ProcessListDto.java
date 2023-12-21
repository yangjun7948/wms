package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/5
 * 流程数据封装
 */
public class ProcessListDto {
    /**
     *流程所属类别
     */
    private String type;

    /**
     *流程所属类别名称
     */
    private String procTypeName;

    private List<ProcessDto> processDtoList;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProcTypeName() {
        return procTypeName;
    }

    public void setProcTypeName(String procTypeName) {
        this.procTypeName = procTypeName;
    }

    public List<ProcessDto> getProcessDtoList() {
        return processDtoList;
    }

    public void setProcessDtoList(List<ProcessDto> processDtoList) {
        this.processDtoList = processDtoList;
    }

    @Override
    public String toString() {
        return "ProcessListDto{" +
                "type='" + type + '\'' +
                ", procTypeName='" + procTypeName + '\'' +
                ", processDtoList=" + processDtoList +
                '}';
    }
}
