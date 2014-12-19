package com.qatang.core.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author qatang
 * @since 2014-12-19 14:57
 */
public enum EnableDisableStatus {
    ALL(-1, "全部"),
    YES(1, "启用"),
    NO(0, "禁用");

    private static Logger logger = LoggerFactory.getLogger(EnableDisableStatus.class);

    private static final Map<Integer, EnableDisableStatus> _MAP = new HashMap<Integer, EnableDisableStatus>();
    private static List<EnableDisableStatus> _LIST = new ArrayList<EnableDisableStatus>();
    private static List<EnableDisableStatus> _ALL_LIST = new ArrayList<EnableDisableStatus>();
    static {
        for(EnableDisableStatus enableDisableStatus : EnableDisableStatus.values()){
            _MAP.put(enableDisableStatus.getValue(), enableDisableStatus);
            _ALL_LIST.add(enableDisableStatus);
            if (!enableDisableStatus.equals(ALL)) {
                _LIST.add(enableDisableStatus);
            }
        }

        synchronized (_LIST) {
            _LIST = Collections.unmodifiableList(_LIST);
        }
        synchronized (_LIST) {
            _ALL_LIST = Collections.unmodifiableList(_ALL_LIST);
        }
    }

    private int value;
    private String name;

    private EnableDisableStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static EnableDisableStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<EnableDisableStatus> list() {
        return _LIST;
    }

    public static List<EnableDisableStatus> listAll() {
        return _ALL_LIST;
    }
}
