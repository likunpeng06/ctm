package com.qatang.core.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author qatang
 * @since 2014-12-19 14:57
 */
public enum OrderDirection {
    ASC(1, "升序"),
    DESC(2, "降序");

    private static Logger logger = LoggerFactory.getLogger(OrderDirection.class);

    private static final Map<Integer, OrderDirection> _MAP = new HashMap<>();
    private static List<OrderDirection> _LIST = new ArrayList<>();
    static {
        for(OrderDirection yesNoStatus : OrderDirection.values()){
            _MAP.put(yesNoStatus.getValue(), yesNoStatus);
            _LIST.add(yesNoStatus);
        }

        synchronized (_LIST) {
            _LIST = Collections.unmodifiableList(_LIST);
        }
    }

    private int value;
    private String name;

    private OrderDirection(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static OrderDirection get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<OrderDirection> list() {
        return _LIST;
    }
}
