package com.xiaoyai.activiti.common.dto.process;


/**
 * @author xieyu
 * @date 2020-10-29
 * 入职审批发起请求参数
 */
public class ProcessGwbdStartParam extends ProcessReqBaseParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;

    private String name;
    private String orgId;
    private String orgName;

    private String entryDate;//入职日期
    private String effectDate;//使用时间

    private String changeType;
    private String txFlag;
    private String changeReason;

    private String position;
    private String belongOrgId;
    private String belongOrgName;
    private String belongDepartmentId;//所属部门
    private String belongDepartmentName;//所属部门名称
    private String directLeaderId;
    private String directLeaderName;//直接上级

    private String position2;
    private String belongOrgId2;
    private String belongOrgName2;
    private String belongDepartmentId2;//所属部门
    private String belongDepartmentName2;//所属部门名称
    private String directLeaderId2;
    private String directLeaderName2;//直接上级
    private String userLevel;

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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getTxFlag() {
        return txFlag;
    }

    public void setTxFlag(String txFlag) {
        this.txFlag = txFlag;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getPosition2() {
        return position2;
    }

    public void setPosition2(String position2) {
        this.position2 = position2;
    }

    public String getBelongOrgId2() {
        return belongOrgId2;
    }

    public void setBelongOrgId2(String belongOrgId2) {
        this.belongOrgId2 = belongOrgId2;
    }

    public String getBelongOrgName2() {
        return belongOrgName2;
    }

    public void setBelongOrgName2(String belongOrgName2) {
        this.belongOrgName2 = belongOrgName2;
    }

    public String getBelongDepartmentId2() {
        return belongDepartmentId2;
    }

    public void setBelongDepartmentId2(String belongDepartmentId2) {
        this.belongDepartmentId2 = belongDepartmentId2;
    }

    public String getBelongDepartmentName2() {
        return belongDepartmentName2;
    }

    public void setBelongDepartmentName2(String belongDepartmentName2) {
        this.belongDepartmentName2 = belongDepartmentName2;
    }

    public String getDirectLeaderId2() {
        return directLeaderId2;
    }

    public void setDirectLeaderId2(String directLeaderId2) {
        this.directLeaderId2 = directLeaderId2;
    }

    public String getDirectLeaderName2() {
        return directLeaderName2;
    }

    public void setDirectLeaderName2(String directLeaderName2) {
        this.directLeaderName2 = directLeaderName2;
    }

    public String getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(String userLevel) {
        this.userLevel = userLevel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
