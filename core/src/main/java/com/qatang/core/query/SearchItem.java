package com.qatang.core.query;

/**
 * @author qatang
 * @since 2015-01-12 19:02
 */
public class SearchItem<T extends Comparable> {
    private String key;

    private SearchOperator operator;

    private T value;

    public SearchItem(String key, SearchOperator operator, T value) {
        this.key = key;
        this.operator = operator;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public SearchOperator getOperator() {
        return operator;
    }

    public void setOperator(SearchOperator operator) {
        this.operator = operator;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
