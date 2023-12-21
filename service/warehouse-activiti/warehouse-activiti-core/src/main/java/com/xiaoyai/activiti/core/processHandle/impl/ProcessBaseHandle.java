package com.xiaoyai.activiti.core.processHandle.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author junyang
 * @date 2021-12-22
 */
public class ProcessBaseHandle {

    List<Map> paramdoCommonParams(JSONObject request) {
        //保存流程保存操作时的意见及附件
        List<Map> commonParams = new ArrayList<>();
        String commentFile = StrUtil.isBlankIfStr(request.get("commentFile")) ? "" : request.get("commentFile").toString();
        String comment = StrUtil.isBlankIfStr(request.get("comment")) ? "" : request.get("comment").toString();
        String value = comment + ":" + commentFile;
        commonParams.add(MapUtil.builder("key", "comment")
                .put("name", "签字意见")
                .put("value", comment).build());
        return commonParams;
    }
}
