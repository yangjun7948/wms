package com.xiaoyai.common.activiti.api.model.activiti.dto;

import java.util.Date;

/**
 * @author xieyu
 * @date 2020/5/21
 * 记录流程表单参数
 */
public class ProcessHistoreDetailDto {
    /**
     * 流程实例id
     * */
    private String processInstanceId;
    /**
     * 变量名
     * */
    private String paramName;
    /**
     * 变量值
     * */
    private String paramValue;
    /**
     * 记录id
     * */
    private String hiDetailId;

    /**
     * 创建时间
     * */
    private Date createDate;

    /**
     * 类型
     * */
    private String type = "formParam";


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHiDetailId() {
        return hiDetailId;
    }

    public void setHiDetailId(String hiDetailId) {
        this.hiDetailId = hiDetailId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
