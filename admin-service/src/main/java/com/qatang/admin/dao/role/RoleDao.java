package com.qatang.admin.dao.role;

import com.qatang.admin.entity.role.Role;
import com.qatang.admin.entity.user.User;
import com.qatang.core.dao.IDao;

/**
 * @author qatang
 * @since 2014-12-19 14:46
 */
public interface RoleDao extends IDao<Role, Long> {
    public Role findByIdentifier(String identifier);

}
