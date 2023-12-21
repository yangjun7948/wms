package com.xiaoyai.activiti.common.dto.process;

/**
 * @author
 * @date
 * 新增人员编制dto
 */
public class NewStaffingDto {
    // 需求岗位
    private String demandJobs;
    // 人员需求数量
    private String demandAmount;
    // 需求原因说明
    private String demandRemark;

    public String getDemandJobs() {
        return demandJobs;
    }

    public void setDemandJobs(String demandJobs) {
        this.demandJobs = demandJobs;
    }

    public String getDemandAmount() {
        return demandAmount;
    }

    public void setDemandAmount(String demandAmount) {
        this.demandAmount = demandAmount;
    }

    public String getDemandRemark() {
        return demandRemark;
    }

    public void setDemandRemark(String demandRemark) {
        this.demandRemark = demandRemark;
    }
}
