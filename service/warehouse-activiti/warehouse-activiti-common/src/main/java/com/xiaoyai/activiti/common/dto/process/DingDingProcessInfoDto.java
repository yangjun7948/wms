package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-10-23
 * 钉钉流程信息dto
 */
public class DingDingProcessInfoDto {
    /**
     * 流程id
     */
    private String procId;
    /**
     * 流程编号
     */
    private String procNum;
    /**
     * 流程名称
     */
    private String name;
    /**
     * 流程图标
     */
    private String icon_url;
    /**
     * 流程url
     */
    private String url;
    /**
     * 流程code
     */
    private String process_code;
    /**
     * 流程type
     */
    private String type;
    /**
     * 流程状态
     */
    private String statusCd;

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProcess_code() {
        return process_code;
    }

    public void setProcess_code(String process_code) {
        this.process_code = process_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
}
