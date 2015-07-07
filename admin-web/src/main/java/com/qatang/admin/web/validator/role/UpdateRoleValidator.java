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
 * @since 2015-01-27 10:02
 */
@Component
public class UpdateRoleValidator extends AbstractValidator<RoleForm> {
    @Autowired
    private RoleService roleService;

    @Override
    public boolean validate(RoleForm roleForm) throws ValidateFailedException {
        logger.info("开始验证roleForm参数");
        if (roleForm == null || roleForm.getRole() == null) {
            String msg = String.format("roleForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("roleForm", msg);
        }
        if (roleForm.getRole().getId() == null) {
            String msg = String.format("更新角色，角色id为空");
            logger.error(msg);
            throw new ValidateFailedException("role.id", msg);
        }

        Role role = roleService.get(roleForm.getRole().getId());
        if (role == null) {
            String msg = String.format("所要更新的角色不存在");
            logger.error(msg);
            throw new ValidateFailedException("role.id", msg);
        }

        if (StringUtils.isEmpty(roleForm.getRole().getName())) {
            String msg = String.format("角色名称不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.name", msg);
        }

        if (StringUtils.isEmpty(roleForm.getRole().getIdentifier())) {
            String msg = String.format("角色标识符不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.identifier", msg);
        }

        Role tmpRole = roleService.findByIdentifier(roleForm.getRole().getIdentifier());
        if (tmpRole != null && !role.getIdentifier().equals(roleForm.getRole().getIdentifier())) {
            String msg = String.format("该标识符已被使用");
            logger.error(msg);
            throw new ValidateFailedException("role.identifier", msg);
        }

        if (roleForm.getRole().getValid() == null) {
            String msg = String.format("是否有效不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.valid", msg);
        }

        if (roleForm.getRole().getIsDefault() == null) {
            String msg = String.format("是否默认角色不能为空");
            logger.error(msg);
            throw new ValidateFailedException("role.isDefault", msg);
        }

        return true;
    }
}
