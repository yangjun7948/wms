package com.xiaoyai.common.activiti.core.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.LinkedList;

/**
 * 系统服务常量类
 */
public class SystemConstant {

    // 角色
    public static class Role {
        // 项目权限类型
        @Getter
        @AllArgsConstructor
        public enum ProjectAuthEnum {
            ALL_PROJECT("0", "全部项目"),
            PART_PROJECT("1", "部分项目");
            private String value;
            private String desc;
        }

        @Getter
        @AllArgsConstructor
        public enum MenuClientTypeEnum{
            pcClient("1","PC端"),
            wechatClient("2","小程序端"),
            CRM("3","crm系统"),
            APP("4","APP端");
            private String value;
            private String desc;
        }

        @Getter
        @AllArgsConstructor
        public enum PlayTypeEnum {
            playing("1","直播"),
            videotape("2","录像"),
            download("4","下载");
            private String value;
            private String desc;
        }

        @Getter
        @AllArgsConstructor
        public enum VideoType{
            camera("1","摄像头");
            private String value;
            private String desc;
        }


        // 数据权限类型
        @Getter
        @AllArgsConstructor
        public enum DsTypeEnum {
            ALL("0", "全部"),
            CUSTOM("1", "自定义"),
            SELF_AND_CHILD("2", "本级及子集"),
            SELF("3", "本级");
            private String value;
            private String desc;

            public static String descOfValue(String value) {
                for (DsTypeEnum orderStatusEnum : values()) {
                    if (orderStatusEnum.getValue().equals(value)) {
                        return orderStatusEnum.getDesc();
                    }
                }
                return "其他";
            }
        }
    }

    // 用户
    public static class User {
        // 新建用户时的默认密码
        public static String DEFAULT_PASSWORD = "123456";

        // 用户是否需要重置密码
        @Getter
        @AllArgsConstructor
        public enum ResetPasswordEnum {
            NEED_RESET("Y", "需要重置"),
            NOT_NEED_RESET("N", "不需要重置");
            private String value;
            private String desc;
        }

        // 账号状态
        @Getter
        @AllArgsConstructor
        public enum AccountStatusEnum {
            LOCK_FLAG_NORMAL("0", "正常"),
            LOCK_FLAG_LOCK("1", "锁定"),
            LOCK_FLAG_NOT_LOGIN("2", "不可登录,需要修改用户名");
            private String value;
            private String desc;

            public static String descOfValue(String value) {
                for (AccountStatusEnum orderStatusEnum : values()) {
                    if (orderStatusEnum.getValue().equals(value)) {
                        return orderStatusEnum.getDesc();
                    }
                }
                return "其他";
            }
        }

    }

    // 机构
    public static class Org {
        // 项目权限类型
        @Getter
        @AllArgsConstructor
        public enum OrgTypeEnum {
            KAN_CHA("1", "勘察企业"),
            SHE_JI("2", "设计企业"),
            SHI_GONG("3", "施工企业"),
            JIAN_LI("4", "监理企业"),
            CHENG_BAO("5", "工程总承包单位"),
            ZHI_JIAN("6", "质量检测机构"),
            JIAN_SHE("7", "建设单位"),
            DAI_JIAN("8", "代建单位"),
            OTHER("99", "其他");
            private String value;
            private String desc;
        }
    }
}
