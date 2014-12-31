package com.qatang.core.form;

import com.qatang.core.constants.GlobalConstants;
import com.qatang.core.enums.OrderDirection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author qatang
 * @since 2014-12-24 16:00
 */
public class AbstractForm implements IForm {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Integer currentPage = 1;
    private Integer pageSize = GlobalConstants.DEFAULT_PAGE_SIZE;
    private Integer totalPages;
    private Long totalSize;

    private String orderField;
    private OrderDirection orderDirection = OrderDirection.DESC;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public OrderDirection getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(OrderDirection orderDirection) {
        this.orderDirection = orderDirection;
    }
}
