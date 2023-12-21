/*
 *
 *      Copyright (c) 2018-2025, tony All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the mingtian-group.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: tony (117332652@qq.com)
 *
 */

package com.xiaoyai.common.activiti.core.constant;

/**
 * @author tony
 * @date 2017/10/29
 */
public interface CommonConstants {
    /**
     * header 中租户ID
     */
    String TENANT_ID = "TENANT_ID";
    /**
     * 删除
     */
    String STATUS_DEL = "1";
    /**
     * 正常
     */
    String STATUS_NORMAL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 编码
     */
    String UTF8 = "UTF-8";

    /**
     * 前端工程名
     */
    String FRONT_END_PROJECT = "zykj-ui";

    /**
     * 后端工程名
     */
    String BACK_END_PROJECT = "zykj";

    /**
     * 验证码前缀
     */
    String DEFAULT_CODE_KEY = "DEFAULT_CODE_KEY_";

    /**
     * 公共参数
     */
    String PIG_PUBLIC_PARAM_KEY = "PIG_PUBLIC_PARAM_KEY";

    /**
     * 成功标记
     */
    Integer SUCCESS = 0;
    /**
     * 失败标记
     */
    Integer FAIL = 1;

    /**
     * 默认存储bucket
     */
    String BUCKET_NAME = "zykj";

    // 文件服务相关常量
    interface File {
        // 文件服务获取下载链接url的时效，单位是天
        // 1天
        Integer SHARE_URL_EXPIRES_MIN = 1 * 3600 * 24;
        // 7天，这是默认最大值
        Integer SHARE_URL_EXPIRES_MAX = 7 * 3600 * 24;
    }

    // classpath 前缀
    String CLASSPATH_PREFIX = "classpath:";


}
