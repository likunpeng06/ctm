package com.qatang.core.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author qatang
 * @since 2014-12-19 14:57
 */
public enum YesNoStatus {
    ALL(-1, "全部"),
    YES(1, "是"),
    NO(0, "否");

    private static Logger logger = LoggerFactory.getLogger(YesNoStatus.class);

    private static final Map<Integer, YesNoStatus> _MAP = new HashMap<Integer, YesNoStatus>();
    private static List<YesNoStatus> _LIST = new ArrayList<YesNoStatus>();
    private static List<YesNoStatus> _ALL_LIST = new ArrayList<YesNoStatus>();
    static {
        for(YesNoStatus yesNoStatus : YesNoStatus.values()){
            _MAP.put(yesNoStatus.getValue(), yesNoStatus);
            _ALL_LIST.add(yesNoStatus);
            if (!yesNoStatus.equals(ALL)) {
                _LIST.add(yesNoStatus);
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

    private YesNoStatus(int value, String name){
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue(){
        return value;
    }

    public static YesNoStatus get(int value){
        try{
            return _MAP.get(value);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static List<YesNoStatus> list() {
        return _LIST;
    }

    public static List<YesNoStatus> listAll() {
        return _ALL_LIST;
    }
}
