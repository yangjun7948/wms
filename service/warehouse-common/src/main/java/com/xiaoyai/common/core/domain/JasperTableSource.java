package com.xiaoyai.common.core.domain;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author junyang
 * @date 2023/9/12
 */
public class JasperTableSource {
    /**
     * table 组件数据源
     */
    private JRBeanCollectionDataSource tableData;

    public JRBeanCollectionDataSource getTableData() {
        return tableData;
    }

    public void setTableData(JRBeanCollectionDataSource tableData) {
        this.tableData = tableData;
    }
}
