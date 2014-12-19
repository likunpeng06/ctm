package com.qatang.admin.service.user.impl;

import com.qatang.admin.entity.user.User;
import com.qatang.core.service.AbstractService;
import com.qatang.admin.dao.user.UserDao;
import com.qatang.admin.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author qatang
 * @since 2014-12-19 14:53
 */
@Service
@Transactional
public class UserServiceImpl extends AbstractService<User, Long> implements UserService {
    @Autowired
    private UserDao getUserDao() {
        return (UserDao) dao;
    }

    public User findByUsername(String username) {
        return getUserDao().findByUsername(username);
    }
}
