package com.xiaoyai.common.activiti.api.model.activiti.dto;


import java.io.Serializable;
import java.util.List;

/**
 * @author xieyu
 * @date 2020/5/11
 * 流程列表返回封装
 */
public class MyProcessDto extends MorePageVo implements Serializable {
    /**
     * 流程列表
     * */
    List<MyProcessApplyDto> myProcessApplyDtos;

    /**
     * 所有流程树列表
     * */
    List<ProcessListDto> processTypeTree;


    public List<MyProcessApplyDto> getMyProcessApplyDtos() {
        return myProcessApplyDtos;
    }

    public void setMyProcessApplyDtos(List<MyProcessApplyDto> myProcessApplyDtos) {
        this.myProcessApplyDtos = myProcessApplyDtos;
    }

    public List<ProcessListDto> getProcessTypeTree() {
        return processTypeTree;
    }

    public void setProcessTypeTree(List<ProcessListDto> processTypeTree) {
        this.processTypeTree = processTypeTree;
    }
}
