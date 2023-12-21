package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-10-23
 * 流程dto
 */
public class ActProcessDto {
    /**
     * '流程id'
     */
    private String procId;
    /**
     * ''流程编号''
     */
    private String procNum;
    /**
     * ''流程名称''
     */
    private String name;
    /**
     * ''流程图标''
     */
    private String iconUrl;
    /**
     * ''流程url''
     */
    private String url;
    /**
     * ''流程code''
     */
    private String processCode;
    /**
     * ''流程所属类详见字典表''
     */
    private String type;
    /**
     * ''创建时间''
     */
    private String createTime;
    /**
     * ''数据状态，详细参考c_status表，S 保存，0, 在用 1失效''
     */
    private String statusCd;

    /**
     * 类型名称
     * */
    private String typeName;

    /**
     *是否显示
     **/
    private String showFlag;

    /**
     * 流程内容
     */
    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProcessCode() {
        return processCode;
    }

    public void setProcessCode(String processCode) {
        this.processCode = processCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getShowFlag() {
        return showFlag;
    }

    public void setShowFlag(String showFlag) {
        this.showFlag = showFlag;
    }
}
