package com.xiaoyai.activiti.common.dto.process;

import lombok.Data;

import java.util.Map;

/**
 * @author xieyu
 * @date 2020-12-01
 */
@Data
public class ProcessCreateRequest {
    /**
     * 申请部门
     * */
    private String deptId;
    /**
     * 表单数据
     * */
    private String formComponentValues;
    /**
     * 申请人
     * */
    private String originatorUserId;

    /**
     * 申请人名称
     * */
    private String originatorUserName;

    /**
     * 流程code
     * */
    private String processCode;

    /**
     * 流程编码
     */
    private String procNum;
    /**
     * 流程名称
     * */
    private String processName;

    /**
     * 传递流程的参数
     * */
    private Map paramMap;

    /**
     *流程业务id
     */
    private String businessId;

    /**
     * 流程发起类型
     * */
    private String startType;

    /**
     * 流程实例id
     * */
    private String processInstanceId;

    /**
     * 流程发起签字意见
     * */
    private String comment;

    /**
     * 流程名称补充
     * */
    private String taskDescriptionSupplement;

    /**
     * 流程发起文件意见
     * */
    private String commentFile;

    @Override
    public String toString() {
        return "ProcessCreateRequest{" +
                "deptId=" + deptId +
                ", formComponentValues='" + formComponentValues + '\'' +
                ", originatorUserId='" + originatorUserId + '\'' +
                ", originatorUserName='" + originatorUserName + '\'' +
                ", processCode='" + processCode + '\'' +
                ", processName='" + processName + '\'' +
                ", paramMap=" + paramMap +
                ", businessId='" + businessId + '\'' +
                ", startType='" + startType + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", comment='" + comment + '\'' +
                ", taskDescriptionSupplement='" + taskDescriptionSupplement + '\'' +
                ", commentFile='" + commentFile + '\'' +
                '}';
    }
}
