package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/13
 * 流程状态返回封装
 */
public class ProcessStatusListDto {
    /**
     * 节点名称
     * */
    private String nodeName;

    /**
     * 流程状态
     * */
    private List<ProcessStatusDto> processStatusDtoList;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<ProcessStatusDto> getProcessStatusDtoList() {
        return processStatusDtoList;
    }

    public void setProcessStatusDtoList(List<ProcessStatusDto> processStatusDtoList) {
        this.processStatusDtoList = processStatusDtoList;
    }

    @Override
    public String toString() {
        return "PeocessStatusListDto{" +
                "nodeName='" + nodeName + '\'' +
                ", processStatusDtoList=" + processStatusDtoList +
                '}';
    }
}
