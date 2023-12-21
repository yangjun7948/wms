package com.xiaoyai.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.xiaoyai.common.annotation.Excel;
import com.xiaoyai.common.core.domain.BaseEntity;

/**
 * 部门矩阵对象 sys_dept_job_title
 * 
 * @author junyang
 * @date 2023-10-10
 */
@Data
public class SysDeptJobTitle
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String deptId;
    private String userId;
    private String jobTitleCode;

    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setJobTitleCode(String jobTitleCode) 
    {
        this.jobTitleCode = jobTitleCode;
    }

    public String getJobTitleCode() 
    {
        return jobTitleCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("userId", getUserId())
            .append("jobTitleCode", getJobTitleCode())
            .toString();
    }
}
