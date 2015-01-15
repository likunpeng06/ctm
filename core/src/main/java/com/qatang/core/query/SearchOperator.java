package com.qatang.core.query;

import java.util.Locale;

/**
 * @author qatang
 * @since 2015-01-12 19:03
 */
public enum SearchOperator {
    EQ("="), IN("in"), LIKE("like"), GT(">"), LT("<"), GTE(">="), LTE("<=");

    private String symbol;

    private SearchOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static SearchOperator fromString(String value) {
        try {
            return valueOf(value.toUpperCase());
        } catch (Exception var2) {
            return null;
        }
    }
}
