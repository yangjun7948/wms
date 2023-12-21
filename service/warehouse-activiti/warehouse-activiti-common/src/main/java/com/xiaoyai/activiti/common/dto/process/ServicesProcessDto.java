package com.xiaoyai.activiti.common.dto.process;

public class ServicesProcessDto {

    private String serviceProject;
    private String headerUserName;
    private String headerUserId;
    private String otherService;

    public String getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(String serviceProject) {
        this.serviceProject = serviceProject;
    }

    public String getHeaderUserName() {
        return headerUserName;
    }

    public void setHeaderUserName(String headerUserName) {
        this.headerUserName = headerUserName;
    }

    public String getHeaderUserId() {
        return headerUserId;
    }

    public void setHeaderUserId(String headerUserId) {
        this.headerUserId = headerUserId;
    }

    public String getOtherService() {
        return otherService;
    }

    public void setOtherService(String otherService) {
        this.otherService = otherService;
    }
}
