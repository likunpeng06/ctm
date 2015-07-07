package com.qatang.admin.web.form.role;

import com.qatang.admin.entity.role.Role;
import com.qatang.core.form.AbstractForm;

import javax.validation.Valid;

/**
 * @author qatang
 * @since 2014-12-24 16:01
 */
public class RoleForm extends AbstractForm {
    private static final long serialVersionUID = 5125173118172011269L;

    @Valid
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
