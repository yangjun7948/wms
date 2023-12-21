package com.xiaoyai.activiti.common.dto.process;


import java.util.List;

/**
 * @author xieyu
 * @date 2020-10-29
 * 入职审批发起请求参数
 */
public class ProcessRzspStartParam extends ProcessReqBaseParam {
    private String procName;
    private String procNum;
    private String procCode;
    private String applyDepartmentName;
    private String applyDepartmentId;
    private String applyUserId;
    private String applyUserName;
    private String associationRecruitmentProcessId;
    private String associationRecruitmentProcessName;
    private List associationRecruitmentProcessList;
    private String name;
    private String sex;
    private String age;
    private String entryPosition;//入职岗位
    private String employingDepartmentId;//入职岗位
    private String employingDepartmentName;//入职岗位
    private String directLeaderId;
    private String directLeaderName;//直接上级
    private String secondEntry;//是否二次入驻
    private String secondEntrySituation;//二次入职情况说明
    private String positionType;//岗位类别
    private String directSupervisorId;
    private String directSupervisorName;//督导师
    private String entryDate;//入职日期
    private String trialStartTime;//试用截止日期
    private String trialTime;//使用时间
    private String jobNumber;
    private String workPlace;
    private String entryStatus;//入职状态
    private String nation;
    private String politicalStatus;
    private String level;
    private String marryFlagOne;//是否结婚
    private String IDNumber;//身份证号
    private String dateOfBirth;//出生年月
    private String position;
    private String phoneNumber;//手机号
    private String highestEducation;//最高学历
    private String profession;//专业
    private String graduatedSchool;//毕业学校
    private String graduatedTime;//毕业学校
    private String professional;//职称
    private String homeAddress;
    private String emergencyContactId;
    private String emergencyContactName;//紧急联系人
    private String detailedAddress;//紧急联系人
    private String emergencyContactPhone;
    private String securityLevel;//安全级别
    private String entryDepartmentId;
    private String entryDepartmentName;//入职部门
    private String trialSalary;//试用工资
    private String trialPeriodBonus;//试用工资
    private String totalProbationPeriod;//试用合计
    private String remark;
    private String basicWage;//基本工资
    private String jobWage;//岗位工资
    private String performancePay;//绩效工资
    private String fullAttendanceAward;//满勤奖
    private String communicationFee;//通讯费
    private String transportationAllowance;//交通补贴
    private String educationSubsidy;//学历补贴
    private String titleSubsidy;//职称补贴
    private String socialSecuritySubsidy;//社保补贴
    private String lunchSubsidy;//午餐补助
    private String buySocialSecurityFlag;//是否买社保
    private String total;//合计
    private String healthReportFlag;//体检报告
    private String leaveReportFlag;//
    private String identityCertificateFlag;//身份复印件
    private String graduationCertificateFlag;
    private String bankCardCertificateFlag;//是否提交银行卡复印件
    private String accumulationFundFlag;//社保公积金缴纳情况确认
    private String householdRegisterFlag;
    private String marriageCertificateFlag;
    private String socialSecurityFlag;
    private String academicCertificateFlag;
    private String degreeCertificateFlag;
    private String skillCertificateFlag;
    private String professionalCertificateFlag;
    private String photoFlag;
    private String contractName;//社保公积金缴纳情况确认
    private String contractSignTime;//社保公积金缴纳情况确认
    private String contractStartTime;//社保公积金缴纳情况确认
    private String contractEndTime;//社保公积金缴纳情况确认
    private String comment;
    private String contractTplId;
    private String contractCompany;
    private String contractType;
    private String overtimePay;
    private List contractFileList;
    private String belongCommunityName;
    private String userBelongCommunityId;
    private String orgId;
    private String staffId;

    public String getSocialSecuritySubsidy() {
        return socialSecuritySubsidy;
    }

    public void setSocialSecuritySubsidy(String socialSecuritySubsidy) {
        this.socialSecuritySubsidy = socialSecuritySubsidy;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getEmployingDepartmentId() {
        return employingDepartmentId;
    }

    public void setEmployingDepartmentId(String employingDepartmentId) {
        this.employingDepartmentId = employingDepartmentId;
    }

    public String getEmployingDepartmentName() {
        return employingDepartmentName;
    }

    public void setEmployingDepartmentName(String employingDepartmentName) {
        this.employingDepartmentName = employingDepartmentName;
    }

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

    public String getAssociationRecruitmentProcessId() {
        return associationRecruitmentProcessId;
    }

    public void setAssociationRecruitmentProcessId(String associationRecruitmentProcessId) {
        this.associationRecruitmentProcessId = associationRecruitmentProcessId;
    }

    public String getAssociationRecruitmentProcessName() {
        return associationRecruitmentProcessName;
    }

    public void setAssociationRecruitmentProcessName(String associationRecruitmentProcessName) {
        this.associationRecruitmentProcessName = associationRecruitmentProcessName;
    }

    public List getAssociationRecruitmentProcessList() {
        return associationRecruitmentProcessList;
    }

    public void setAssociationRecruitmentProcessList(List associationRecruitmentProcessList) {
        this.associationRecruitmentProcessList = associationRecruitmentProcessList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEntryPosition() {
        return entryPosition;
    }

    public void setEntryPosition(String entryPosition) {
        this.entryPosition = entryPosition;
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

    public String getPositionType() {
        return positionType;
    }

    public void setPositionType(String positionType) {
        this.positionType = positionType;
    }

    public String getDirectSupervisorId() {
        return directSupervisorId;
    }

    public void setDirectSupervisorId(String directSupervisorId) {
        this.directSupervisorId = directSupervisorId;
    }

    public String getDirectSupervisorName() {
        return directSupervisorName;
    }

    public void setDirectSupervisorName(String directSupervisorName) {
        this.directSupervisorName = directSupervisorName;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getTrialStartTime() {
        return trialStartTime;
    }

    public void setTrialStartTime(String trialStartTime) {
        this.trialStartTime = trialStartTime;
    }

    public String getTrialTime() {
        return trialTime;
    }

    public void setTrialTime(String trialTime) {
        this.trialTime = trialTime;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getEntryStatus() {
        return entryStatus;
    }

    public void setEntryStatus(String entryStatus) {
        this.entryStatus = entryStatus;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMarryFlagOne() {
        return marryFlagOne;
    }

    public void setMarryFlagOne(String marryFlagOne) {
        this.marryFlagOne = marryFlagOne;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHighestEducation() {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation) {
        this.highestEducation = highestEducation;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getGraduatedSchool() {
        return graduatedSchool;
    }

    public void setGraduatedSchool(String graduatedSchool) {
        this.graduatedSchool = graduatedSchool;
    }

    public String getGraduatedTime() {
        return graduatedTime;
    }

    public void setGraduatedTime(String graduatedTime) {
        this.graduatedTime = graduatedTime;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getEmergencyContactId() {
        return emergencyContactId;
    }

    public void setEmergencyContactId(String emergencyContactId) {
        this.emergencyContactId = emergencyContactId;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }

    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getEntryDepartmentId() {
        return entryDepartmentId;
    }

    public void setEntryDepartmentId(String entryDepartmentId) {
        this.entryDepartmentId = entryDepartmentId;
    }

    public String getEntryDepartmentName() {
        return entryDepartmentName;
    }

    public void setEntryDepartmentName(String entryDepartmentName) {
        this.entryDepartmentName = entryDepartmentName;
    }

    public String getTrialSalary() {
        return trialSalary;
    }

    public void setTrialSalary(String trialSalary) {
        this.trialSalary = trialSalary;
    }

    public String getTrialPeriodBonus() {
        return trialPeriodBonus;
    }

    public void setTrialPeriodBonus(String trialPeriodBonus) {
        this.trialPeriodBonus = trialPeriodBonus;
    }

    public String getTotalProbationPeriod() {
        return totalProbationPeriod;
    }

    public void setTotalProbationPeriod(String totalProbationPeriod) {
        this.totalProbationPeriod = totalProbationPeriod;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getHealthReportFlag() {
        return healthReportFlag;
    }

    public void setHealthReportFlag(String healthReportFlag) {
        this.healthReportFlag = healthReportFlag;
    }

    public String getLeaveReportFlag() {
        return leaveReportFlag;
    }

    public void setLeaveReportFlag(String leaveReportFlag) {
        this.leaveReportFlag = leaveReportFlag;
    }

    public String getIdentityCertificateFlag() {
        return identityCertificateFlag;
    }

    public void setIdentityCertificateFlag(String identityCertificateFlag) {
        this.identityCertificateFlag = identityCertificateFlag;
    }

    public String getGraduationCertificateFlag() {
        return graduationCertificateFlag;
    }

    public void setGraduationCertificateFlag(String graduationCertificateFlag) {
        this.graduationCertificateFlag = graduationCertificateFlag;
    }

    public String getBankCardCertificateFlag() {
        return bankCardCertificateFlag;
    }

    public void setBankCardCertificateFlag(String bankCardCertificateFlag) {
        this.bankCardCertificateFlag = bankCardCertificateFlag;
    }

    public String getAccumulationFundFlag() {
        return accumulationFundFlag;
    }

    public void setAccumulationFundFlag(String accumulationFundFlag) {
        this.accumulationFundFlag = accumulationFundFlag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSecondEntry() {
        return secondEntry;
    }

    public void setSecondEntry(String secondEntry) {
        this.secondEntry = secondEntry;
    }

    public String getSecondEntrySituation() {
        return secondEntrySituation;
    }

    public void setSecondEntrySituation(String secondEntrySituation) {
        this.secondEntrySituation = secondEntrySituation;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractSignTime() {
        return contractSignTime;
    }

    public void setContractSignTime(String contractSignTime) {
        this.contractSignTime = contractSignTime;
    }

    public String getContractStartTime() {
        return contractStartTime;
    }

    public void setContractStartTime(String contractStartTime) {
        this.contractStartTime = contractStartTime;
    }

    public String getContractEndTime() {
        return contractEndTime;
    }

    public void setContractEndTime(String contractEndTime) {
        this.contractEndTime = contractEndTime;
    }

    public List getContractFileList() {
        return contractFileList;
    }

    public void setContractFileList(List contractFileList) {
        this.contractFileList = contractFileList;
    }

    public String getContractTplId() {
        return contractTplId;
    }

    public void setContractTplId(String contractTplId) {
        this.contractTplId = contractTplId;
    }

    public String getContractCompany() {
        return contractCompany;
    }

    public void setContractCompany(String contractCompany) {
        this.contractCompany = contractCompany;
    }

    public String getHouseholdRegisterFlag() {
        return householdRegisterFlag;
    }

    public void setHouseholdRegisterFlag(String householdRegisterFlag) {
        this.householdRegisterFlag = householdRegisterFlag;
    }

    public String getMarriageCertificateFlag() {
        return marriageCertificateFlag;
    }

    public void setMarriageCertificateFlag(String marriageCertificateFlag) {
        this.marriageCertificateFlag = marriageCertificateFlag;
    }

    public String getAcademicCertificateFlag() {
        return academicCertificateFlag;
    }

    public void setAcademicCertificateFlag(String academicCertificateFlag) {
        this.academicCertificateFlag = academicCertificateFlag;
    }

    public String getDegreeCertificateFlag() {
        return degreeCertificateFlag;
    }

    public void setDegreeCertificateFlag(String degreeCertificateFlag) {
        this.degreeCertificateFlag = degreeCertificateFlag;
    }

    public String getSkillCertificateFlag() {
        return skillCertificateFlag;
    }

    public void setSkillCertificateFlag(String skillCertificateFlag) {
        this.skillCertificateFlag = skillCertificateFlag;
    }

    public String getProfessionalCertificateFlag() {
        return professionalCertificateFlag;
    }

    public void setProfessionalCertificateFlag(String professionalCertificateFlag) {
        this.professionalCertificateFlag = professionalCertificateFlag;
    }

    public String getPhotoFlag() {
        return photoFlag;
    }

    public void setPhotoFlag(String photoFlag) {
        this.photoFlag = photoFlag;
    }

    public String getOvertimePay() {
        return overtimePay;
    }

    public void setOvertimePay(String overtimePay) {
        this.overtimePay = overtimePay;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getBelongCommunityName() {
        return belongCommunityName;
    }

    public void setBelongCommunityName(String belongCommunityName) {
        this.belongCommunityName = belongCommunityName;
    }

    public String getUserBelongCommunityId() {
        return userBelongCommunityId;
    }

    public void setUserBelongCommunityId(String userBelongCommunityId) {
        this.userBelongCommunityId = userBelongCommunityId;
    }

    public String getSocialSecurityFlag() {
        return socialSecurityFlag;
    }

    public void setSocialSecurityFlag(String socialSecurityFlag) {
        this.socialSecurityFlag = socialSecurityFlag;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
