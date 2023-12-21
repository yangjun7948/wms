package com.xiaoyai.activiti.common.dto.process;

import java.util.List;

/**
 * @author
 * @date
 * 物品领用流程dto
 */
public class DailyReimbursementDetailDto {

    private String startTime;
    private String endTime;
    private String startPlace;
    private String endPlace;
    private String transportation;
    private String lunchFee;
    private String accommodation;
    private String subtotal;

    private List relevantContractId;
    private List relevantContractList;
    private String relevantContractName;

    private String bearDepartmentName;

    public List getRelevantContractList() {
        return relevantContractList;
    }

    public void setRelevantContractList(List relevantContractList) {
        this.relevantContractList = relevantContractList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    public String getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getLunchFee() {
        return lunchFee;
    }

    public void setLunchFee(String lunchFee) {
        this.lunchFee = lunchFee;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
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
}
