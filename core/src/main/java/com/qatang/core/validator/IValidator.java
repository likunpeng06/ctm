package com.qatang.core.validator;

import com.qatang.core.exception.ValidateFailedException;

/**
 * @author qatang
 * @since 2015-01-27 09:53
 */
public interface IValidator<T> {
    /**
     * 验证前端提交参数合法性
     * @param t
     * @return
     * @throws ValidateFailedException
     */
    public boolean validate(T t) throws ValidateFailedException;
}
