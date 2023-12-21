package com.xiaoyai.activiti.common.dto.process;

/**
 * @Author Cying
 * @Date 2021-01-04
 */
public class performanceDetaiDto {
    private String performanceContent;
    private String performanceAmount;
    private String performanceOther;
    private String performanceEndDate;

    public String getPerformanceContent() {
        return performanceContent;
    }

    public void setPerformanceContent(String performanceContent) {
        this.performanceContent = performanceContent;
    }

    public String getPerformanceAmount() {
        return performanceAmount;
    }

    public void setPerformanceAmount(String performanceAmount) {
        this.performanceAmount = performanceAmount;
    }

    public String getPerformanceOther() {
        return performanceOther;
    }

    public void setPerformanceOther(String performanceOther) {
        this.performanceOther = performanceOther;
    }

    public String getPerformanceEndDate() {
        return performanceEndDate;
    }

    public void setPerformanceEndDate(String performanceEndDate) {
        this.performanceEndDate = performanceEndDate;
    }
}
