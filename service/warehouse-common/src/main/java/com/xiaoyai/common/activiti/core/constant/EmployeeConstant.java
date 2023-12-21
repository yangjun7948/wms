package com.xiaoyai.common.activiti.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 劳务服务常量
 */
public class EmployeeConstant {

    // 班组
    public static class EmpEmployeeGroup {

        // 劳务人员类型
        @Getter
        @AllArgsConstructor
        public enum JobLevelEnum {
            JOB_LEVEL_NORMAL(0, "普通员工"),
            JOB_LEVEL_LEADER(1, "组长");
            private int value;
            private String desc;
        }

    }

    // 劳务人员
    public static class Employee {
        // 性别
        @Getter
        @AllArgsConstructor
        public enum SexEnum {
            WOMAN("0", "女"),
            MAN("1", "男");
            private String value;
            private String desc;
        }

        // 当前状态
        @Getter
        @AllArgsConstructor
        public enum StatusEnum {
            STATUS_LEAVE("0", "离场"),
            STATUS_IN("1", "进场");
            private String value;
            private String desc;
        }

        // 身体状况
        @Getter
        @AllArgsConstructor
        public enum HealthyEnum {
            HEALTHY_BAD("0", "较差"),
            HEALTHY_NORMAL("1", "一般"),
            HEALTHY_GOOD("2", "良好");
            private String value;
            private String desc;

            public static String descOfValue(String value) {
                for (HealthyEnum orderStatusEnum : values()) {
                    if (orderStatusEnum.getValue().equals(value)) {
                        return orderStatusEnum.getDesc();
                    }
                }
                return "其他";
            }

        }

        // 人员类型
        @Getter
        @AllArgsConstructor
        public enum TypeEnum {
            TYPE_TEMP("0", "临时工"),
            TYPE_REGULAR("1", "正式工");
            private String value;
            private String desc;
        }

        // 人员类型
        @Getter
        @AllArgsConstructor
        public enum PostEnum {
            POST_MANAGER("manager", "管理人员");
            private String value;
            private String desc;

            public static String descOfValue(String value) {
                for (PostEnum orderStatusEnum : values()) {
                    if (orderStatusEnum.getValue().equals(value)) {
                        return orderStatusEnum.getDesc();
                    }
                }
                return "其他";
            }
        }
    }

}
