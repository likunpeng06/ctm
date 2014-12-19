package com.qatang.core.dao;

import com.qatang.core.query.Searchable;
import org.springframework.data.domain.Page;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

/**
 * @author qatang
 * @since 2014-12-19 14:55
 */
public class BaseDao<T, ID extends Serializable> extends AbstractDao<T, ID> {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<T> findAll(Searchable searchable) {
//        List<T> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
//        long total = searchable.hasPageable() ? count(searchable) : list.size();
//        return new PageImpl<T>(
//                list,
//                searchable.getPage(),
//                total
//        );
        return null;
    }

    @Override
    public long count(Searchable searchable) {
//        return repositoryHelper.count(countAllQL, searchable, searchCallback);
        return 0;
    }
}
