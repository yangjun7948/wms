package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @author
 * @date
 * 物品领用流程dto
 */
public class ReimbursementDetailDto {

    private String occurDate;

    private String feeType;

    private String amount;

    private List relevantContractId;
    private String relevantContractName;
    private List relevantContractList;

    private String bearDepartmentName;
    private String bearDepartmentId;

    public List getRelevantContractList() {
        return relevantContractList;
    }

    public void setRelevantContractList(List relevantContractList) {
        this.relevantContractList = relevantContractList;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public List getRelevantContractId() {
        return relevantContractId;
    }

    public void setRelevantContractId(List relevantContractId) {
        this.relevantContractId = relevantContractId;
    }

    public String getRelevantContractName() {
        return relevantContractName;
    }

    public void setRelevantContractName(String relevantContractName) {
        this.relevantContractName = relevantContractName;
    }

    public String getBearDepartmentName() {
        return bearDepartmentName;
    }

    public void setBearDepartmentName(String bearDepartmentName) {
        this.bearDepartmentName = bearDepartmentName;
    }

    public String getBearDepartmentId() {
        return bearDepartmentId;
    }

    public void setBearDepartmentId(String bearDepartmentId) {
        this.bearDepartmentId = bearDepartmentId;
    }
}
