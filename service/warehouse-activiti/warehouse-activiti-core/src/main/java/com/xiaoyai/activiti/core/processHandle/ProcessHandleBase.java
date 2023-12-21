package com.xiaoyai.activiti.core.processHandle;

import com.alibaba.fastjson2.JSONObject;
import com.xiaoyai.activiti.common.dto.process.ProcessCreateRequest;

import java.util.Map;

/**
 * @author xieyu
 * @date 2020-10-29
 * 流程处理基类
 */
public interface ProcessHandleBase {
     ProcessCreateRequest beforeProcessHandle(JSONObject reqParam);

     void afterProcessHandle(JSONObject reqParam);

     void afterProcessBackHandle(Map info);
}
