package com.qatang.core.exception;

/**
 * @author qatang
 * @since 2015-01-27 09:52
 */
public class ValidateFailedException extends Exception {
    private static final long serialVersionUID = -7312037489137281571L;

    protected final static String MSG = "参数验证失败异常";

    private String field;

    public ValidateFailedException() {
        super(MSG);
    }

    public ValidateFailedException(String field, String message) {
        super(MSG + "：" + message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
