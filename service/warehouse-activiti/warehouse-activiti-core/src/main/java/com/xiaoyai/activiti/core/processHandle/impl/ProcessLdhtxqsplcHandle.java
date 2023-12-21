package com.xiaoyai.activiti.core.processHandle.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.xiaoyai.activiti.common.dto.process.ProcessCreateRequest;
import com.xiaoyai.activiti.core.processHandle.ProcessHandleBase;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 离职交接流程处理
 *
 * @author wangguanghui
 */
@Component("procrs20-ldhtxqsplc")
public class ProcessLdhtxqsplcHandle extends ProcessBaseHandle implements ProcessHandleBase {

    @Override
    public ProcessCreateRequest beforeProcessHandle(JSONObject reqParam) {
        ProcessCreateRequest request = new ProcessCreateRequest();
        request.setProcessCode(reqParam.getString("procCode"));
        request.setOriginatorUserId(reqParam.getString("applyUserId"));
        request.setDeptId(reqParam.getString("departmentId"));
        request.setProcessName(reqParam.getString("procName"));
        request.setOriginatorUserName(reqParam.getString("applyUserName"));
        request.setComment(reqParam.getString("comment"));

        //设置参数Map
        Map<String, String> map = new HashMap<>();
        map.put("employingDepartmentId", reqParam.getString("departmentId"));
        map.put("contractStartDate", reqParam.getString("contractStartDate"));
        map.put("contractEndDate", reqParam.getString("contractEndDate"));
        map.put("contractType", reqParam.getString("contractType"));
        map.put("userLevel", reqParam.getString("userLevel"));
        map.put("userOrgId", reqParam.getString("userOrgId"));
        map.put("userDeptId", reqParam.getString("departmentId"));
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
