package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author junyang
 * @date 2021-03-26
 */
@AllArgsConstructor
@Getter
public enum StaffContractStatusEnum {

    LOSE_EFFICACY("0", "已失效"),
    ACTIVE("1", "生效中");

    private final String type;
    private final String description;

}
