package com.qatang.core.dao;

import com.qatang.core.query.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * @author qatang
 * @since 2014-12-19 14:55
 */
public interface IDao<T, ID extends Serializable> extends JpaRepository<T, ID> {
    /**
     * 根据条件查询所有
     * 条件 + 分页 + 排序
     * @param searchable
     * @return
     */
    public Page<T> findAll(Searchable searchable);


    /**
     * 根据条件统计所有记录数
     * @param searchable
     * @return
     */
    public long count(Searchable searchable);
}
