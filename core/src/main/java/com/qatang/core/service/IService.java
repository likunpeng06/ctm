package com.qatang.core.service;

import com.qatang.core.query.Searchable;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @author qatang
 * @since 2014-12-19 14:57
 */
public interface IService<T, ID extends Serializable> {
    public T save(T t);

    public T update(T t);

    public T get(ID id);

    public void delete(ID id);

    List<T> findAll();

    /**
     * 根据条件查询所有
     * 条件 + 分页 + 排序
     * @param searchable
     * @return
     */
    public Page<T> find(Searchable searchable);

    /**
     * 根据条件统计所有记录数
     * @param searchable
     * @return
     */
    public long count(Searchable searchable);
}
