package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author Cying
 * @Date 2021-03-17
 */
@Getter
@AllArgsConstructor
public enum BlackListStateEnum {

    BLACK_EXIST("0", "添加黑名单/在库"),
    BLACK_REMOVE("1", "已移除"),
    BLACK_EDIT("2", "编辑黑名单");

    /**
     * 类型
     */
    private String value;
    /**
     * 描述
     */
    private String description;
}
