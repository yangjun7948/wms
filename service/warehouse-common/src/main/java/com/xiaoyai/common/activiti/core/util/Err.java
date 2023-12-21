package com.xiaoyai.common.activiti.core.util;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author dingj
 * @date 2020/1/10 16:29
 */
@Data
@ApiModel(description = "异常对象返回结果实体类")
@NoArgsConstructor
public class Err implements Serializable {

    private Boolean needCode;

    public Err(Boolean needCode) {
        this.needCode = needCode;
    }
}
