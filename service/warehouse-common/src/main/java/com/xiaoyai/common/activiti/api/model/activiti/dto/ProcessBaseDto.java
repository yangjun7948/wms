package com.xiaoyai.common.activiti.api.model.activiti.dto;


import com.alibaba.fastjson2.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/23
 * 流程返回基类
 */
public class ProcessBaseDto implements Serializable {

    /**
     * 表单数据
     * */
    private JSONObject formData;

    /**
     * 流程流转意见
     * */
    private List<ProcessCommentCirculate> processCommentList;

    /**
     * 流程各节点意见
     * */
    private List nodeCommentList;

    /**
     * 流程附件资源
     * */
    private String fileAddress;

    /**
     * 流程id
     * */
    private String procId;

    /**
     * 流程状态
     * */
    private String statusType;

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }

    public JSONObject getFormData() {
        return formData;
    }

    public void setFormData(JSONObject formData) {
        this.formData = formData;
    }

    public List<ProcessCommentCirculate> getProcessCommentList() {
        return processCommentList;
    }

    public void setProcessCommentList(List<ProcessCommentCirculate> processCommentList) {
        this.processCommentList = processCommentList;
    }

    public List getNodeCommentList() {
        return nodeCommentList;
    }

    public void setNodeCommentList(List nodeCommentList) {
        this.nodeCommentList = nodeCommentList;
    }

    public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }
}
