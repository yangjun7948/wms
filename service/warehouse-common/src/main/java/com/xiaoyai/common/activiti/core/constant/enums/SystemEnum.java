package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class SystemEnum {

    @Getter
    @AllArgsConstructor
    public enum NodeType {
        ORG("1", "机构"),
        DEPT("2", "部门"),
        USER("3", "用户");
        private String value;
        private String desc;
    }
}
