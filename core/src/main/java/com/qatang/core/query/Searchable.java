package com.qatang.core.query;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 通用查询条件接口
 * @author qatang
 * @since 2014-12-19 14:57
 */
public interface Searchable {
    public Pageable getPage();

    public Sort getSort();
}
