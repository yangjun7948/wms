package com.xiaoyai.common.activiti.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class WorkspaceConstant {

    // 通知单
    public static class Notice {

        public static String NAME = "通知单";

        //  0-统计(全部)/1-监管/2-检查/3-整改
        @Getter
        @AllArgsConstructor
        public enum PageTypeEnum {
            STATISTICS("0", "统计"),
            WATCH("1", "监管"),
            CHECK("2", "检查"),
            RECTIFICATION("3", "整改");
            private String value;
            private String desc;
        }

        // 通知单类型：安全/质量问题
        @Getter
        @AllArgsConstructor
        public enum NoticeTypeEnum {
            SAFE("1", "安全"),
            QUALITY("2", "质量");
            private String value;
            private String desc;
        }

        // 通知单状态
        @Getter
        @AllArgsConstructor
        public enum NoticeStateEnum {
            WAIT_CONFIRM("1", "待确认", "未确认", "未确认"),
            UPDATING("2", "整改中", "未回复", "整改中"),
            NOT_CHECK("3", "未复验", "复验中", "未复验"),
            CHECK_FAIL("4", "未通过", "未通过", "未通过"),
            OVER("5", "已完结", "已完结", "已完结");
            private String value;
            private String jianLi;
            private String shiGong;
            private String jianShe;

            /**
             * 根据值获取枚举
             *
             * @param value
             * @return
             */
            public static NoticeStateEnum getEnumByValue(String value) {
                for (NoticeStateEnum item : values()) {
                    if (item.getValue().equals(value)) {
                        return item;
                    }
                }
                return NoticeStateEnum.OVER;
            }

            /**
             * 根据值和机构类型获取对应的状态描述
             *
             * @param value   状态值
             * @param orgType 机构类型
             * @return
             */
            public static String getDescByValueAndOrgType(String value, String orgType) {
                NoticeStateEnum type = NoticeStateEnum.getEnumByValue(value);
                if (SystemConstant.Org.OrgTypeEnum.JIAN_LI.getValue().equals(orgType)) {
                    return type.getJianLi();
                } else if (SystemConstant.Org.OrgTypeEnum.SHI_GONG.getValue().equals(orgType)) {
                    return type.getShiGong();
                } else if (SystemConstant.Org.OrgTypeEnum.JIAN_SHE.getValue().equals(orgType)) {
                    return type.getJianShe();
                }
                return type.getJianLi();
            }
        }

    }

    // 抄送
    public static class Copy {

        // 抄送类型 通知0/处罚1/复验2
        @Getter
        @AllArgsConstructor
        public enum CopyTypeEnum {
            NOTICE("0", "通知"),
            TICKET("1", "处罚"),
            RECHECK("2", "复验");
            private String value;
            private String desc;
        }

    }

    // 通知单/回复单详情
    public static class Detail {

        //  类型  1: 通知单内容 2：回复单内容
        @Getter
        @AllArgsConstructor
        public enum DetailTypeEnum {
            NOTICE("1", "通知单"),
            REPLY("2", "回复单");
            private String value;
            private String desc;
        }

    }

    // 处罚单信息
    public static class Ticket {

        public static String NAME = "处罚单";

        //  处罚类型 1-安全；2-质量；3-进度
        @Getter
        @AllArgsConstructor
        public enum PunishTypeEnum {
            SAFE("1", "安全"),
            QUALITY("2", "质量"),
            PROGRESS("3", "进度");
            private String value;
            private String desc;

            public static String descOfValue(String value) {
                for (PunishTypeEnum item : values()) {
                    if (item.getValue().equals(value)) {
                        return item.getDesc();
                    }
                }
                return "其他";
            }
        }

        // 处罚状态 0未确认；1确认
        @Getter
        @AllArgsConstructor
        public enum TicketStateEnum {
            NOT_CONFIRM("1", "未确认"),
            OVER("5", "已完结");
            private String value;
            private String desc;
        }

    }

    public static class Reply {
        @Getter
        @AllArgsConstructor
        public enum OfferEnum {
            NOT_CHECK("0", "未检查"),
            OFFER("1", "通过"),
            RETURN("2", "不通过");
            private String value;
            private String desc;
        }
    }


    public static class Progress {
        @Getter
        @AllArgsConstructor
        public enum Column {
            BRANCH(0, "分部工程"),
            CHILD_BRANCH(1, "子分部工程"),
            SUB_ITEM(2, "分项工程"),
            DAYS(3, "天数"),
            PLAN_START_DATE(4, "计划开工时间"),
            PLAN_END_DATE(5, "计划完工时间");
            private int index;
            private String desc;
        }

        // 进度状态 0-未开始；1-提前；2-正常；3-延期；4-进行中
        @Getter
        @AllArgsConstructor
        public enum ProgressState {
            WAIT("0", "未开始"),
            PRE("1", "提前"),
            NORMAL("2", "正常"),
            DELAY("3", "延期"),
            DOING("4", "进行中");
            private String value;
            private String desc;
        }

        // 验收状态 0-未提交；1-未验收；2-未通过;3-已完成;
        @Getter
        @AllArgsConstructor
        public enum CheckState {
            NOT_SUBMIT("0", "未提交"),
            NOT_CHECK("1", "未验收"),
            NOT_SUCCESS("2", "未通过"),
            SUCCESS("3", "已完成");
            private String value;
            private String desc;
        }


        // 计划类型：0-总计划；1-年计划；2-月计划；3-周计划
        @Getter
        @AllArgsConstructor
        public enum ProgressType {
            GLOBAL("0", "总计划"),
            YEAR("1", "年计划"),
            MONTH("2", "月计划"),
            WEEK("3", "周计划");
            private String value;
            private String desc;
        }

    }


}
