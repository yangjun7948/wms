package com.xiaoyai.common.activiti.core.constant;

/**
 * 通用正则表达式常量
 */
public interface PatternConstants {

    /**
     * 匹配 数字/字母/下划线
     */
    String CODE_PATTERN = "^\\w+$";

    /**
     * 截取中间字符串
     */
    String CHECK_BATCH_REGX = "我单位已完成了(.*?)，现报上该工";

}
