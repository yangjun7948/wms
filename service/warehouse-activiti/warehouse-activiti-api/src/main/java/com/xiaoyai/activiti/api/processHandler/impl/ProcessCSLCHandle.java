package com.xiaoyai.activiti.api.processHandler.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoyai.activiti.common.dto.process.ProcessCSLCStartParam;
import com.xiaoyai.activiti.common.dto.process.ProcessCreateRequest;

import com.xiaoyai.activiti.core.processHandle.ProcessHandleBase;
import com.xiaoyai.common.activiti.core.util.BeanConvertUtil;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 离职交接流程处理
 *
 * @author wangguanghui
 */
@Component("procxz01-cslc")
public class ProcessCSLCHandle extends ProcessBaseHandle implements ProcessHandleBase {

    @Override
    public ProcessCreateRequest beforeProcessHandle(JSONObject reqParam) {
        ProcessCSLCStartParam processLzjjStartParam = BeanConvertUtil.covertBean(reqParam, ProcessCSLCStartParam.class);
        ProcessCreateRequest request = new ProcessCreateRequest();
        request.setProcessCode(processLzjjStartParam.getProcCode());
        request.setOriginatorUserId(processLzjjStartParam.getApplyUserId());
        request.setDeptId(processLzjjStartParam.getApplyDepartmentId());
        request.setProcessName(processLzjjStartParam.getProcName());
        request.setOriginatorUserName(processLzjjStartParam.getApplyUserName());
        request.setComment(processLzjjStartParam.getComment());

        String descSupplement;
        descSupplement = "（测试流程: 申请人）";

        request.setTaskDescriptionSupplement(descSupplement);

        //设置参数Map
        Map<String, String> map = new HashMap<>();

        request.setParamMap(map);

        //处理公共参数
        List<Map> commonParams = this.paramdoCommonParams(reqParam);
        commonParams.forEach(param -> reqParam.put(param.get("key").toString(), param.get("value")));

        request.setFormComponentValues(JSON.toJSONString(reqParam));
        return request;
    }

    @Override
    public void afterProcessHandle(JSONObject reqParam) {

    }

    @Override
    public void afterProcessBackHandle(Map info) {

    }

}
