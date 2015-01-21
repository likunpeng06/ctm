package com.qatang.admin.service.resource;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.core.service.IService;

import java.util.List;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
public interface ResourceService extends IService<Resource, Long> {

    public Resource findByIdentifier(String identifier);

    public List<Resource> findByParentId(Long parentId);

}
