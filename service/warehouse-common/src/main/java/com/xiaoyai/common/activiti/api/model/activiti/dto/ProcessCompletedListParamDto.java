package com.xiaoyai.common.activiti.api.model.activiti.dto;


import java.util.List;

/**
 * @author xieyu
 * @date 2021-01-07
 * 办结流程查询请求参数
 */
public class ProcessCompletedListParamDto extends com.java110.dto.PageDto {
    /**
     * 用户id
     * */
    private String userId;

    /**
     * 查询关键字
     * */
    private String keyWord;
    private String wyUserId;

    private List<String> procTypeList;
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

    public String getWyUserId() {
        return wyUserId;
    }

    public void setWyUserId(String wyUserId) {
        this.wyUserId = wyUserId;
    }

    public void setProcTypeList(List<java.lang.String> procTypeList) {
        this.procTypeList = procTypeList;
    }

    public List<String> getProcTypeList() {
        return procTypeList;
    }
}
