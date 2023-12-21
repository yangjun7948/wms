package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author junyang
 * @date 2019/12/12
 */
public class MessageEnum {
    /**
     * 待办类型
     */
    @Getter
    @AllArgsConstructor
    public enum TodoType {
        PERSON("1", "个人"),
        ROLE("2", "角色"),
        ORG("3", "组织");
        private String value;
        private String desc;
    }
}
