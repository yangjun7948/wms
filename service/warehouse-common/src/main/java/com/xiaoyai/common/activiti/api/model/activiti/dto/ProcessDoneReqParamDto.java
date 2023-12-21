package com.xiaoyai.common.activiti.api.model.activiti.dto;

import com.java110.dto.PageDto;

import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/23
 * 查询我的已办请求参数
 */
public class ProcessDoneReqParamDto extends PageDto {
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

    private String wyUserId;
    /**
     * 流程创建者
     * */
    private String createUser;

    private List procTypeList;

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

    public List getProcTypeList() {
        return procTypeList;
    }

    public void setProcTypeList(List procTypeList) {
        this.procTypeList = procTypeList;
    }

    public String getWyUserId() {
        return wyUserId;
    }

    public void setWyUserId(String wyUserId) {
        this.wyUserId = wyUserId;
    }

    @Override
    public String toString() {
        return "ProcessDoneReqParamDto{" +
                "userId='" + userId + '\'' +
                ", keyWord='" + keyWord + '\'' +
                ", procId='" + procId + '\'' +
                ", createUser='" + createUser + '\'' +
                ", procTypeList=" + procTypeList +
                '}';
    }
}
