package com.xiaoyai.common.activiti.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author junyang
 * @date 2021-03-26
 */
@AllArgsConstructor
@Getter
public enum SignedStatusEnum {

    FIRST_TIME_TO_SIGN("0", "首次签订"),
    RENEW("1", "续签"),
    CHANGE_SUBJECT_OF_CONTRACT("2", "变更合同单位主体");

    private final String type;
    private final String description;

}
