package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xieyu
 * @date 2021-10-14
 */
public class HireEnum {
    /**
     * 数据状态
     */
    @AllArgsConstructor
    @Getter
    public enum DataState {
        BEING_USED("0", "在用"),
        EXPIRED("1", "删除");

        private final String value;
        private final String description;
    }

    /**
     * 数据状态
     */
    @AllArgsConstructor
    @Getter
    public enum state {
        PENDING("0", "待定中"),
        IN_THE_INTERVIEW("1", "面试中"),
        TALENT_POOL("2", "人才库"),
        ELIMINATED("3", "已淘汰");

        private final String value;
        private final String description;
    }

    /**
     * 面试轮次
     */
    @AllArgsConstructor
    @Getter
    public enum interviewRound {
        NO_INTERVIEW("0", "未面试"),
        PRELIMINARY_TEST("1", "初试"),
        RETEST("2", "复试"),
        FINAL_TEST("3", "终试");

        private final String value;
        private final String description;
    }

    /**
     * 面试结果
     */
    @AllArgsConstructor
    @Getter
    public enum interviewResult {
        ADOPT("0", "通过"),
        ELIMINATE("1", "淘汰"),
        UNDETERMINED("2", "待定"),
        PROPOSED_EMPLOYMENT("3", "拟录用"),
        TALENT_POOL("4", "人才库"),
        GIVE_UP("5", "放弃面试");

        private final String value;
        private final String description;
    }
    /**
     * 面试结果
     */
    @AllArgsConstructor
    @Getter
    public enum EntryStatus {
        GIVE_UP("0", "放弃入职"),
        PROPOSED_ENTRY("1", "拟入职");

        private final String value;
        private final String description;
    }
    /**
     * 健康状况
     */
    @AllArgsConstructor
    @Getter
    public enum HealthStatus {
        Y(0, "是"),
        F(1, "否");

        private final Integer value;
        private final String description;
    }

    /**
     * 紧急程度
     */
    @AllArgsConstructor
    @Getter
    public enum EmergencyLevel {
        // 0: 正常, 1: 重要, 2: 紧急
        NORMAL("0", "正常"),
        IMPORTANT("1", "重要"),
        EMERGENCY("2", "紧急");

        private final String value;
        private final String description;
    }
}
