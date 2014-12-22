package com.qatang.core.dao;

import com.qatang.core.query.Searchable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
        String hql = "from User u where 1=1";
        String countHql = "select count(u) from User u";
        Query query = entityManager.createQuery(hql);
        Query countQuery = entityManager.createQuery(countHql);
        long count = (Long) countQuery.getSingleResult();

        return new PageImpl<T>(query.getResultList(), new PageRequest(1, 200), count);
    }

    @Override
    public long count(Searchable searchable) {
//        return repositoryHelper.count(countAllQL, searchable, searchCallback);
        return 0;
    }
}
