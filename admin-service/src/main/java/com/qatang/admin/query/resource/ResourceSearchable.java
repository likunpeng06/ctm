package com.qatang.admin.query.resource;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.core.query.AbstractSearchable;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qatang
 * @since 2014-12-29 16:00
 */
public class ResourceSearchable extends AbstractSearchable<Resource> {
    private String id;
    private String name;

    @Override
    protected Predicate createPredicate(Root<Resource> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.isNotEmpty(this.getId())) {
            Path<String> idPath = root.get("id");
            List<String> resourceIdList = Arrays.asList(StringUtils.split(this.getId(), ","));
            predicateList.add(idPath.in(resourceIdList));
        }
        if (StringUtils.isNotEmpty(this.getName())) {
            predicateList.add(criteriaBuilder.like(root.get("name"), "%" + this.getName() + "%"));
        }

        criteriaQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
