package com.xiaoyai.common.activiti.api.model.activiti.dto;

import lombok.Data;

/**
 * @author xieyu
 * @date 2020/5/5
 * 流程数据封装
 */
@Data
public class ProcessDto {
    /**
     *流程所属类别
     */
    private String type;

    private String iconUrl;

    private String url;
    /**
     *流程所属类别名称
     */
    private String procTypeName;

    /**
     *流程id
     */
    private String procId;

    /**
     *流程名称
     */
    private String procName;

    /**
     * 流程code
     * */
    private String procCode;


    private String context;

    private String createTime;

    private String statusCd;

    private String procNum;
    @Override
    public String toString() {
        return "ProcessDto{" +
                "type='" + type + '\'' +
                ", procTypeName='" + procTypeName + '\'' +
                ", procId='" + procId + '\'' +
                ", procName='" + procName + '\'' +
                ", procCode='" + procCode + '\'' +
                '}';
    }
}
