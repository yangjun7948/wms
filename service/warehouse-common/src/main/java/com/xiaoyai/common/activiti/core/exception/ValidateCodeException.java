package com.xiaoyai.common.activiti.core.exception;

/**
 * @author tony
 * @date 2018年06月22日16:22:15
 */
public class ValidateCodeException extends Exception {
    private static final long serialVersionUID = -7285211528095468156L;

    /**
     * 是否需要输入验证码
     */
    private boolean needCode = false;

    public ValidateCodeException() {
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }

    public ValidateCodeException(String msg, boolean needCode) {
        super(msg);
        this.needCode = needCode;
    }

    public boolean isNeedCode() {
        return needCode;
    }

    public void setNeedCode(boolean needCode) {
        this.needCode = needCode;
    }
}
