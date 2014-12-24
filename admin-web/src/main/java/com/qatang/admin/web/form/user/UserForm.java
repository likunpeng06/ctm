package com.qatang.admin.web.form.user;

import com.qatang.admin.entity.user.User;
import com.qatang.core.form.AbstractForm;

import javax.validation.Valid;

/**
 * @author qatang
 * @since 2014-12-24 16:01
 */
public class UserForm extends AbstractForm {
    private static final long serialVersionUID = 2491016017662942280L;

    @Valid
    private User user;
    private String conPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }
}