package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author junyang
 * @date 2019/11/22
 */
public class BillEnum {
    @Getter
    @AllArgsConstructor
    /**
     * 单据类型
     */
    public enum BillType {
        NOTICE("1", "通知单"),
        PUNISH("2", "处罚单"),
        CONTACT("3", "联系单"),
        REPLY("4", "回复单");
        private String value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    /**
     * 业务类型
     */
    public enum BusinessType {
        SAFE("1", "安全"),
        QUALITY("2", "质量"),
        PROGRESS("3", "进度"),
        OTHER("4", "其他");
        private String value;
        private String desc;

        /**
         * 根据值获取枚举
         *
         * @param value 值
         * @return 枚举
         */
        public static BusinessType getEnumByValue(String value) {
            for (BusinessType item : values()) {
                if (item.getValue().equals(value)) {
                    return item;
                }
            }
            return BusinessType.OTHER;
        }
    }

    @Getter
    @AllArgsConstructor
    /**
     * 接收类型
     */
    public enum ReceiveType {
        RECEIVE("1", "接收"),
        COPY("2", "抄送");
        private String value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    /**
     * 已读未读
     */
    public enum ReadFlag {
        NO("0", "未读"),
        YES("1", "已读");
        private String value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor

    /**
     * 联系单状态
     */
    public enum ConatctBillState {
        NOSEND("0", "草稿"),
        SEND("1", "已发送");
        private String value;
        private String desc;
    }

    /**
     * 通知单状态    1-草稿;2-未审核;3-审核未通过;4-未回复;5-未复查;6-未通过;7-完结;
     */
    @Getter
    @AllArgsConstructor
    public enum NoticeBillState {
        NOT_SEND("1", "草稿"),
        NOT_EXAM("2", "未审核"),
        EXAM_RETURN("3", "审核未通过"),
        NOT_REPLY("4", "未回复"),
        NOT_RECHECK("5", "未复查"),
        NOT_PASS("6", "未通过"),
        FINISH("7", "完结");
        private String value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    /**
     * 处罚单状态
     */
    public enum PunishBillState {
        NOSEND("1", "草稿"),
        UNAUDITED("2", "未审核"),
        NOTPASS("3", "未通过"),
        SEND("4", "已发送");
        private String value;
        private String desc;
    }

    /**
     * 权限
     */
    @Getter
    @AllArgsConstructor
    public enum Permission {
        //质量
        NOTICE_QUALITY_SAVE("notice_quality_save", "通知单新增编辑权限"),
        NOTICE_QUALITY_EXAM("notice_quality_exam", "通知单审核权限"),
        NOTICE_QUALITY_DEL("notice_quality_del", "通知单删除权限"),
        NOTICE_QUALITY_REPLY("notice_quality_reply","通知单回复权限"),
        //安全
        NOTICE_SAFE_SAVE("notice_safe_save", "通知单新增编辑权限"),
        NOTICE_SAFE_EXAM("notice_safe_exam", "通知单审核权限"),
        NOTICE_SAFE_DEL("notice_safe_del", "通知单删除权限"),
        NOTICE_SAFE_REPLY("notice_safe_reply","通知单回复权限"),
        //进度
        NOTICE_PROGRESS_SAVE("notice_progress_save", "通知单新增编辑权限"),
        NOTICE_PROGRESS_EXAM("notice_progress_exam", "通知单审核权限"),
        NOTICE_PROGRESS_DEL("notice_progress_del", "通知单删除权限"),
        NOTICE_PROGRESS_REPLY("notice_progress_reply","通知单回复权限"),

        PUNISH_EXAM("ws_punish_exam","处罚单审核权限"),
        PUNISH_ADD("ws_punish_add","处罚单新增编辑权限"),

        //检查单
        CHECK_SAVE("check_save","检查单新增权限"),
        CHECK_REPLY("check_reply","检查单回复权限"),
        CHECK_RECHECK("check_recheck","检查单复查权限");
        private String value;
        private String desc;
    }

    /**
     * 查询类型
     */
    @Getter
    @AllArgsConstructor
    public enum QueryType {
        JIANLI(1, "监理"),
        LEADER(2, "总监代表"),
        OTHER(3, "其他（接收/抄送）");
        private int value;
        private String desc;
    }

    /**
     * 权限
     */
    @Getter
    @AllArgsConstructor
    public enum PunishPermission {
        PUNISH_EXAM("ws_punish_exam", "处罚单审核权限"),
        PUNISH_ADD("ws_punish_add","新增处罚单");
        private String value;
        private String desc;
    }

    @Getter
    @AllArgsConstructor
    public enum ExamResult {
        SUCCESS("1", "审核通过"),
        FAIL("2", "审核不通过");
        private String value;
        private String desc;
    }

    /**
     * 回复状态 [1-未复查;2-已通过;3-未通过;]
     */
    @Getter
    @AllArgsConstructor
    public enum ReplyBillState {
        NOT_CHECK("1", "未复查"),
        PASS("2", "已通过"),
        RETURN("3", "未通过");
        private String value;
        private String desc;
    }

    /**
     * 操作类型 [1-发送;2-查看;3-回复;4-退回;5-通过;6-再次回复;]
     */
    @Getter
    @AllArgsConstructor
    public enum OperateType {
        SEND("1", "发送"),
        WATCH("2", "查看"),
        REPLY("3", "回复"),
        RETURN("4", "退回"),
        PASS("5", "通过"),
        RE_REPLY("6", "重新回复");
        private String value;
        private String desc;
    }

    /**
     * 房屋检查状态
     */
    @Getter
    @AllArgsConstructor
    public enum RoomCheckState {
        UnCheck("0", "未检查"),
        Checked("1", "已检查");
        private String value;
        private String desc;
    }
}
