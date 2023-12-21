package com.xiaoyai.activiti.common.dto.process;

/**
 * @author
 * @date
 * 物品领用流程dto
 */
public class PaymentDto {
    private String applicationPayment;
    private String receivedInvoice;
    private String actualAmount;
    private String paymentMethod;
    private String receiveAccountName;
    private String receiveBank;
    private String receiveAccount;
    private String fileUrls;

    public String getApplicationPayment() {
        return applicationPayment;
    }

    public void setApplicationPayment(String applicationPayment) {
        this.applicationPayment = applicationPayment;
    }

    public String getReceivedInvoice() {
        return receivedInvoice;
    }

    public void setReceivedInvoice(String receivedInvoice) {
        this.receivedInvoice = receivedInvoice;
    }

    public String getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(String actualAmount) {
        this.actualAmount = actualAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReceiveAccountName() {
        return receiveAccountName;
    }

    public void setReceiveAccountName(String receiveAccountName) {
        this.receiveAccountName = receiveAccountName;
    }

    public String getReceiveBank() {
        return receiveBank;
    }

    public void setReceiveBank(String receiveBank) {
        this.receiveBank = receiveBank;
    }

    public String getReceiveAccount() {
        return receiveAccount;
    }

    public void setReceiveAccount(String receiveAccount) {
        this.receiveAccount = receiveAccount;
    }

    public String getFileUrls() {
        return fileUrls;
    }

    public void setFileUrls(String fileUrls) {
        this.fileUrls = fileUrls;
    }
}
