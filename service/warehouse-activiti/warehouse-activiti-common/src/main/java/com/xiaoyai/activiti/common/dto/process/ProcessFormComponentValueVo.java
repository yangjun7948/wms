package com.xiaoyai.activiti.common.dto.process;

/**
 * @author xieyu
 * @date 2020-12-01
 */
public class ProcessFormComponentValueVo {
    /**
     * 附加值
     * */
    private String extValue;
    /**
     * 参数名
     * */
    private String name;
    /**
     * 参数值
     * */
    private String value;

    public String getExtValue() {
        return extValue;
    }

    public void setExtValue(String extValue) {
        this.extValue = extValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
