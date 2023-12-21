package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xieyu
 * @date 2021-04-22
 */
public class AttendanceSalaryEnum {

    /**
     * 考勤状态
     */
    @AllArgsConstructor
    @Getter
    public enum AttendanceStatus {
        UN_UPLOAD("0", "未上传"),
        UPLOAD("1", "已上传");

        private final String type;
        private final String description;
    }

    /**
     * 薪资状态
     */
    @AllArgsConstructor
    @Getter
    public enum SalaryStatus {
        UN_UPLOAD("0", "未上传"),
        UPLOAD("1", "已上传");

        private final String type;
        private final String description;
    }

    /**
     * 发送状态
     */
    @AllArgsConstructor
    @Getter
    public enum SendStatus {
        UN_SEND("0", "未发送"),
        SEND("1", "已发送");
        private final String type;
        private final String description;
    }

    /**
     * 查看状态
     */
    @AllArgsConstructor
    @Getter
    public enum CheckStatus {
        UN_CHECK("0", "未查看"),
        CHECK("1", "已查看");
        private final String type;
        private final String description;
    }

    /**
     * 确认状态
     */
    @AllArgsConstructor
    @Getter
    public enum ConfirmStatus {
        UN_CONFIRM("0", "未确认"),
        CONFIRM("1", "已确认");
        private final String type;
        private final String description;
    }
}
