package com.xiaoyai.common.activiti.api.model.activiti.dto;

import com.java110.dto.PageDto;

import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/23
 * 查询我的请求参数
 */
public class ProcessMyRequestListParamDto extends PageDto {
    /**
     * 用户id
     * */
    private String userId;

    /**
     * 查询关键字
     * */
    private String keyWord;

    /**
     * 流程id
     * */
    private String procId;

    /**
     * 流程创建者
     * */
    private String createUser;

    /**
     * 开始时间
     * */
    private String startTime;

    /**
     * 结束时间
     * */
    private String endTime;

    /**
     * 流程id
     */
    private String prodId;

    /**
     * 流程类别列表
     */
    private List<String> procTypeList;

    private String wyUserId;
    /**
     * 请求类型 -1：全部  0：未结束  1：已结束
     */
    private String type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public List<String> getProcTypeList() {
        return procTypeList;
    }

    public void setProcTypeList(List<String> procTypeList) {
        this.procTypeList = procTypeList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWyUserId() {
        return wyUserId;
    }

    public void setWyUserId(String wyUserId) {
        this.wyUserId = wyUserId;
    }

    @Override
    public String toString() {
        return "ProcessMyRequestListParamDto{" +
                "userId='" + userId + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", procId='" + procId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", prodId='" + prodId + '\'' +
                ", procTypeList=" + procTypeList +
                ", type='" + type + '\'' +
                '}';
    }
}
