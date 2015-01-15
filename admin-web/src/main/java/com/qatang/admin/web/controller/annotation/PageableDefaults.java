package com.qatang.admin.web.controller.annotation;

import com.qatang.core.constants.GlobalConstants;

import java.lang.annotation.*;

/**
 * @author qatang
 * @since 2015-01-12 10:58
 */
@Target({ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PageableDefaults {
    /**
     * 当前页
     * @return
     */
    int pageNumber() default 1;

    /**
     * 每页记录条数
     * @return
     */
    int pageSize() default GlobalConstants.DEFAULT_PAGE_SIZE;

    /**
     * 默认的排序 格式为{"a=desc, b=asc"}
     */
    String[] sort() default {};
}
