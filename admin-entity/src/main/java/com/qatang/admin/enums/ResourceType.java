package com.qatang.admin.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author qatang
 * @since 2014-12-19 14:57
 */
public enum ResourceType {
    ALL(-1, "全部"),
//    DEFAULT(0, "默认"),
    MENU(1, "菜单"),
    FUNCTION(2, "功能");

    private static Logger logger = LoggerFactory.getLogger(ResourceType.class);

    private static final Map<Integer, ResourceType> _MAP = new HashMap<>();
    private static List<ResourceType> _LIST = new ArrayList<>();
    private static List<ResourceType> _ALL_LIST = new ArrayList<>();
    static {
        for(ResourceType resourceType : ResourceType.values()){
            _MAP.put(resourceType.getValue(), resourceType);
            _ALL_LIST.add(resourceType);
            if (!resourceType.equals(ALL)) {
                _LIST.add(resourceType);
            }
        }

        synchronized (_LIST) {
            _LIST = Collections.unmodifiableList(_LIST);
        }
        synchronized (_ALL_LIST) {
            _ALL_LIST = Collections.unmodifiableList(_ALL_LIST);
        }
    }

    private int value;
    private String name;

    private ResourceType(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static ResourceType get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<ResourceType> list() {
        return _LIST;
    }

    public static List<ResourceType> listAll() {
        return _ALL_LIST;
    }
}
