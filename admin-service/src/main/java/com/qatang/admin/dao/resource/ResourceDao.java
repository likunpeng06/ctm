package com.qatang.admin.dao.resource;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.core.dao.IDao;

import java.util.List;

/**
 * @author qatang
 * @since 2014-12-19 14:46
 */
public interface ResourceDao extends IDao<Resource, Long> {
    public Resource findByIdentifier(String identifier);

    public List<Resource> findByParent_IdOrderByPriorityDesc(Long parentId);
}
