package com.qatang.admin.dao.user;

import com.qatang.admin.entity.user.User;
import com.qatang.core.dao.IDao;

/**
 * @author qatang
 * @since 2014-12-19 14:46
 */
public interface UserDao extends IDao<User, Long> {
    public User findByUsername(String username);
}
