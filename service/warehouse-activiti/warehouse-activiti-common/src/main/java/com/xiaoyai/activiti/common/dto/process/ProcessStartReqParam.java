package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-10-26
 * 发起流程请求参数
 */
public class ProcessStartReqParam {
    /**
     * 流程id
     * */
    private String procId;

    /**
     * 流程名称
     * */
    private String name;

    /**
     * 流程分类
     * */
    private String type;

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
