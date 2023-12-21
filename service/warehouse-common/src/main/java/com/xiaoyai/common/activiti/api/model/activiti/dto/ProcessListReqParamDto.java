package com.xiaoyai.common.activiti.api.model.activiti.dto;

/**
 * @author xieyu
 * @date 2020-07-24
 * 获取流程列表请求参数
 */
public class ProcessListReqParamDto {
    private String tableName;
    private String tableColumns;
    private String procId;
    private String systemType;

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(String tableColumns) {
        this.tableColumns = tableColumns;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }
}
