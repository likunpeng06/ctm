package com.qatang.core.query;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

/**
 * 通用查询条件接口
 * @author qatang
 * @since 2014-12-19 14:57
 */
public interface Searchable<T> {
    public Pageable getPageable();

    public Sort getSortable();

    public Specification<T> getSpecification();
}
