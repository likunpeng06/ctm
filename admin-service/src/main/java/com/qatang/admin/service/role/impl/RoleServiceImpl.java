package com.qatang.admin.service.role.impl;

import com.qatang.admin.dao.role.RoleDao;
import com.qatang.admin.entity.role.Role;
import com.qatang.admin.service.role.RoleService;
import com.qatang.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author qatang
 * @since 2014-12-19 14:53
 */
@Service
@Transactional
public class RoleServiceImpl extends AbstractService<Role, Long> implements RoleService {
    @Autowired
    private RoleDao roleDao;

    public Role findByIdentifier(String identifier) {
        return roleDao.findByIdentifier(identifier);
    }

}
