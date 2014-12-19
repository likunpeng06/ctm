package com.qatang.core.dao;

import com.qatang.core.query.Searchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import java.io.Serializable;

/**
 * @author qatang
 * @since 2014-12-19 14:55
 */
public abstract class AbstractDao<T, ID extends Serializable> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Page<T> findAll(Searchable searchable) {
        return null;
    }

    public long count(Searchable searchable) {
        return 0;
    }
}
