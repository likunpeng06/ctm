package com.qatang.admin.query.game;

import com.qatang.admin.entity.game.Game;
import com.qatang.core.query.AbstractSearchable;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author qatang
 * @since 2014-12-29 16:00
 */
public class GameSearchable extends AbstractSearchable<Game> {
    private String id;
    private String name;
    private Date beginCreatedTime;
    private Date endCreatedTime;

    @Override
    protected Predicate createPredicate(Root<Game> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.isNotEmpty(this.getId())) {
            Path<String> idPath = root.get("id");
            List<String> userIdList = Arrays.asList(StringUtils.split(this.getId(), ","));
            predicateList.add(idPath.in(userIdList));
        }
        if (StringUtils.isNotEmpty(this.getName())) {
            predicateList.add(criteriaBuilder.like(root.get("name"), "%" + this.getName() + "%"));
        }
        if (this.getBeginCreatedTime() != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), this.getBeginCreatedTime()));
        }
        if (this.getEndCreatedTime() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdTime"), this.getEndCreatedTime()));
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

    public Date getBeginCreatedTime() {
        return beginCreatedTime;
    }

    public void setBeginCreatedTime(Date beginCreatedTime) {
        this.beginCreatedTime = beginCreatedTime;
    }

    public Date getEndCreatedTime() {
        return endCreatedTime;
    }

    public void setEndCreatedTime(Date endCreatedTime) {
        this.endCreatedTime = endCreatedTime;
    }
}
