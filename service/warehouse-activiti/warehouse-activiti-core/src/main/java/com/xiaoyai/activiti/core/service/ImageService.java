package com.xiaoyai.activiti.core.service;

import java.io.IOException;

/**
 * @author junyang
 * @date 2021-11-01
 */
public interface ImageService {
    byte[] getFlowImgByProcInstId(String processInstanceId) throws IOException;
}
