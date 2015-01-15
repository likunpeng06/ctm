package com.qatang.admin.web.controller.annotation.resolver;

import com.qatang.admin.web.controller.annotation.PageableDefaults;
import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.query.SearchFilter;
import com.qatang.core.query.SearchItem;
import com.qatang.core.query.SearchOperator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.ServletRequest;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * @author qatang
 * @since 2015-01-12 11:01
 */
public class SearchFilterMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SearchFilter.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Map<String, Object> searchParams = getParametersStartingWith((ServletRequest)webRequest.getNativeRequest(), GlobalConstants.SEARCH_PREFIX);

        Pageable pageable = getPageable(parameter, webRequest);

        List<SearchItem> searchItemList = generateSearchItem(parameter, searchParams);

        SearchFilter searchFilter = new SearchFilter(searchParams, pageable);
        searchFilter.setSearchItemList(searchItemList);

        return searchFilter;
    }

    private List<SearchItem> generateSearchItem(MethodParameter parameter, Map<String, Object> searchParams) {
        List<SearchItem> searchItemList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : searchParams.entrySet()) {
            String key_operator = entry.getKey();
            Object value = entry.getValue();

            if (value.getClass().isArray()) {
                String[] valueStrArray = (String[]) value;
            } else {
                String valueStr = (String) value;
                int index = key_operator.lastIndexOf("_");
                String key = key_operator.substring(0, index);
                String operator = key_operator.substring(index + 1);


                SearchItem<String> searchItem = new SearchItem<>(key, SearchOperator.fromString(operator), valueStr);
                searchItemList.add(searchItem);
            }
        }
        return searchItemList;
    }

    /**
     * 取得带相同前缀的Request Parameters.
     * 返回的结果的Parameter名已去除前缀.
     */
    private Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix) {
        Validate.notNull(request, "Request must not be null");
        Enumeration paramNames = request.getParameterNames();
        Map<String, Object> params = new TreeMap<>();
        if (prefix == null) {
            prefix = "";
        }
        while ((paramNames != null) && paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            if (StringUtils.isEmpty(prefix) || paramName.startsWith(prefix)) {
                String unprefixed = paramName.substring(prefix.length());
                String[] values = request.getParameterValues(paramName);
                if (values == null || values.length == 0) {
                    // Do nothing, no values found at all.
                } else if (values.length > 1) {
                    params.put(unprefixed, values);
                } else {
                    if (StringUtils.isNotEmpty(values[0])) {
                        params.put(unprefixed, values[0]);
                    }
                }
            }
        }
        return params;
    }

    private Pageable getPageable(MethodParameter parameter, NativeWebRequest webRequest) {
        Map<String, Object> pageParams = getParametersStartingWith((ServletRequest)webRequest.getNativeRequest(), GlobalConstants.PAGE_PREFIX);
        Map<String, Object> sortParams = getParametersStartingWith((ServletRequest)webRequest.getNativeRequest(), GlobalConstants.SORT_PREFIX);

        PageableDefaults pageableDefaults = getPageDefaultsAnnotation(parameter);

        Pageable defaultPageRequest = getDefaultPageRequest(pageableDefaults);

        Sort sort = getSort(sortParams, defaultPageRequest);

        if (pageParams == null || pageParams.size() == 0) {
            return new PageRequest(pageableDefaults.pageNumber() - 1, pageableDefaults.pageSize(), sort);
        }

        Integer pageNumber = (Integer)pageParams.get(GlobalConstants.PAGE_NUMBER);
        if (pageNumber == null) {
            pageNumber = defaultPageRequest.getPageNumber();
        }

        Integer pageSize = (Integer)pageParams.get(GlobalConstants.PAGE_SIZE);
        if (pageSize == null) {
            pageSize = defaultPageRequest.getPageSize();
        }

        Pageable pageable = new PageRequest(pageNumber - 1, pageSize, sort);
        return pageable;
    }

    private Sort getSort(Map<String, Object> sortParams, Pageable defaultPageRequest) {
        if (sortParams == null || sortParams.size() == 0) {
            return defaultPageRequest.getSort();
        }
        Sort sort = null;
        for (String name : sortParams.keySet()) {
            Sort newSort = new Sort(Sort.Direction.fromString((String)sortParams.get(name)), name);
            if (sort == null) {
                sort = newSort;
            } else {
                sort = sort.and(newSort);
            }
        }

        if (sort == null) {
            return defaultPageRequest.getSort();
        }

        return sort;
    }

    private Pageable getDefaultPageRequest(PageableDefaults pageableDefaults) {
        if (pageableDefaults != null) {
            int pageNumber = pageableDefaults.pageNumber();
            int pageSize = pageableDefaults.pageSize();

            Sort sort = null;

            String[] sortStrArray = pageableDefaults.sort();
            for (String sortStr : sortStrArray) {
                String[] sortStrPair = sortStr.split("=");
                Sort newSort = new Sort(Sort.Direction.fromString(sortStrPair[1]), sortStrPair[0]);
                if (sort == null) {
                    sort = newSort;
                } else {
                    sort = sort.and(newSort);
                }
            }
            return new PageRequest(pageNumber, pageSize, sort);
        }

        return new PageRequest(0, GlobalConstants.DEFAULT_PAGE_SIZE);
    }

    private PageableDefaults getPageDefaultsAnnotation(MethodParameter parameter) {
        //首先从参数上找
        PageableDefaults pageableDefaults = parameter.getParameterAnnotation(PageableDefaults.class);
        //找不到从方法上找
        if (pageableDefaults == null) {
            pageableDefaults = parameter.getMethodAnnotation(PageableDefaults.class);
        }
        return pageableDefaults;
    }
}
