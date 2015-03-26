package com.qatang.admin.service.log.impl;

import com.qatang.admin.dao.log.LogDao;
import com.qatang.admin.entity.log.Log;
import com.qatang.admin.service.log.LogService;
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
public class LogServiceImpl extends AbstractService<Log, Long> implements LogService {
    @Autowired
    private LogDao logDao;

}
