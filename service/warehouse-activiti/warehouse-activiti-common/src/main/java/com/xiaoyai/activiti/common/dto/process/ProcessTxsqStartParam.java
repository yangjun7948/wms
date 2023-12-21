package com.xiaoyai.activiti.common.dto.process;


import java.util.List;

/**
 * @author xieyu
 * @date 2020-10-29
 * 入职审批发起请求参数
 */
public class ProcessTxsqStartParam extends ProcessReqBaseParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;
    private String postionChangeProcessName;
    private List postionChangeProcessList;
    private String name;
    private String orgId;
    private String orgName;
    private String position;
    private String belongDepartmentId;//所属部门
    private String belongDepartmentName;//所属部门名称
    private String directLeaderId;
    private String directLeaderName;//直接上级
    private String entryDate;//入职日期
    private String effectDate;//使用时间
    private String txReason;//调薪原因
    private String belongOrgId;
    private String belongOrgName;
    private String userLevel;

    private String basicWage;//基本工资
    private String jobWage;//岗位工资
    private String performancePay;//绩效工资
    private String fullAttendanceAward;//满勤奖
    private String communicationFee;//通讯费
    private String transportationAllowance;//交通补贴
    private String educationSubsidy;//学历补贴
    private String titleSubsidy;//职称补贴
    private String lunchSubsidy;//午餐补助
    private String buySocialSecurityFlag;//是否买社保
    private String total;//合计

    private String basicWage2;//基本工资
    private String jobWage2;//岗位工资
    private String performancePay2;//绩效工资
    private String fullAttendanceAward2;//满勤奖
    private String communicationFee2;//通讯费
    private String transportationAllowance2;//交通补贴
    private String educationSubsidy2;//学历补贴
    private String titleSubsidy2;//职称补贴
    private String lunchSubsidy2;//午餐补助
    private String buySocialSecurityFlag2;//是否买社保
    private String total2;//合计

    private String comment;

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public String getProcNum() {
        return procNum;
    }

    public void setProcNum(String procNum) {
        this.procNum = procNum;
    }

    @Override
    public String getProcCode() {
        return procCode;
    }

    @Override
    public void setProcCode(String procCode) {
        this.procCode = procCode;
    }

    @Override
    public String getApplyDepartmentName() {
        return applyDepartmentName;
    }

    @Override
    public void setApplyDepartmentName(String applyDepartmentName) {
        this.applyDepartmentName = applyDepartmentName;
    }

    @Override
    public String getApplyDepartmentId() {
        return applyDepartmentId;
    }

    @Override
    public void setApplyDepartmentId(String applyDepartmentId) {
        this.applyDepartmentId = applyDepartmentId;
    }

    @Override
    public String getApplyUserId() {
        return applyUserId;
    }

    @Override
    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    @Override
    public String getApplyUserName() {
        return applyUserName;
    }

    @Override
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getPostionChangeProcessName() {
        return postionChangeProcessName;
    }

    public void setPostionChangeProcessName(String postionChangeProcessName) {
        this.postionChangeProcessName = postionChangeProcessName;
    }

    public List getPostionChangeProcessList() {
        return postionChangeProcessList;
    }

    public void setPostionChangeProcessList(List postionChangeProcessList) {
        this.postionChangeProcessList = postionChangeProcessList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBelongDepartmentId() {
        return belongDepartmentId;
    }

    public void setBelongDepartmentId(String belongDepartmentId) {
        this.belongDepartmentId = belongDepartmentId;
    }

    public String getBelongDepartmentName() {
        return belongDepartmentName;
    }

    public void setBelongDepartmentName(String belongDepartmentName) {
        this.belongDepartmentName = belongDepartmentName;
    }

    public String getDirectLeaderId() {
        return directLeaderId;
    }

    public void setDirectLeaderId(String directLeaderId) {
        this.directLeaderId = directLeaderId;
    }

    public String getDirectLeaderName() {
        return directLeaderName;
    }

    public void setDirectLeaderName(String directLeaderName) {
        this.directLeaderName = directLeaderName;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(String effectDate) {
        this.effectDate = effectDate;
    }

    public String getTxReason() {
        return txReason;
    }

    public void setTxReason(String txReason) {
        this.txReason = txReason;
    }

    public String getBelongOrgId() {
        return belongOrgId;
    }

    public void setBelongOrgId(String belongOrgId) {
        this.belongOrgId = belongOrgId;
    }

    public String getBelongOrgName() {
        return belongOrgName;
    }

    public void setBelongOrgName(String belongOrgName) {
        this.belongOrgName = belongOrgName;
    }

    public String getBasicWage() {
        return basicWage;
    }

    public void setBasicWage(String basicWage) {
        this.basicWage = basicWage;
    }

    public String getJobWage() {
        return jobWage;
    }

    public void setJobWage(String jobWage) {
        this.jobWage = jobWage;
    }

    public String getPerformancePay() {
        return performancePay;
    }

    public void setPerformancePay(String performancePay) {
        this.performancePay = performancePay;
    }

    public String getFullAttendanceAward() {
        return fullAttendanceAward;
    }

    public void setFullAttendanceAward(String fullAttendanceAward) {
        this.fullAttendanceAward = fullAttendanceAward;
    }

    public String getCommunicationFee() {
        return communicationFee;
    }

    public void setCommunicationFee(String communicationFee) {
        this.communicationFee = communicationFee;
    }

    public String getTransportationAllowance() {
        return transportationAllowance;
    }

    public void setTransportationAllowance(String transportationAllowance) {
        this.transportationAllowance = transportationAllowance;
    }

    public String getEducationSubsidy() {
        return educationSubsidy;
    }

    public void setEducationSubsidy(String educationSubsidy) {
        this.educationSubsidy = educationSubsidy;
    }

    public String getTitleSubsidy() {
        return titleSubsidy;
    }

    public void setTitleSubsidy(String titleSubsidy) {
        this.titleSubsidy = titleSubsidy;
    }

    public String getLunchSubsidy() {
        return lunchSubsidy;
    }

    public void setLunchSubsidy(String lunchSubsidy) {
        this.lunchSubsidy = lunchSubsidy;
    }

    public String getBuySocialSecurityFlag() {
        return buySocialSecurityFlag;
    }

    public void setBuySocialSecurityFlag(String buySocialSecurityFlag) {
        this.buySocialSecurityFlag = buySocialSecurityFlag;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getBasicWage2() {
        return basicWage2;
    }

    public void setBasicWage2(String basicWage2) {
        this.basicWage2 = basicWage2;
    }

    public String getJobWage2() {
        return jobWage2;
    }

    public void setJobWage2(String jobWage2) {
        this.jobWage2 = jobWage2;
    }

    public String getPerformancePay2() {
        return performancePay2;
    }

    public void setPerformancePay2(String performancePay2) {
        this.performancePay2 = performancePay2;
    }

    public String getFullAttendanceAward2() {
        return fullAttendanceAward2;
    }

    public void setFullAttendanceAward2(String fullAttendanceAward2) {
        this.fullAttendanceAward2 = fullAttendanceAward2;
    }

    public String getCommunicationFee2() {
        return communicationFee2;
    }

    public void setCommunicationFee2(String communicationFee2) {
        this.communicationFee2 = communicationFee2;
    }

    public String getTransportationAllowance2() {
        return transportationAllowance2;
    }

    public void setTransportationAllowance2(String transportationAllowance2) {
        this.transportationAllowance2 = transportationAllowance2;
    }

    public String getEducationSubsidy2() {
        return educationSubsidy2;
    }

    public void setEducationSubsidy2(String educationSubsidy2) {
        this.educationSubsidy2 = educationSubsidy2;
    }

    public String getTitleSubsidy2() {
        return titleSubsidy2;
    }

    public void setTitleSubsidy2(String titleSubsidy2) {
        this.titleSubsidy2 = titleSubsidy2;
    }

    public String getLunchSubsidy2() {
        return lunchSubsidy2;
    }

    public void setLunchSubsidy2(String lunchSubsidy2) {
        this.lunchSubsidy2 = lunchSubsidy2;
    }

    public String getBuySocialSecurityFlag2() {
        return buySocialSecurityFlag2;
    }

    public void setBuySocialSecurityFlag2(String buySocialSecurityFlag2) {
        this.buySocialSecurityFlag2 = buySocialSecurityFlag2;
    }

    public String getTotal2() {
        return total2;
    }

    public void setTotal2(String total2) {
        this.total2 = total2;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }
}
