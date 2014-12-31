package com.qatang.core.converter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qatang
 * @since 2014-12-19 14:55
 */
public class DateConverter implements Converter<String, Date> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Date convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
