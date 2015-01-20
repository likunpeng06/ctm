package com.qatang.admin.service.role;

import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.core.service.IService;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
public interface RoleService extends IService<Role, Long> {

    public Role findByIdentifier(String identifier);

}
