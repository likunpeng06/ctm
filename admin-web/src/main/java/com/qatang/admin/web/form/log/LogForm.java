package com.qatang.admin.web.form.log;

import com.qatang.admin.entity.log.Log;
import com.qatang.core.form.AbstractForm;

import javax.validation.Valid;

/**
 * @author qatang
 * @since 2014-12-24 16:01
 */
public class LogForm extends AbstractForm {
    private static final long serialVersionUID = -8545515739642886075L;

    @Valid
    private Log log;

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
