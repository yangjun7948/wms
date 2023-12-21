package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author junyang
 * @date 2022-03-02
 */
public class AttendanceEnum {
    /**
     * 考勤组类型
     */
    @AllArgsConstructor
    @Getter
    public enum Grouptype {
        FIX("0", "固定班制"),
        SHEDULING("1", "排班制"),
        FREE("2", "自由班制");
        private final String type;
        private final String description;
    }

    @AllArgsConstructor
    @Getter
    public enum TrueOrFalse {
        TRUE("1", "真"),
        FALSE("0", "假");
        private final String type;
        private final String description;
    }


    @AllArgsConstructor
    @Getter
    public enum ClockWay {
        TRUE("0", "内勤"),
        FALSE("1", "外勤");
        private final String type;
        private final String description;
    }

    @AllArgsConstructor
    @Getter
    public enum Ways {
        LOCATION("1", "地点打卡"),
        WIFI("2", "WIFI打卡"),
        ALL("3", "都是");
        private final String type;
        private final String description;
    }

    @AllArgsConstructor
    @Getter
    public enum clockType {
        ONDUTY("OnDuty", "上班"),
        OFFDUTY("OffDuty", "下班");
        private final String type;
        private final String description;
    }
    @AllArgsConstructor
    @Getter
    public enum clockResult {
        CLOCK_STATE_NORMAL("Normal", "正常"),
        CLOCK_STATE_EARLY("Early", "早退"),
        CLOCK_STATE_LATE("Late", "迟到"),
        CLOCK_STATE_SERIOUSLATE("SeriousLate", "严重迟到"),
        CLOCK_STATE_ABSENTEEISM("Absenteeism", "旷工迟到"),
        CLOCK_STATE_OUTSIDE("Outside", "考勤范围之外"),
        CLOCK_STATE_NOTSIGNED("NotSigned", "未打卡");
        private final String type;
        private final String description;
    }

    @AllArgsConstructor
    @Getter
    public enum PunchCardState {
        YES("1", "打卡"),
        NO("0", "不打卡");
        private final String type;
        private final String description;
    }

}
