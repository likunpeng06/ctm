package com.qatang.admin.query.user;

import com.qatang.admin.entity.user.User;
import com.qatang.core.enums.EnableDisableStatus;
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
public class UserSearchable extends AbstractSearchable<User> {
    private String id;
    private String username;
    private String email;
    private String mobile;
    private Date beginCreatedTime;
    private Date endCreatedTime;
    private EnableDisableStatus valid;

    @Override
    protected Predicate createPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (StringUtils.isNotEmpty(this.getId())) {
            Path<String> idPath = root.get("id");
            List<String> userIdList = Arrays.asList(StringUtils.split(this.getId(), ","));
            predicateList.add(idPath.in(userIdList));
        }
        if (StringUtils.isNotEmpty(this.getUsername())) {
            predicateList.add(criteriaBuilder.like(root.get("username"), "%" + this.getUsername() + "%"));
        }
        if (StringUtils.isNotEmpty(this.getEmail())) {
            predicateList.add(criteriaBuilder.like(root.get("email"), "%" + this.getEmail() + "%"));
        }
        if (StringUtils.isNotEmpty(this.getMobile())) {
            predicateList.add(criteriaBuilder.like(root.get("mobile"), "%" + this.getMobile() + "%"));
        }
        if (this.getBeginCreatedTime() != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdTime"), this.getBeginCreatedTime()));
        }
        if (this.getEndCreatedTime() != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdTime"), this.getEndCreatedTime()));
        }
        if (this.getValid() != null && this.getValid() != EnableDisableStatus.ALL) {
            predicateList.add(criteriaBuilder.equal(root.get("valid"), this.getValid()));
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }
}
