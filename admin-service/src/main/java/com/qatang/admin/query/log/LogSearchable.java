package com.qatang.admin.query.log;

import com.qatang.admin.entity.log.Log;
import com.qatang.core.query.AbstractSearchable;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author qatang
 * @since 2014-12-29 16:00
 */
public class LogSearchable extends AbstractSearchable<Log> {
    private Long userId;
    private String username;
    private String url;
    private String params;
    private Date beginCreatedTime;
    private Date endCreatedTime;

    @Override
    protected Predicate createPredicate(Root<Log> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (this.getUserId() != null) {
            predicateList.add(criteriaBuilder.equal(root.get("user").get("id"), this.getUserId()));
        }
        if (StringUtils.isNotEmpty(this.getUsername())) {
            predicateList.add(criteriaBuilder.like(root.get("user").get("username"), "%" + this.getUsername() + "%"));
        }
        if (StringUtils.isNotEmpty(this.getUrl())) {
            predicateList.add(criteriaBuilder.like(root.get("url"), "%" + this.getUrl() + "%"));
        }
        if (StringUtils.isNotEmpty(this.getParams())) {
            predicateList.add(criteriaBuilder.like(root.get("params"), "%" + this.getParams() + "%"));
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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
