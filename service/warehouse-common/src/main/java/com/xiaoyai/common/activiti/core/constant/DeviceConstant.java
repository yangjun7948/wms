package com.xiaoyai.common.activiti.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class DeviceConstant {

    public static class VideoGroup {

        // 接入类型 [0-RTMP推流;1-GB28181拉流]
        @Getter
        @AllArgsConstructor
        public enum InProtocol {
            RTMP("0", "RTMP推流"),
            GB28181("1", "GB28181拉流");
            private String value;
            private String desc;
        }

    }

    public static class VideoEquip {

        // 自动拉流（0：false，1：true）
        @Getter
        @AllArgsConstructor
        public enum AutoStart {
            FALSE(0, "不开启自动拉流"),
            TRUE(1, "开启自动拉流");
            private Integer value;
            private String desc;
        }

        // 设备状态 on(在线),off(离线),failed(失败/锁定),notreg未注册
        @Getter
        @AllArgsConstructor
        public enum DeviceState {
            ON("on", "在线"),
            OFF("off", "离线"),
            FAILED("failed", "失败/锁定"),
            NOTREG("notreg", "未注册");
            private String value;
            private String desc;
        }


        // 视频播放协议，支持 FLV,HLS,RTMP
        @Getter
        @AllArgsConstructor
        public enum OutProtocol {
            FLV("flv", "FLV"),
            HLS("hls", "HLS"),
            RTMP("rtmp", "RTMP");
            private String value;
            private String desc;
        }


    }

    public static class VideoStream {

        // 流状态（on(在线),off(离线)）
        @Getter
        @AllArgsConstructor
        public enum StreamStatus {
            ON("on", "在线"),
            OFF("off", "离线");
            private String value;
            private String desc;
        }

        // 流是否启用（0:禁用1：启用）
        @Getter
        @AllArgsConstructor
        public enum Enabled {
            UNABLE(0, "禁用"),
            ENABLE(1, "启用");
            private Integer value;
            private String desc;
        }
    }

}
