package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-10-26
 * 流程编辑请求参数
 */
public class ProcessEditReqParam {
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

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    private String context;
    /**
     * 流程是否显示
     * */
    private String showFlag;

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

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }
}
