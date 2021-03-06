package com.qatang.core.enums.converter;

import com.qatang.core.enums.YesNoStatus;
import org.springframework.core.convert.converter.Converter;

import javax.persistence.AttributeConverter;

/**
 * @author qatang
 * @since 2014-12-19 14:56
 */
public class YesNoStatusConverter implements AttributeConverter<YesNoStatus, Integer>, Converter<String, YesNoStatus> {
    @Override
    public Integer convertToDatabaseColumn(YesNoStatus yesNoStatus) {
        return yesNoStatus.getValue();
    }

    @Override
    public YesNoStatus convertToEntityAttribute(Integer integer) {
        return YesNoStatus.get(integer);
    }

    @Override
    public YesNoStatus convert(String s) {
        return YesNoStatus.get(Integer.parseInt(s));
    }
}
