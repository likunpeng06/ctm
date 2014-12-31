package com.qatang.core.service;

import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.dao.IDao;
import com.qatang.core.query.Searchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.io.Serializable;

/**
 * @author qatang
 * @since 2014-12-19 14:57
 */
@Transactional
public abstract class AbstractService<T, ID extends Serializable> implements IService<T, ID> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected IDao<T, ID> dao;

    @Override
    public T save(T t) {
        return dao.save(t);
    }

    @Override
    public T update(T t) {
        return dao.save(t);
    }

    @Override
    public T get(ID id) {
        return dao.getOne(id);
    }

    @Override
    public void delete(ID id) {
        dao.delete(id);
    }

    @Override
    public Page<T> findAll(Searchable searchable) {
        if (searchable == null) {
            return dao.findAll(new PageRequest(0, GlobalConstants.DEFAULT_PAGE_SIZE));
        }
        return dao.findAll(searchable.getSpecification(), searchable.getPageable());
    }

    @Override
    public long count(Searchable searchable) {
        if (searchable == null) {
            return dao.count();
        }
        return dao.count(searchable.getSpecification());
    }
}
