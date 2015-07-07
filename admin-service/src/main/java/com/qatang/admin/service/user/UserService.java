package com.qatang.admin.service.user;

import com.qatang.admin.entity.user.User;
import com.qatang.core.service.IService;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
public interface UserService extends IService<User, Long> {

    public User findByUsername(String username);

    public User findByEmail(String email);
}
