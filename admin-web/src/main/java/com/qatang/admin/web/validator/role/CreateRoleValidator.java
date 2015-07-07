package com.qatang.admin.web.validator.role;

import com.qatang.admin.entity.role.Role;
import com.qatang.admin.service.role.RoleService;
import com.qatang.admin.web.form.role.RoleForm;
import com.qatang.core.exception.ValidateFailedException;
import com.qatang.core.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qatang
 * @since 2015-01-27 11:07
 */
@Component
public class CreateRoleValidator extends AbstractValidator<RoleForm> {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean validate(RoleForm roleForm) throws ValidateFailedException {
        if (StringUtils.isEmpty(roleForm.getRole().getName())) {
            String msg = String.format("角色名称不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.name", msg);
        }

        if (StringUtils.isEmpty(roleForm.getRole().getIdentifier())) {
            String msg = String.format("标识符不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.identifier", msg);
        }

        Role conRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (conRole != null) {
            String msg = String.format("标识符已被使用");
            logger.error(msg);
            throw new ValidateFailedException("role.identifier", msg);
        }

        if (roleForm.getRole().getIsDefault() == null) {
            String msg = String.format("是否默认角色不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.isDefault", msg);
        }

        if (roleForm.getRole().getValid() == null) {
            String msg = String.format("是否有效必须选");
            logger.error(msg);
            throw new ValidateFailedException("role.valid", msg);
        }
        return true;
    }
}
