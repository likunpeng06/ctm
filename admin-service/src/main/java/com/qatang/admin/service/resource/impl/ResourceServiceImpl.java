package com.qatang.admin.service.resource.impl;

import com.qatang.admin.dao.resource.ResourceDao;
import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.service.resource.ResourceService;
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
public class ResourceServiceImpl extends AbstractService<Resource, Long> implements ResourceService {
    @Autowired
    private ResourceDao resourceDao;

    public Resource findByIdentifier(String identifier) {
        return resourceDao.findByIdentifier(identifier);
    }

}
