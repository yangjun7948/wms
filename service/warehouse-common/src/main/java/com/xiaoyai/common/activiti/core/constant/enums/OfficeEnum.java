package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OA服务枚举常量
 */
public class OfficeEnum {

    @Getter
    @AllArgsConstructor
    public enum FileExamState {
        NOT_EXAM("1", "未审核"),
        RETURN("2", "未通过"),
        OFFER("3", "已通过");
        private String value;
        private String desc;
    }

    // 文件审核类型 [1-上传;2-删除;]
    @Getter
    @AllArgsConstructor
    public enum FileExamType {
        UPLOAD("1", "上传"),
        DELETE("2", "删除");
        private String value;
        private String desc;
    }

    /**
     * 权限
     */
    @Getter
    @AllArgsConstructor
    public enum FilePermission {
        File_EXAM("oa_skydrive_exam", "文件审核权限");
        private String value;
        private String desc;
    }

}
