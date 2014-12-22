package com.qatang.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qatang
 * @since 2014-12-20 13:43
 */
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final static String ERROR_MESSAGE_KEY = "errorMessage";
    protected final static String SUCCESS_MESSAGE_KEY = "successMessage";
    protected final static String FORWARD_URL = "forwardUrl";
}
