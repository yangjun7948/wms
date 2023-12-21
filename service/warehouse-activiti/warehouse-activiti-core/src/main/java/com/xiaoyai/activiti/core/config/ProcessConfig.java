package com.xiaoyai.activiti.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xieyu
 * @date 2020-10-29
 * 流程配置文件
 */
@Component
@ConfigurationProperties(prefix = "process")
public class ProcessConfig {
    private String pictureUrl;

    private String fileUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
