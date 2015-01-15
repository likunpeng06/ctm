package com.qatang.core.query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author qatang
 * @since 2015-01-12 16:12
 */
public class SearchFilter {
    /**
     * 原始查询参数
     */
    private Map<String, Object> searchParams;

    /**
     * 转换后查询参数
     */
    private List<SearchItem> searchItemList;

    /**
     * 翻页信息
     */
    private Pageable pageable;

    /**
     * 排序信息
     */
    private Sort sort;

    public SearchFilter() {

    }

    public SearchFilter(Map<String, Object> searchParams) {
        this.searchParams = searchParams;
    }

    public SearchFilter(Map<String, Object> searchParams, Pageable pageable) {
        this.searchParams = searchParams;
        this.pageable = pageable;
    }

    public <T> Specification<T> getSpecification() {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (searchItemList != null && searchItemList.size() > 0) {

                    List<Predicate> predicates = new ArrayList<>();
                    for (SearchItem searchItem : searchItemList) {
                        if (searchItem.getValue() == null) {
                            continue;
                        }

                        switch (searchItem.getOperator()) {
                            case EQ:
                                predicates.add(criteriaBuilder.equal(root.get(searchItem.getKey()), searchItem.getValue()));
                                break;
                            case IN:
                                List<String> inStrList = Arrays.asList(StringUtils.split((String)searchItem.getValue(), ","));
                                predicates.add(criteriaBuilder.in(root.get(searchItem.getKey()).in(inStrList)));
                                break;
                            case LIKE:
                                predicates.add(criteriaBuilder.like(root.get(searchItem.getKey()), "%" + searchItem.getValue() + "%"));
                                break;
                            case GT:
                                predicates.add(criteriaBuilder.greaterThan(root.get(searchItem.getKey()), searchItem.getValue()));
                                break;
                            case LT:
                                predicates.add(criteriaBuilder.lessThan(root.get(searchItem.getKey()), searchItem.getValue()));
                                break;
                            case GTE:
                                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(searchItem.getKey()), searchItem.getValue()));
                                break;
                            case LTE:
                                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(searchItem.getKey()), searchItem.getValue()));
                                break;
                        }
                    }

                    // 将所有条件用 and 联合起来
                    if (!predicates.isEmpty()) {
                        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
                    }
                }

                return criteriaBuilder.conjunction();
            }
        };
    }

    public Map<String, Object> getSearchParams() {
        return searchParams;
    }

    public void setSearchParams(Map<String, Object> searchParams) {
        this.searchParams = searchParams;
    }

    public List<SearchItem> getSearchItemList() {
        return searchItemList;
    }

    public void setSearchItemList(List<SearchItem> searchItemList) {
        this.searchItemList = searchItemList;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }
}
