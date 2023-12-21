package com.xiaoyai.activiti.common.dto.process;

/**
 * @author
 * @date
 * 原有人员编制dto
 */
public class OriginalStaffingDto{
    // 岗位
    private String jobs;
    // 数量
    private String amount;
    // 备注
    private String remark;

    public String getJobs() {
        return jobs;
    }

    public void setJobs(String jobs) {
        this.jobs = jobs;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
