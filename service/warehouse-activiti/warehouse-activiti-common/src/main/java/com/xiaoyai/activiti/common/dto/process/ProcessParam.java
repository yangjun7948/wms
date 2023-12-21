package com.xiaoyai.activiti.common.dto.process;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author Cying
 * @Date 2020-11-05
 */
@Data
public class ProcessParam implements Serializable {
    /**
     * 流程名称
     */
    private String procName;
    /**
     * 流程编号
     */
    private String procNum;
    /**
     * 流程code
     */
    private String procCode;
    /**
     * 申请部门名称
     */
    private String applyDepartmentName;
    /**
     * 申请部门id
     */
    private String applyDepartmentId;
    /**
     * 申请人id
     */
    private String applyUserId;
    /**
     * 申请人名称
     */
    private String applyUserName;
    /**
     *  意见
     */
    private String comment;

    /**
     * 附件
     */
    private String fileUrls;

}
