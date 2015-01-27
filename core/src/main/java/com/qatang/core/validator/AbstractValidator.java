package com.qatang.core.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qatang
 * @since 2015-01-27 09:55
 */
public abstract class AbstractValidator<T> implements IValidator<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
}
