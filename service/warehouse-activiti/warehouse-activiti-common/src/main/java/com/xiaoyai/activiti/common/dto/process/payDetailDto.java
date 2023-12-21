package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2021-01-04
 */
public class payDetailDto {
    private String paymentType;
    private String payAmount;
    private String payDate;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(String payAmount) {
        this.payAmount = payAmount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }
}
