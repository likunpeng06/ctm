package com.qatang.admin.query.role;

import com.qatang.admin.entity.role.Role;
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
public class RoleSearchable extends AbstractSearchable<Role> {
    private String id;
    private String identifier;

    @Override
    protected Predicate createPredicate(Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.isNotEmpty(this.getId())) {
            Path<String> idPath = root.get("id");
            List<String> roleIdList = Arrays.asList(StringUtils.split(this.getId(), ","));
            predicateList.add(idPath.in(roleIdList));
        }
        if (StringUtils.isNotEmpty(this.getIdentifier())) {
            predicateList.add(criteriaBuilder.like(root.get("identifier"), "%" + this.getIdentifier() + "%"));
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
