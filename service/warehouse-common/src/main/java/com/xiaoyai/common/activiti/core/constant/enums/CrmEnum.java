package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xieyu
 * @date 2021-10-14
 */
public class CrmEnum {
    /**
     * 客户状态
     */
    @AllArgsConstructor
    @Getter
    public enum CustomerState {
        REPORT_CUSTOMER("1", "报备客户"),
        EFFICIENT_CUSTOMER("2", "有效客户"),
        PUBLIC_CUSTOMER("3", "公有客户");

        private final String value;
        private final String description;
    }
    /**
     * 客户过期状态
     */
    @AllArgsConstructor
    @Getter
    public enum RelationState{
        NOT_EXPIRED("0", "未过期"),
        EXPIRED("1", "已过期");

        private final String value;
        private final String description;
    }
    /**
     * 审核状态
     */
    @AllArgsConstructor
    @Getter
    public enum ApproveState{
        REFUSED("0", "拒绝"),
        PASSED("1", "通过"),
        PENDING_APPROVAL("2", "待审核");

        private final String value;
        private final String description;
    }

    /**
     * 删除状态
     */
    @AllArgsConstructor
    @Getter
    public enum DelFlag{
        UNDEL("0", "在用的"),
        DEL("1", "失效的");

        private final String value;
        private final String description;
    }
    /**
     * 划归类型
     */
    @AllArgsConstructor
    @Getter
    public enum RelationType{
        UN_ASSIGN("0", "非划归"),
        ASSIGN("1", "1划归");

        private final String value;
        private final String description;
    }
}
