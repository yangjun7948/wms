package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dingj
 * @date 2019/12/26 13:35
 */
public class ProgressEnum {
    /**
     * 进度计划类型 1-总;2-年;3-月
     */
    @Getter
    @AllArgsConstructor
    public enum PlanType {
        GLOBAL("1", "总计划"),
        YEAR("2", "年计划"),
        MONTH("3", "月计划");
        private String value;
        private String desc;
    }

    /**
     *总，年，月计划状态 1-待制定；2-待维护；3-已完结
     */
    @Getter
    @AllArgsConstructor
    public enum ProgressStatus {
        PLAN_MAKE("1", "待制定"),
        PLAN_MAKEING("2", "制定中"),
        PLAN_FIX("3", "待维护"),
        PLAN_END("4", "已完结");
        private String value;
        private String desc;
    }

    /**
     * 计划完成状态1-已完成；2-未完成；3-部分完成
     */
    @Getter
    @AllArgsConstructor
    public enum ProgresscCompleteStatus {
        ALL_COMPLETE("1", "已完成"),
        NOT_COMPLETE("2", "未完成"),
        PART_COMPLETE("3", "部分完成");
        private String value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum ProgressFixStatus {
        NOT_FIX("0", "待维护"),
        COMPLETE_FIX("1", "已完成");
        private String value;
        private String desc;
    }
    /**
     * 是否关键
     */
    @Getter
    @AllArgsConstructor
    public enum IsKey {
        YES_KEY("1", "关键"),
        NO_KEY("2", "不关键");
        private String value;
        private String desc;
    }

    /**
     * 是否合格
     */
    @Getter
    @AllArgsConstructor
    public enum IsQualified {
        YES_QUALIFIED("1", "合格"),
        NO_QUALIFIED("2", "不合格");
        private String value;
        private String desc;
        }


    @Getter
    @AllArgsConstructor
    public enum Column {
        AREA_NAME(0, "区段"),
        NAME(1, "分项工程名称"),
        START_DATE(2, "计划开工时间"),
        END_DATE(3, "计划完工时间");
        private int index;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum InterviewResult {
        TRUE_FLAG("0", "是"),
        FALSE_FLAG("1", "否"),
        UNDETERMINED_FLAG("2", "待定");
        private String vlaue;
        private String label;
    }
}
